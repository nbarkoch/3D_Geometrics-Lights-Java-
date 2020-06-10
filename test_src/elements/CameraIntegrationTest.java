package elements;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Testing Camera and 3D objects integration, if they work right together
 */
public class CameraIntegrationTest {
    /**
     * two cameras for this class
     */
    Camera camera1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
    Camera camera2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));

    /**
     * Auxiliary calculation for number of intersections with rays of camera and a 3d component (DRY)
     *
     * @param intersectable  is a 3D object which possible to intersect with rays, this could includes composite component
     * @param camera         camera class which represent the location and direction of point of view
     * @param nX             number of pixels in every row
     * @param nY             number of pixels in every column
     * @param screenDistance minimum distance of camera to view plane
     * @param screenWidth    Total screen/view plane width
     * @param screenHeight   Total screen/view plane height
     * @return total number of intersections from camera's view plane's rays and the 3D object
     */
    private int count_camera_intersections(Intersectable intersectable, Camera camera, int nX, int nY, double screenDistance, double screenWidth, double screenHeight) {
        int counter = 0;
        List<Point3D> intersections;
        for (int i = 0; i < nY; i++) { // for every (value) pixel in view plane (matrix)
            for (int j = 0; j < nX; j++) {
                // V temp list of intersection points V
                intersections = intersectable.findIntersections(camera.constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth, screenHeight));
                // in every time we get some intersections we will add the number of them to counter
                if (intersections != null) {
                    counter += intersections.size();
                }
            }
        }
        return counter;
    }

    /**
     * Test method for Camera Integration (ray construction + intersections)
     * Each test there are rays for all pixels in View Plane 3 by 3 pixels, and size 3 by 3,
     * we will consider cutting each ray with the body of the tested object and summarize the number of intersections from all rays.
     * In this test method we check Sphere and camera's rays number of intersections
     */
    @Test
    public void CameraSphereIntersections() {
        // **** Group: Sphere ray's camera intersections

        // TC01: 3X3 small sphere for minimum capture (radius = 1)
        Sphere sphere1 = new Sphere(new Point3D(0, 0, 3), 1d);
        assertEquals(2, count_camera_intersections(sphere1, camera1, 3, 3, 1, 3, 3), "wrong number of points, should be 2");

        // TC02: 3X3 big sphere for maximum capture (radius = 2.5)
        Sphere sphere2 = new Sphere(new Point3D(0, 0, 2.5), 2.5);
        assertEquals(18, count_camera_intersections(sphere2, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 18");

        // TC03: 3X3 normal sphere for intermediate capture (radius = 2)
        Sphere sphere3 = new Sphere(new Point3D(0, 0, 2), 2d);
        assertEquals(10, count_camera_intersections(sphere3, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 10");

        // TC04: 3X3 big sphere for intermediate capture (radius = 4)
        Sphere sphere4 = new Sphere(new Point3D(0, 0, 4 / 3d), 4d);
        assertEquals(9, count_camera_intersections(sphere4, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 9");

        // TC05: 3X3 big sphere for intermediate capture (radius = 4)
        Sphere sphere5 = new Sphere(new Point3D(0, 0, -1), 0.5);
        assertEquals(0, count_camera_intersections(sphere5, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 0");
    }

    /**
     * Test method for Camera Integration (ray construction + intersections)
     * Each test there are rays for all pixels in View Plane 3 by 3 pixels, and size 3 by 3,
     * we will consider cutting each ray with the body of the tested object and summarize the number of intersections from all rays.
     * In this test method we check Plane and camera's rays number of intersections
     */
    @Test
    public void CameraPlaneIntersections() {
        // **** Group: Plane ray's camera intersections

        // TC11: 3X3 parallel plane to camera's view plane maximum capture
        Plane plane1 = new Plane(new Point3D(0, 0, 3), camera2.get_vTo());
        assertEquals(9, count_camera_intersections(plane1, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 9");

        // TC12: 3X3 plane with different direction in y axis but still maximum capture
        Plane plane2 = new Plane(new Point3D(0, 0, 2), new Vector(new Point3D(0, -1, -2)));
        assertEquals(9, count_camera_intersections(plane2, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 9");

        // TC13: 3X3 plane with different direction in y axis intermediate capture
        Plane plane3 = new Plane(new Point3D(0, 0, 3), new Vector(new Point3D(0, -2, -2)));
        assertEquals(6, count_camera_intersections(plane3, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 6");

        // TC14: 3X3 orthogonal plane to camera's view plane no capture
        Plane plane4 = new Plane(new Point3D(0, 0, 3), camera2.get_vUp());
        assertEquals(0, count_camera_intersections(plane4, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 0");

    }

    /**
     * Test method for Camera Integration (ray construction + intersections)
     * Each test there are rays for all pixels in View Plane 3 by 3 pixels, and size 3 by 3,
     * we will consider cutting each ray with the body of the tested object and summarize the number of intersections from all rays.
     * In this test method we check Triangle and camera's rays number of intersections
     */
    @Test
    public void CameraTriangleIntersections() {
        // **** Group: Triangle ray's camera intersections

        // TC21: 3X3 small triangle for minimum capture
        Triangle triangle1 = new Triangle(new Point3D(0, -1, 2),
                new Point3D(1, 1, 2),
                new Point3D(-1, 1, 2));
        assertEquals(1, count_camera_intersections(triangle1, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 1");

        // TC22: 3X3 higher but small triangle for two points capture
        Triangle triangle2 = new Triangle(new Point3D(0, -20, 2),
                new Point3D(1, 1, 2),
                new Point3D(-1, 1, 2));
        assertEquals(2, count_camera_intersections(triangle2, camera2, 3, 3, 1, 3, 3), "wrong number of points, should be 2");

        // TC23: nX,nY: 4X4 distance: 2 screenWidth,Height: 4 | big triangle for maximum capture
        Triangle triangle3 = new Triangle(new Point3D(0, -10, 4),
                new Point3D(10, 10, 4),
                new Point3D(-10, 10, 4));
        assertEquals(16, count_camera_intersections(triangle3, camera1, 4, 4, 2, 4, 4), "wrong number of points, should be 16");
        
        // TC24: 3X3 small triangle for no capture
        Triangle triangle4 = new Triangle(new Point3D(0, 0, 3),
                new Point3D(4,0,0),
                new Point3D(-4, 0,0));
        assertEquals(0, count_camera_intersections(triangle4, camera2,3,3,1,3,3), "wrong number of points, should be 0");

    }
}
