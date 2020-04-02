package geometries;

import static primitives.Util.alignZero;


/**
 * Radial Geometry represent all the 3D geometry objects which including the param radius
 */
public abstract class RadialGeometry implements Geometry {

    double _radius;

    /**
     * Radial Geometry constructor receiving a radius value
     *
     * @param radius radius value
     */
    public RadialGeometry(double radius) {
        this._radius = alignZero(radius); // if it too close to zero make it zero
    }

    /**
     * Copy constructor for Radial Geometry
     *
     * @param other RadialGeometry
     */
    public RadialGeometry(RadialGeometry other) {
        _radius = other._radius;
    }

    /**
     * Getter operation for the radius field
     *
     * @return radius of the radial geometry
     */
    public double get_radius() {
        return _radius;
    }

}
