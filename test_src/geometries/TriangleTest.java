package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    /**
     * Test method for
     * {@link geometries.Triangle#Triangle(Point3D, Point3D, Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(
                new Point3D(0, 0, 1),
                new Point3D(1, 0, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        System.out.print(sqrt3);
        System.out.print(tr.getNormal(null));
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3).scale(-1), tr.getNormal(null), "Bad normal to triangle");
    }

    /**
     * Test method for
     * {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    void findIntersections() {
        Triangle triangle = new Triangle(
                new Point3D(3, 0, 4),
                new Point3D(5, 0, -1),
                new Point3D(1, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside triangle
        assertEquals(List.of(new Point3D(3, 0, 1.625)),
                triangle.findIntersections(new Ray(new Point3D(3, 1.5, 2), new Vector(0, -2, -0.5))), "Ray's line should be in triangle at one (maybe different) point");
        // TC02: Outside against edge
        assertNull(triangle.findIntersections(new Ray(new Point3D(5, 1.5, 2), new Vector(0, -2, -0.5))), "Ray's line shouldn't be in triangle");
        // TC03: Outside against vertex
        assertNull(triangle.findIntersections(new Ray(new Point3D(3, 1.5, 4), new Vector(0, -2, 0.5))), "Ray's line shouldn't be in triangle");

        // =============== Boundary Values Tests ==================
        // **** Group: the ray begins "before" the plane (the check for case which the ray begins in plane is in class plane)
        Triangle triangle1 = new Triangle(
                new Point3D(3, 0, 4),
                new Point3D(3, 0, -1),
                new Point3D(1, 0, 1));
        // TC11: Intersect on edge
        assertNull(triangle1.findIntersections(new Ray(new Point3D(3, 1, 2), new Vector(0, -2, 0))), "Ray's line shouldn't be count in edge of triangle");
        // TC12: Intersect in vertex
        assertNull(triangle1.findIntersections(new Ray(new Point3D(3, 2, 4), new Vector(0, -2, 0))), "Ray's line shouldn't be count in vertex of triangle");
        // TC13: Intersect on edge's continuation
        assertNull(triangle1.findIntersections(new Ray(new Point3D(-1, 2, -2), new Vector(0, -2, 0))), "Ray's line shouldn't be count in continuation of edge in triangle");
    }
}