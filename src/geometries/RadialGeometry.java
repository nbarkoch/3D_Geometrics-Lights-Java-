package geometries;

import primitives.Color;
import primitives.Material;

import static primitives.Util.alignZero;


/**
 * Radial Geometry represent all the 3D geometry objects which including the param radius
 */
public abstract class RadialGeometry extends Geometry {

    protected double _radius;

    /**
     * Radial Geometry constructor receiving a radius value, material and emission light color inputs.
     *
     * @param material is the object's effects from lights, three values: diffusion, specular, and shininess.
     * @param emission represent color of the radial geometry
     * @param radius   radius value
     */
    public RadialGeometry(Color emission, Material material, double radius) {
        super(emission, material);
        this._radius = alignZero(radius); // if it too close to zero make it zero
    }

    /**
     * Radial Geometry constructor receiving a radius value and emission light color, no material.
     * uses the main Constructor with material and emission light inputs.
     *
     * @param emission represent color of the radial geometry
     * @param radius   radius value
     */
    public RadialGeometry(Color emission, double radius) {
        this(emission, new Material(0, 0, 0), radius);
    }

    /**
     * Radial Geometry constructor receiving a radius value, without material and emission light color
     * uses the main Constructor with material and emission light inputs.
     *
     * @param radius radius value
     */
    public RadialGeometry(double radius) {
        this(Color.BLACK, new Material(0, 0, 0), radius);
    }

    /**
     * Copy constructor for Radial Geometry
     *
     * @param other RadialGeometry
     */
    public RadialGeometry(RadialGeometry other) {
        super(other._emission);
        _radius = other._radius;
    }

    /**
     * Getter operation for the radius field
     *
     * @return radius of the radial geometry
     */
    public double getRadius() {
        return _radius;
    }

}
