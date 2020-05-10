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

    // ***************** Constructors ********************** //

    /**
     * Light constructor accepting the intensity of colors value
     * creates the light's intensity
     * @param intensity the intensity of light (RGB colors)
     */
     public Light(Color intensity){
         _intensity = new Color(intensity);
     }

    // ***************** Getters ********************** //

    /**
     * getter for Light intensity
     *
     * @return the value of the Light intensity.
     */
    public Color getIntensity(){
        return new Color(_intensity);
    }
}
