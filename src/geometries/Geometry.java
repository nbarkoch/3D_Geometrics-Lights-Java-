package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all 3D geometry objects with getNormal method (and has the possibility to be intersected)
 */
public interface Geometry extends Intersectable {
    Vector getNormal(Point3D _point);
}
