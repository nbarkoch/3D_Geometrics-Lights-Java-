package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Test method for
     * {@link geometries.Sphere#Sphere(Point3D, double)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There are simple tests here
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 3);
        assertEquals(new Vector(3, 0, 0).normalize().scale(-1), sphere.getNormal(new Point3D(4, 0, 0)), "bad normal calculator");
        assertEquals(new Vector(0, 2, 0).normalize(), sphere.getNormal(new Point3D(1, -3, 0)), "bad normal calculator");
    }
}