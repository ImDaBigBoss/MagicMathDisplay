package com.connexal.magicmathdisplay.math;

/**
 * Class representing a 3-dimensional vector and providing basic operations.
 */
public class Vector3d {
    private double x;
    private double y;
    private double z;

    /**
     * Constructor to initialize a 3D vector with given components.
     * @param x x component
     * @param y y component
     * @param z z component
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Get the x component of the vector.
     * @return the x component
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get the y component of the vector.
     * @return the y component
     */
    public double getY() {
        return this.y;
    }

    /**
     * Get the z component of the vector.
     * @return the z component
     */
    public double getZ() {
        return this.z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3d v) {
        this.set(v.x, v.y, v.z);
    }

    /**
     * Add the given components to this vector. The operation is performed in place, updating this vector.
     * @param x the x component to add
     * @param y the y component to add
     * @param z the z component to add
     * @return same instance of the vector
     */
    public Vector3d add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Add the given vector to this vector. The operation is performed in place, updating this vector.
     * @param v the vector to add
     * @return same instance of the vector
     */
    public Vector3d add(Vector3d v) {
        return this.add(v.x, v.y, v.z);
    }

    /**
     * Add two vectors and return a new vector representing the sum.
     * @param v1 the first vector
     * @param v2 the second vector
     * @return a new vector representing the sum
     */
    public static Vector3d add(Vector3d v1, Vector3d v2) {
        return v1.copy().add(v2);
    }

    /**
     * Scale this vector by the given scalar. The operation is performed in place, updating this vector.
     * @param scalar the scaling factor
     * @return same instance of the vector
     */
    public Vector3d scale(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    /**
     * Scale the given vector by the specified scalar. The original vector remains unchanged.
     * @param v the vector to scale
     * @param scalar the scaling factor
     * @return a new scaled vector
     */
    public static Vector3d scale(Vector3d v, double scalar) {
        return v.copy().scale(scalar);
    }

    /**
     * Calculate the magnitude of the vector.
     * @return the magnitude
     */
    public double magnitude() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    /**
     * Normalize the vector to have a magnitude of 1. The operation is performed in place, updating this vector.
     * If the vector is zero, it remains unchanged.
     * @return same instance of the vector
     */
    public Vector3d normalize() {
        double magnitude = this.magnitude();
        if (magnitude == 0) {
            return this;
        }

        return this.scale(1 / magnitude);
    }

    /**
     * Normalize the given vector to have a magnitude of 1. The original vector remains unchanged.
     * @param v the vector to normalize
     * @return a new normalized vector
     */
    public static Vector3d normalized(Vector3d v) {
        return v.copy().normalize();
    }

    /**
     * Calculate the dot product of two vectors.
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the dot product
     */
    public static double dot(Vector3d v1, Vector3d v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    /**
     * Calculate the cross product of two vectors.
     * @param v1 the first vector
     * @param v2 the second vector
     * @return a new vector representing the cross product
     */
    public static Vector3d cross(Vector3d v1, Vector3d v2) {
        double cx = v1.y * v2.z - v1.z * v2.y;
        double cy = v1.z * v2.x - v1.x * v2.z;
        double cz = v1.x * v2.y - v1.y * v2.x;
        return new Vector3d(cx, cy, cz);
    }

    /**
     * Calculates the vector between the points at the end of both vector.
     * The direction of the output vector will be from B to A.
     * @param a the destination vector
     * @param b the start vector
     * @return a vector going from B to A
     */
    public static Vector3d difference(Vector3d a, Vector3d b) {
        return Vector3d.add(Vector3d.scale(b, -1), a);
    }

    /**
     * Linearly interpolates between two vectors based on the parameter t.
     * @param a the starting vector
     * @param b the ending vector
     * @param t the interpolation factor (0.0 to 1.0)
     * @return a new vector representing the interpolated result
     */
    public static Vector3d interpolate(Vector3d a, Vector3d b, double t) {
        double x = a.x + ((b.x - a.x) * t);
        double y = a.y + ((b.y - a.y) * t);
        double z = a.z + ((b.z - a.z) * t);
        return new Vector3d(x, y, z);
    }

    /**
     * Creates a Vector3d from the given Quaternion's vector components.
     * @param q the quaternion
     * @return a new Vector3d representing the vector part of the quaternion
     */
    public static Vector3d fromQuaternion(Quaternion q) {
        return new Vector3d(q.getIComponent(), q.getJComponent(), q.getKComponent());
    }

    /**
     * Creates a copy of this vector.
     * @return a new Vector3d that is a copy of this vector
     */
    public Vector3d copy() {
        return new Vector3d(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Vector3d that = (Vector3d) obj;
        return Double.compare(that.x, this.x) == 0 &&
                Double.compare(that.y, this.y) == 0 &&
                Double.compare(that.z, this.z) == 0;
    }

    @Override
    public String toString() {
        return "Vector3d(" + x + ", " + y + ", " + z + ")";
    }

    /**
     * Creates a zero vector (0, 0, 0).
     * @return a new Vector3d
     */
    public static Vector3d zero() {
        return new Vector3d(0, 0, 0);
    }

    /**
     * Creates a Minecraft up vector (0, 1, 0).
     * @return a new Vector3d
     */
    public static Vector3d up() {
        return new Vector3d(0, 1, 0);
    }

    /**
     * Creates a Minecraft down vector (0, -1, 0).
     * @return a new Vector3d
     */
    public static Vector3d down() {
        return new Vector3d(0, -1, 0);
    }

    /**
     * Creates a Minecraft north vector (0, 0, -1).
     * @return a new Vector3d
     */
    public static Vector3d north() {
        return new Vector3d(0, 0, -1);
    }

    /**
     * Creates a Minecraft south vector (0, 0, 1).
     * @return a new Vector3d
     */
    public static Vector3d south() {
        return new Vector3d(0, 0, 1);
    }

    /**
     * Creates a Minecraft east vector (1, 0, 0).
     * @return a new Vector3d
     */
    public static Vector3d east() {
        return new Vector3d(1, 0, 0);
    }

    /**
     * Creates a Minecraft west vector (-1, 0, 0).
     * @return a new Vector3d
     */
    public static Vector3d west() {
        return  new Vector3d(0, 0, -1);
    }
}
