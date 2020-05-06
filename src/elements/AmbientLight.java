package elements;

import primitives.Color;

/**
 * Ambient light class represents a fixed-intensity and fixed color light source
 * that affects all objects in the scene equally.
 *  <ul>
 * <li>Upon rendering, all objects in the scene are brightened with the specified intensity and color.</li>
 * <li>Mainly used to provide the scene with a basic view of the different objects in it.</li>
 * <li>The simplest type of lighting to implement and models how light can be</li>
 * <li>scattered or reflected many times producing a uniform effect.</li>
 *  </ul>
 */
public class AmbientLight {
    /**
     * the Ambient light intensity in point (I_P = K_A ∙ I_A)
     */
    private Color _intensity;

    // ***************** Constructors ********************** //

    /**
     * Ambient Light constructor accepting the intensity's value and the color light source's value
     * creates the fixed ambient light's intensity
     *
     * @param intensity color with some intensity
     * @param kA Attenuation coefficient
     */
    public AmbientLight(Color intensity, double kA) {
        _intensity = intensity.scale(kA);
    }

    // ***************** Getters ********************** //

    /**
     * getter for ambient lighting intensity
     *
     * @return the value of the color ambient lighting intensity.
     */
    public Color getIntensity() {
        return new Color(_intensity);
    }
}

