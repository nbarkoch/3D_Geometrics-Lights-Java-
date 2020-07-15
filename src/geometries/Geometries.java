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
public class Geometries extends Intersectable {
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
     * method sets the values of the bounding volume for the intersectable component
     */
    @Override
    public void setBoundingRegion() {
        super.setBoundingRegion(); // first, create a default bounding region if necessary
        for (Intersectable geo : _geometries) // in a recursive call set bounding region for all the
            geo.setBoundingRegion();          // components and composites inside
        double minX, maxX, minY, maxY, minZ, maxZ;
        minX = minY = minZ = Double.POSITIVE_INFINITY;
        maxX = maxY = maxZ = Double.NEGATIVE_INFINITY;
        for (Intersectable geo : _geometries) {
            minX = Math.min(geo._boundingBox.getMinX(), minX);
            maxX = Math.max(geo._boundingBox.getMaxX(), maxX);
            minY = Math.min(geo._boundingBox.getMinY(), minY);
            maxY = Math.max(geo._boundingBox.getMaxY(), maxY);
            minZ = Math.min(geo._boundingBox.getMinZ(), minZ);
            maxZ = Math.max(geo._boundingBox.getMaxZ(), maxZ);
        }
        // set the minimum and maximum values in 3 axes for this bounding region of the component
        _boundingBox.setBoundingBox(minX, maxX, minY, maxY, minZ, maxZ);
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
            intersections_in = geo.findIntersectBoundingRegion(ray, maxDistance);
            if (intersections_in != null) {
                if (intersections == null) // first time we get intersection points from a geo in main list
                    intersections = new ArrayList<>(); // We will allocate memory for this
                intersections.addAll(intersections_in); // addAll points from the geo iterator to main intersections list
            }
        }
        return intersections;
    }


    /**
     * method which creates hierarchy with the volumes of the components,
     * by grouping the closest components to single component. this implemintation makes the components having
     * only two objects as maximum.
     * @return the new structure of the geometries inside the current instance
     */
    public Geometries boundingVolumeHierarchy() {
        Geometries component = disassemblyGeometries(_geometries, new Geometries());
        Intersectable left = null, right = null;
        while (component._geometries.size() > 1) {
            double Best = Double.POSITIVE_INFINITY;
            for (Intersectable componentI : component._geometries) {
                for (Intersectable componentJ : component._geometries) {
                    if (!componentI._boundingBox.equals(componentJ._boundingBox) &&
                            componentI._boundingBox.getMaxDistance(componentJ._boundingBox) < Best) {
                        Best = componentI._boundingBox.getMaxDistance(componentJ._boundingBox);
                        left = componentI;
                        right = componentJ;
                    }
                }
            }
            Geometries componentTag = new Geometries(left, right);
            componentTag.setBoundingRegion();
            component.remove(left, right).add(componentTag).setBoundingRegion();
        }
        this._geometries = component._geometries;
        return this;
    }

    /**
     * method which flatten all the geometries inside some geometries so that all components/composites will have the
     * same depth of hierarchy tree which is 1,
     * the function is working in a recursive way to get to all the composites in the original tree
     * @param geometries the geometries we're about to disassembly
     * @param geometriesFinal the result of the disassembled geometries
     * @return the flatten geometries, the result
     */
    private Geometries disassemblyGeometries(List<Intersectable> geometries, Geometries geometriesFinal) {
        for (Intersectable geo : geometries) {
            if (geo instanceof Geometries) {
                disassemblyGeometries(((Geometries) geo)._geometries, geometriesFinal);
            } else {
                if (!geometriesFinal._geometries.contains(geo))
                    geometriesFinal.add(geo);
            }
        }
        return geometriesFinal;
    }


    /**
     * method which creates a string of all the hierarchy of the components and the composites in the tree
     * @return a string of all the hierarchy tree
     */
    public String hierarchyTree() {
        return hierarchyTree(this, " ");
    }

    /**
     * method which creates a string of all the hierarchy of the components and the composites in the tree.
     * works in a recursive way
     * @param geometries current geometries we want to show it's objects
     * @param tabs number of tabs whose for express the relationship between parent and sons
     * @return string of current 'geometries' and (in a recursive call) his sons
     */
    private String hierarchyTree(Geometries geometries, String tabs) {
        String str = "";
        for (Intersectable geo : geometries._geometries) {
            if (geo instanceof Geometries) {
                str += tabs + "Geometries: {\n";
                str += hierarchyTree((Geometries) geo, tabs + " ");
                str += tabs + "   }\n";
            } else {
                str += tabs + "   " + geo.getClass() + "\n";
            }
        }
        return str;
    }


}
