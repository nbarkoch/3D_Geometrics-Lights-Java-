package geometries;

import primitives.Point3D;

/**
 * Class Triangle is the basic geometric structure which consists of three points in space. The sum of all internal angles
 * in a triangle is always 180 degrees.
 */
public class Triangle extends Polygon {

    /**
     * Constructor of Triangle
     * @param vertex first vertex representing the first point of triangle
     * @param vertex1 second vertex representing the second point of triangle
     * @param vertex2 third vertex representing the third and last point of triangle
     */
    public Triangle(Point3D vertex, Point3D vertex1, Point3D vertex2) {
        super(new Point3D[]{vertex, vertex1, vertex2});
    }
}
