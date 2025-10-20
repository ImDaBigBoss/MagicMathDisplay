package com.connexal.magicmathdisplay.math;

public class Sphere {
    /**
     * Generates points on a sphere using the Fibonacci sphere algorithm.
     * @param pointCount the number of points to generate
     * @param radius the radius of the sphere
     * @return an array of Vector3d points on the sphere
     * @throws IllegalArgumentException if pointCount is less than 5 or radius is not positive
     */
    public static Vector3d[] fibonacciSphere(int pointCount, double radius) {
        if (pointCount < 5) {
            throw new IllegalArgumentException("A sphere must have at least 3 points.");
        }
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius of the sphere must be more than 0.");
        }

        Vector3d[] points = new Vector3d[pointCount];

        double phi = Math.PI * (Math.sqrt(5.) - 1.); // golden angle in radians
        for (int i = 0; i < pointCount; i++) {
            double y = 1 - (i / (double)(pointCount - 1)) * 2; // y goes from 1 to -1
            double radiusAtY = Math.sqrt(1 - y * y); // radius at y

            double theta = phi * i; // golden angle increment

            double x = Math.cos(theta) * radiusAtY;
            double z = Math.sin(theta) * radiusAtY;

            points[i] = new Vector3d(x * radius, y * radius, z * radius);
        }

        return points;
    }
}
