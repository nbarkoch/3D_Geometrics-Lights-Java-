package elements;

import primitives.Color;

/**
 * Abstract Class Light is a basic element which is defined by the intensity of colors of the lighting,
 * Refers to the light propagation model.
 * its function is to illuminate the objects with colors in different ways.
 */
public abstract class Light {
    /**
     * the color of the light, the intensity of RGB colors
     */
    protected Color _intensity;

    /**
     * the radius of the light, lights in this project are basic, doesn't have special surfaces,
     * therefore the few of our lights will have attribute of sphere shape.
     */
    protected double _radius = 0;

    // ***************** Constructors ********************** //

    /**
     * Light constructor accepting the intensity of colors value
     * creates the light's intensity
     *
     * @param intensity the intensity of light (RGB colors)
     */
    public Light(Color intensity) {
        _intensity = new Color(intensity);
    }

    // ***************** Getters ********************** //

    /**
     * getter for Light intensity
     *
     * @return the value of the Light intensity.
     */
    public Color getIntensity() {
        return new Color(_intensity);
    }

    /**
     * getter for the radius of light
     *
     * @return the value of the radius of the light
     */
    public double getRadius() {
        return _radius;
    }
}
