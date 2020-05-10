package geometries;

import primitives.*;

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
     * Main Constructor of Triangle, with three points, material and emission light color inputs
     * @param material is the object's effects from lights, three values: diffusion, specular, and shininess.
     * @param emission represent color of Triangle
     * @param vertex   first vertex representing the first point of triangle
     * @param vertex1  second vertex representing the second point of triangle
     * @param vertex2  third vertex representing the third and last point of triangle
     */
    public Triangle(Color emission, Material material, Point3D vertex, Point3D vertex1, Point3D vertex2) {
        super(emission, material, vertex, vertex1, vertex2);
    }

    /**
     * Constructor of Triangle, including emission light color and without material input.
     * uses the main Constructor of Triangle with material and emission light inputs.
     *
     * @param emission represent color of Triangle
     * @param vertex   first vertex representing the first point of triangle
     * @param vertex1  second vertex representing the second point of triangle
     * @param vertex2  third vertex representing the third and last point of triangle
     */
    public Triangle(Color emission, Point3D vertex, Point3D vertex1, Point3D vertex2) {
        this(emission, new Material(0,0,0), vertex, vertex1, vertex2);
    }


    /**
     * Constructor of class Triangle, without material values and emission light color.
     * uses the main Constructor of Triangle with material and emission light inputs.
     *
     * @param vertex  first vertex representing the first point of triangle
     * @param vertex1 second vertex representing the second point of triangle
     * @param vertex2 third vertex representing the third and last point of triangle
     */
    public Triangle(Point3D vertex, Point3D vertex1, Point3D vertex2) {
        this(Color.BLACK, new Material(0,0,0), vertex, vertex1, vertex2);
    }


    //*************** Admin *****************//

    /**
     * Triangle method equals
     *
     * @param obj to compete with
     * @return boolean result (same values of the 3 points)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Triangle)) return false;
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
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = super.findIntersections(ray);
        if (intersections == null) return null;
        intersections.get(0).geometry = this;
        return intersections;
    }
}
