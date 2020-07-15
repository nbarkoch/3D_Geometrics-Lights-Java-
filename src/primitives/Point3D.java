package primitives;

/**
 * Point3D: class for representing a point in 3D dimension
 * represented by three coordinates (places on x y and z axises)
 */
public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;
    // static readonly member point called ZERO
    public final static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);


    //********** Constructors ***********/

    /**
     * Constructor for creating a point by input of three coordinates
     *
     * @param x coordinate on the x axis
     * @param y coordinate on the y axis
     * @param z coordinate on the z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this._x = x;
        this._y = y;
        this._z = z;
    }

    /**
     * Constructor for creating a point by input of three numbers representing coordinates
     *
     * @param x number representing the place of coordinate in x axis
     * @param y number representing the place of coordinate in y axis
     * @param z number representing the place of coordinate in z axis
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    //********** Getters ***********/

    /**
     * @return the Coordinate with _x value
     */
    public Coordinate get_x() {
        return _x;
    }
    /**
     * @return the Coordinate with _y value
     */
    public Coordinate get_y() {
        return _y;
    }
    /**
     * @return the Coordinate with _z value
     */
    public Coordinate get_z() {
        return _z;
    }


    /**
     * @return the value of coordinate _x
     */
    public double getX() {
        return _x._coord;
    }

    /**
     * @return the value of coordinate _y
     */
    public double getY() {
        return _y._coord;
    }

    /**
     * @return the value of coordinate _z
     */
    public double getZ() {
        return _z._coord;
    }



    //********** Calculation methods ***********/

    /**
     * Vector Subtraction: Subtraction between two points returns a vector with direction from the subtract to the subtracted
     *
     * @param vertex param vertex is the second point
     * @return a vector from the second point to the point at which the action is performed
     */
    public Vector subtract(Point3D vertex) {
        return new Vector(
                this._x._coord - vertex._x._coord,
                this._y._coord - vertex._y._coord,
                this._z._coord - vertex._z._coord);
    }

    /**
     * Adding Vector to a Point - Returns a new point, by doing interconnect operation the vector's head point and the point
     *
     * @param vector a vector which his point value added to the point
     * @return a new point whose coordinate values are the result of the points interconnect operation
     */
    public Point3D add(Vector vector) {
        return new Point3D(this._x._coord + vector._head._x._coord,
                this._y._coord + vector._head._y._coord,
                this._z._coord + vector._head._z._coord);
    }

    /**
     * The length between two points squared
     *
     * @param other_point the second point for calculating th distance
     * @return the number representing the distance squared
     */
    public double distanceSquared(Point3D other_point) {
        return (this._x._coord - other_point._x._coord) * (this._x._coord - other_point._x._coord) +
                (this._y._coord - other_point._y._coord) * (this._y._coord - other_point._y._coord) +
                (this._z._coord - other_point._z._coord) * (this._z._coord - other_point._z._coord);
    }

    /**
     * The length between two points
     *
     * @param other_point the second point for calculating th distance
     * @return the number representing the distance
     */
    public double distance(Point3D other_point) {
        return Math.sqrt(distanceSquared(other_point));
    }


    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                       // step 1 - we are talking about the same point
        if (o == null || !(o instanceof Point3D))
            return false;                                 // step 2 - we are talking about object which isn't a point or null
        Point3D point3D = (Point3D) o;                    // step 3 - let's treat it as a point, and check coordinates
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


}
