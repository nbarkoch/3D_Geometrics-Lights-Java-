package primitives;

import java.util.Objects;

public class Ray {
    Point3D _p00;
    Vector _direction;

    public Ray(Point3D _p00, Vector _direction) {
        this._p00 = _p00;
        this._direction = _direction;
    }

    public Point3D get_p00() {
        return _p00;
    }

    public Vector get_direction() {
        return _direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(_p00, ray._p00) &&
                Objects.equals(_direction, ray._direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p00=" + _p00 +
                ", _direction=" + _direction +
                '}';
    }
}
