package elements;

import geometries.Intersectable;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.*;

/**
 * Class Camera represent view of the geometric world through the view plane (which represent the picture),
 * Through the view plane the camera captures the geometric world.
 * it produces graphic views of the objects using the view plane and rays and object intersections.
 * The rays converge according to the location of the pixel centers in the view plane.
 */
public class Camera {
    private Point3D _p0;
    /**
     * directions of the camera to the right, up and front  (compared to the original x,y,z axis),
     * all vectors orthogonal to each other
     */
    private Vector _vUp;
    private Vector _vTo;
    private Vector _vRight;


    /**
     * number of rays in a single pixel for active super sampling
     */
    private int _numOfRaysInPixel = 1;

    /**
     * number of rays in the focal plane of the camera (per pixel),
     * the more rays in focal plane the more quality of the blurring
     */
    private int _numOfRaysInAperture = 1;

    /**
     * the distance between the camera's beginning location to the center of focal plane,
     * forward vision becomes sharper in the distance, the more it's close to the focal plane
     */
    private double _focalDist = 0;

    /**
     * the size of the aperture surface (located in the camera location), the size is relative to a single pixel, example: 1 = one pixel, and so on.
     * the smaller the aperture the greater the width of the sharpness, (similar to blind view without glasses)
     */
    private double _apertureSize = 0;


    /**
     * Camera constructor which get as input location point and two orthogonal vectors represent the direction
     *
     * @param p0  location of the camera
     * @param vTo direction of the camera to the front (compared to the original z axis)
     * @param vUp direction of the camera to the up (compared to the original y axis)
     * @throws IllegalArgumentException for two vectors (vUp and vDown) which should be orthogonal
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        this._p0 = p0;
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("Vectors vUp and vTo aren't orthogonal");
        this._vTo = vTo.normalized();
        this._vUp = vUp.normalized();
        this._vRight = _vTo.crossProduct(_vUp).normalized();
    }

    /**
     * Method construct a ray with a starting point equal to the camera, and with direction to the pixel center of the view plane,
     * which is selected by indexes j (column number) and i (line number).
     *
     * @param nX             number of pixels in every row
     * @param nY             number of pixels in every column
     * @param j              number of index represent which of the columns the ray is crossing through
     * @param i              number of index represent which of the rows the ray is crossing through
     * @param screenDistance minimum distance of camera to view plane
     * @param screenWidth    Total screen/view plane width
     * @param screenHeight   Total screen/view plane height
     * @return the required constructed ray
     * @throws IllegalArgumentException illegal cases which can cause false results or exceptions
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {
        if (isZero(screenDistance) || screenDistance < 0 || // view plane in location of camera or behind
                i >= nY || j >= nX || i < 0 || j < 0)     // illegal values of indexes and number of pixels
            throw new IllegalArgumentException("Illegal values for view plane");
        // Image Center:
        Point3D pCenter = _p0.add(_vTo.scale(screenDistance));  // Pc = P0 + d∙Vto
        // Ratio (pixel width & height):
        double rY = alignZero(screenHeight / nY);   //  Ry = h/Ny
        double rX = alignZero(screenWidth / nX);   //  Rx = w/Nx
        // Pixel[i,j] center:
        double yI = (i - nY / 2d) * rY + rY / 2;                //  yi = (i – Ny/2)∙Ry + Ry/2
        double xJ = (j - nX / 2d) * rX + rX / 2;               //  xj = (j – Nx/2)∙Rx + Rx/2
        Point3D pIJ = pCenter;                           // Pi,j = Pc + (xj∙Vright – yi∙Vup)

        if (!isZero(xJ))            // if the location in matrix's row is 0, we should skip this command because
            pIJ = pIJ.add(_vRight.scale(xJ));  // we don't need it and it will throw exception of ZERO direction
        if (!isZero(yI))            // if the location in matrix's column is 0, we should skip this command, same reason
            pIJ = pIJ.add(_vUp.scale(-yI));
        Vector vIJ = pIJ.subtract(_p0).normalize();   //  vi,j = Pi,j – P0
        return new Ray(_p0, vIJ);
    }


    /**
     * Method construct list of rays,
     * super sampling rays has a starting point equal to the camera, and with direction to the pixel of the view plane,
     * depth of field rays has target point which is the focal point, and their beginning point is in the aperture plane
     * which is a surface of random points per pixel.
     * pixel selected by indexes j (column number) and i (line number).
     *
     * @param nX             number of pixels in every row
     * @param nY             number of pixels in every column
     * @param j              number of index represent which of the columns the ray is crossing through
     * @param i              number of index represent which of the rows the ray is crossing through
     * @param screenDistance minimum distance of camera to view plane
     * @param screenWidth    Total screen/view plane width
     * @param screenHeight   Total screen/view plane height
     * @return the required constructed ray
     * @throws IllegalArgumentException illegal cases which can cause false results or exceptions
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY,
                                               int j, int i, double screenDistance,
                                               double screenWidth, double screenHeight) {
        List<Ray> _rays = new ArrayList<>();
        Ray center_ray = constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth, screenHeight);
        if (_numOfRaysInPixel == 1) {
            _rays.add(center_ray);
        }
        double rY = alignZero(screenHeight / nY);   //  Ry = h/Ny
        double rX = alignZero(screenWidth / nX);   //  Rx = w/Nx
        if (_numOfRaysInPixel != 1) {
            _rays.addAll(center_ray.raysInGrid(_vUp, _vRight, _numOfRaysInPixel, screenDistance, rX, rY));
        }
        if (_numOfRaysInAperture != 1) {
            List<Ray> temp_rays = new LinkedList<>();
            // apertureSize is the value of how many pixels it spreads on
            double apertureRadius = sqrt(_apertureSize * (rY * rX)) / 2d;
            for (Ray ray : _rays)
                // creating list of focal rays (from the aperture plane towards the focal point)
                temp_rays.addAll(
                        ray.randomRaysInCircle(ray.get_p00(),
                                _vUp, _vRight, apertureRadius, _numOfRaysInAperture, _focalDist));
            _rays = temp_rays; // the original rays included in the temp rays
        }
        return _rays;
    }


    //*******Getters*******//

