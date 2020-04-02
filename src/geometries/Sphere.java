package geometries;

import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * Class Sphere is defined as the set of points that are all at the same distance _radius from a given point,
 * represented by center point for location and radius which is the distance
 */
public class Sphere extends RadialGeometry {
    Point3D _center;


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
     * get the center point f class sphere
     *
     * @return the center point of sphere
     */
    public Point3D get_center() {
        return new Point3D(_center);
    }

    // TODO: implementation
    @Override
    public Vector getNormal(Point3D p) {
        return null;
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

}
