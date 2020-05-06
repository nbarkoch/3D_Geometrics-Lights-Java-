package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Triangle is the basic geometric structure which consists of three points in space. The sum of all internal angles
 * in a triangle is always 180&#176; (degrees).
 * <p>
 * NB:</p>
 * <ul>
 * <li>we prefer to implement triangle by composition more than inheritance</li>
 * <li>furthermore, class Triangle isn't kind of Plane.</li>
 * </ul>
 * which means that we will have:
 * <ul>
 * <li>three points for definition, and not inheritance of class Plane.</li>
 * <li>Triangle is kind of Polygon, that's why we can do inheritance from this class.</li>
 * </ul>
 */
public class Triangle extends Polygon {
    //*********** Constructors ***********//

    /**
     * Constructor of Triangle
     *
     * @param vertex  first vertex representing the first point of triangle
     * @param vertex1 second vertex representing the second point of triangle
     * @param vertex2 third vertex representing the third and last point of triangle
     */
    public Triangle(Point3D vertex, Point3D vertex1, Point3D vertex2) {
        super(new Point3D[]{vertex, vertex1, vertex2});
    }

    /*************** Admin *****************/

    // still not quit sure if class Triangle shouldn't use the equal method which about to be made in polygon class
    // instead of this one
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Triangle)) return false;
        Triangle other_triangle = (Triangle) obj;
        // Purpose: Make sure that all vertices of one triangle exist in another triangle
        // (after they are both known to have 3 points)
        boolean flag;
        for (Point3D vertex : _vertices) {
            flag = false;
            for (Point3D others_vertex : other_triangle._vertices)
                if (others_vertex.equals(vertex)) {
                    flag = true;
                    break;
                }
            if (!flag)
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String output = "";
        for (Point3D vertex : _vertices)
            output = output + " " + vertex;
        return output;
    }

    /**
     * findIntersections method will group all the points which the ray intersect with the class Triangle
     *
     * @param ray which could intersects the Triangle
     * @return list of points representing the intersection points of the triangle and ray
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}
