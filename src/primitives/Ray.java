package primitives;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.sqrt;
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
     * @param p00       point3D representing the beginning of the ray
     * @param direction vector representing the direction,
     *                   by the start point and direction we can find the end point of the ray
     */
    public Ray(Point3D p00, Vector direction) {
        this._p00 = p00;
        this._direction = direction.normalized();
    }

    /**
     * Copy constructor for a deep copy of an Ray object.
     *
     * @param ray the ray which been copied
     */
    public Ray(Ray ray) {
        this._p00 = ray._p00;
        this._direction = ray._direction;
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
        return _p00;
    }

    public Vector get_direction() {
        return _direction;
    }

    public Point3D get_target_point(double length) {
        return isZero(length) ? _p00 : _p00.add(_direction.scale(length));
    }

    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Ray)) return false;
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


    /**
     * auxiliary function to randomly scatter points within a circular surface.
     * returns a list of rays which related to the surface.
     *
     * @param center  center point of the circular surface.
     * @param vUp     upper vector of circular surface.
     * @param vRight  right vector of circular surface.
     * @param radius  radius of circular surface.
     * @param numRays number of rays we create in the circular surface.
     * @param dist    distance from circular surface to the other point. the point could be the target or the beginning.
     * @return list of rays from one point toward the surface and vice versa (rays from the surface to one point)
     */
    public List<Ray> randomRaysInCircle(Point3D center, Vector vUp, Vector vRight, double radius, int numRays, double dist) {
        List<Ray> rays = new LinkedList<>();
        rays.add(this); // including the original ray
        if (radius == 0) // radius input zero means there's no circular surface.
            return rays;
        Point3D p0 = _p00; // starting point
        Point3D target = get_target_point(dist);
        for (int i = 1; i < numRays; ++i) {
            double cosTetah = -1 + (Math.random() * 2); // min = -1, max = 1, which means the degree of the line which the points located (between 0 to Pie)
            double sinTetah = sqrt(1 - cosTetah * cosTetah); //
            double d = -radius + (Math.random() * (2 * radius));// min = -radius, max = +radius, which means the location on the diameter
            // Move from polar to Cartesian system:
            double x_move = d * cosTetah;
            double y_move = d * sinTetah;
            Point3D pMove; // location point after move from center point of circular surface.
            pMove = center;
            if (!isZero(x_move))
                pMove = pMove.add(vRight.scale(x_move));
            if (!isZero(y_move))
                pMove = pMove.add(vUp.scale(y_move));
            // the vectors normalized inside Ray constructor
            if (p0.equals(center))
                rays.add(new Ray(pMove, (target.subtract(pMove)))); // from surface
            else
                rays.add(new Ray(p0, (pMove.subtract(p0)))); // toward surface
        }
        return rays;
    }


    /**
     * Method that distributes points on a grid-shaped square surface/matrix. inside a single pixel.
     * except for the original mid-point surface saved.
     * it's a case where the rays come from one point to random points in a pixel of the view plane.
     * we're using the original ray which gives us the starting point and the original direction
     *
     * @param numRays number of rays we create from the beginning point (location of camera) to the view plane.
     *                (must have an integer sqrt)
     * @param vUp     upper vector of square surface.
     * @param vRight  right vector of square surface.
     * @param dist    of the camera from the view plane
     * @param rX      the width of a single pixel in view plane
     * @param rY      the height of a single pixel in view plane
     * @return the list of the rays which come from the same point towards the surface of a single chosen pixel
     */
    public List<Ray> raysInGrid(Vector vUp, Vector vRight, int numRays, double dist, double rX, double rY) {
        List<Ray> rays = new LinkedList<>();
        Point3D p0 = _p00;
        Point3D pC = get_target_point(dist);
        // we will create number of points which will located like in matrix (with size raysOneDim X raysSecondDim)
        int raysOneDim = (int) sqrt(numRays);
        // point to point distance
        // sliding units for each ray relative to the pixel size and number of rays that can be at the same row/column
        double x_move = rX / raysOneDim;
        double y_move = rY / raysOneDim;
        // starting point is sliding from center point in half the pixel size in the axis of the view plane
        // in the negative directions of matrix on surface
        Point3D p00 = pC.add(vRight.scale((raysOneDim / 2) * x_move)).add(vUp.scale((-raysOneDim / 2) * y_move));
        if (numRays % 2 == 0)
            p00.add(vRight.scale(-x_move / 2)).add(vUp.scale(y_move / 2));
        Point3D pMove;
        for (int i = 0; i < raysOneDim; ++i) {
            for (int j = 0; j < raysOneDim; ++j) {
                pMove = p00;
                // Moving the point within a range of half the width and length
                // of a sliding unit that is a point-to-point distance
                double random_moveX = -0.5 * x_move + (Math.random() * ((0.5 * x_move + 0.5 * x_move)));
                double random_moveY = -0.5 * y_move + (Math.random() * ((0.5 * y_move + 0.5 * y_move)));
                if (!isZero(i * x_move + random_moveX))
                    pMove = pMove.add(vRight.scale((i) * x_move + random_moveX));
                if (!isZero(j * y_move + random_moveY))
                    pMove = pMove.add(vUp.scale((j) * y_move + random_moveY));
                if (i == raysOneDim / 2 && j == i) pMove = pC;
                rays.add(new Ray(p0, (pMove.subtract(p0)))); // normalized inside Ray constructor
            }
        }
        return rays;
    }


}
