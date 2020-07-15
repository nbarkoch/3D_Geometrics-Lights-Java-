package elements;


import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Point light is defined by the location of the lighting, and the intensity of light, (and attenuation coefficients).
 * It transmits energy in all directions on an equal level.
 * The attenuation constants give better control over the lighting model to the extent that the distance affects the intensity.
 */
public class PointLight extends Light implements LightSource {
    /**
     * location of the light source
     */
    protected Point3D _position;
    /**
     * by the attenuation factors give better control of the lighting model to the extent that the distance affects the intensity.
     */
    protected double _kA, _kB, _kC;

    /**
     * Point light constructor gets 6 parameters
     *
     * @param intensity intensity of light source
     * @param position  the location of the PointLight
     * @param kA        fixed attenuation regardless of distance,
     * @param kB        fixed attenuation dependent on distance,
     * @param kC        fixed attenuation depending on square distance.
     * @param radius    is the radius of the point light resulting a circled light
     */
    public PointLight(Color intensity, Point3D position, double kA, double kB, double kC, double radius) {
        super(intensity);
        _kA = kA;
        _kB = kB;
        _kC = kC;
        _position = position;
        _radius = radius;
    }

    /**
     * Point light constructor gets 5 parameters
     *
     * @param intensity intensity of light source
     * @param position  the location of the PointLight
     * @param kA        fixed attenuation regardless of distance,
     * @param kB        fixed attenuation dependent on distance,
     * @param kC        fixed attenuation depending on square distance.
     */
    public PointLight(Color intensity, Point3D position, double kA, double kB, double kC) {
        this(intensity, position, kA, kB, kC, 0);
    }

    /**
     * gets the intensity of a point that influenced by the point light
     *
     * @param point the location of the intersection of the ray of PointLight with an object
     * @return the intensity in that point
     */
    @Override
    public Color getIntensity(Point3D point) {
        // distance from the light to the objects
        double d = getDistance(point);
        return _intensity.scale(1d / (_kA + _kB * d + _kC * d * d));
    }

    /**
     * gets the direction vector from the light point to the selected point of the object
     *
     * @param point a point that lies on the surface of the shape
     * @return the normalized direction vector from the light point to the this point
     */
    @Override
    public Vector getL(Point3D point) {
        return point.subtract(_position).normalize();
    }

    /**
     * gets the distance between a point (which suppose to be in a geometry) and the light location point
     *
     * @param point the location point we want to calculate the distance between it and the location of light
     * @return the value of distance
     */
    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

}
