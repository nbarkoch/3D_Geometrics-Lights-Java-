package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Point3D.ZERO;
import static primitives.Util.isZero;

/**
 * Class Plane is 2D basic object in geometry which represented by two vectors which come from the same point and
 * have different directions (together reflecting the basic 2D object.)
 * defined by a point and a normal vector
 */
public class Plane implements Geometry {
    Point3D _p;
    Vector _normal;


    //*********** Constructors ***********//

    /**
     * Plane constructor must be defined here by three vectors, one of them represent the beginning point,
     * and the two others represent the normal vector
     *
     * @param P1 use it for the location
     * @param P2 vector represent one dimension
     * @param P3 vector represent other dimension
     */
    public Plane(Point3D P1, Point3D P2, Point3D P3) {
        _p = new Point3D(P1);
        Vector v1 = P2.subtract(P1);
        Vector v2 = P3.subtract(P1);
        _normal = (v1.crossProduct(v2)).normalize().scale(-1);
    }

    /**
     * Plane constructor must be defined here by normal vector and point, by normal vector we can represent two vectors
     * which creates the 2D plane object, by the point we can represent the location
     *
     * @param p      represent the location
     * @param normal represent the two dimensions
     */
    public Plane(Point3D p, Vector normal) {
        _normal = new Vector(normal);
        _p = new Point3D(p);
    }


    //********** Getters ***********/

    /**
     * getter of the point represent the location of class Plane
     *
     * @return Point3D representing the location of class Plane
     */
    public Point3D get_p() {
        return new Point3D(_p);
    }

    /**
     * get the vector normal of plane,
     * @param _point 3D point we supposed is in polygon
     * @return vector normal (_normal)
     */
    @Override
    public Vector getNormal(Point3D _point) {
        return new Vector(_normal);
    }

    // polygon get no input
    public Vector getNormal() {
        return getNormal(ZERO);
    }

    /**
     * equals method is for compare between two objects with checking if they have the same details which relevant to the class
     * in this method we compare details of class Plane, which represent by the vector normal and a 3D point.
     * @param obj the object to compare
     * @return boolean result True/False
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Plane)) return false;
        Plane other_plane = (Plane) obj;
        try // check if the points are in the same 3D Plane
        {
            _p.subtract(other_plane._p).crossProduct(_normal);
        } catch (IllegalArgumentException exception2) {   // check if the directions are the same
            return (_normal.equals(other_plane._normal) || other_plane._normal.scale(-1).equals(_normal));
        }
        return false;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_p=" + _p +
                ", _normal=" + _normal +
                '}';
    }

    /**
     * findIntersections method will group all the points which the ray intersect with the class Plane
     * @param ray which could intersects the Plane
     * @return list of points representing the intersection points of the plane and ray
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if (_p.equals(ray.get_p00())) // if ray begin in same point representing the plane
            return null;
        Vector v = ray.get_direction();
        if(isZero(_normal.dotProduct(v))) // if ray parallel to plane (normal orthogonal to ray's direction)
            return null;
        Vector u = _p.subtract(ray.get_p00());
        if(isZero(_normal.dotProduct(u))) // if ray begins in plane
            return null;

        double t = _normal.dotProduct(u)/_normal.dotProduct(v);
        // t = 0 - ray begins in plane, t < 0 - ray doesn't intersect the plane
        return t>0? List.of(ray.get_target_point(t)): null;
    }
}
