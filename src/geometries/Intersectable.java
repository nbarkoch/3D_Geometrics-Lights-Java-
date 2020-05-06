package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable is the interface for all objects which have the possibility to intersect with rays
 * including a method 'findIntersections' which find all points of intersection with the object and a ray
 */
public interface Intersectable {
    List<Point3D> findIntersections(Ray ray);
}
