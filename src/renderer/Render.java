package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.sqrt;
import static java.lang.StrictMath.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Render represent the rendering of the scene into a jpg file picture
 * the class rendering the scene to jpg file through image writer.
 * The purpose of this class is to render properly the scene of 3d geometries to a colored matrix of the image
 */
public class Render {
    /**
     * scene object from class Scene for rendering
     */
    private Scene _scene;
    /**
     * image writer object for writing the pixels after rendering into a file
     */
    private ImageWriter _imageWriter;
    /**
     * The maximum value of the tree height (number of intersections) for reflection/refraction calculations for a geometry the camera sees.
     * Stop condition for transparency:
     * how far we consider to calculate the ray's intersections with
     * other geometries which are behind original geometry.
     * Stop condition for refraction:
     * how far we consider to calculate the intersections of the rays
     * which created by refractions in the original geometric body.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * the minimum value of transparency/refraction to be considered if the calculation
     * for reflection/refraction is necessary in the current geometry.
     */
    private static final double MIN_CALC_COLOR_K = 0.01;

    /**
     * number of threads for the rendering method
     */
    private int _threads = 1;
    /**
     * number of spare threads for the rendering method
     */
    private final int SPARE_THREADS = 2;
    /**
     * boolean value of active debugging the percentage in the process
     */
    private boolean _print = false;

