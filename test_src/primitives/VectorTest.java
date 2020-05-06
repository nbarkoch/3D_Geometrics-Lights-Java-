package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 */
class VectorTest {
    /**
     * Test method for {@link primitives.Vector#subtract(Vector)}.
     */
    @Test
    public void testSubtract() {
        Vector v1 = new Vector(0.1, 1, 2);
        Vector v2 = new Vector(0.1, 0, 3.1);
        Vector v3 = new Vector(0, 1, -1.1);

        // ============ Equivalence Partitions Tests ============== //
        Vector v12 = v1.subtract(v2);
        assertEquals(v3, v12, "add function does'nt work properly");

        // =============== Boundary Values Tests ================== //
        try {
            v12.subtract(v3);
            fail("Vector (0,0,0) not valid");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    public void testAdd() {
        Vector v1 = new Vector(-1, -1, 2);
        Vector v2 = new Vector(0.999999999999, 0, -0.191);
        Vector v3 = new Vector(0.000000000001, 1, -1.809);

        // ============ Equivalence Partitions Tests ============== //
        Vector v12 = v1.add(v2);
        assertEquals(new Vector(-0.000000000001, -1, 1.809), v12,
                "add function does'nt work properly");

        // =============== Boundary Values Tests ================== //
        try {
            v3.add(v1.add(v2));
            // or, same idea:
            v1.add(v2.add(v3));
            fail("Vector (0,0,0) not valid");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {
        Vector v1 = new Vector(2, 4, 4);

        // ============ Equivalence Partitions Tests ============== //
        assertEquals(new Vector(12, 24, 24), v1.scale(v1.length()),
                "scale function does'nt work properly");

        // =============== Boundary Values Tests ================== //
        try {
            v1.scale(0);
            fail("Vector (0,0,0) not valid");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(3.5, -5.0, 10.0);
        Vector v2 = new Vector(2.5, 7, 0.5);
        Vector v3 = new Vector(-72.5, 23.25, 37.0); //
        v3 = v1.crossProduct(v2); // v3 is normal to v1 and v2

        // ============ Equivalence Partitions Tests ============== //
        assertEquals(v1.dotProduct(v2), v2.dotProduct(v1));
        // Vectors which are perpendicular to each other if using dot product gives zero value
        assertEquals(0, v3.dotProduct(v2), 1e-10); // ||V||*||U||*cos(a) = V*U
        assertEquals(0, v3.dotProduct(v1), 1e-10); // and a = 90 degrees
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        Vector v4 = v1.crossProduct(v3);
        Vector v5 = v3.crossProduct(v1);
        assertEquals(v4.scale(-1), v5, "Expected equals v4 = v5*(-1)");

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), v4.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(v4.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(v4.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");


        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    public void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(-72.5, 23.25, 37.0);
        assertEquals(84, v.length(), 0.655);

        Vector v2 = new Vector(2, 4, 4);
        assertEquals(6, v2.length());
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    public void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(3.5, -5, 10);
        v.normalize();
        assertEquals(1, v.length(), 1e-10, "Normalize function doesn't work properly\nWrong result");
        // =============== Boundary Values Tests ==================
        // test if normalize can work on a vector with close to zero lengthSquared
        try {
            v = new Vector(0.000000000001, 0, 0);
            v.normalize(); // we done alignToZero in result of lengthSquared method in class Vector
            fail("Didn't throw divide by zero exception");
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }
}