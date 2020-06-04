package primitives;

import static primitives.Util.isZero;

/**
 * Class Ray is the a basic object in geometry - the set of points on a straight line that are on one side relatively
 * to a given point on the straight line called the beginning of the ray. Defined by point and direction (unit vector).
 */
public class Ray {

    /**
     * beginning point of ray
     */
    private Point3D _p00;
    /**
     * direction of the ray
     */
    private Vector _direction;

    /**
     * a value of deviation of ray's head, for making the transparency, shading and more to work properly.
     * Due to inaccuracies in positioning points on the geometries,
     * it is necessary to move an apex in or out of the geometry in order for ray intersection to be calculated as required.
     */
    private static final double DELTA = 0.1;

    //********** Constructors ***********/

    /**
     * Constructor for creating a ray
     *
     * @param _p00       point3D representing the beginning of the ray
     * @param _direction vector representing the direction,
     *                   by the start point and direction we can find the end point of the ray
     */
    public Ray(Point3D _p00, Vector _direction) {
        this._p00 = new Point3D(_p00);
        this._direction = _direction.normalized();
    }

    /**
     * Copy constructor for a deep copy of an Ray object.
     *
     * @param ray the ray which been copied
     */
    public Ray(Ray ray) {
        this._p00 = new Point3D(ray._p00);
        this._direction = ray._direction.normalized();
    }

    /**
     * Construct a new ray which moved the head point by DELTA in direction of the secondary ray but
     * over the normal’s line. (this was required to be done here because of RDD)
     *
     * @param head      the beginning point of the original ray (=without shifting)
     * @param direction the direction of the original ray
     * @param normal    the normal of the geometry at the point which the ray intersect with
     */
    public Ray(Point3D head, Vector direction, Vector normal) {
        // The sign + or - is according to the sign of direction·normal
        int sign = direction.dotProduct(normal) > 0 ? 1 : -1;
        // if isZero(direction·normal) is zero it doesn't matter as much
        this._p00 = head.add(normal.scale(sign * DELTA));
        this._direction = direction.normalized();
    }


    //********** Getters ***********/

    public Point3D get_p00() {
        return new Point3D(_p00);
    }

    public Vector get_direction() {
        return new Vector(_direction);
    }

    public Point3D get_target_point(double length) {
        return isZero(length) ? _p00 : _p00.add(_direction.scale(length));
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