    /**
     * number of rays in any light,
     * the more rays in light with large radius the more quality of soft shading
     */
    private int _numOfRaysInLight = 1;


    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            //if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.println( percents + "%");
            if (percents >= 0)
                return true;
            //if (Render.this._print) System.out.println( 100 + "%");
            return false;
        }
    }


    // ***************** Constructors ********************** //

    /**
     * Constructor of class Render gets two inputs by them it represented
     *
     * @param imageWriter for size and resolution of picture,
     *                    and the tool which transform camera's view plane into real pixels
     * @param scene       for the scene, i.g. camera direction and geometries placed
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }


    // ***************** Operations ******************** //

    /**
     * Method of rendering scene to image.
     * Considered: Image size and resolution,
     * Position the camera with respect to the 3D geometries,
     * The background color of the scene and the ambient color of the objects.
     */
    public void renderImage() {

        Camera camera = _scene.getCamera();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double height = _imageWriter.getHeight();
        double width = _imageWriter.getWidth();
        double distance = _scene.getDistance();

        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    List<Ray> beamRays =
                            camera.constructRaysThroughPixel(nX, nY, pixel.col, pixel.row, distance, width, height);
                    _imageWriter.writePixel(pixel.col, pixel.row, calcColor(beamRays).getColor());
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (_print) System.out.print("\rfinished\n");
    }


    /**
     * Basic method for a basic calculation of color in a geometry cause of single ray which in front of the camera.
     * this first step will activate the main calcColor method, and could lead to recursive calls by that.
     * The ambient light of the geometry should not be affected by anything
     * related to refraction or reflection with other geometries. so we will add it once.
     *
     * @param geopoint the point we want to print
     * @param inRay    the ray of the camera in this case
     * @return the color of chosen point
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1d).add(_scene.getAmbientLight().getIntensity());
    }

    /**
     * Calculating single pixel color in cause of single ray intersection
     * Method designed to take care of the object color under the definition we defined in the graphic scene
     * The calculation for point color is according to Phong Reflectance Model.
     * which means that we are summing lights on one point, for each light we sum the three known values for reflection
     * (diffusion, specular and shininess)
     * which will give us the right pixel of color for the current light.
     *
     * @param geopoint the point we want to print
     * @param inRay    the ray we are at this call talk about. could be camera's rays, but also reflected or refracted rays.
     * @param level    the current level of the calculation for the color in the same point of the original geometry.
     *                 which means how many steps of ray intersections we've done for the current point
     * @param k        the current value of intensity of the intersection to the color of point
     * @return the color of chosen point
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay, int level, double k) {
        // first, check if the height of the recursive tree is too high
        if (level == 0) return Color.BLACK;

        // Local Effects:

        // direction of camera to the intersection point:
        Vector v = geopoint.point.subtract(_scene.getCamera().getP0()).normalize();
        // Vector normal of the object in the point:
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        // Material of the same object:
        Material material = geopoint.geometry.getMaterial();
        // values of reflection:
        int nShininess = material.getNShininess();
        double kd = material.getKD();
        double ks = material.getKS();

        Color color;
        color = geopoint.geometry.getEmission(); // add intensity of emission light
        // we will consider summing up all lights reflections on the intersection point:
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(geopoint.point); // direction of the chosen light source to the point p
            // 1. Fixing wrong illumination:
            // (l∙n and v∙n must be with same sign)
            // if the illumination point is on one side of the surface and the viewpoint on the other,
            // (including if the direction of viewpoint orthogonal to the normal's surface or to the light's vector)
            // there won't be any reflections from the light towards the camera (no diffusion nor specular)
            if (l.dotProduct(n) * v.dotProduct(n) > 0) {
                double ktr = transparency(lightSource, l, n, geopoint); // transparency of light in the point,
                // The higher it is, the less specular and diffusion affect the pixel,
                // and thus in the image it will manifest as a shadow.
                // 2. Adding the Light Source Effect to Transparency Provided that in the next recursive call
                // we won't reach a too small value of transparency
                if ((ktr * k) > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr);
                    // Also in this way, we multiply the value of transparency in all coefficients
                    // (Ii*ktr)(d+s) = Ii(d*ktr + s*ktr)
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        // Global Effects:

        // Recursive call for reflected ray:
        // kr is the value of the reflection in the geometry (0 < kr < 1)
        double kr = material.getKR(), kkr = k * kr;

        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            //else
            //    color = color.add(_scene.getBackground()).scale(kr);
        }

        // Recursive call for a refracted ray:
        // kt is the value of the refraction in the geometry (0 < kt < 1)
        double kt = material.getKT(), kkt = k * kt;

        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geopoint.point, inRay);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            //else
            //    color = color.add(_scene.getBackground().scale(kt));
        }
        return color;
    }

    /**
     * Method for calculating average color of single pixel.
     * amount of rays come belong to same single pixel intersects geometries (or not) and receive a color.
     * the average color is the color of pixel.
     * this method get a list of rays and find the closest intersection, gets it's color for each one,
     * and calculate the average color.
     *
     * @param rays list of rays belong to same pixel
     * @return the average color of the pixel
     */
    private Color calcColor(List<Ray> rays) {
        Color background = _scene.getBackground();
        Color color = Color.BLACK; // average color

        for (Ray ray : rays) {
            GeoPoint closestPoint = findClosestIntersection(ray); // closest intersection point to camera
            if (closestPoint == null)   // if there's no intersection then gets the background color
                color = color.add(background);
            else // calc color for each intersection point
                color = color.add(calcColor(closestPoint, ray));
        }
        return color.scale(1d / rays.size()); // the average
    }


    /**
     * find the closest point from camera to some 3D object with the current ray,
     * which could be represented by iteration of pixel.
     *
     * @param ray the ray we are about to find it's intersections and find the closest one
     * @return a geo point of the intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene.getGeometries().findIntersectBoundingRegion(ray);
        return intersections != null && !intersections.isEmpty() ? getClosestPoint(intersections, ray.get_p00()) : null;
    }


    /**
     * Construct a reflected ray, with opposite direction of the light's ray, with shifted starting point on the line
     * of the normal's geometry at the current point towards the direction of the new ray
     *
     * @param n     the vector normal of the geometry at the known point
     * @param point the intersection point in geometry
     * @param inRay the ray of the light source which intersect thr geometry at the known point
     * @return new Ray represent the reflection of the light's ray
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
        // create the reflected ray which it's direction the reflection of the light's direction
        // and it's beginning point moved closer to the light source (with normal or opposite to normal)
        Vector v = inRay.get_direction();
        Vector r = v.subtract(n.scale(2 * n.dotProduct(v))); // v - 2*(v∙n)*n
        return new Ray(point, r, n);
    }

    /**
     * Construct a refracted ray with shifted starting point on the line
     * of the normal's geometry at the current point towards the direction of the ray
     *
     * @param n     the vector normal of the geometry at the known point
     * @param point the intersection point in geometry
     * @param inRay the ray of the light source which intersect thr geometry at the known point
     * @return new Ray represent the refraction of the light's ray
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
        // create the reflected ray which it's direction the reflection of the light's direction
        // and it's beginning point shifted behind surface of the geometry (with normal or opposite to normal)
        return new Ray(point, inRay.get_direction(), n);
    }

    /**
     * Method for calculating the diffusive reflection on a known point of object which about to get into a pixel.
     * the formula: (kD∙|l∙n|)∙lightIntensity
     *
     * @param kD             diffusion - net movement of light from a region of higher concentration to a region of lower concentration.
     * @param l              direction of the light source to the intersection point
     * @param n              Vector normal of the object in the intersection point
     * @param lightIntensity intensity of the intersection point that influenced by the light source
     * @return color of the intersection point after adding the diffuse reflection effect.
     */
    private Color calcDiffusive(double kD, Vector l, Vector n, Color lightIntensity) {
        if (isZero(l.dotProduct(n))) return Color.BLACK; // scale can't be zero
        // When l and n are normalized, l·n is the cosine of the angle between them. Maximum diffusion is gained in
        // the normal’s direction (i.e. when the light source is opposite to the tangent surface)
        //* |l∙n| - We don't care if the normal vector of the object is directed up or down
        return lightIntensity.scale(kD * abs(l.dotProduct(n)));
    }

    /**
     * Method for calculating the specular reflection on a known point of object which about to get into a pixel,
     * including the shininess reflection in the specular.
     * the formula: (kS∙(max(0,−v∙r))^nSH)∙lightIntensity
     *
     * @param kS             specular - reflected light directed, as from a smooth, polished surface (opposed to diffuse).
     * @param l              direction of the light source to the intersection point
     * @param n              Vector normal of the object in the intersection point
     * @param v              direction of camera to the intersection point
     * @param nShininess     - shininess -  Blurs the specular highlight
     * @param lightIntensity intensity of the intersection point that influenced by the light source
     * @return color of the intersection point after adding the specular reflection effect.
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        if (isZero(l.dotProduct(n))) return Color.BLACK; // scale can't be zero
        // Reflectance vector:
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n))); // r = l−2∙(l∙n)∙n
        //* v∙r should be negative because we want to get the positive result when we know that the two vectors should be at negative directions
        //* max(0,−v∙r) - if the angle between v and r is more than 180 degrees (0 > −v∙r),
        //  then there won't be shown specular light reflection, include no shininess.
        return lightIntensity.scale(kS * pow(max(0, -1d * v.dotProduct(r)), nShininess));
    }

    /**
     * Method designed to find the closest intersection point to the head of ray with the geometries,
     * the method is doing comparision between the intersection points with the geometries and the related ray
     *
     * @param points represent all intersection points with this ray and the geometries in some scene
     * @param head   represent the beginning of the related ray, which is,
     *               We measure the distance from it to the intersection points
     * @return the closest point (has the shortest distance)
     */
    private GeoPoint getClosestPoint(List<GeoPoint> points, Point3D head) {
        GeoPoint closest_point = points.get(0);
        for (GeoPoint geoPoint : points) // simply comparing every single point and catch the smallest value of distance
        {
            if ((geoPoint.point).distance(head) < closest_point.point.distance(head))
                closest_point = geoPoint;
        }
        return closest_point;
    }

    /**
     * Method for adding a grid over the image
     *
     * @param interval the length and width of each square of grid
     * @param color    color of grid
     */
    public void printGrid(int interval, java.awt.Color color) {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i += interval)
            for (int j = 0; j < nX; j++)
                _imageWriter.writePixel(j, i, color);
        for (int i = 0; i < nY; i++)
            for (int j = 0; j < nX; j += interval)
                _imageWriter.writePixel(j, i, color);
    }

    /**
     * Method whose job is to run the image creation from the imageWriter class.
     * (encapsulation, _imageWriter is private member)
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * unshaded is a boolean method which check if the current point doest get intensity of light from a light source
     * which is known to come from the direction of light to  point (vector 'l')
     *
     * @param light    is the light source we are refer to if the point gets light from it
     * @param l        is the direction from the light source to the point
     * @param n        is the normal of the correct geometry at that point
     * @param geopoint the geometry point, the point we want to check if it shaded or not
     * @return boolean result,
     * true - there's no other geometries that cover the point from the know direction of light.
     * false - light source cn't reach the point
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        // Step 1: Create light ray
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        // Step 2: check for intersections with other geometries with the light ray
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay, light.getDistance(geopoint.point));
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 &&
                    gp.geometry.getMaterial().getKT() == 0)
                return false;
        }
        return true;
    }

    /**
     * a method which gives a value for the intensity of light from a light source in the current point,
     * which is known to come from the direction of light to  point (vector 'l').
     * We will accrue the coefficient of transparency of the geometries we intersect by the light rays
     * which come from the point towards the light source, in the form of average,
     * after calculating for each single light ray in the form of multiplication the value of transparency.
     *
     * @param light    is the light source we are refer to if the point gets light from it
     * @param l        is the direction from the light source to the point
     * @param n        is the normal of the correct geometry at that point
     * @param geopoint the geometry point, the point we want to check if it shaded or not
     * @return accumulation of the transparency multiplication of all objects intersections with the same ray
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        // Step 1: Create light ray
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray center_lightRay = new Ray(geopoint.point, lightDirection, n);
        double lightDistance = light.getDistance(geopoint.point);
        // Step 2: check for intersections with other geometries with the light rays
        // create x and y axis for the circle, which means vectorUp and vectorRight.
        Vector vRight = lightDirection.getOrthogonal();
        Vector vUp = lightDirection.crossProduct(vRight);
        // we have two orthogonal vectors to light Direction which represent x and y axis for the circle
        List<Ray> lightRays = center_lightRay.randomRaysInCircle(center_lightRay.get_target_point(lightDistance),
                vUp, vRight, light.getRadius(), _numOfRaysInLight, lightDistance);
        double ktr;
        double averageKtr = 0;
        // the weight of single ray with respect to all rays which came from same point
        double weight = (1d / lightRays.size());
        // Step: calculate the average transparency of all the transparencies which created from each light ray
        for (Ray lightRay : lightRays) {
            ktr = 1.0;
            List<GeoPoint> intersections = _scene.getGeometries().findIntersectBoundingRegion(lightRay, lightDistance);
            if (intersections == null) {
                averageKtr += 1.0 * weight; // no shadow exist for this ray
                continue;                 // move to next ray
            }
            // Step 3: calculate the intensity of light at that point for each light ray
            for (GeoPoint gp : intersections) { // there are number of intersections with same ray and geometries
                ktr *= gp.geometry.getMaterial().getKT();
                // if the intersection is with a matted object then it will stop with zero
                if (ktr < MIN_CALC_COLOR_K) {
                    // ktr = 0 so there's no need to add anything
                    break;
                }
            }
            averageKtr += ktr * weight;
        }
        return averageKtr;
    }

    //*******Setters*******//

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }


    /**
     * Set soft shadows on
     *
     * @param numOfRaysInLight the number of rays per single point on geometry for calculating the shadow
     * @return the camera object itself
     */
    public Render setSoftShadows(int numOfRaysInLight) {
        if (numOfRaysInLight <= 0)
            throw new IllegalArgumentException("the number of rays in towards light surface must be at least 1");
        this._numOfRaysInLight = numOfRaysInLight;
        return this;
    }

    //*******Getters*******//


    /**
     * get the number of rays per single light source
     *
     * @return integer value number of rays
     */
    public int getNumOfRaysInLight() {
        return _numOfRaysInLight;
    }


}
