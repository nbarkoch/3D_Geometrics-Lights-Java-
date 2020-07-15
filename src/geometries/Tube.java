package geometries;

import primitives.*;

import java.util.List;

import static java.lang.StrictMath.max;
import static java.lang.StrictMath.sqrt;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Tube represent a smooth infinite surface, curvature at every point on its face fixed.
 * That is the cylinder which does not have a length.
 * Represented by ray and radius, (direction, point which belong to infinite line, radius)
 */
public class Tube extends RadialGeometry {
    protected Ray _axisRay;


    //*********** Constructors ***********//

    /**
     * Main Constructor for class Tube, inputs are ray and radius, with material and emission light color
     *
     * @param material is the object's effects from lights, three values: diffusion, specular, and shininess.
     * @param emission represent color of Tube
     * @param axisRay  represent the direction and the place
     * @param radius   the radius from the center line to the surface
     */
    public Tube(Color emission, Material material, Ray axisRay, double radius) {
        super(emission, material, radius);
        _axisRay = new Ray(axisRay);
    }

    /**
     * Constructor for class Tube, inputs are ray and radius, with emission light color, with no material input
     * uses the main Constructor of Tube with material and emission light inputs.
     *
     * @param emission represent color of Tube
     * @param axisRay  represent the direction and the place
     * @param radius   the radius from the center line to the surface
     */
    public Tube(Color emission, Ray axisRay, double radius) {
        this(emission, Material.DEFAULT, axisRay, radius);
    }

    /**
     * Constructor for class Tube, no material and emission light color inputs
     * uses the main Constructor of Tube with material and emission light inputs.
     *
     * @param axisRay represent the direction and the place
     * @param radius  the radius from the center line to the surface
     */
    public Tube(Ray axisRay, double radius) {
        this(Color.BLACK, Material.DEFAULT, axisRay, radius);
    }

    /**
     * Copy Constructor of class Tube, deep copy of the members: radius, ray
     *
     * @param tube the tube to do deep copy
     */
    public Tube(Tube tube) {
        super(tube.getEmission(), tube.getMaterial(), tube._radius);
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


    //*************** Admin *****************//

    /**
     * Tube method equals implementation
     *
     * @param obj to compete with
     * @return boolean result (same direction (or opposite), same radius, two beginning points should be on the same line )
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Tube)) return false;
        Tube other_tube = (Tube) obj;
        // need same direction, both vectors are normalized so their length is 1
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

    /**
     * findIntersections method will group all the points which the ray intersect with the class Tube
     * Cases are: ray cross tube (2 points) ,ray launch tube(1 points), ray doesn't intersect tube at all (0 points)
     * or that the distance is beyond the maximum (0 points).
     *
     * @param ray         which could intersect the tube
     * @param maxDistance is the maximum distance to find intersections in
     * @return list of points
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        Point3D p = ray.get_p00();
        Point3D p0 = _axisRay.get_p00();
        Vector deltaP = p.subtract(p0);
        Vector v = ray.get_direction();
        Vector va = _axisRay.get_direction();
        double VxVa = v.dotProduct(va);
        double deltaPxVa = deltaP.dotProduct(va);
        double A, B, C;
        if (isZero(VxVa) && !isZero(deltaPxVa)) {
            A = v.lengthSquared();
            B = 2 * (v.dotProduct(deltaP.subtract(va.scale(deltaPxVa))));
            C = (deltaP.subtract(va.scale(deltaPxVa))).lengthSquared() - _radius * _radius;
        } else if (isZero(VxVa) && isZero(deltaPxVa)) {
            A = v.lengthSquared();
            B = 2 * (v.dotProduct(deltaP));
            C = (deltaP).lengthSquared() - _radius * _radius;
        } else if (!isZero(VxVa) && isZero(deltaPxVa)) {
            A = v.subtract(va.scale(VxVa)).lengthSquared();
            B = 2 * ((v.subtract(va.scale(VxVa))).dotProduct(deltaP));
            C = (deltaP).lengthSquared() - _radius * _radius;
        } else {
            A = (v.subtract(va.scale(VxVa))).lengthSquared();
            B = 2 * ((v.subtract(va.scale(VxVa))).dotProduct(deltaP.subtract(va.scale(deltaPxVa))));
            C = (deltaP.subtract(va.scale(deltaP.dotProduct(va)))).lengthSquared() - _radius * _radius;
        }
        double calc_d = B * B - 4 * A * C;
        if (calc_d < 0) return null;
        double t1 = (-1 * B + sqrt(calc_d)) / (2d * A);
        double t2 = (-1 * B - sqrt(calc_d)) / (2d * A);

        // takes only positive and no zero results, also in the distance:
        if (isZero(calc_d)) return List.of(new GeoPoint(this, ray.get_target_point(t1)));
        if (alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0 && t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.get_target_point(t1)), new GeoPoint(this, ray.get_target_point(t2)));
        if (alignZero(t1 - maxDistance) <= 0 && t1 > 0)
            return List.of(new GeoPoint(this, ray.get_target_point(t1)));
        if (alignZero(t2 - maxDistance) <= 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.get_target_point(t2)));
        return null;
    }

}
