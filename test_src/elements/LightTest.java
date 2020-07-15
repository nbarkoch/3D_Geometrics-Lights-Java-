package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightTest {

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));

        ImageWriter imageWriter = new ImageWriter("sphereDirectional", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(new PointLight(new Color(500, 300, 0), new Point3D(-50, 50, -50), 1, 0.00001, 0.000001));

        ImageWriter imageWriter = new ImageWriter("spherePoint", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(500, 300, 0), new Point3D(-50, 50, -50),
                new Vector(1, -1, 2), 1, 0.00001, 0.00000001));

        ImageWriter imageWriter = new ImageWriter("sphereSpot", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.8, 0.2, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.8, 0.2, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

        scene.addLights(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, 1)));

        ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

        scene.addLights(new PointLight(new Color(500, 250, 250),
                new Point3D(10, 10, 130),
                1, 0.0005, 0.0005));

        ImageWriter imageWriter = new ImageWriter("trianglesPoint", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

        scene.addLights(new SpotLight(new Color(500, 250, 250),
                new Point3D(10, 10, 130), new Vector(-2, 2, 1),
                1, 0.0001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("trianglesSpot", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by the three kind of lights:
     * directional light, point light and spot light.
     */
    @Test
    public void sphereDirectionalPointSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(
                new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, 1)),
                new PointLight(new Color(0, 300, 300), new Point3D(-40, -10, -30), 1, 0.00001, 0.00002),
                new SpotLight(new Color(300, 300, 0), new Point3D(70, 76, -60),
                        new Vector(-1, 1, 2), 1, 0.00001, 0.0000004)
        );

        ImageWriter imageWriter = new ImageWriter("sphereDirectionalPointSpot", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by the three kind of lights:
     * directional light, point light and spot light.
     */
    @Test
    public void trianglesDirectionalPointSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 300),
                        new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

        scene.addLights(
                new DirectionalLight(new Color(280, 100, 0), new Vector(0.5, 0, 1)),
                new PointLight(new Color(500, 150, 30), new Point3D(-50, -40, 70),
                        1, 0.0001, 0.0009),
                new SpotLight(new Color(250, 250, 250),
                        new Point3D(60, 70, 130), new Vector(-0.8, 0, 1),
                        1, 0.0001, 0.000008));

        ImageWriter imageWriter = new ImageWriter("trianglesDirectionalPointSpot", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of an infinite Cylinder lighted by a directional light
     */
    @Test
    public void tubeDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Tube(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100),
                        new Ray(new Point3D(1, 14, 2), new Vector(1, 1, -20)), 10));

        scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));

        ImageWriter imageWriter = new ImageWriter("tubeDirectional", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a cylinder lighted by a directional light
     */
    @Test
    public void cylinderDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Cylinder(new Color(java.awt.Color.CYAN), new Material(0.5, 0.5, 100), 300,
                        new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 1)), 20));

        scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));

        ImageWriter imageWriter = new ImageWriter("cylinderDirectional", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a cylinder lighted by a point light
     */
    @Test
    public void cylinderPoint() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Cylinder(new Color(java.awt.Color.GREEN), new Material(0.5, 0.5, 100), 70,
                        new Ray(new Point3D(-20, -15, 0), new Vector(1, 1, 1)), 20));

        scene.addLights(new PointLight(new Color(500, 300, 0), new Point3D(-50, 5, -50), 1, 0.00001, 0.000001));

        ImageWriter imageWriter = new ImageWriter("cylinderPoint", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a sphere lighted by the three kind of lights:
     * directional light, point light and spot light.
     * Using Super Sampling
     */
    @Test
    public void sphereDirectionalPointSpot_SuperSampling() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0))
        .setSuperSampling(49));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(
                new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, 1)),
                new PointLight(new Color(0, 300, 300), new Point3D(-40, -10, -30), 1, 0.00001, 0.00002),
                new SpotLight(new Color(300, 300, 0), new Point3D(70, 76, -60),
                        new Vector(-1, 1, 2), 1, 0.00001, 0.0000004)
        );
        scene.getGeometries().setBoundingRegion();
        ImageWriter imageWriter = new ImageWriter("sphereDirectionalPointSpot_ss", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by the three kind of lights:
     * directional light, point light and spot light.
     * Using Depth of field
     */
    @Test
    public void sphereDirectionalPointSpot_DepthOfField() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setDepthOfField(81,200,2)
        );
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(
                new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, 1)),
                new PointLight(new Color(0, 300, 300), new Point3D(-40, -10, -30), 1, 0.00001, 0.00002),
                new SpotLight(new Color(300, 300, 0), new Point3D(70, 76, -60),
                        new Vector(-1, 1, 2), 1, 0.00001, 0.0000004)
        );
        scene.getGeometries().setBoundingRegion();
        ImageWriter imageWriter = new ImageWriter("sphereDirectionalPointSpot_dof", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3);

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a sphere lighted by the three kind of lights:
     * directional light, point light and spot light.
     * Using Super Sampling and Depth Of Field
     */
    @Test
    public void sphereDirectionalPointSpot_SuperSamplingAndDepthOfField() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setSuperSampling(16)
                .setDepthOfField(30,200,2)
        );
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), 50, new Point3D(0, 0, 50)));

        scene.addLights(
                new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, 1)),
                new PointLight(new Color(0, 300, 300), new Point3D(-40, -10, -30), 1, 0.00001, 0.00002),
                new SpotLight(new Color(300, 300, 0), new Point3D(70, 76, -60),
                        new Vector(-1, 1, 2), 1, 0.00001, 0.0000004)
        );
        scene.getGeometries().setBoundingRegion();
        ImageWriter imageWriter = new ImageWriter("sphereDirectionalPointSpot_ss_dof", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3);

        render.renderImage();
        render.writeToImage();
    }

}

