package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all 3D geometry objects with getNormal method
 */
public interface Geometry {
    Vector getNormal(Point3D _point);
}
