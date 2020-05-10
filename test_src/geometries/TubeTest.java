package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    /**
     * Test method for
     * {@link geometries.Tube#getNormal(Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ============== //
        // TC01: There is a simple single test here, point is always on the side
        Tube tube = new Tube(new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0)), 3);
        Vector n = new Vector(0, 2, 0);
        assertEquals(n.normalize(), tube.getNormal(new Point3D(0, 3, 0)), "Bad normal to tube");
    }


}