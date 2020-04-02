package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Class Tube represent a smooth infinite surface, curvature at every point on its face fixed.
 * That is the cylinder which does not have a length.
 * Represented by ray and radius, (direction, first and second points, radius)
 */
public class Tube extends RadialGeometry {
    protected Ray _axisRay;


    //*********** Constructors ***********//

    /**
     * Constructor for class Tube,
     *
     * @param axisRay represent the direction and the place
     * @param radius  the radius from the center line to the surface
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        _axisRay = new Ray(axisRay);
    }

    /**
     * Copy Constructor of class Tube, deep copy of the members: radius, ray
     *
     * @param tube the tube to do deep copy
     */
    public Tube(Tube tube) {
        super(tube._radius);
        _axisRay = new Ray(tube._axisRay);
    }

    //********** Getters ***********/

    /**
     * getter to the ray (values of direction and place) which represent the tube
     *
     * @return value ray of the tube
     */
    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }

    @Override
    public Vector getNormal(Point3D p) {
        // Vp = p - p00 (vector from p00 to p)
        // t = V * Vp (number result represent dotProduct between direction of ray and the Vector Vp)
        double t = _axisRay.get_direction().dotProduct(p.subtract(_axisRay.get_p00()));
        // for o point we need to add point p00 as a start point to direction t*V
        Point3D o = _axisRay.get_p00();
        if (!isZero(t))  // we can't create the t*V vector if result with direction ZERO
        {
            // Scalar multiplication with number t and vector of ray
            // will give as a vector representing the direction with length from p0 to point o
            o = o.add(_axisRay.get_direction().scale(t));
        }
        return p.subtract(o).normalize(); // .scale(-1) ??
    }


    /*************** Admin *****************/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Tube)) return false;
        Tube other_tube = (Tube) obj;
        // need same direction, don't need same length
        // compare points of tubes, compare radius and compare directions of tubes (could be -1 from the other but it's the same)
        try  // two beginning points should be on the same line (crossProduct should give point ZERO as a direction point)
        {    // if we do (p1 - p2) we get a vector from p2 to p1, it suppose to be the same direction of the two tubes.
            this._axisRay.get_p00().subtract(other_tube._axisRay.get_p00()).crossProduct(other_tube._axisRay.get_direction());
        } catch (IllegalArgumentException exception2) {
            return (isZero(this._radius - other_tube._radius)) &&
                    (this._axisRay.get_direction().equals(other_tube._axisRay.get_direction())
                            || this._axisRay.get_direction().equals(other_tube._axisRay.get_direction().scale(-1)));
        }
        return false;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }


}
