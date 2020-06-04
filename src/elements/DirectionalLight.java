package elements;


import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Directional Light defined by the intensity of light emitted from an undefined, and vector direction.
 * Because the energy propagation is uniform in its direction,
 * It will always go from surface to surface in the same way that energy is stored, no attention to distance
 * and the whole scene receives the intensity of light in the same way. (Il = I0)
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * The direction of the directional light
     */
    private Vector _direction;

    /**
     * Constructor of Directional Light defined by the direction and intensity of light
     *
     * @param intensity of the directional light
     * @param direction of the directional light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     * gets the intensity of a point that influenced by the spot light,
     *
     * @param point no attention to distance, so the point isn't a consumed parameter
     * @return the intensity of the directional light
     */
    public Color getIntensity(Point3D point) {
        return new Color(_intensity);
    }

    /**
     * gets the direction vector from the directional light to the selected point of the object
     *
     * @param point a point that lies on the surface of the shape
     * @return the normalized direction vector of the directional light which is uniform in its direction in any location
     */
    public Vector getL(Point3D point) {
        return new Vector(_direction);
    }

    /**
     * gets the distance between a point (which suppose to be in a geometry) and the directional light location
     *
     * @param point the location point we want to calculate the distance between it and the location of light
     * @return the value of distance (infinity value, we refer to it as sunlight)
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
