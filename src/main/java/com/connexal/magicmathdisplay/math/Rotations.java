package com.connexal.magicmathdisplay.math;

/**
 * Utility class for rotation-related mathematical operations.
 */
public class Rotations {
    /**
     * Rotates a vector around a given axis by a specified angle.
     * @param axis the axis to rotate around
     * @param angle the angle in radians
     * @param vector the vector to be rotated
     * @return the new rotated vector
     */
    public static Vector3d rotateVector(Vector3d axis, double angle, Vector3d vector) {
        if (axis.magnitude() == 0) {
            throw new IllegalArgumentException("Can't use a null axis vector for rotation");
        }

        Quaternion q = Quaternion.fromVector3d(vector);

        double halfAngle = angle / 2;
        Quaternion axisQ = Quaternion.fromVector3d(Vector3d.normalized(axis));
        axisQ.scale(Math.sin(halfAngle));
        axisQ.add(Math.cos(halfAngle), 0, 0, 0);

        Quaternion rotated = Quaternion.mul(Quaternion.mul(axisQ, q), Quaternion.inverse(axisQ));
        return Vector3d.fromQuaternion(rotated);
    }
}
