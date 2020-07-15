package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Composite class which includes components and composite geometries
 * <p>
 * NB: we use ArrayList because we prefer Constant-time positional access in the use of this class</p>
 */
public class Geometries implements Intersectable {
    private List<Intersectable> _geometries = new ArrayList<>();

    /**
     * Constructor which can have input of geometries
     *
     * @param geometries which the composite class will include
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }


    /**
     * add method allow to add (even zero) geometries to the composite class
     *
     * @param geometries which we want to add to the composite class
     * @return the geometries after the add
     */
    public Geometries add(Intersectable... geometries) {
        _geometries.addAll(Arrays.asList(geometries));
        return this;
    }

    /**
     * remove method allow to remove (even zero) geometries from the composite class
     *
     * @param geometries which we want to add to the composite class
     * @return the geometries after the remove
     */
    public Geometries remove(Intersectable... geometries) {
        _geometries.removeAll(Arrays.asList(geometries));
        return this;
    }


    /**
     * findIntersections method will group all the points which the ray intersect in the entire composite geometry
     * in the maximum distance which defined
     *
     * @param ray         which could intersects the geometries
     * @param maxDistance the maximum distance we will like to calculate the intersections in it
     * @return list of points representing the intersection points of the geometries and ray
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geo : _geometries) {
            List<GeoPoint> intersections_in;
            intersections_in = geo.findIntersections(ray, maxDistance);
            if (intersections_in != null) {
                if (intersections == null) // first time we get intersection points from a geo in main list
                    intersections = new ArrayList<>(); // We will allocate memory for this
                intersections.addAll(intersections_in); // addAll points from the geo iterator to main intersections list
            }
        }
        return intersections;
    }


}