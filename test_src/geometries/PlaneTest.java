package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

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
            Plane p = new Plane(new Point3D(1, 0, 0), new Point3D(0, 0, 0), new Point3D(1, 0, 0));
            fail("Failed constructing a correct polygon");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        // TC02: ZERO vector normal, plane with no surface
        try {
            Plane p = new Plane(new Point3D(1, 2, 3), new Point3D(0, 0, 0), new Point3D(3, 6, 9));
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
}