package primitives;

import java.util.Objects;

public class Vector {
    Point3D _head;
    public final static Vector ZERO = new Vector(new Point3D(0.0, 0.0, 0.0));

    /*********** Constructors ***********/
    /**
     * Constuctor for creating a vector
     *
     * @param _head Point3D representing the head
     */
    public Vector(Point3D _head) throws IllegalArgumentException {
        if (_head.equals(ZERO._head))
            throw new IllegalArgumentException("vector can't have head to be zero!");
        this._head = _head;
    }

    /*********** getters and setters ***********/

    public Point3D get_head() {
        return _head;
    }

    public void set_head(Point3D _head) {
        this._head = _head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector other_vector = (Vector) o;
        return this._head.equals(other_vector._head);
    }

    @Override
    public String toString() {
        return "(Vector{)" +
                "_head=" + _head +
                '}';
    }

    /**
     * @param other Vector
     * @return dot product (double)
     */
    public double dotProduct(Vector other) {
        return _head._x.get() * other._head._x.get() +
                _head._y.get() * other._head._y.get() +
                _head._z.get() * other._head._z.get();
    }

    /**
     * @param _edge
     * @return Vector for crossproduct using right thumb rule
     */
    public Vector crossProduct(Vector _edge) {
        return Vector.ZERO;
    }
}
