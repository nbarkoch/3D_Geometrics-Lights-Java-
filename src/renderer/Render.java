package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.StrictMath.*;

/**
 * Class Render represent the rendering of the scene into a jpg file picture
 * the class rendering the scene to jpg file through image writer.
 * The purpose of this class is to render properly the scene of 3d geometries to a colored matrix of the image
 */
public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;

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
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double height = _imageWriter.getHeight();
        double width = _imageWriter.getWidth();
        double distance = _scene.getDistance();

        GeoPoint closestPoint; // represent the closest point from camera to some 3D object in every ray
        //                       which represent by iteration of pixel
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                // for every pixel we create ray which cross directly through it in the middle
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                // we calculate the intersection points with the ray and the geometries in scene and add them to new list of points
                if (intersectionPoints == null)
                    _imageWriter.writePixel(j, i, background); // just print the background in this pixel
                else {
                    closestPoint = getClosestPoint(intersectionPoints); // closest intersection point to camera
                    _imageWriter.writePixel(j, i, calcColor(closestPoint).getColor()); // get color of the point
                }
            }
        }
    }


    /**
     * Method designed to take care of the object color under the definition we defined in the graphic scene
     * The calculation for point color is according to Phong Reflectance Model.
     * which means that we are summing lights on one point, for each light we sum the three known values for reflection
     * (diffusion, specular and shininess)
     * which will give us the right pixel of color for the current light.
     *
     * @param intersection the point we want to print
     * @return the color of chosen point
     */
    private Color calcColor(GeoPoint intersection) {
        Color color = _scene.getAmbientLight().getIntensity();  // intensity of ambient light
        color = color.add(intersection.geometry.getEmission()); // add intensity of emission light
        // direction of camera to the intersection point:
        Vector v = intersection.point.subtract(_scene.getCamera().getP0()).normalize();
        // Vector normal of the object in the point:
        Vector n = intersection.geometry.getNormal(intersection.point);
        // Material of the same object:
        Material material = intersection.geometry.getMaterial();
        // values of reflection:
        int nShininess = material.getNShininess();
        double kd = material.getKD();
        double ks = material.getKS();
        // we will consider summing up all lights reflections on the intersection point:
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(intersection.point); // direction of the chosen light source to the point p
            double ln = l.dotProduct(n); // l∙n
            double vn = v.dotProduct(n); // v∙n
            // Fixing wrong illumination:
            // if the illumination point is on one side of the surface and the viewpoint on the other,
            // (including if the direction of viewpoint orthogonal to the normal's surface or to the light's vector)
            // there won't be any reflections from the light towards the camera (no diffusion nor specular)
            if ((ln > 0 && vn > 0) || (ln < 0 && vn < 0)) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * Method for calculating the diffusive reflection on a known point of object which about to get into a pixel.
     * the formula: (kD∙|l∙n|)∙lightIntensity
     * @param kD diffusion - net movement of light from a region of higher concentration to a region of lower concentration.
     * @param l direction of the light source to the intersection point
     * @param n Vector normal of the object in the intersection point
     * @param lightIntensity intensity of the intersection point that influenced by the light source
     * @return color of the intersection point after adding the diffuse reflection effect.
     */
    private Color calcDiffusive(double kD, Vector l, Vector n, Color lightIntensity){
        // When l and n are normalized, l·n is the cosine of the angle between them. Maximum diffusion is gained in
        // the normal’s direction (i.e. when the light source is opposite to the tangent surface)
        //* |l∙n| - We don't care if the normal vector of the object is directed up or down
        return lightIntensity.scale(kD*abs(l.dotProduct(n)));
    }

    /**
     * Method for calculating the specular reflection on a known point of object which about to get into a pixel,
     * including the shininess reflection in the specular.
     * the formula: (kS∙(max(0,−v∙r))^nSH)∙lightIntensity
     * @param kS specular - reflected light directed, as from a smooth, polished surface (opposed to diffuse).
     * @param l direction of the light source to the intersection point
     * @param n Vector normal of the object in the intersection point
     * @param v direction of camera to the intersection point
     * @param nShininess - shininess -  Blurs the specular highlight
     * @param lightIntensity intensity of the intersection point that influenced by the light source
     * @return color of the intersection point after adding the specular reflection effect.
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity){
        // Reflectance vector:
        Vector r = l.subtract(n.scale(2*l.dotProduct(n))); // r = l−2∙(l∙n)∙n
        //* v∙r should be negative because we want to get the positive result when we know that the two vectors should be at negative directions
        //* max(0,−v∙r) - if the angle between v and r is more than 180 degrees (0 > −v∙r),
        //  then there won't be shown specular light reflection, include no shininess.
        return lightIntensity.scale(kS*pow(max(0,-1d*v.dotProduct(r)), nShininess));
    }

    /**
    * Method designed to find the closest intersection point with specific ray of camera
    * compared to other intersection points with the geometries and same ray
    *
    * @param points represent all intersection points with this ray and the geometries in some scene
    * @return the closest point (has the shortest distance)
    */
    private GeoPoint getClosestPoint(List<GeoPoint> points) {
        GeoPoint closest_point = points.get(0);
        Point3D camera_p0 = _scene.getCamera().getP0();
        for (GeoPoint geoPoint : points) // simply comparing every single point and catch the smallest value of distance
        {
            if ((geoPoint.point).distance(camera_p0) < closest_point.point.distance(camera_p0))
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
}
