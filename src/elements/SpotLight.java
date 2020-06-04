package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static java.lang.Math.max;


/**
 * class Spot Light defined by position, direction and light intensity.
 * it's a kind of point light so we choose to extend it from pointLight.
 */
public class SpotLight extends PointLight {
    /**
     * The direction which most of the intensity going through
     */
    private Vector _direction;

    /**
     * @param intensity intensity of light source
     * @param position  the location of the PointLight
     * @param direction the direction of the highest intensity of spotLight
     * @param kA        fixed attenuation regardless of distance,
     * @param kB        fixed attenuation dependent on distance,
     * @param kC        fixed attenuation depending on square distance.
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kA, double kB, double kC) {
        super(intensity, position, kA, kB, kC);
        _direction = direction.normalized();
    }

    /**
     * gets the intensity of a point that influenced by the spot light,
     *
     * @param point the location of the intersection of the ray of SpotLight with an object
     * @return the intensity in that point
     */
    public Color getIntensity(Point3D point) {
        // distance from the light to the objects
        double d = getDistance(point);
        // dir∙l is the relation (angle ratio) between the direction of spot light
        // and the direction which we get from the light source to the intersection point
        double dirL = max(0d, _direction.dotProduct(getL(point))); // if it's negative than it means nothing for us
        return _intensity.scale(dirL / (_kA + _kB * d + _kC * d * d));
    }
}
