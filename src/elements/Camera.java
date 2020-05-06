package elements;

import primitives.*;

import static primitives.Util.*;

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
     * Class Camera represent view of the geometric world through the view plane (which represent the picture),
     * Through the view plane the camera captures the geometric world.
     * it produces graphic views of the objects using the view plane and rays and object intersections.
     * The rays converge according to the location of the pixel centers in the view plane.
     *
     * @param p0  location of the camera
     * @param vTo direction of the camera to the front (compared to the original z axis)
     * @param vUp direction of the camera to the up (compared to the original y axis)
     * @throws IllegalArgumentException for two vectors (vUp and vDown) which should be orthogonal
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
        this._p0 = new Point3D(p0);
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
                                        double screenWidth, double screenHeight) throws IllegalArgumentException {
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
        if (!isZero(xJ))
            pIJ = pIJ.add(_vRight.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(_vUp.scale(-yI));
        Vector vIJ = pIJ.subtract(_p0).normalize();   //  vi,j = Pi,j – P0
        return new Ray(_p0, vIJ);
    }


    //*******Getters*******//

    public Point3D get_p0() {
        return new Point3D(_p0);
    }

    public Vector get_vUp() {
        return new Vector(_vUp);
    }

    public Vector get_vTo() {
        return new Vector(_vTo);
    }

    public Vector get_vRight() {
        return new Vector(_vRight);
    }
}
