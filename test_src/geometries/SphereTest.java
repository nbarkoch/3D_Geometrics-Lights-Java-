package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Test method for
     * {@link geometries.Sphere#Sphere(double, Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There are simple tests here
        Sphere sphere = new Sphere(3, new Point3D(1, 0, 0));
        assertEquals(new Vector(3, 0, 0).normalize().scale(-1), sphere.getNormal(new Point3D(4, 0, 0)), "bad normal calculator");
        assertEquals(new Vector(0, 2, 0).normalize(), sphere.getNormal(new Point3D(1, -3, 0)), "bad normal calculator");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new GeoPoint(sphere, p1), new GeoPoint(sphere, p2)), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        Point3D p3 = new Point3D(1.7717797887081346, 0.6358898943540673, 0);
        result = sphere.findIntersections(new Ray(new Point3D(0.5, 0, 0),
                new Vector(2, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere, p3)), result, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(3, 0, 0),
                new Vector(3, 1, 0)));
        assertNull(result, "Ray does not cross sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        Point3D p4 = new Point3D(0.40000000000000013, 0.7999999999999999, 0.0);
        result = sphere.findIntersections(new Ray(new Point3D(2, 0, 0),
                new Vector(-2, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere, p4)), result, "Ray crosses sphere");
        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(2, 0, 0),
                new Vector(2, 1, 0)));
        assertNull(result, "Ray's beginning point shouldn't count as cross through sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Point3D p5 = new Point3D(1, -1, 0);
        Point3D p6 = new Point3D(1, 1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, 1.75, 0),
                new Vector(0, -1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).point.get_y().get() > result.get(1).point.get_y().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new GeoPoint(sphere, p5), new GeoPoint(sphere, p6)), result, "Ray should cross sphere at two points");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 1, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere, p5)), result, "Ray crosses sphere only one place");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere, p5)), result, "Ray crosses sphere only one place");

        // TC16: Ray starts at the center (1 points)
        Point3D p7 = new Point3D(1.0, 0.7071067811865475, 0.7071067811865475);
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 0),
                new Vector(0, 0.5, 0.5)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere, p7)), result, "Ray crosses sphere only one place");

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, -1, 0),
                new Vector(0, -1, 0)));
        assertNull(result, "Ray's beginning point shouldn't count as cross through sphere");

        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, -1.75, 0),
                new Vector(0, -1, 0)));
        assertNull(result, "Ray shouldn't cross sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(0.75, 1, 0),
                new Vector(1, 0, 0)));
        assertNull(result, "Ray's line shouldn't be count in tangent point in sphere");

        // TC20: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(1, 1, 0),
                new Vector(1, 0, 0)));
        assertNull(result, "Ray's beginning point shouldn't count as cross through sphere");

        // TC21: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(1.25, 1, 0),
                new Vector(1, 0, 0)));
        assertNull(result, "Ray should be out of sphere");
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntersections(new Ray(new Point3D(1, 2, 0),
                new Vector(1, 0, 0)));
        assertNull(result, "Ray shouldn't cross sphere");
    }

}