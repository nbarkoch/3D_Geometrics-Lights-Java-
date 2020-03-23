package primitives;
import static primitives.Util.isZero;

/**
 * Class Vector is basic object in geometry of direction and size, defined by the end point
 * (where a starting point is the beginning of the axes)
 */
public class Vector {
    Point3D _head;

    public final static Vector ZERO = new Vector(new Point3D(0.0, 0.0, 0.0));

    //*********** Constructors ***********//

    /**
     * Constructor for creating a vector from a Vector
     * @param _vector Vector representing the vector to build as new one
     */
    public Vector(Vector _vector) {
        this._head = new Point3D(_vector._head);
    }

    /**
     * Constructor for creating a vector from a Point3D
     * @param _head Point3D representing the head
     * @throws IllegalArgumentException in any case of the head point input is the beginning of the axes
     */
    public Vector(Point3D _head) throws IllegalArgumentException {
        if (ZERO != null && _head.equals(ZERO._head)) // first the will create the vector zero, next time there's shouldn't more than he.
            throw new IllegalArgumentException("A vector can't have head point to be the ZERO point");
        this._head = new Point3D(_head);
    }

    /**
     * Constructor for creating a vector by input of three coordinates of the head point
     * @param _x coordinate on the x axis
     * @param _y coordinate on the y axis
     * @param _z coordinate on the z axis
     * @throws IllegalArgumentException in case of the three coordinates input value are zero
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) throws IllegalArgumentException {
        this(new Point3D(_x,_y,_z));
    }

    /**
     * Constructor for creating a vector by input of three numbers representing coordinates
     * @param x number representing the place of coordinate in x axis
     * @param y number representing the place of coordinate in y axis
     * @param z number representing the place of coordinate in z axis
     * @throws IllegalArgumentException in case of the three numbers input are zero
     */
    public Vector(double x, double y, double z) throws IllegalArgumentException  {
        this(new Point3D(x,y,z));
    }

    /**
     * Vector value getter
     * @return head of vector value in Point3D representation
     */
    public Point3D get_head() {
        return new Point3D(_head);
    }

    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector other_vector = (Vector) o;
        return this._head.equals(other_vector._head);
    }

    @Override
    public String toString() {
        return "(Vector{)" +
                "_head=" + _head +
                '}';
    }

    /**
     * Vector Subtraction: Subtraction between two vectors returns a vector with direction from the subtract head point
     * to the subtracted head point.
     * @param _vector representing the vector we are about to subtract
     * @return a vector from the second vector's head point to the first vector's head point at which the action is performed.
     */
    public Vector subtract(Vector _vector) {
        return new Vector(_head.subtract(_vector.get_head()));
    }

    /**
     * Vector interconnect: Returns a new vector whose head is a point containing the interconnect result of two vector
     * @param _vector a vector which his head point value added to the first vector's head point, by that we have a new point
     * @return a new vector whose head point coordinate values are the result of the vector interconnect operation
     */
    public Vector add(Vector _vector) {
        return new Vector(_head.add(_vector));
    }

    /**
     * Scalar multiplication: (Returns New Vector)
     * @param c scalar which is for to be multiplied by the coordinate values of a point of a vector
     * @return a new vector contains a point which it's value represent the result of the multiplication operation
     */
    public Vector scale(double c){
        return new Vector(_head._x.get()*c,
                            _head._y.get()*c,
                            _head._z.get()*c);
    }

    /**
     * dotProduct
     * @param other The second Vector which we about to do on him the dot product
     * @return dot product (double)
     */
    public double dotProduct(Vector other) {
        return _head._x.get() * other._head._x.get() +
                _head._y.get() * other._head._y.get() +
                _head._z.get() * other._head._z.get();
    }

    /**
     * Vector Multiplier - Returns a new vector that is perpendicular to the two existing vectors (cross-product)
     * @param _vector the other Vector which we about to do on him the cross product
     * @return Vector for cross product using right thumb rule
     */
    public Vector crossProduct(Vector _vector) {
        return new Vector(this._head._y.get()*_vector._head._z.get() - this._head._z.get()*_vector._head._y.get(),
                this._head._z.get()*_vector._head._x.get() - this._head._x.get()*_vector._head._z.get(),
                this._head._x.get()*_vector._head._y.get() - this._head._y.get()*_vector._head._x.get());
    }

    /**
     * The length of the vector squared
     * @return the number representing the sum of values in the vector's head point, squared
     */
    public double lengthSquared(){
        return this._head._x.get()*this._head._x.get()+
                this._head._y.get()*this._head._y.get()+
                this._head._z.get()*this._head._z.get();
    }

    /**
     * The length of the vector
     * @return the number representing the sum of values in the vector's head point
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }


    /**
     * The vector normalization action changes the vector itself
     * (the only action that changes the object on which it was summoned)
     * the action also return the vector for concatenation of operations if necessary
     * @return the vector after it normalized
     */
    public Vector normalize(){
        if(isZero(length())) // if we try to normalize vector ZERO
            return this; // can't davide by zero
        this._head = scale(1/length())._head;
        return this;
    }

    /**
     * normalization action which change a copy of the vector
     * the action also return the vector for concatenation of operations if necessary
     * @return a copy of the vector after it normalized
     */
    public Vector normalized(){
        return (new Vector(this)).normalize();
    }
}
