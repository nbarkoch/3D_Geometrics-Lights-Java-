package primitives;

/**
 * Material class represent the reflection of a light component on it,
 * in three known values: diffusion, specular, and shininess.
 */
public class Material {
    double _kD;
    double _kS;
    int _nShininess;

    /**
     * Material Constructor represented by three values of lighting reflection on a component
     * @param kD diffusion - net movement of light from a region of higher concentration to a region of lower concentration.
     * @param kS specular - reflected light directed, as from a smooth, polished surface (opposed to diffuse).
     * @param nShininess shininess -  Blurs the specular highlight,
     *                   which is the spot of light that appears on shiny objects when illuminated.
     *                   A higher value will make the specular highlight smaller
     *                   and a lower value will make the highlight broader.
     */
    public Material(double kD, double kS, int nShininess){
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
    }

    /**
     * getter for the diffusion reflection value of the material
     * @return diffusion reflection value
     */
    public double getKD() {
        return _kD;
    }

    /**
     * getter for the specular reflection value of the material
     * @return specular reflection value
     */
    public double getKS() {
        return _kS;
    }

    /**
     * getter for the shininess effect value of the material
     * @return shininess effect value
     */
    public int getNShininess() {
        return _nShininess;
    }


}
