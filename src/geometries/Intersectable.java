package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Intersectable is the interface for all objects which have the possibility to intersect with rays
 * including a method 'findIntersections' which find all points of intersection with the object and a ray
 */
public interface Intersectable {

    /**
     * Class GeoPoint represent a 3D point which belong to specific geometry.
     * needed to show the object's color by intersection point from ray and the object.
     * since this class is just for to unify two values, (for intersection points) we will do them as public.
     */
    public static class GeoPoint {
        /**
         * the geometry which the point belong to
         */
        public Geometry geometry;
        /**
         * the point itself, the location
         */
        public Point3D point;

        /**
         * Constructor for class GeoPoint, since this class is just for to unify two values,
         * we will do them as public
         *
         * @param point    the point itself, the location
         * @param geometry the geometry which the point belong to
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.point = point;
            this.geometry = geometry;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;                   // step 1 - we are talking about the same geoPoint
            if (o == null || !(o instanceof GeoPoint))  // step 2 - we are talking about object which isn't a geoPoint or null
                return false;
            GeoPoint geoPoint = (GeoPoint) o;             // step 3 - let's treat it as a geoPoint, and check values:
            return this.geometry.equals((geoPoint).geometry) &&     // if it belong to the same defined geometry
                    this.point.equals(geoPoint.point);              // if it is has the same location's coordinates values
        }
    }


    //******


    /**
     * method of find intersections with only ray value input
     *
     * @param ray the ray which we about to check for intersection with it and some geometries which in her way
     * @return the intersection points with the ray and the geometries
     */
    default List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * method of find intersections with only ray value and distance inputs
     *
     * @param ray         the ray which we about to check for intersection with it and some geometries which in her way
     * @param maxDistance the maximum distance we will like to calculate the intersections in it
     * @return the intersection points with the ray and the geometries in the know distance
     */
    abstract List<GeoPoint> findIntersections(Ray ray, double maxDistance);

}
