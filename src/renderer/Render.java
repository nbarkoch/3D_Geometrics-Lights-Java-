package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

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
     * @param imageWriter for size and resolution of picture,
     *                    and the tool which transform camera's view plane into real pixels
     * @param scene for the scene, i.g. camera direction and geometries placed
     */
    Render(ImageWriter imageWriter, Scene scene){
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

        Point3D closestPoint; // represent the closest point from camera to some 3D object in every ray
        //                       which represent by iteration of pixel
        for(int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                // for every pixel we create ray which cross directly through it in the middle
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
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
     * @param p the point we want to print
     * @return the color of chosen point
     */
    private Color calcColor(Point3D p) {
        return _scene.getAmbientLight().getIntensity();
    }

    /**
     * Method designed to find the closest intersection point with specific ray of camera
     * compared to other intersection points with the geometries and same ray
     * @param points represent all intersection points with this ray and the geometries in some scene
     * @return the closest point (has the shortest distance)
     */
    private Point3D getClosestPoint(List<Point3D> points) {
        Point3D closest_point = points.get(0);
        Point3D camera_p0 = _scene.getCamera().get_p0();
        for (Point3D point : points) // simply comparing every single point and catch the smallest value of distance
        {
            if(point.distance(camera_p0) < closest_point.distance(camera_p0))
                closest_point = point;
        }
        return closest_point;
    }

    /**
     * Method for adding a grid over the image
     * @param interval the length and width of each square of grid
     * @param color color of grid
     */
    public void printGrid(int interval, java.awt.Color color) {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for(int i =0; i<nY; i += interval)
            for(int j=0; j<nX; j++)
                _imageWriter.writePixel(j,i,color);
        for(int i =0; i<nY; i++)
            for(int j=0; j<nX; j += interval)
                _imageWriter.writePixel(j,i,color);
    }

    /**
     * Method whose job is to run the image creation from the imageWriter class.
     * (encapsulation, _imageWriter is private member)
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}
