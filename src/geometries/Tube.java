package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Class Tube represent a smooth infinite surface, curvature at every point on its face fixed.
 * That is the cylinder which does not have a length.
 * Represented by ray and radius, (direction, first and second points, radius)
 */
public class Tube extends RadialGeometry {
    protected Ray _axisRay;


    //*********** Constructors ***********//

    /**
     * Constructor for class Tube,
     * @param axisRay represent the direction and the place
     * @param radius the radius from the center line to the surface
     */
    public Tube(Ray axisRay, double radius){
        super(radius);
        _axisRay = new Ray(axisRay);
    }

    /**
     * Copy Constructor of class Tube, deep copy of the members: radius, ray
     * @param tube the tube to do deep copy
     */
    public Tube(Tube tube){
        super(tube._radius);
        _axisRay = new Ray(tube._axisRay);
    }

    //********** Getters ***********/

     /**
     * getter to the ray (values of direction and place) which represent the tube
     * @return value ray of the tube
     */
    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }

    // TODO: implementation
    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }


    /*************** Admin *****************/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null ) return false;
        if (!(obj instanceof Tube)) return false;
        Tube other_tube = (Tube) obj;
        // need same direction, don't need same length
        // ray -> defined by direction vector, point for beginning
        // we need to see if they both have:
        // same direction (crossProduct),
        // and beginning points are on the same line (crossProduct)
        try //same direction (crossProduct should give ZERO as a direction point)
        {
            this._axisRay.get_direction().crossProduct(other_tube._axisRay.get_direction());
        }
        catch (IllegalArgumentException exception1)
        {        // after we know that the tubes are with same direction that we can compare their place in the dimension
                 // the two rays are suppose to be same with their direction and place on same infinite line
            try  // two beginning points should be on the same line (crossProduct should give point ZERO as a direction point)
            {    // if we do (p1 - p2) we get a vector from p2 to p1, it suppose to be the same direction of the two tubes.
                this._axisRay.get_p00().subtract(other_tube._axisRay.get_p00()).crossProduct(other_tube._axisRay.get_direction());
            }
            catch(IllegalArgumentException exception2)
            {
                return (Util.isZero(this._radius - other_tube._radius));
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }


}
