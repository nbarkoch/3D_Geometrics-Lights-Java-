package primitives;

/**
 * Point3D: class for representing a point in 3D dimension
 * represented by three coordinates (places on x y and z axises)
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;
    // static readonly member point called ZERO
    public final static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);



    //********** Constructors ***********/

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
     * Constructor for creating a point by input of three numbers representing coordinates
     *
     * @param x number representing the place of coordinate in x axis
     * @param y number representing the place of coordinate in y axis
     * @param z number representing the place of coordinate in z axis
     */
    public Point3D(double x, double y, double z) {
        this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
    }

    /**
     * Copy Constructor for creating a point by input of other point
     * @param _point representing a point, including three coordinates
     */
    public Point3D(Point3D _point) {
        this(_point._x.get(), _point._y.get(), _point._z.get());
    }



    //********** Getters ***********/

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



    //********** Calculation methods ***********/

    /**
     * Vector Subtraction: Subtraction between two points returns a vector with direction from the subtract to the subtracted
     * @param vertex param vertex is the second point
     * @return a vector from the second point to the point at which the action is performed
     */
    public Vector subtract(Point3D vertex) {
        return new Vector(
                new Point3D(this._x.get() - vertex._x.get(),
                        this._y.get() - vertex._y.get(),
                        this._z.get() - vertex._z.get()));
    }

    /**
     * Adding Vector to a Point - Returns a new point, by doing interconnect operation the vector's head point and the point
     * @param _vector a vector which his point value added to the point
     * @return a new point whose coordinate values are the result of the points interconnect operation
     */
    public Point3D add(Vector _vector){
        return new Point3D(this._x.get() + _vector._head._x.get(),
                this._y.get() + _vector._head._y.get(),
                this._z.get() + _vector._head._z.get());
    }

    /**
     * The length between two points squared
     * @param other_point the second point for calculating th distance
     * @return the number representing the distance squared
     */
    public double distanceSquared(Point3D other_point){
        return  (this._x.get() - other_point._x.get())*(this._x.get() - other_point._x.get())+
                (this._y.get() - other_point._y.get())*(this._y.get() - other_point._y.get())+
                (this._z.get() - other_point._z.get())*(this._z.get() - other_point._z.get());
    }

    /**
     * The length between two points
     * @param other_point the second point for calculating th distance
     * @return  the number representing the distance
     */
    public double distance(Point3D other_point){
        return Math.sqrt(distanceSquared(other_point));
    }



    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                       // step 1 - we are talking about the same point
        if (o == null || getClass() != o.getClass())
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