    /**
     * get the location point of camera
     *
     * @return 3D Point p0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * get the upper vector of the camera (represent the y axis of camera, position of the view)
     *
     * @return Vector vUp
     */
    public Vector getVUp() {
        return _vUp;
    }

    /**
     * get the forward vector of the camera (represent the z axis of camera, forward direction of the view)
     *
     * @return Vector vTo
     */
    public Vector getVTo() {
        return _vTo;
    }

    /**
     * get the right vector of the camera (represent the x axis of camera, position of the view)
     *
     * @return Vector vRight
     */
    public Vector getVRight() {
        return _vRight;
    }

    /**
     * get the number of rays per single pixel
     *
     * @return integer value number of rays
     */
    public int getNumOfRaysInPixel() {
        return _numOfRaysInPixel;
    }

    /**
     * get the number of rays in the aperture plane per single pixel
     *
     * @return integer value number of rays
     */
    public int getNumOfRaysInAperture() {
        return _numOfRaysInAperture;
    }

    /**
     * get the focal distance from the camera to the focal plane (the area of focus)
     *
     * @return value of distance
     */
    public double getFocalDist() {
        return _focalDist;
    }


    /**
     * get the aperture surface's (per pixel) size, value relative to a single pixel
     *
     * @return value of size which relative to a single pixel
     */
    public double getApertureSize() {
        return _apertureSize;
    }


    //*******Setters*******//

    /**
     * Set super sampling on
     *
     * @param numOfRaysInPixel number of rays in a single pixel for active super sampling
     * @return the camera object itself
     * @throws IllegalArgumentException for invalid number of rays or has a sqrt which isn't integer
     */
    public Camera setSuperSampling(int numOfRaysInPixel)
            throws IllegalArgumentException {
        if (numOfRaysInPixel <= 0 || (int) sqrt(numOfRaysInPixel) * sqrt(numOfRaysInPixel) != numOfRaysInPixel)
            throw new IllegalArgumentException("the number of rays in grid must be at least 1 and has an integer sqrt");
        this._numOfRaysInPixel = numOfRaysInPixel;
        return this;
    }


    /**
     * Set depth of field on
     *
     * @return the camera object itself
     * @param numOfRaysInAperture number of rays in the focal plane of the camera (per pixel),
     *                            the more rays in focal plane the more quality of the blurring
     * @param focalDist the distance between the camera's beginning location to the center of focal plane,
     *                  forward vision becomes sharper in the distance, the more it's close to the focal plane
     * @param apertureSize the size of the aperture surface (located in the camera location),
     *                    the size is relative to a single pixel, example: 1 = one pixel, and so on.
     *                    the smaller the aperture the greater the width of the sharpness,
     *                    (similar to blind view without glasses)
     * @throws IllegalArgumentException for invalid number of rays in the surface
     *
     */
    public Camera setDepthOfField(int numOfRaysInAperture, double focalDist, double apertureSize) {
        if (numOfRaysInAperture <= 0)
            throw new IllegalArgumentException("the number of rays in the aperture surface must be at least 1");
        this._numOfRaysInAperture = numOfRaysInAperture;
        this._focalDist = focalDist;
        this._apertureSize = apertureSize;
        return this;
    }


}