package renderer;

import geometries.Cylinder;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
                        new Point3D(0, 0, 50)),
                new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006));
        scene.getGeometries().setBoundingRegion();
        scene.getGeometries().boundingVolumeHierarchy();
        ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);
        //System.out.println(scene.getGeometries().hierarchyTree());
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(10000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
                new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, 750, 150),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     * producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50))
        );
        //scene.getGeometries().boundingVolumeHierarchy();
        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));
        ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of 3 - 4 geometries lighted by a point light
     */
    @Test
    public void threeGeometries_1() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, -110, -300), new Vector(0, 0.1, 1), new Vector(0, -1, 0.1)));
        scene.setDistance(300);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.2, 0.35, 30, 0, 0.2), //
                        new Point3D(-150, 60, 100), new Point3D(150, 60, 100), new Point3D(75, 60, 280)), //
                new Triangle(Color.BLACK, new Material(0.2, 0.35, 30, 0, 0.2), //
                        new Point3D(-150, 60, 100), new Point3D(-75, 60, 280), new Point3D(75, 60, 280)), //

                new Polygon(Color.BLACK, new Material(0.1, 0.35, 30, 0, 0.2), //
                        new Point3D(-75, 60, 280), new Point3D(-75, -145, 280), new Point3D(75, -145, 280), //
                        new Point3D(75, 60, 280)), //

                new Polygon(Color.BLACK, new Material(0.1, 0.35, 30, 0, 0.2), //
                        new Point3D(150, 60, 100), new Point3D(75, 60, 280), new Point3D(75, -145, 280), //
                        new Point3D(150, -145, 100)), //
                new Polygon(Color.BLACK, new Material(0.1, 0.35, 30, 0, 0.2), //
                        new Point3D(-150, 60, 100), new Point3D(-75, 60, 280), new Point3D(-75, -145, 280), //
                        new Point3D(-150, -145, 100)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.3, 40, 0, 0.1), // )
                        30, new Point3D(30, 30, 150)),
                new Cylinder(new Color(java.awt.Color.GREEN), new Material(0.2, 0.3, 50, 0, 0.1),
                        75, new Ray(new Point3D(-25, 60, 170), new Vector(0, -1, 0)), 25),
                new Triangle(new Color(java.awt.Color.RED), new Material(0.3, 0.2, 50, 0.7, 0), //
                        new Point3D(-25, 60, 125), new Point3D(30, 60, 130), new Point3D(0, -30, 70)),
                new Triangle(new Color(java.awt.Color.RED), new Material(0.3, 0.2, 50, 0.7, 0), //
                        new Point3D(-25, 60, 125), new Point3D(22, 60, 110), new Point3D(0, -30, 70)),
                new Triangle(new Color(java.awt.Color.RED), new Material(0.3, 0.2, 50, 0.7, 0), //
                        new Point3D(30, 60, 130), new Point3D(22, 60, 110), new Point3D(0, -30, 70)),
                new Triangle(new Color(java.awt.Color.RED), new Material(0.3, 0.2, 50, 0.7, 0), //
                        new Point3D(30, 60, 130), new Point3D(22, 60, 110), new Point3D(-25, 57, 125)));
        scene.addLights(new PointLight(new Color(1000, 700, 700), new Point3D(-60, -120, -50), 1, 4E-5, 2E-7));


        ImageWriter imageWriter = new ImageWriter("threeGeometries", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of 10 plus geometries lighted by all kinds of light, different view
     */
    @Test
    public void my_pic_view1() {

        // a condition for creating realistic images
        // kD + (kS or kR) + kT <= 1

        Scene scene = new Scene("Test scene");
        //scene.setCamera(new Camera(new Point3D(0, -150, -900), new Vector(0, 0.1, 1), new Vector(0, -1, 0.1)));
        scene.setCamera(
                new Camera(
                        new Point3D(-480, -250, -900),
                        new Vector(0.4, 0.2, 1),
                        new Vector(0, -1, 0.2))
        );
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.1, 0.15, 10), 60,
                        new Ray(new Point3D(0, 0, 242), new Vector(0, 0, -1)), 48.4),
                new Cylinder(Color.BLACK, new Material(0.2, 0.35, 30), 95,
                        new Ray(new Point3D(0, 0, 290), new Vector(0, 0, -1)), 40),
                new Sphere(new Color(20, 20, 40), new Material(0.1, 0.35, 40, 0.4, 0), 46, new Point3D(0, 0, 194)),
                new Sphere(new Color(java.awt.Color.GRAY), new Material(0.2, 0.2, 40, 0, 0.1), // )
                        30, new Point3D(0, 0, 200)),
                new Sphere(new Color(java.awt.Color.cyan), new Material(0.2, 0.2, 30, 0, 0.1), // )
                        16, new Point3D(0, 0, 183)),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.3, 0.3, 50, 0, 0.3), // )
                        10, new Point3D(0, 0, 175)),

                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-50, -80, 300), new Point3D(50, -80, 300), new Point3D(50, -100, 300), new Point3D(-50, -100, 300)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-50, -80, 320), new Point3D(50, -80, 320), new Point3D(50, -100, 320), new Point3D(-50, -100, 320)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(50, -80, 300), new Point3D(50, -80, 320), new Point3D(50, -100, 320), new Point3D(50, -100, 300)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-50, -80, 300), new Point3D(-50, -80, 320), new Point3D(-50, -100, 320), new Point3D(-50, -100, 300)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(50, -100, 300), new Point3D(50, -100, 320), new Point3D(-50, -100, 320), new Point3D(-50, -100, 300)),


                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-90, -80, 280), new Point3D(90, -80, 280), new Point3D(100, -70, 280), new Point3D(100, 60, 280),
                        new Point3D(90, 70, 280), new Point3D(-90, 70, 280), new Point3D(-100, 60, 280),
                        new Point3D(-100, -70, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-90, -80, 350), new Point3D(90, -80, 350), new Point3D(100, -70, 350), new Point3D(100, 60, 350),
                        new Point3D(90, 70, 350), new Point3D(-90, 70, 350), new Point3D(-100, 60, 350),
                        new Point3D(-100, -70, 350)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-90, -80, 280), new Point3D(90, -80, 280),
                        new Point3D(90, -80, 350), new Point3D(-90, -80, 350)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-90, 70, 280), new Point3D(90, 70, 280),
                        new Point3D(90, 70, 350), new Point3D(-90, 70, 350)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(100, -70, 280), new Point3D(100, 60, 280),
                        new Point3D(100, 60, 350), new Point3D(100, -70, 350)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(100, 60, 350), new Point3D(90, 70, 350),
                        new Point3D(90, 70, 280), new Point3D(100, 60, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(90, -80, 350), new Point3D(100, -70, 350),
                        new Point3D(100, -70, 280), new Point3D(90, -80, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-100, -70, 350), new Point3D(-90, -80, 350),
                        new Point3D(-90, -80, 280), new Point3D(-100, -70, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-100, 60, 350), new Point3D(-90, 70, 350),
                        new Point3D(-90, 70, 280), new Point3D(-100, 60, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-100, -70, 350), new Point3D(-100, 60, 350),
                        new Point3D(-100, 60, 280), new Point3D(-100, -70, 280)),
                new Triangle(Color.BLACK, new Material(0.4, 0.4, 60), //
                        new Point3D(9, -12, 170), new Point3D(-5, -11, 170), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -12, 170), new Point3D(10, -21, 177), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -12, 170), new Point3D(20, -10, 175), new Point3D(10, -21, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(20, -10, 175), new Point3D(10, -21, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(20, -10, 175), new Point3D(30, 1, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(31, -11, 183), new Point3D(30, 1, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, 0, 186), new Point3D(31, -11, 183), new Point3D(30, 1, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, 0, 186), new Point3D(33, 11, 187), new Point3D(30, 1, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, 0, 186), new Point3D(33, 11, 187), new Point3D(45, 11, 187)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(39, 20, 186), new Point3D(33, 11, 187), new Point3D(45, 11, 187)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(39, 20, 186), new Point3D(31, 19, 186), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(27, 15, 186), new Point3D(31, 19, 186), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(27, 15, 186), new Point3D(20, 20, 180), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(27, 15, 186), new Point3D(20, 20, 180), new Point3D(17, 19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(20, 20, 180), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(20, 20, 180), new Point3D(17, 19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(10, 37, 174), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(2, 20, 170), new Point3D(17, 19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(10, 37, 174), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(2, 20, 170), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-10, 32, 170), new Point3D(10, 37, 174), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-11, 19, 178), new Point3D(2, 20, 170), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-10, 32, 170), new Point3D(-25, 27, 170), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-11, 19, 178), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-25, 27, 170), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-11, 19, 178), new Point3D(-22, 10, 178)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-25, 27, 170), new Point3D(-30, 11, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-30, 11, 180), new Point3D(-22, 10, 178)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-35, 18, 170), new Point3D(-25, 27, 170), new Point3D(-30, 11, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-30, 11, 180), new Point3D(-22, 10, 178)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-35, 18, 170), new Point3D(-39, 7, 171), new Point3D(-30, 11, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-30, 11, 180), new Point3D(-39, 7, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-40, 0, 171), new Point3D(-39, 7, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-40, 0, 171), new Point3D(-27, -8, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-17, -9, 171), new Point3D(-27, -8, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-29, -19, 170), new Point3D(-27, -8, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-27, -8, 185), new Point3D(-29, -19, 170), new Point3D(-40, 0, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-27, -8, 185), new Point3D(-16, -18, 180), new Point3D(-29, -19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-27, -8, 185), new Point3D(-16, -18, 180), new Point3D(-17, -9, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-5, -11, 170), new Point3D(-16, -18, 180), new Point3D(-17, -9, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-12, -29, 170), new Point3D(-16, -18, 180), new Point3D(-29, -19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-5, -11, 170), new Point3D(-16, -18, 180), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-12, -29, 170), new Point3D(-16, -18, 180), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-12, -29, 170), new Point3D(-4, -30, 170), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -32, 170), new Point3D(-4, -30, 170), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -32, 170), new Point3D(10, -21, 177), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -32, 170), new Point3D(10, -21, 177), new Point3D(19, -28, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(10, -21, 177), new Point3D(19, -28, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(29, -20, 170), new Point3D(19, -28, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(29, -20, 170), new Point3D(31, -11, 183)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, -9, 172), new Point3D(29, -20, 170), new Point3D(31, -11, 183)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, 0, 186), new Point3D(37, -10, 170), new Point3D(31, -11, 183))
        );

        scene.addLights(
                new DirectionalLight(new Color(60, 40, 40), new Vector(-0.8, 1, 1)),
                new PointLight(new Color(90, 90, 120),
                        new Point3D(-100, -40, 50), 1, 0.00001, 0.000001),
                new SpotLight(new Color(500, 300, 300), new Point3D(-110, -80, 0),
                        new Vector(1, 0, 1), 1, 4E-5, 2E-7));


        ImageWriter imageWriter = new ImageWriter("nice_picture_view1", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene) //
                .setMultithreading(3) //
                .setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of 10 plus geometries lighted by all kinds of light
     */
    @Test
    public void my_pic_view2() {

        // a condition for creating realistic images
        // kD + (kS or kR) + kT <= 1

        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, -150, -900), new Vector(0, 0.1, 1), new Vector(0, -1, 0.1)));
        //scene.setCamera(new Camera(new Point3D(-480, -250, -900), new Vector(0.4, 0.2, 1), new Vector(0, -1, 0.2)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.1, 0.15, 10), 60,
                        new Ray(new Point3D(0, 0, 242), new Vector(0, 0, -1)), 48.4),
                new Cylinder(Color.BLACK, new Material(0.2, 0.35, 30), 95,
                        new Ray(new Point3D(0, 0, 290), new Vector(0, 0, -1)), 40),
                new Sphere(new Color(20, 20, 40), new Material(0.1, 0.35, 40, 0.4, 0), 46, new Point3D(0, 0, 194)),
                new Sphere(new Color(java.awt.Color.GRAY), new Material(0.2, 0.2, 40, 0, 0.1), // )
                        30, new Point3D(0, 0, 200)),
                new Sphere(new Color(java.awt.Color.cyan), new Material(0.2, 0.2, 30, 0, 0.1), // )
                        16, new Point3D(0, 0, 183)),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.3, 0.3, 50, 0, 0.3), // )
                        10, new Point3D(0, 0, 175)),

                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-50, -80, 300), new Point3D(50, -80, 300), new Point3D(50, -100, 300), new Point3D(-50, -100, 300)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-50, -80, 320), new Point3D(50, -80, 320), new Point3D(50, -100, 320), new Point3D(-50, -100, 320)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(50, -80, 300), new Point3D(50, -80, 320), new Point3D(50, -100, 320), new Point3D(50, -100, 300)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-50, -80, 300), new Point3D(-50, -80, 320), new Point3D(-50, -100, 320), new Point3D(-50, -100, 300)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(50, -100, 300), new Point3D(50, -100, 320), new Point3D(-50, -100, 320), new Point3D(-50, -100, 300)),


                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-90, -80, 280), new Point3D(90, -80, 280), new Point3D(100, -70, 280), new Point3D(100, 60, 280),
                        new Point3D(90, 70, 280), new Point3D(-90, 70, 280), new Point3D(-100, 60, 280),
                        new Point3D(-100, -70, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-90, -80, 350), new Point3D(90, -80, 350), new Point3D(100, -70, 350), new Point3D(100, 60, 350),
                        new Point3D(90, 70, 350), new Point3D(-90, 70, 350), new Point3D(-100, 60, 350),
                        new Point3D(-100, -70, 350)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-90, -80, 280), new Point3D(90, -80, 280),
                        new Point3D(90, -80, 350), new Point3D(-90, -80, 350)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-90, 70, 280), new Point3D(90, 70, 280),
                        new Point3D(90, 70, 350), new Point3D(-90, 70, 350)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(100, -70, 280), new Point3D(100, 60, 280),
                        new Point3D(100, 60, 350), new Point3D(100, -70, 350)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(100, 60, 350), new Point3D(90, 70, 350),
                        new Point3D(90, 70, 280), new Point3D(100, 60, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(90, -80, 350), new Point3D(100, -70, 350),
                        new Point3D(100, -70, 280), new Point3D(90, -80, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-100, -70, 350), new Point3D(-90, -80, 350),
                        new Point3D(-90, -80, 280), new Point3D(-100, -70, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-100, 60, 350), new Point3D(-90, 70, 350),
                        new Point3D(-90, 70, 280), new Point3D(-100, 60, 280)),
                new Polygon(new Color(20, 20, 30), new Material(0.4, 0.4, 60, 0, 0.2), //
                        new Point3D(-100, -70, 350), new Point3D(-100, 60, 350),
                        new Point3D(-100, 60, 280), new Point3D(-100, -70, 280)),
                new Triangle(Color.BLACK, new Material(0.4, 0.4, 60), //
                        new Point3D(9, -12, 170), new Point3D(-5, -11, 170), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -12, 170), new Point3D(10, -21, 177), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -12, 170), new Point3D(20, -10, 175), new Point3D(10, -21, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(20, -10, 175), new Point3D(10, -21, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(20, -10, 175), new Point3D(30, 1, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(31, -11, 183), new Point3D(30, 1, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, 0, 186), new Point3D(31, -11, 183), new Point3D(30, 1, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, 0, 186), new Point3D(33, 11, 187), new Point3D(30, 1, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, 0, 186), new Point3D(33, 11, 187), new Point3D(45, 11, 187)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(39, 20, 186), new Point3D(33, 11, 187), new Point3D(45, 11, 187)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(39, 20, 186), new Point3D(31, 19, 186), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(27, 15, 186), new Point3D(31, 19, 186), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(27, 15, 186), new Point3D(20, 20, 180), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(27, 15, 186), new Point3D(20, 20, 180), new Point3D(17, 19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(20, 20, 180), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(20, 20, 180), new Point3D(17, 19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(10, 37, 174), new Point3D(25, 30, 174)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(2, 20, 170), new Point3D(17, 19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(10, 37, 174), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(8, 26.5, 175), new Point3D(2, 20, 170), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-10, 32, 170), new Point3D(10, 37, 174), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-11, 19, 178), new Point3D(2, 20, 170), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-10, 32, 170), new Point3D(-25, 27, 170), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-11, 19, 178), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-25, 27, 170), new Point3D(-8, 26, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-11, 19, 178), new Point3D(-22, 10, 178)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-25, 27, 170), new Point3D(-30, 11, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-19, 20, 180), new Point3D(-30, 11, 180), new Point3D(-22, 10, 178)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-35, 18, 170), new Point3D(-25, 27, 170), new Point3D(-30, 11, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-30, 11, 180), new Point3D(-22, 10, 178)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-35, 18, 170), new Point3D(-39, 7, 171), new Point3D(-30, 11, 180)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-30, 11, 180), new Point3D(-39, 7, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-40, 0, 171), new Point3D(-39, 7, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-40, 0, 171), new Point3D(-27, -8, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-17, -9, 171), new Point3D(-27, -8, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-30, 2, 186), new Point3D(-29, -19, 170), new Point3D(-27, -8, 185)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-27, -8, 185), new Point3D(-29, -19, 170), new Point3D(-40, 0, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-27, -8, 185), new Point3D(-16, -18, 180), new Point3D(-29, -19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-27, -8, 185), new Point3D(-16, -18, 180), new Point3D(-17, -9, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-5, -11, 170), new Point3D(-16, -18, 180), new Point3D(-17, -9, 171)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-12, -29, 170), new Point3D(-16, -18, 180), new Point3D(-29, -19, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-5, -11, 170), new Point3D(-16, -18, 180), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-12, -29, 170), new Point3D(-16, -18, 180), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-12, -29, 170), new Point3D(-4, -30, 170), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -32, 170), new Point3D(-4, -30, 170), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -32, 170), new Point3D(10, -21, 177), new Point3D(-4, -22, 177)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(9, -32, 170), new Point3D(10, -21, 177), new Point3D(19, -28, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(10, -21, 177), new Point3D(19, -28, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(29, -20, 170), new Point3D(19, -28, 170)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(22, -18, 180), new Point3D(29, -20, 170), new Point3D(31, -11, 183)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, -9, 172), new Point3D(29, -20, 170), new Point3D(31, -11, 183)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(37, 0, 186), new Point3D(37, -10, 170), new Point3D(31, -11, 183))
        );

        scene.addLights(
                new DirectionalLight(new Color(60, 40, 40), new Vector(-0.8, 1, 1)),
                new PointLight(new Color(90, 90, 120),
                        new Point3D(-100, -40, 50), 1, 0.00001, 0.000001),
                new SpotLight(new Color(500, 300, 300), new Point3D(-110, -80, 0),
                        new Vector(1, 0, 1), 1, 4E-5, 2E-7));


        ImageWriter imageWriter = new ImageWriter("nice_picture_view2", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
