package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
    /**
     * Test method for
     * {@link geometries.Cylinder#getNormal(Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ============== //
        Cylinder cylinder = new Cylinder(10, new Ray(new Point3D(2,2,2), new Vector(0,0,1)), 2.5);

        // TC01: point is on the sides of the cylinder
        assertEquals(new Vector(0,1,0).normalize(), cylinder.getNormal(new Point3D(2,4.5,5)), "Bad normal on side to cylinder");
        // TC02: point is on upper base of cylinder
        assertEquals(new Vector(0,0,1).normalize(), cylinder.getNormal(new Point3D(1,4,12)), "Bad normal on upper base to cylinder");
        // TC03: point is on lower base of cylinder
        assertEquals(new Vector(0,0,-1).normalize(), cylinder.getNormal(new Point3D(3,2.5,2)), "Bad normal on lower base to cylinder");

        // =============== Boundary Values Tests ================== //
        // TC10: point is on the upper base and also on side of the cylinder
        assertEquals(new Vector(0,0,1).normalize(), cylinder.getNormal(new Point3D(2,4.5,12)), "Bad normal on upper base and side to cylinder");
        // TC11: point is on the lower base and also on side of the cylinder
        assertEquals(new Vector(0,0,-1).normalize(), cylinder.getNormal(new Point3D(0,2,2)), "Bad normal on upper base and side to cylinder");
    }
}