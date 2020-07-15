package primitives;

import geometries.Tube;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import static primitives.Point3D.ZERO;

/**
 * Class Vector is basic object in geometry of direction and size, defined by the end point
 * (where a starting point is the beginning of the axis)
 */
public class Vector {
    protected Point3D _head;

    //*********** Constructors ***********//

    /**
     * Constructor for creating a vector from a Point3D
     *
     * @param head Point3D representing the head
     * @throws IllegalArgumentException in any case of the head point input is the beginning of the axes
     */
    public Vector(Point3D head) {
        if (_head.equals(ZERO))
            throw new IllegalArgumentException("A vector can't have head point to be the ZERO point");
        this._head = head;
    }

    /**
     * Constructor for creating a vector by input of three numbers representing coordinates
     *
     * @param x number representing the place of coordinate in x axis
     * @param y number representing the place of coordinate in y axis
     * @param z number representing the place of coordinate in z axis
     * @throws IllegalArgumentException in case of the three numbers input are zero
     */
    public Vector(double x, double y, double z) {
        _head = new Point3D(x, y, z);
        if (_head.equals(ZERO))
            throw new IllegalArgumentException("A vector can't have head point to be the ZERO point");
    }

    public Vector(Vector other) {
        _head = other._head;
    }

    //********** Getters ***********/

    /**
     * Vector value getter
     *
     * @return head of vector value in Point3D representation
     */
    public Point3D get_head() {
        return _head;
    }

    //********** Calculation methods ***********/

    /**
     * Vector Subtraction: Subtraction between two vectors returns a vector with direction from the subtract head point
     * to the subtracted head point.
     *
     * @param vector representing the vector we are about to subtract
     * @return a vector from the second vector's head point to the first vector's head point at which the action is performed.
     * @throws IllegalArgumentException if result is ZERO point - there will be exception in Constructor
     */
    public Vector subtract(Vector vector) {
        return _head.subtract(vector._head);
    }

    /**
     * Vector interconnect: Returns a new vector whose head is a point containing the interconnect result of two vector
     *
     * @param vector a vector which his head point value added to the first vector's head point, by that we have a new point
     * @return a new vector whose head point coordinate values are the result of the vector interconnect operation
     * @throws IllegalArgumentException if result is ZERO point - there will be exception in Constructor
     */
    public Vector add(Vector vector) {
        return new Vector(_head._x._coord + vector._head._x._coord,
                _head._y._coord + vector._head._y._coord,
                _head._z._coord + vector._head._z._coord);
    }

    /**
     * Scalar multiplication: (Returns New Vector)
     *
     * @param c scalar which is for to be multiplied by the coordinate values of a point of a vector
     * @return a new vector contains a point which it's value represent the result of the multiplication operation
     * @throws IllegalArgumentException if result is ZERO point - there will be exception in Constructor
     */
    public Vector scale(double c) {
        return new Vector(_head._x._coord * c,
                _head._y._coord * c,
                _head._z._coord * c);
    }

    /**
     * dotProduct
     *
     * @param other The second Vector which we about to do on him the dot product
     * @return dot product (double)
     */
    public double dotProduct(Vector other) {
        return _head._x._coord * other._head._x._coord +
                _head._y._coord * other._head._y._coord +
                _head._z._coord * other._head._z._coord;
    }

    /**
     * Vector Multiplier - Returns a new vector that is perpendicular to the two existing vectors (cross-product)
     *
     * @param vector the other Vector which we about to do on him the cross product
     * @return Vector for cross product using right thumb rule
     * @throws IllegalArgumentException if result is ZERO point - there will be exception in Constructor
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                this._head._y._coord * vector._head._z._coord - this._head._z._coord * vector._head._y._coord,
                this._head._z._coord * vector._head._x._coord - this._head._x._coord * vector._head._z._coord,
                this._head._x._coord * vector._head._y._coord - this._head._y._coord * vector._head._x._coord);
    }

    /**
     * The length of the vector squared
     *
     * @return the number representing the sum of values in the vector's head point, squared
     */
    public double lengthSquared() {
        return alignZero(dotProduct(this));
    }

    /**
     * The length of the vector
     *
     * @return the number representing the sum of values in the vector's head point
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * The vector normalization action changes the vector itself
     * (the only action that changes the object on which it was summoned)
     * the action also return the vector for concatenation of operations if necessary
     *
     * @return the vector after it normalized
     * @throws ArithmeticException - if length of vector is too short (close to zero) we can't divide by zero.
     */
    public Vector normalize() throws ArithmeticException {
        if (isZero(length())) // if we try to normalize vector ZERO
            throw new ArithmeticException("divide by Zero");
        this._head = scale(1 / length())._head;
        return this;
    }

    /**
     * normalization action which change a copy of the vector
     * the action also return the vector for concatenation of operations if necessary
     *
     * @return a copy of the vector after it normalized
     */
    public Vector normalized() {
        return (new Vector(this)).normalize();
    }

    /**
     * methoid which create an orthogonal vector to the current instance
     * @return the new orthogonal vector
     */
    public Vector getOrthogonal2() {
        double[] board = {_head._x._coord, _head._y._coord, _head._z._coord};
        // technique of creating orthogonal vector which prevent from creating vector zero.
        if (board[0] < board[1] && board[0] < board[2])  // x , y , z, -- x = 0, -z, y
            return new Vector(0, -board[2], board[1]).normalize();
        if (board[1] < board[0] && board[1] < board[2])  // x , y , z, -- y = -z, 0, x
            return new Vector(-board[2], 0, board[1]).normalize();
        // x , y , z, -- z = -y, x, 0
        return new Vector(-board[1], board[0], 0).normalize();
    }

    public Vector getOrthogonal() {
        return new Vector(-_head._z._coord, 0, _head._x._coord).normalize();
    }

    /*************** Admin *****************/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Vector)) return false;
        Vector other_vector = (Vector) obj;
        return this._head.equals(other_vector._head);
    }

    @Override
    public String toString() {
        return "(Vector{)" +
                "_head=" + _head +
                '}';
    }
}
