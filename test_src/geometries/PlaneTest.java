package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    /**
     * Test method for
     * {@link geometries.Plane#Plane(Point3D, Point3D, Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ============== //

        // TC01: Repeated points
        try {
            new Plane(new Point3D(1, 0, 0), new Point3D(0, 0, 0), new Point3D(1, 0, 0));
            fail("Failed constructing a correct polygon");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        // TC02: ZERO vector normal, plane with no surface
        try {
            new Plane(new Point3D(1, 2, 3), new Point3D(0, 0, 0), new Point3D(3, 6, 9));
            fail("Failed constructing a correct polygon");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ============== //
        // TC01: There is a simple single test here
        Plane plane1 = new Plane(new Point3D(4, 2, 3), new Point3D(0, 0, 2), new Point3D(0, 0, 0));
        Point3D p1 = new Point3D(4, 2, 3);
        Point3D p2 = new Point3D(0, 0, 2);
        Point3D p3 = new Point3D(0, 0, 0);
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        assertEquals((v1.crossProduct(v2)).normalize().scale(-1), plane1.getNormal(null), "Wrong result for normal");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane (1 points)
        Plane plane = new Plane(new Point3D(4, 2, 3), new Point3D(0, 0, 2), new Point3D(0, 0, 0));
        assertEquals(List.of(new GeoPoint(plane, new Point3D(1.5, 0.75, 1.375))),
                plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(4, -2, 3))), "Ray's line should be in plane");

        // TC02: Ray does not intersect the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(4, 2, 3))), "Ray's line should be out of plane");

        // =============== Boundary Values Tests ==================
        Plane plane1 = new Plane(new Point3D(2, 0, 2), new Point3D(0, 0, 2), new Point3D(0, 0, 0));
        // **** Group: Ray is parallel to the plane
        // TC11: the ray included in the plane
        assertNull(plane1.findIntersections(new Ray(new Point3D(0.5, -2, 1), new Vector(2, 0, 0))), "Ray's line shouldn't be in plane");
        // TC12: the ray not included in the plane
        assertNull(plane1.findIntersections(new Ray(new Point3D(0.5, 0, 1), new Vector(2, 0, 0))), "Ray's line shouldn't count as crossing the plane");
        // **** Group: Ray is orthogonal to the plane
        // TC13: case which P0 is BEFORE the plane
        assertEquals(List.of(new GeoPoint(plane1, new Point3D(0.5, 0.0, 1.0))),
                plane1.findIntersections(new Ray(new Point3D(0.5, 2, 1), new Vector(0.0, -1.0, 0.0))), "Ray's line should be in plane");

        // TC14: case which P0 is IN the plane
        assertNull(plane1.findIntersections(new Ray(new Point3D(0.5, 0, 1), new Vector(0.0, -1.0, 0.0))), "Ray's line and beginning point shouldn't count as crossing the plane");

        // TC15: case which P0 is AFTER the plane
        assertNull(plane1.findIntersections(new Ray(new Point3D(0.5, -2, 1), new Vector(0.0, -1.0, 0.0))), "Ray's line should be out of plane");

        //**** Group: Special cases
        // TC16: Ray is neither orthogonal nor parallel to the plane and begins at the plane
        // (P0 is in the plane, but not the ray)
        assertNull(plane1.findIntersections(new Ray(new Point3D(0.5, 0, 1), new Vector(1, -1.25, -0.3))), "Ray's line shouldn't count as crossing the plane");
        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in
        // the same point which appears as reference point in the plane (Q)
        assertNull(plane1.findIntersections(new Ray(new Point3D(2.0, 0.0, 2.0), new Vector(1, -1.25, -0.3))), "Ray's line shouldn't count as crossing the plane");
    }
}