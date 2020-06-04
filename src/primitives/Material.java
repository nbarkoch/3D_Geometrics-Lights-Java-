package primitives;

/**
 * Material class represent the reflection of a light component on it,
 * in three known values: diffusion, specular, and shininess.
 */
public class Material {

    /**
     * Diffusion coefficient
     */
    double _kD;

    /**
     * Specular coefficient
     */
    double _kS;

    /**
     * Level of shininess
     */
    int _nShininess;

    /**
     * Refraction coefficient (example: glass)
     */
    double _kT;

    /**
     * Reflection coefficient (example: mirror)
     */
    double _kR;

    /**
     * Material Constructor represented by three values of lighting reflection on a component
     *
     * @param kD         diffusion - net movement of light from a region of higher concentration to a region of lower concentration.
     * @param kS         specular - reflected light directed, as from a smooth, polished surface (opposed to diffuse).
     * @param nShininess shininess -  Blurs the specular highlight,
     *                   which is the spot of light that appears on shiny objects when illuminated.
     *                   A higher value will make the specular highlight smaller
     *                   and a lower value will make the highlight broader.
     * @param kR         Reflection of material from a light source,
     *                   there will be a reflection of light according to the value.
     *                   in this project, the value is about to be or 0 (for a transparent geometry)
     *                   or 1 for all the other geometries.
     * @param kT         Refraction level of the material from a light source passing through it
     */
    public Material(double kD, double kS, int nShininess, double kT, double kR) {
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
        this._kT = kT;
        this._kR = kR;
    }

    /**
     * Material Constructor represented by three values of lighting reflection on a component, using the main constructor
     * with no values for the transparency and refraction.
     *
     * @param kD         diffusion - net movement of light from a region of higher concentration to a region of lower concentration.
     * @param kS         specular - reflected light directed, as from a smooth, polished surface (opposed to diffuse).
     * @param nShininess shininess -  Blurs the specular highlight,
     *                   which is the spot of light that appears on shiny objects when illuminated.
     *                   A higher value will make the specular highlight smaller
     *                   and a lower value will make the highlight broader.
     */
    public Material(double kD, double kS, int nShininess) {
        this(kD, kS, nShininess, 0, 0);
    }

    /**
     * getter for the diffusion reflection value of the material
     *
     * @return diffusion reflection value
     */
    public double getKD() {
        return _kD;
    }

    /**
     * getter for the specular reflection value of the material
     *
     * @return specular reflection value
     */
    public double getKS() {
        return _kS;
    }

    /**
     * getter for the shininess effect value of the material
     *
     * @return shininess effect value
     */
    public int getNShininess() {
        return _nShininess;
    }

    /**
     * getter for the refraction value of the material
     *
     * @return Refraction value
     */
    public double getKT() {
        return _kT;
    }

    /**
     * getter for the reflection value of the material
     *
     * @return Reflection value
     */
    public double getKR() {
        return _kR;
    }

}
