package geometries;

import java.util.List;

import primitives.*;

import static primitives.Point3D.ZERO;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge path. The polygon must be convex.
     * with material and emission light color.
     *
     * @param material represent material of Polygon
     * @param emission represent emission light of Polygon
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Color emission, Material material, Point3D... vertices) {
        super(emission, material);
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal(ZERO);

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with the normal. If all the rest consequent edges will generate the same sign -
        // the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }


    /**
     * Polygon constructor based on vertices list, including emission light color. uses the main Constructor with material and emission light inputs.
     * The list must be ordered by edge path. The polygon must be convex.
     *
     * @param emission represent emission light of Polygon
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Color emission, Point3D... vertices) {
        this(emission, new Material(0, 0, 0), vertices);
    }

    /**
     * Polygon constructor based on vertices list. uses the main Constructor with material and emission light inputs.
     * The list must be ordered by edge path. The polygon must be convex. without material and emission light color.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        this(Color.BLACK, new Material(0, 0, 0), vertices);
    }


    /**
     * Polygon class has imaginary plane, plane has a method getNormal, so we will use his implementation
     *
     * @param point 3D point we supposed is in polygon
     * @return normalized vector which represent the normal of the polygon
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }


    /**
     * findIntersections method will group all the points which the ray intersect with the class Polygon
     * in case that the distance is beyond the maximum then it'll never count as intersection
     *
     * @param ray         which could intersects the Polygon
     * @param maxDistance is the maximum distance to find intersections in
     * @return list of points representing the intersection points of the polygon and ray
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        // ***First check if ray intersect the imaginary plane that can be created from the polygon
        List<GeoPoint> intersections = _plane.findIntersections(ray, maxDistance);
        if (intersections == null)
            return null;
        // ***Next check if it intersect in the polygon
        // 1.
        int n = _vertices.size();
        Vector[] vectors = new Vector[n]; // array of vectors v1,v2...vn
        int i = 0;
        for (; i < n; i++)
            vectors[i] = _vertices.get(i).subtract(ray.get_p00()); //vi = Pi − P0
        // 2.
        Vector[] normals = new Vector[n]; // array of vector normals N1,N2...Nn
        for (i = 0; i < n - 1; i++)
            normals[i] = vectors[i].crossProduct(vectors[i + 1]).normalize(); //Ni = normalize (vi × vi+1)
        normals[i] = (vectors[i].crossProduct(vectors[0])).normalize(); //Vn = normalize (vn × v1)
        // 3.
        double result;
        int last_sign, sign; // sign = 0 -> zero, sign = 1 -> positive, sign = -1 -> negative
        // let's say i = 0;
        result = ray.get_direction().dotProduct(normals[0]); // v ∙ N1
        last_sign = result >= 0 ? result == 0 ? 0 : 1 : -1;  // result of v ∙ Ni = 0/+/- (value doesn't matter, only if it's zero )
        for (i = 1; i < n; i++) {
            result = ray.get_direction().dotProduct(normals[i]);
            sign = result >= 0 ? result == 0 ? 0 : 1 : -1;  // v ∙ Ni = 0/+/-
            if (sign != last_sign || sign == 0) // not all signs are same. or there is a result of 0
                return null;    // if v ∙ Ni = 0 it means that the ray is intersect the edge(tzelah) of polygon which belong to Ni
            // if two normals will result 0 it means that the ray intersect a vertex(nekuda) which belong to the two Ni and Nj
            last_sign = sign;
        }
        // ***Final, it means it does intersect in one point in polygon
        intersections.get(0).geometry = this;
        return intersections;
    }

    //*************** Admin *****************//

    /**
     * Polygon method equals implementation
     *
     * @param obj to compare with
     * @return boolean result (same values of the n points in polygon)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Triangle)) return false;
        Polygon other_polygon = (Polygon) obj;
        if (other_polygon._vertices.size() != this._vertices.size()) return false;
        // Purpose: Make sure that all vertices of one polygon exist in another polygon
        // (after they are both known to have n points)
        boolean flag;
        for (Point3D vertex : _vertices) {
            flag = false;
            for (Point3D others_vertex : other_polygon._vertices)
                if (others_vertex.equals(vertex)) {
                    flag = true;
                    break;
                }
            if (!flag)
                return false;
        }
        return true;
    }

}
