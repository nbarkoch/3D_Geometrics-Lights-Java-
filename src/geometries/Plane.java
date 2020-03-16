package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane:
 */
public class Plane implements Geometry {
    Point3D _p;
    Vector _normal;

    public Plane(Point3D vertex, Point3D vertex1, Point3D vertex2) {

    }

    @Override
    public Vector getNormal(Point3D _point) {
        return Vector.ZERO;
    }

    public Vector getNormal() {
        return getNormal(new Point3D(0.0, 0.0, 0.0));
    }
}
