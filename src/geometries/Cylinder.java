package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Cylinder represent a smooth surface which defined with length, curvature at every point on its face fixed.
 * Represented by ray and radius, (direction, point which belong to infinite line, radius)
 */
public class Cylinder extends Tube {

    private double _height;


    //*********** Constructors ***********//

    /**
     * Main Constructor of class Cylinder, inputs are height ray and radius,
     * including material and emission light color.
     *
     * @param material is the object's effects from lights, three values: diffusion, specular, and shininess.
     * @param emission represent color of Cylinder
     * @param height   represent the height of the cylinder, because it's actually a tube with length
     * @param ray      represent the direction and the place
     * @param radius   the radius from the center line to the surface
     */
    public Cylinder(Color emission, Material material, double height, Ray ray, double radius) {
        super(emission, material, ray, radius);
        _height = height;
    }

    /**
     * Constructor of class Cylinder, including emission light color, without material input.
     * uses the main Constructor of Cylinder with material and emission light inputs.
     *
     * @param emission represent color of Cylinder
     * @param height   represent the height of the cylinder, because it's actually a tube with length
     * @param ray      represent the direction and the place
     * @param radius   the radius from the center line to the surface
     */
    public Cylinder(Color emission, double height, Ray ray, double radius) {
        this(emission, Material.DEFAULT, height, ray, radius);
    }

    /**
     * Constructor of class Cylinder, without material and emission light color inputs.
     * uses the main Constructor of Cylinder with material and emission light inputs.
     *
     * @param height represent the height of the cylinder, because it's actually a tube with length.
     * @param ray    represent the direction and the place
     * @param radius the radius from the center line to the surface
     */
    public Cylinder(double height, Ray ray, double radius) {
        this(Color.BLACK, Material.DEFAULT, height, ray, radius);
    }


    //********** Getters ***********/

    /**
     * getter to the height's cylinder as a double value
     *
     * @return the length/height of the cylinder
     */
    public double get_height() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D p) {
        double t = _axisRay.get_direction().dotProduct(p.subtract(_axisRay.get_p00()));
        if (isZero(t - _height)) // if point located on upper base
            return _axisRay.get_direction().normalize();
        else if (isZero(t))  // if point located in lower base
            return _axisRay.get_direction().normalize().scale(-1);
        // >>> of course if height is less than t than throw exception - the point doesn't on the surface
        Point3D o = _axisRay.get_p00().add(_axisRay.get_direction().scale(t)); // if point located on the side
        return p.subtract(o).normalize(); //.scale(-1); ?
    }


    //*************** Admin *****************//

    /**
     * Tube method equals implementation
     *
     * @param obj to compete with
     * @return boolean result (same height, same location point, same direction, same radius)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Cylinder)) return false;
        Cylinder other_cylinder = (Cylinder) obj;
        return (isZero(this._height - other_cylinder._height)) &&
                (this._axisRay.get_p00().equals(other_cylinder._axisRay.get_p00())) &&
                (this._axisRay.get_direction().equals(other_cylinder._axisRay.get_direction())) &&
                isZero(this._radius - other_cylinder._radius);
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * findIntersections method will group all the points which the ray intersect with the class Tube
     * Cases are: ray cross cylinder (2 points) ,ray launch cylinder(1 points), ray doesn't intersect cylinder at all (0 points)
     * or that the distance is beyond the maximum (0 points).
     *
     * @param ray         which could intersect the cylinder
     * @param maxDistance is the maximum distance to find intersections in
     * @return list of points
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        //if (!getBoundingBox().intersects(ray, maxDistance)) return null;
        // Step 1: intersect with tube
        List<GeoPoint> intersections = super.findIntersections(ray, maxDistance);
        if (intersections == null) return null;
        // Step 2: intersect is between caps
        Vector va = _axisRay.get_direction();
        Point3D A = _axisRay.get_p00();
        Point3D B = _axisRay.get_target_point(_height);
        double lowerBound, upperBound;
        List<GeoPoint> intersectionsCylinder = new ArrayList<>();
        for (GeoPoint gPoint : intersections) {
            lowerBound = va.dotProduct(gPoint.point.subtract(A));
            upperBound = va.dotProduct(gPoint.point.subtract(B));
            if (lowerBound > 0 && upperBound < 0) {
                // the check for distance, if the intersection point is beyond the distance
                if (alignZero(gPoint.point.distance(ray.get_p00()) - maxDistance) <= 0)
                    intersectionsCylinder.add(gPoint);
            }
        }
        // Step 3: intersect with each plane which belongs to the caps
        Plane planeA = new Plane(this._emission, this._material, A, va);
        Plane planeB = new Plane(this._emission, this._material, B, va);
        List<GeoPoint> intersectionPlaneA = planeA.findIntersections(ray, maxDistance);
        List<GeoPoint> intersectionPlaneB = planeB.findIntersections(ray, maxDistance);
        if (intersectionPlaneA == null && intersectionPlaneB == null)
            return intersectionsCylinder;
        // Step 4: intersect inside caps
        Point3D q3, q4;
        if (intersectionPlaneA != null) {
            q3 = intersectionPlaneA.get(0).point;
            if (q3.subtract(A).lengthSquared() < _radius * _radius) {
                intersectionsCylinder.add(intersectionPlaneA.get(0));
            }
        }
        if (intersectionPlaneB != null) {
            q4 = intersectionPlaneB.get(0).point;
            if (q4.subtract(B).lengthSquared() < _radius * _radius) {
                intersectionsCylinder.add(intersectionPlaneB.get(0));
            }
        }
        if (intersectionsCylinder.isEmpty()) return null;
        return intersectionsCylinder;
    }

}
