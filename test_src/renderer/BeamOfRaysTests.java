package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;
import primitives.*;
import scene.Scene;


/**
 * Tests for big and complex scenes using beam of rays
 *
 * @author Naor Bar Kochva
 */
public class BeamOfRaysTests {


    @Test
    public void blah() {
        Scene scene = new Scene("Test scene");
        //scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0.1, 1), new Vector(0, -1, 0.1)));
        //scene.setCamera(new Camera(new Point3D(-200, 100, -500), new Vector(0.65, 0.05, 1), new Vector(0, -1, 0.05)));
        scene.setCamera(new Camera(
                        new Point3D(0, 100, -600),
                        new Vector(0, 0.04, 1),
                        new Vector(0, -1, 0.04))
                .setDepthOfField(60, 420, 100)
        );
        scene.setDistance(400);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        Geometries surface2 = new Geometries(
                pattern(10, 10, 10));


        scene.addGeometries( //
                surface2
                ,
                new Polygon(Color.BLACK, new Material(0.01, 0.1, 50),
                        new Point3D(-250, 150, 415), new Point3D(250, 150, 415),
                        new Point3D(250, -450, 415), new Point3D(-250, -450, 415))
                ,
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        7, new Point3D(-120, 143, 100)),
                new Polygon(Color.BLACK, new Material(0.5, 0.5, 60),
                        new Point3D(-60, 143, -210), new Point3D(60, 143, -210),
                        new Point3D(60, 90, -210), new Point3D(-60, 90, -210))

        );

        scene.getGeometries().setBoundingRegion();
        scene.getGeometries().boundingVolumeHierarchy();

        scene.addLights(
                new SpotLight(new Color(330, 220, 220), //
                        new Point3D(-70, -400, -400), new Vector(2, 5, 5), 1, 4E-5, 2E-7, 20)
                , new SpotLight(new Color(330, 220, 220), //
                        new Point3D(70, -400, -400), new Vector(-2, 5, 5), 1, 4E-5, 2E-7, 20)

                //,new SpotLight(new Color(20, 20, 50), //
                // new Point3D(0, -400, -200), new Vector(-2, 2, 0), 1, 4E-5, 2E-7)
        );

