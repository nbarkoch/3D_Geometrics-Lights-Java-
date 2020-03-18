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
        _normal = new Vector(vertex1.get_y().get()*vertex2.get_z().get() - vertex1.get_z().get()*vertex2.get_y().get(),
                vertex1.get_z().get()*vertex2.get_x().get() - vertex1.get_x().get()*vertex2.get_z().get(),
                vertex1.get_x().get()*vertex2.get_y().get() - vertex1.get_y().get()*vertex2.get_x().get());

    }

    public Plane(Point3D p, Vector normal){
        _normal = normal;
        _p = p;
    }


    @Override
    public Vector getNormal(Point3D _point) {
        return null;
        //return new Vector(_normal);
    }

    public Vector getNormal() {
        return getNormal(Vector.ZERO.get_head());
    }
}
