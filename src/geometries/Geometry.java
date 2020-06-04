package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all 3D geometry objects with material and emission fields, and getNormal method (and has the possibility to be intersected)
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission;
    protected Material _material;

    public abstract Vector getNormal(Point3D _point);


    // ***************** Constructors ********************** //


    /**
     * Constructor for class Geometry, sets the input material to be the object's material's values, and
     * input emission light color to be the entire object's color
     *
     * @param material is the object's effects from lights, three values: diffusion, specular, and shininess.
     * @param emission is the object's own color which the camera can see in the scene,
     *                 it also has it's own light for the scene, but it isn't glowing outside it's shape.
     */
    public Geometry(Color emission, Material material) {
        this._material = material;
        this._emission = new Color(emission);
    }

    /**
     * Constructor for class Geometry, uses the main Constructor with material and emission light inputs.
     *
     * @param emission is the object's own color which the camera can see in the scene,
     *                 it also has it's own light for the scene, but it isn't glowing outside it's shape.
     */
    public Geometry(Color emission) {
        this(emission, new Material(0, 0, 0));
    }

    /**
     * Default Constructor for Class Geometry, uses the main Constructor with material and emission light inputs.
     */
    public Geometry() {
        this(Color.BLACK, new Material(0, 0, 0));
    }


    //*************** Getters ******************//

    /**
     * Getter returns the emission color of the geometry
     *
     * @return color of the geometry
     */
    public Color getEmission() {
        return new Color(_emission);
    }


    /**
     * Getter returns the material of the geometry
     *
     * @return material of the geometry
     */
    public Material getMaterial() {
        return _material;
    }
}
