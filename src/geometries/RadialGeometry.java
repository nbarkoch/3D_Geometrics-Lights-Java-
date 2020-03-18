package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;

public abstract class RadialGeometry implements Geometry{
    double _radius;



    /**
     *  Radial Geometry constructor receiving a radius value
     * @param radius radius value
     */
    public  RadialGeometry(double radius) {
        // if it too close to zero make it zero
        this._radius = alignZero(radius);
    }

    /**
     * Copy constructor for Radial Geometry
     * @param other RadialGeometry
     */
    public RadialGeometry(RadialGeometry other) {
        _radius = other._radius;
    }

    /**
     * Getter operation for the radius field
     * @return radius of the radial geometry
     */
    public double get_radius() {
        return _radius;
    }

    @Override
    public Vector getNormal(Point3D _point) {
        return null;
    }
}
