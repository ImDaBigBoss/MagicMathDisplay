package com.connexal.magicmathdisplay.renderer.primitives;

import com.connexal.magicmathdisplay.math.Sphere;
import com.connexal.magicmathdisplay.math.Vector3d;

/**
 * Utility class for creating rotatable primitives.
 */
public class RotatableBuilder {
    /**
     * Creates a circle with the specified radius, number of points, and normal vector.
     * @param centre the centre of the circle
     * @param radius the radius of the circle
     * @param pointCount the number of points to approximate the circle
     * @param normal the normal vector of the circle's plane (should be a unit vector)
     * @throws IllegalArgumentException if pointCount is less than 3
     */
    public static Rotatable circle(Vector3d centre, double radius, int pointCount, Vector3d normal) {
        if (pointCount < 3) {
            throw new IllegalArgumentException("A circle must have at least 3 points.");
        }
        if (radius <= 0) {
            throw new IllegalArgumentException("The radius of the circle must be more than 0.");
        }

        // Calculate the points to form the circle in the XY plane
        // This can be done by calculating the n-th complex roots of the unit (1).
        Vector3d[] points = new Vector3d[pointCount];
        for (int i = 0; i < pointCount; i++) {
            double angle = (2 * Math.PI * i) / pointCount;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            points[i] = new Vector3d(x, y, 0);
        }

        // Build the object
        Rotatable obj = new Rotatable(Vector3d.zero(), Vector3d.south(), points);
        obj.setCentre(centre); // Move to the correct centre
        obj.setNormal(normal); // Rotate to the correct normal
        return obj;
    }

    /**
     * Creates a rectangle with the specified radius, number of points, and normal vector.
     * @param centre the centre of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param pointCount the number of points to approximate the rectangle
     * @param normal the normal vector of the rectangle's plane (should be a unit vector)
     * @throws IllegalArgumentException if pointCount is less than 3
     */
    public static Rotatable rectangle(Vector3d centre, int width, int height, int pointCount, Vector3d normal) {
        if (pointCount < 3) {
            throw new IllegalArgumentException("A rectangle must have at least 4 points.");
        }

        // Calculate the points to form the rectangle in the XY plane
        Vector3d[] points = new Vector3d[pointCount];
        int perimeter = 2 * (width + height);
        for (int i = 0; i < pointCount; i++) {
            double distanceAlongPerimeter = ((double) i / pointCount) * perimeter;
            double x, y;
            if (distanceAlongPerimeter < width) {
                x = -width / 2.0 + distanceAlongPerimeter;
                y = -height / 2.0;
            } else if (distanceAlongPerimeter < width + height) {
                x = width / 2.0;
                y = -height / 2.0 + (distanceAlongPerimeter - width);
            } else if (distanceAlongPerimeter < 2 * width + height) {
                x = width / 2.0 - (distanceAlongPerimeter - (width + height));
                y = height / 2.0;
            } else {
                x = -width / 2.0;
                y = height / 2.0 - (distanceAlongPerimeter - (2 * width + height));
            }

            points[i] = new Vector3d(x, y, 0);
        }

        // Build the object
        Rotatable obj = new Rotatable(Vector3d.zero(), Vector3d.south(), points);
        obj.setCentre(centre); // Move to the correct centre
        obj.setNormal(normal); // Rotate to the correct normal
        return obj;
    }

    /**
     * Creates a sphere with the specified radius and a number of points.
     * @param centre the centre of the sphere
     * @param radius the radius of the sphere
     * @param pointCount the number of points to approximate the sphere
     * @throws IllegalArgumentException if pointCount is less than 3
     */
    public static Rotatable sphere(Vector3d centre, double radius, int pointCount) {
        // The fibonacci sphere algorithm already does sanity checks for inputs

        // Calculate the points on the sphere using the Fibonacci sphere algorithm
        Vector3d[] points = Sphere.fibonacciSphere(pointCount, radius);

        // Build the object
        Rotatable obj = new Rotatable(Vector3d.zero(), Vector3d.east(), points);
        obj.setCentre(centre); // Move to the correct centre
        return obj;
    }

    /**
     * Creates a star with the specified radius and a number of points.
     * @param centre the centre of the star
     * @param radius the radius of the star
     * @param branchCount the number of points to approximate the star
     * @throws IllegalArgumentException if pointCount is less than 3
     */
    public static Rotatable star(Vector3d centre, double radius, int branchCount, int pointsPerBranch) {
        // The fibonacci sphere algorithm already does sanity checks for inputs

        // Calculate the points on the sphere using the Fibonacci sphere algorithm
        Vector3d[] rootPoints = Sphere.fibonacciSphere(branchCount, radius);

        // Build the branches out from the root points
        Vector3d[] points = new Vector3d[branchCount * pointsPerBranch];
        for (int i = 0; i < branchCount; i++) {
            Vector3d root = Vector3d.normalized(rootPoints[i]).scale(radius / pointsPerBranch);
            for (int j = 0; j < pointsPerBranch; j++) {
                Vector3d point = Vector3d.scale(root, j + 1);
                points[i * pointsPerBranch + j] = point;
            }
        }

        // Build the object
        Rotatable obj = new Rotatable(Vector3d.zero(), Vector3d.east(), points);
        obj.setCentre(centre); // Move to the correct centre
        return obj;
    }
}
