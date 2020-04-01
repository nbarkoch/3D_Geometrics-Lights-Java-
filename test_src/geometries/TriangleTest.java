package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    /**
     * Test method for
     * {@link geometries.Triangle#Triangle(Point3D,Point3D,Point3D)}.
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
        assertEquals( new Vector(sqrt3, sqrt3, sqrt3).scale(-1), tr.getNormal(null),"Bad normal to triangle");
    }
}