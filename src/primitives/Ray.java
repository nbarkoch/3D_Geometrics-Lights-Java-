package primitives;

/**
 * Class Ray is the a basic object in geometry - the set of points on a straight line that are on one side relatively
 * to a given point on the straight line called the beginning of the ray. Defined by point and direction (unit vector).
 */
public class Ray {
    Point3D _p00;
    Vector _direction;



    //********** Constructors ***********/

    /**
     * Constructor for creating a ray
     * @param _p00 point3D representing the beginning of the ray
     * @param _direction vector representing the direction,
     * by the start point and direction we can find the end point of the ray
     */
    public Ray(Point3D _p00, Vector _direction) {
        this._p00 = new Point3D(_p00);
        this._direction = _direction.normalized();
    }

    /**
     * Copy constructor for a deep copy of an Ray object.
     * @param ray the ray which been copied
     */
    public Ray(Ray ray) {
        this._p00 = new Point3D(ray._p00);
        this._direction = ray._direction.normalized();
    }


    //********** Getters ***********/

    public Point3D get_p00() {
        return new Point3D(_p00);
    }

    public Vector get_direction() {
        return new Vector (_direction);
    }



    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray other_ray = (Ray) o;
        return _p00.equals(other_ray._p00) &&
                _direction.equals(other_ray._direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p00=" + _p00 +
                ", _direction=" + _direction +
                '}';
    }
}
