package scene;

import elements.*;
import geometries.*;
import primitives.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represent the scene of the geometries in the area by the view of the camera.
 * <p>a step before framing into a restricted picture (rendering with imageWriter to jpg file)</p>
 */
public class Scene {
    /**
     * the name of the scene
     */
    private final String _name;
    /**
     * background color of scene
     */
    private Color _background;
    /**
     * the ambient light in scene
     */
    private AmbientLight _ambientLight;
    /**
     * The 3D model body collection
     */
    private Geometries _geometries;
    /**
     * the camera of scene
     */
    private Camera _camera;
    /**
     * distance from camera to simulated screen
     */
    private double _distance;

    /**
     * a list of lights in the scene
     */
    private List<LightSource> _lights = new LinkedList<LightSource>();

    // ***************** Constructors ********************** //

    /**
     * Constructor of scene get only input of name (as String)
     *
     * @param name of the scene (picture)
     */
    public Scene(String name) {
        this._name = name;
        _geometries = new Geometries();
    }


    // ***************** Getters/Setters ********************** //

    /**
     * get the name of the scene
     *
     * @return string name of scene
     */
    public String getName() {
        return new String(_name);
    }

    /**
     * get the Color of the background in scene
     *
     * @return color background scene
     */
    public Color getBackground() {
        return new Color(_background);
    }

    /**
     * get the intensity of ambient light of the scene
     *
     * @return color ambient light scene
     */
    public AmbientLight getAmbientLight() {
        return new AmbientLight(_ambientLight.getIntensity(), 1);
    }

    /**
     * get all lights of the scene
     *
     * @return list of Light sources (could be different light forms)
     */
    public List<LightSource> getLights() {
        return _lights;
    }

    /**
     * gets all the geometries in the scene
     *
     * @return Geometries of scene
     */
    public Geometries getGeometries() {
        return new Geometries(_geometries);
    }

    /**
     * gets the camera of the scene
     *
     * @return Camera of scene
     */
    public Camera getCamera() {
        return new Camera(_camera.getP0(), _camera.getVTo(), _camera.getVUp());
    }

    /**
     * gets the distance from the camera and the simulated screen in scene
     *
     * @return a double value of distance
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * set a new color for background
     *
     * @param background value of color (RGB intensities)
     */
    public void setBackground(Color background) {
        this._background = new Color(background);
    }

    /**
     * sets a new ambient light for the scene
     *
     * @param ambientLight value of color (RGB intensities)
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        this._ambientLight = new AmbientLight(ambientLight.getIntensity(), 1);
    }

    /**
     * seting new values for the camera in scene
     *
     * @param camera new values of location and direction of camera
     */
    public void setCamera(Camera camera) {
        this._camera = new Camera(camera.getP0(), camera.getVTo(), camera.getVUp());
    }

    /**
     * sets a new distance from camera to the simulated screen of scene
     *
     * @param distance a double value of distance
     */
    public void setDistance(double distance) {
        this._distance = distance;
    }

    // ***************** Operations ******************** //

    /**
     * Method for adding geometries to the composite geometries member in class Scene
     *
     * @param geometries the other geometries we're about to add
     */
    public void addGeometries(Intersectable... geometries) {
        this._geometries.add(geometries);
    }

    /**
     * Method for adding light sources to the list of lights of the Scene
     *
     * @param lights the other lights we're about to add
     */
    public void addLights(LightSource... lights) {
        this._lights.addAll(Arrays.asList(lights));
    }

}
