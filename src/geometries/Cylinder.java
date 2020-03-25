package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Class Cylinder represent a smooth surface which defined with length, curvature at every point on its face fixed.
 * Represented by ray and radius, (direction, first and second points, radius)
 */
public class Cylinder extends Tube{

    double _height;


    //*********** Constructors ***********//

    /**
     * Constructor of class Cylinder
     * @param height represent the height of the cylinder, because it's actually a tube with length
     * @param ray represent the direction and the place
     * @param radius the radius from the center line to the surface
     */
    public Cylinder (double height, Ray ray, double radius)
    {
        super(ray, radius);
        _height = height;
    }


    //********** Getters ***********/

    /**
     * getter to the height's cylinder as a double value
     * @return the length/height of the cylinder
     */
    public double get_height() { return _height;}

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
        if (!(obj instanceof Cylinder)) return  false;
        Cylinder other_cylinder = (Cylinder ) obj;
        return (Util.isZero(this._height - other_cylinder._height)) &&
                (this._axisRay.get_p00().equals(other_cylinder._axisRay.get_p00())) &&
                super.equals(other_cylinder);
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }

}
