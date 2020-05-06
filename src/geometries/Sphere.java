package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * Class Sphere is defined as the set of points that are all at the same distance _radius from a given point,
 * represented by center point for location and radius which is the distance
 */
public class Sphere extends RadialGeometry {
    private Point3D _center;


    //*********** Constructors ***********//

    /**
     * Constructor for sphere, by entrance of center point and radius
     *
     * @param center point represent the center of the sphere
     * @param radius radius represent the distance between each point on sphere to the cnter
     */
    public Sphere(Point3D center, double radius) {
        super(radius);
        _center = new Point3D(center);
    }


    //********** Getters ***********/

    /**
     * get the center point of class sphere
     *
     * @return the center point of sphere
     */
    public Point3D get_center() {
        return new Point3D(_center);
    }

    @Override
    public Vector getNormal(Point3D p) {
        return p.subtract(_center).normalize().scale(-1);
    }


    /*************** Admin *****************/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Sphere)) return false;
        Sphere other_sphere = (Sphere) obj;
        return this._center.equals(other_sphere._center) && (Util.isZero(this._radius - other_sphere._radius));
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * findIntersections method will group all the points which the ray intersect with the class Sphere
     * Cases are: ray cross sphere (2 points) ,ray launch sphere(1 points), ray doesn't intersect sphere at all (0 points)
     * NB: All the algorithm works successfully because of the normalize method in Ray direction vector
     *
     * @param ray which could intersects the Triangle
     * @return list of points representing the intersection points of the triangle and ray
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if (_center.equals(ray.get_p00())) // if we do _center.subtract(_p00) we will get vector with ZERO direction exception
            return List.of(ray.get_target_point(_radius));
        Vector u = _center.subtract(ray.get_p00()); // u = O − P0
        double tm = Util.alignZero(u.dotProduct(ray.get_direction())); // tm = v∙u
        double d = Math.sqrt(u.lengthSquared() - tm * tm); // couldn't get negative number because v normalized, tm*tm <= |u|

        if (d > _radius) //  there are no intersections (outside of sphere)
            return null;
        double th = Util.alignZero(Math.sqrt(_radius * _radius - d * d));
        if (th == 0) // if ray launches sphere
            return null;
        double t1 = Util.alignZero(tm + th);
        double t2 = Util.alignZero(tm - th);
        // takes only positive and no zero results:
        return t1 > 0 && t2 > 0 ? List.of(ray.get_target_point(t1), ray.get_target_point(t2)) :
                t1 <= 0 && t2 <= 0 ? null :
                        t1 <= 0 ? List.of(ray.get_target_point(t2)) :
                                List.of(ray.get_target_point(t1));
    }
}
