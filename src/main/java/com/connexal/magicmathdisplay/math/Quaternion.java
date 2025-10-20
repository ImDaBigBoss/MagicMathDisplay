package com.connexal.magicmathdisplay.math;

/**
 * Class representing a quaternion number and providing basic operations.
 * A quaternion is represented as: q = a + bi + cj + dk
 * where a, b, c, d are real numbers and i, j, k are the fundamental quaternion units.
 */
public class Quaternion {
    /**
     * Real part
     */
    private double a;
    /**
     * i component
     */
    private double b;
    /**
     * j component
     */
    private double c;
    /**
     * k component
     */
    private double d;

    /**
     * Constructor to initialize a quaternion with given components.
     * @param a real part
     * @param b i component
     * @param c j component
     * @param d k component
     */
    public Quaternion(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Create a copy of the given quaternion.
     * @param q the quaternion to copy
     */
    public Quaternion(Quaternion q) {
        this.a = q.a;
        this.b = q.b;
        this.c = q.c;
        this.d = q.d;
    }

    /**
     * Get the real part of the quaternion.
     * @return the real part
     */
    public double getRealPart() {
        return this.a;
    }

    /**
     * Get the i component of the quaternion.
     * @return the i component
     */
    public double getIComponent() {
        return this.b;
    }

    /**
     * Get the j component of the quaternion.
     * @return the j component
     */
    public double getJComponent() {
        return this.c;
    }

    /**
     * Get the k component of the quaternion.
     * @return the k component
     */
    public double getKComponent() {
        return this.d;
    }

    /**
     * Add given components to this quaternion. The operation is performed in place, updating this quaternion.
     * @param a the real part to add
     * @param b the i component to add
     * @param c the j component to add
     * @param d the k component to add
     * @return same instance of the quaternion
     */
    public Quaternion add(double a, double b, double c, double d) {
        this.a += a;
        this.b += b;
        this.c += c;
        this.d += d;
        return this;
    }

    /**
     * Add another quaternion to this quaternion. The operation is performed in place, updating this quaternion.
     * @param q the quaternion to add
     * @return same instance of the quaternion
     */
    public Quaternion add(Quaternion q) {
        this.a += q.a;
        this.b += q.b;
        this.c += q.c;
        this.d += q.d;
        return this;
    }

    /**
     * Add two quaternions and return a new quaternion as the result.
     * @param q1 first quaternion
     * @param q2 second quaternion
     * @return new quaternion representing the sum of q1 and q2
     */
    public static Quaternion add(Quaternion q1, Quaternion q2) {
        return new Quaternion(q1).add(q2);
    }

    /**
     * Multiply this quaternion by another quaternion. The operation is performed in place, updating this quaternion.
     * The computed result is this = this * q (and not this = q * this).
     * @param q the quaternion to multiply with
     * @return same instance of the quaternion
     */
    public Quaternion mul(Quaternion q) {
        double na = this.a * q.a - this.b * q.b - this.c * q.c - this.d * q.d;
        double nb = this.a * q.b + this.b * q.a + this.c * q.d - this.d * q.c;
        double nc = this.a * q.c - this.b * q.d + this.c * q.a + this.d * q.b;
        double nd = this.a * q.d + this.b * q.c - this.c * q.b + this.d * q.a;

        this.a = na;
        this.b = nb;
        this.c = nc;
        this.d = nd;
        return this;
    }

    /**
     * Multiply two quaternions and return a new quaternion as the result.
     * The computed result is q1 * q2 (and not q2 * q1).
     * @param q1 first quaternion
     * @param q2 second quaternion
     * @return new quaternion representing the product of q1 and q2
     */
    public static Quaternion mul(Quaternion q1, Quaternion q2) {
        return new Quaternion(q1).mul(q2);
    }

    /**
     * Scale this quaternion by a scalar value. The operation is performed in place, updating this quaternion.
     * @param scalar the scalar value to scale by
     * @return same instance of the quaternion
     */
    public Quaternion scale(double scalar) {
        this.a *= scalar;
        this.b *= scalar;
        this.c *= scalar;
        this.d *= scalar;
        return this;
    }

    /**
     * Scale a quaternion by a scalar value and return a new quaternion as the result.
     * @param q the quaternion to scale
     * @param scalar the scalar value to scale by
     * @return new quaternion representing the scaled quaternion
     */
    public static Quaternion scale(Quaternion q, double scalar) {
        return new Quaternion(q).scale(scalar);
    }

    /**
     * Compute the norm (magnitude) of this quaternion.
     * @return the norm of the quaternion
     */
    public double norm() {
        return Math.sqrt(a * a + b * b + c * c + d * d);
    }

    /**
     * Compute the conjugate of a quaternion and return a new quaternion as the result.
     * @param q the quaternion to conjugate
     * @return new quaternion representing the conjugate of q
     */
    public static Quaternion conjugate(Quaternion q) {
        return new Quaternion(q.a, -q.b, -q.c, -q.d);
    }

    /**
     * Compute the inverse of a quaternion and return a new quaternion as the result.
     * @param q the quaternion to invert
     * @return new quaternion representing the inverse of q
     * @throws ArithmeticException if the quaternion is zero (norm is zero)
     */
    public static Quaternion inverse(Quaternion q) {
        double norm = q.norm();
        if (norm == 0) {
            throw new ArithmeticException("Cannot compute inverse of a zero quaternion.");
        }

        Quaternion conjugate = conjugate(q);
        return scale(conjugate, 1.0 / (norm * norm));
    }

    /**
     * Create a quaternion from a 3D vector, setting the real part to zero.
     * @param v the 3D vector
     * @return new quaternion representing the vector
     */
    public static Quaternion fromVector3d(Vector3d v) {
        return new Quaternion(0, v.getX(), v.getY(), v.getZ());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Quaternion that = (Quaternion) obj;
        return Double.compare(that.a, this.a) == 0 &&
               Double.compare(that.b, this.b) == 0 &&
               Double.compare(that.c, this.c) == 0 &&
               Double.compare(that.d, this.d) == 0;
    }

    @Override
    public String toString() {
        return String.format("%.2f + %.2fi + %.2fj + %.2fk", a, b, c, d);
    }
}
