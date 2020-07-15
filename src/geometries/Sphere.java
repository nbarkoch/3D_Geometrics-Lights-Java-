package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Sphere is defined as the set of points that are all at the same distance _radius from a given point,
 * represented by center point for location and radius which is the distance
 */
public class Sphere extends RadialGeometry {
    private Point3D _center;


    //*********** Constructors ***********//

    /**
     * Main Constructor for sphere, by entrance of center point and radius, including material and emission light color.
     *
     * @param material is the object's effects from lights, three values: diffusion, specular, and shininess.
     * @param emission represent color of Sphere
     * @param center   point represent the center of the sphere
     * @param radius   radius represent the distance between each point on sphere to the cnter
     */
    public Sphere(Color emission, Material material, double radius, Point3D center) {
        super(emission, material, radius);
        _center = center;
    }

    /**
     * Constructor for sphere, by entrance of center point and radius, with emission light color input
     * and without material input. uses the main Constructor of Sphere with material and emission light inputs.
     *
     * @param emission represent color of Sphere
     * @param center   point represent the center of the sphere
     * @param radius   radius represent the distance between each point on sphere to the cnter
     */
    public Sphere(Color emission, double radius, Point3D center) {
        this(emission, new Material(0, 0, 0), radius, center);
    }

    /**
     * Constructor for sphere, by entrance of center point and radius, no material and emission light color
     * uses the main Constructor of Sphere with material and emission light inputs.
     *
     * @param center point represent the center of the sphere
     * @param radius radius represent the distance between each point on sphere to the cnter
     */
    public Sphere(double radius, Point3D center) {
        this(Color.BLACK, new Material(0, 0, 0), radius, center);
    }


    //********** Getters ***********/

    /**
     * get the center point of class sphere
     *
     * @return the center point of sphere
     */
    public Point3D getCenter() {
        return _center;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return p.subtract(_center).normalize();
    }


    //*************** Admin *****************//

    /**
     * Sphere method equals
     *
     * @param obj to compete with
     * @return boolean result (same values of the center points and radius)
     */
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
     * or that the distance is beyond the maximum (0 points).
     * NB: All the algorithm works successfully because of the normalize method in Ray direction vector
     *
     * @param ray         which could intersects the Triangle
     * @param maxDistance is the maximum distance to find intersections in
     * @return list of points representing the intersection points of the triangle and ray
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        //if (!boundingVolume.intersectBoundingVolume(ray)) return null;
        if (_center.equals(ray.get_p00())) // if we do _center.subtract(_p00) we will get vector with ZERO direction exception
            return List.of(new GeoPoint(this, ray.get_target_point(_radius)));
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
        // takes only positive and no zero results, also in the distance:
        if (alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0 && t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.get_target_point(t1)), new GeoPoint(this, ray.get_target_point(t2)));
        if (alignZero(t1 - maxDistance) <= 0 && t1 > 0)
            return List.of(new GeoPoint(this, ray.get_target_point(t1)));
        if (alignZero(t2 - maxDistance) <= 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.get_target_point(t2)));
        return null;
    }

    /**
     * method sets the values of the bounding volume for the intersectable sphere
     */
    @Override
    public void setBoundingRegion() {
        super.setBoundingRegion();
        _boundingBox.setBoundingBox(
                _center.getX() - _radius,
                _center.getX() + _radius,
                _center.getY() - _radius,
                _center.getY() + _radius,
                _center.getZ() - _radius,
                _center.getZ() + _radius
        );
    }
}
