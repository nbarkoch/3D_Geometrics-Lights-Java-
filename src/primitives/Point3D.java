package primitives;

import java.util.Objects;

/**
 * Point3D: class for representing a point in 3D environment
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    /*********** Constructors ***********/
    /**
     * Constructor for creating a point by input of three coordinates
     *
     * @param _x coordinate on the x axis
     * @param _y coordinate on the y axis
     * @param _z coordinate on the z axis
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    /**
     * Constructor for creating a point by input of three coordinates
     *
     * @param _x number representing the place of coordinate in x axis
     * @param _y number representing the place of coordinate in y axis
     * @param _z number representing the place of coordinate in z axis
     */
    public Point3D(double _x, double _y, double _z) {
        this(new Coordinate(_x), new Coordinate(_y), new Coordinate(_z));
    }

    /**
     * Constructor for creating a point by input of other point
     *
     * @param _point representing a point, including three coordinates
     */
    public Point3D(Point3D _point) {
        this(_point._x.get(), _point._y.get(), _point._z.get());
    }

    /*********** getters and setters ***********/
    /**
     * @return new Coordinate with _x value
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    public void set_x(Coordinate _x) {
        this._x = new Coordinate(_x);
    }

    public void set_y(Coordinate _y) {
        this._y = new Coordinate(_y);
    }

    public void set_z(Coordinate _z) {
        this._z = new Coordinate(_z);
    }

    /*************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                                     // step 1 - we are talking about the same point
        if (o == null || getClass() != o.getClass())
            return false;      // step 2 - we are talking about object which isn't a point or null
        Point3D point3D = (Point3D) o;                                  // step 3 - let's treat it as a point, and check coordinates
        return this._x.equals(point3D._x) &&
                this._y.equals(point3D._y) &&
                this._z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x +
                ", " + _y +
                ", " + _z +
                ')';
    }

    public Vector subtract(Point3D vertex) {
        return Vector.ZERO;
    }

}
