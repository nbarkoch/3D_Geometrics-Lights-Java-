package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Class Plane is 2D basic object in geometry which represented by two vectors which come from the same point and
 * have different directions (together reflecting the basic 2D object.)
 * defined by a point and a normal vector
 */
public class Plane implements Geometry {
    Point3D _p;
    Vector _normal;

    /**
     * Plane constructor must be defined here by three vectors, one of them represent the beginning point,
     * and the two others represent the normal vector
     * @param vertex use it for the location
     * @param vertex1 vector represent one dimension
     * @param vertex2 vector represent other dimension
     */
    public Plane(Point3D vertex, Point3D vertex1, Point3D vertex2) {
        _normal = new Vector(vertex1.get_y().get()*vertex2.get_z().get() - vertex1.get_z().get()*vertex2.get_y().get(),
                vertex1.get_z().get()*vertex2.get_x().get() - vertex1.get_x().get()*vertex2.get_z().get(),
                vertex1.get_x().get()*vertex2.get_y().get() - vertex1.get_y().get()*vertex2.get_x().get());

    }

    /**
     * Plane constructor must be defined here by normal vector and point, by normal vector we can represent two vectors
     * which creates the 2D plane object, by the point we can represent the location
     * @param p represent the location
     * @param normal represent the two dimensions
     */
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
