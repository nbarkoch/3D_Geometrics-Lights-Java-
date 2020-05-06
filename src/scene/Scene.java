package scene;
import elements.*;
import geometries.*;
import primitives.Color;

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


    // ***************** Constructors ********************** //

    /**
     * Constructor of scene get only input of name (as String)
     * @param name of the scene (picture)
     */
    public Scene(String name) {
        this._name = name;
    }


    // ***************** Getters/Setters ********************** //

    public String getName() {
        return new String(_name);
    }

    public Color getBackground() {
        return new Color(_background);
    }

    public AmbientLight getAmbientLight() {
        return new AmbientLight(_ambientLight.getIntensity(), 1);
    }

    public Geometries getGeometries() {
        return new Geometries(_geometries);
    }

    public Camera getCamera() {
        return new Camera(_camera.get_p0(), _camera.get_vTo(), _camera.get_vUp());
    }

    public double getDistance() {
        return _distance;
    }

    public void setBackground(Color background) {
        this._background = new Color(background);
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        this._ambientLight = new AmbientLight(ambientLight.getIntensity(), 1);
    }

    public void setCamera(Camera camera) {
        this._camera = new Camera(camera.get_p0(), camera.get_vTo(), camera.get_vUp());
    }

    public void setDistance(double distance) {
        this._distance = distance;
    }

    // ***************** Operations ******************** //

    /**
     * Method for adding geometries to the composite geometries member in class Scene
     * @param geometries the other geometries we're about to add
     */
    public void addGeometries(Intersectable... geometries) {
        if (_geometries == null) // if it's first time, because in the constructor there's no initialization
            _geometries = new Geometries();
        this._geometries.add(geometries);
    }

}