        ImageWriter imageWriter = new ImageWriter("picture r", 500, 277.77, 900, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a 5 molecules lighted by two spot lights and one directional
     */
    @Test
    public void radialGeometriesOnSurfaceBoundingVolumeHierarchy() {
        Scene scene = new Scene("Test scene");
        //scene.setCamera(new Camera(new Point3D(-200, 100, -500), new Vector(0.65, 0.05, 1), new Vector(0, -1, 0.05)));
        scene.setCamera(new Camera(new Point3D(0, 100, -600), new Vector(0, 0.04, 1), new Vector(0, -1, 0.04))
                //.setDepthOfField(100, 400, 90)
        );
        scene.setDistance(1150);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        Geometries geoMolecule1 = new Geometries(
                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(60, 143, 50))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                40, new Ray(new Point3D(60, 143, 50), new Vector(-1, -1, -1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(36.90598923241497, 119.90598923241497, 26.905989232414967))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                33, new Ray(new Point3D(36.90598923241497, 119.90598923241497, 26.905989232414967),
                                new Vector(0, -1, 1)), 1.8)
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                33, new Ray(new Point3D(36.90598923241497, 119.90598923241497, 26.905989232414967),
                                new Vector(-1, 0, 1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(13.571465453258902, 119.90598923241497, 50.24051301157103))
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(36.90598923241497, 96.5714654532589, 50.24051301157103))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                40, new Ray(new Point3D(36.90598923241497, 119.90598923241497, 26.905989232414967),
                                new Vector(0, 0, -1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(36.90598923241497, 119.90598923241497, -13.094010767585033))
                        ,

                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                33, new Ray(new Point3D(36.90598923241497, 119.90598923241497, -13.094010767585033),
                                new Vector(1, -1, 1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(55.95854811567262, 100.85343034915732, 5.95854811567262))
                        ,

                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                40, new Ray(new Point3D(36.90598923241497, 119.90598923241497, -13.094010767585033),
                                new Vector(-1, 0, -1)), 1.8)
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                33, new Ray(new Point3D(36.90598923241497, 119.90598923241497, -13.094010767585033),
                                new Vector(0, 1, -1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(36.90598923241497, 143.24051301157104, -36.4285345467411))
                ),
                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(8.621717984953069, 119.90598923241497, -41.378282015046935))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                33, new Ray(new Point3D(8.621717984953069, 119.90598923241497, -41.378282015046935),
                                new Vector(-1, 1, 0)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-14.712805794202996, 143.24051301157104, -41.378282015046935))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                33, new Ray(new Point3D(8.621717984953069, 119.90598923241497, -41.378282015046935),
                                new Vector(-1, -1, 0)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-14.712805794202996, 96.5714654532589, -41.378282015046935))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                40, new Ray(new Point3D(8.621717984953069, 119.90598923241497, -41.378282015046935),
                                new Vector(0, 0, -1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(8.621717984953069, 119.90598923241497, -81.37828201504693))
                )
        );

        Geometries geoMolecule2 = new Geometries(
                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-120, 143, 100))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                33, new Ray(new Point3D(-120, 143, 100),
                                new Vector(1, -1, 0)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(-96.66547622084394, 119.66547622084394, 100.0))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-96.66547622084394, 119.66547622084394, 100.0),
                                new Vector(0, -1, 1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-96.66547622084394, 94.91673887931478, 124.74873734152916))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-96.66547622084394, 119.66547622084394, 100.0),
                                new Vector(1, 0, 0)), 1.8)
                ),
                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(-61.66547622084394, 119.66547622084394, 100.0))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-61.66547622084394, 119.66547622084394, 100.0),
                                new Vector(1, 1, 0)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-36.91673887931478, 143.4142135623731, 100.0))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-61.66547622084394, 119.66547622084394, 100.0),
                                new Vector(-1, 1, 0)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-86.4142135623731, 143.4142135623731, 100.0))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-61.66547622084394, 119.66547622084394, 100.0),
                                new Vector(0, -1, 0)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-61.66547622084394, 84.66547622084394, 100.0))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-61.66547622084394, 119.66547622084394, 65.0),
                                new Vector(0, 0, -1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-61.66547622084394, 119.66547622084394, 30.0))
                )
        );

        Geometries geoMolecule3 = new Geometries(
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        7, new Point3D(-110, 143, -50))
                ,
                new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                        35, new Ray(new Point3D(-110, 143, -50),
                        new Vector(1, -0.6, 1)), 1.8)
                ,
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        10, new Point3D(-87.21694057835583, 129.3301643470135, -27.21694058))
                ,
                new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                        35, new Ray(new Point3D(-87.21694057835583, 129.3301643470135, -27.21694058),
                        new Vector(1, 0.6, 0)), 1.8)
                ,
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        7, new Point3D(-62.20468817841678, 143.33751578697692, -27.21694058))
                ,
                new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                        35, new Ray(new Point3D(-87.21694057835583, 129.3301643470135, -27.21694058),
                        new Vector(-1, 0.6, 1)), 1.8)
                ,
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        7, new Point3D(-110.0, 143.0, 25.56611884328835))
                ,
                new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                        35, new Ray(new Point3D(-87.21694057835583, 129.3301643470135, -27.21694058),
                        new Vector(0, -1, 0)), 1.8)
                ,
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                        7, new Point3D(-87.21694057835583, 94.3301643470135, -27.21694058))
        );

        Geometries geoMolecule4 = new Geometries(
                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-50, 143, -210))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-50, 143, -210),
                                new Vector(1, -0.5, 1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(-26.666666666666668, 131.33333333333334, -186.66666666666666))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-26.666666666666668, 131.33333333333334, -186.66666666666666),
                                new Vector(1, 0.5, 1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-3.3333333333333357, 143.0, -163.33333333333331))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-26.666666666666668, 131.33333333333334, -186.66666666666666),
                                new Vector(-1, -1, 0)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-51.41540400819583, 106.58459599180418, -186.66666666666666))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-26.666666666666668, 131.33333333333334, -186.66666666666666),
                                new Vector(1, -1, -1)), 1.8)
                ),
                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(-6.459407245029762, 111.12607391169644, -206.87392608830356))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-6.459407245029762, 111.12607391169644, -206.87392608830356),
                                new Vector(1, -0.5, 1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(16.87392608830357, 99.45940724502977, -183.54059275497022))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-6.459407245029762, 111.12607391169644, -206.87392608830356),
                                new Vector(-1, -0.5, -1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-29.792740578363095, 99.45940724502977, -230.2072594216369))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(-6.459407245029762, 111.12607391169644, -206.87392608830356),
                                new Vector(1, 1, -1)), 1.8)
                ),
                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(13.747852176607143, 131.33333333333334, -227.08118550994047))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(13.747852176607143, 131.33333333333334, -227.08118550994047),
                                new Vector(-1, 0.5, -1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(-9.585481156726189, 143.0, -250.4145188432738))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(13.747852176607143, 131.33333333333334, -227.08118550994047),
                                new Vector(1, 0.5, 1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(37.081185509940475, 143.0, -203.74785217660713))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(13.747852176607143, 131.33333333333334, -227.08118550994047),
                                new Vector(1, -1, -1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(33.955111598244045, 111.12607391169644, -247.28844493157737))
                )
        );

        Geometries geoMolecule5 = new Geometries(
                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(100, 143, -130))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(100, 143, -130),
                                new Vector(-1, -1, -1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(79.7927405783631, 122.7927405783631, -150.2072594216369))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                33, new Ray(new Point3D(79.7927405783631, 122.7927405783631, -150.2072594216369),
                                new Vector(-0.1, -1, -0.1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(76.52525368654007, 90.11787166013283, -153.47474631345992))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(79.7927405783631, 122.7927405783631, -150.2072594216369),
                                new Vector(-1, 1, -1)), 1.8)
                ),

                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(59.58548115672619, 143.0, -170.4145188432738))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(79.7927405783631, 122.7927405783631, -150.2072594216369),
                                new Vector(-1, 0, 0.5)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                10, new Point3D(48.487788893366044, 122.7927405783631, -134.5547835791384))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(48.487788893366044, 122.7927405783631, -134.5547835791384),
                                new Vector(-1, 1, -1)), 1.8)
                ),
                new Geometries(
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(28.28052947172914, 143.0, -154.7620430007753))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                35, new Ray(new Point3D(48.487788893366044, 122.7927405783631, -134.5547835791384),
                                new Vector(1, 1, 1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(68.69504831500295, 143.0, -114.34752415750148))
                        ,
                        new Cylinder(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), //
                                33, new Ray(new Point3D(48.487788893366044, 122.7927405783631, -134.5547835791384),
                                new Vector(0.1, -1, 0.1)), 1.8)
                        ,
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 20, 0, 0.5), // )
                                7, new Point3D(51.75527578518907, 90.11787166013283, -131.28729668731538))
                )
        );


        Geometries surface = new Geometries(
                new Polygon(Color.BLACK, new Material(0.2, 0.1, 50, 0, 0.4),
                        new Point3D(-250, 150, -500), new Point3D(250, 150, -500),
                        new Point3D(250, 150, 415), new Point3D(-250, 150, 415))
        );

        //Geometries surface2 = new Geometries(
        //        pattern(10, 10, 25));

        Geometries geoMole23 = new Geometries(geoMolecule2, geoMolecule3);
        Geometries geoMole123 = new Geometries(geoMolecule1, geoMole23);
        Geometries geoMole45 = new Geometries(geoMolecule4, geoMolecule5);
        scene.addGeometries(
                geoMole123,
                geoMole45,
                geoMolecule5,
                surface,
                new Polygon(Color.BLACK, new Material(0.01, 0.1, 50),
                        new Point3D(-250, 150, 415), new Point3D(250, 150, 415),
                        new Point3D(250, -450, 415), new Point3D(-250, -450, 415))
        );

        scene.getGeometries().setBoundingRegion();
        scene.getGeometries().boundingVolumeHierarchy();
        System.out.println("Hierarchy Tree:\n" + scene.getGeometries().hierarchyTree());


        scene.addLights(
                new SpotLight(new Color(400, 360, 360), //
                        new Point3D(-70, -400, -400), new Vector(2, 5, 5), 1, 4E-5, 2E-7, 40)
                , new SpotLight(new Color(400, 330, 330), //
                        new Point3D(70, -400, -400), new Vector(-2, 5, 5), 1, 4E-5, 2E-7, 20),
                new DirectionalLight(new Color(20, 10, 10), new Vector(5, 1, 3))
        );
        ImageWriter imageWriter = new ImageWriter("picture radialGeometriesOnSurface", 500, 277.77, 900, 500);
        //Render render = new Render(imageWriter, scene).setDebugPrint();
        Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setSoftShadows(81);
        render.renderImage();
        render.writeToImage();
    }





    public Polygon Square(Color color, Material material, Point3D center, double size) {
        return new Polygon(color, material,
                center.add(new Vector(size, 0, size)),
                center.add(new Vector(-size, 0, size)),
                center.add(new Vector(-size, 0, -size)),
                center.add(new Vector(size, 0, -size))
        );
    }

    public Intersectable pattern(int numWithe, int numLen, double size) {
        Color color1 = new Color(0, 0, 0);
        Color color2 = new Color(0, 0, 0);
        Material material1 = new Material(0.23, 0.2, 40, 0, 0.3);
        Material material2 = new Material(0.2, 0.1, 50, 0, 0.4);

        Geometries geometries = new Geometries();
        for (int i = -numWithe - 1; i < numWithe + 1; i++) {
            for (int j = -numLen - 8; j < numLen + 8; j++) {
                Material material = (i + j) % 2 == 0 ? material1 : material2;
                Color color = (i + j) % 2 == 0 ? color1 : color2;
                geometries.add(Square(color, material, new Point3D(i * size, 150, j * size), size / 2));
            }
        }
        return geometries;

    }
}
