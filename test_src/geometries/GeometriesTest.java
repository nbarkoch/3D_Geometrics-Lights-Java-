package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // (All tests were tested on a Geogebra 3D calculator)
        // =============== Boundary Values Tests ==================

        // TC01: Empty Bodies Collection
        Geometries geometries = new Geometries();

        List<GeoPoint> result = geometries.findIntersections(new Ray(new Point3D(0, -2, 0),
                new Vector(0, 2, 2)));
        assertEquals(0, result == null ? 0 : result.size(), "Wrong number of points");

        geometries.add(new Triangle(
                        new Point3D(-2, 0, 0), new Point3D(0, 0, 2.55), new Point3D(1, 0, 0)),
                new Sphere(1d, new Point3D(0, 0, 1)),
                new Plane(new Point3D(0, 2, 0), new Vector(0, 0, 2)));

        // TC02: No shape has been intersected
        result = geometries.findIntersections(new Ray(new Point3D(0, -2, 0),
                new Vector(1, 2, 3)));
        assertEquals(0, result == null ? 0 : result.size(), "Wrong number of points");

        // TC03: Only one geometry object has been intersected
        result = geometries.findIntersections(new Ray(new Point3D(0, -2, 0.1),
                new Vector(0.5, 2, 0)));
        assertEquals(1, result.size(), "Wrong number of points");

        // TC04: All of geometry objects has been intersected
        result = geometries.findIntersections(new Ray(new Point3D(0, -2, -0.2),
                new Vector(0, 2, 2.52)));
        assertEquals(4, result.size(), "Wrong number of points");

        // ============ Equivalence Partitions Tests ==============

        // TC11: Some (but not all) of geometry objects has been intersected ()
        result = geometries.findIntersections(new Ray(new Point3D(0, -2, 0),
                new Vector(0, 2, 2)));
        assertEquals(3, result.size(), "Wrong number of points");
    }
}