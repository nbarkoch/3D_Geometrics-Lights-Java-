package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Light Source defined by the intensity of light,
 * Refers to the light reflectance model.
 */
public interface LightSource {

    /**
     * gets the intensity of a point that influenced by the light source
     *
     * @param p the location of the intersection of the ray which came from light source with an object
     * @return the intensity in that point
     */
    public Color getIntensity(Point3D p);

    /**
     * gets the direction vector from the light source to the selected point of the object
     *
     * @param p a point that lies on the surface of a shape
     * @return the direction vector from the light source to this point
     */
    public Vector getL(Point3D p);

    /**
     * gets the distance between a point (which suppose to be belong to a geometry)
     * and the light source
     *
     * @param point the location point we want to calculate the distance between it and the location of light
     * @return the value of distance
     */
    public double getDistance(Point3D point);
}
