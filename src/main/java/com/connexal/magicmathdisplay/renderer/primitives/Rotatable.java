package com.connexal.magicmathdisplay.renderer.primitives;

import com.connexal.magicmathdisplay.math.Rotations;
import com.connexal.magicmathdisplay.math.Vector3d;

import java.util.Collection;
import java.util.List;

public class Rotatable implements Primitive {
    /**
     * The points that make up the primitive.
     */
    private final Vector3d[] points;
    /**
     * The centre point of the primitive.
     */
    private final Vector3d centre;
    /**
     * The normal vector of the primitive's plane. It has a magnitude of 1.
     */
    private final Vector3d normal;

    Rotatable(Vector3d centre, Vector3d normal, Vector3d[] points) {
        this.centre = centre;
        this.normal = normal;
        this.points = points;
    }

    /**
     * Sets the centre of the primitive by translating all points.
     * @param centre the new centre position
     */
    public void setCentre(Vector3d centre) {
        for (Vector3d point : this.points) {
            point.add(Vector3d.difference(centre, this.centre));
        }
        this.centre.set(centre);
    }

    /**
     * Sets the normal vector of the primitive's plane.
     * This will effectively rotate the primitive to align with the new normal.
     * @param normal the new normal vector (should be a unit vector)
     */
    public void setNormal(Vector3d normal) {
        if (normal.magnitude() == 0) {
            throw new IllegalArgumentException("Normals cannot be zero");
        }
        Vector3d normalVector = Vector3d.normalized(normal);

        if (this.normal.equals(Vector3d.zero()) || this.normal.equals(normalVector)) {
            return; // No point in doing anything
        }

        // Calculate the angle between the two normals
        double dotProduct = Vector3d.dot(this.normal, normalVector);
        double angle = Math.acos(dotProduct); // We don't need to divide by magnitudes as both are unit vectors

        // Special cases :
        // - Maybe the vectors are collinear with the same direction
        if (dotProduct == 1) {
            return; // No point in doing anything
        }
        // - Maybe the vectors are opposites, in which case we rotate by PI around the
        else if (dotProduct == -1) {
            // Find a new rotation axis, in between both vectors
            Vector3d rotationAxis = new Vector3d(-this.normal.getY(), this.normal.getX(), 0);
            if (rotationAxis.equals(Vector3d.zero())) {
                rotationAxis = new Vector3d(this.normal.getZ(), 0, -this.normal.getX());
                if (rotationAxis.magnitude() == 0) {
                    throw new InternalError("Unexpected rotation axis");
                }
            }

            this.rotate(Math.PI, rotationAxis);
        }
        // No special case
        else {
            // Find the rotation axis
            Vector3d rotationAxis = Vector3d.cross(this.normal, normalVector).normalize();
            // Rotate the primitive around the rotation axis by the calculated angle
            this.rotate(angle, rotationAxis);
        }

        this.normal.set(normalVector); // We set it directly to avoid floating point inaccuracies, even if it was set during rotation
    }

    /**
     * Rotates the primitive around the specified axis by the given angle.
     * @param angle angle in radians
     * @param axis the axis to rotate around
     * @param centre the point to rotate around
     */
    public void rotate(double angle, Vector3d axis, Vector3d centre) {
        // Calculate new normal
        this.normal.set(Rotations.rotateVector(axis, angle, this.normal).normalize());

        // Calculate the new centre
        if (!this.centre.equals(centre)) {
            Vector3d relativeCentre = Vector3d.difference(this.centre, centre);
            relativeCentre = Rotations.rotateVector(axis, angle, relativeCentre);
            this.centre.set(Vector3d.add(relativeCentre, centre));
        }

        // Rotate each point around the axis
        for (Vector3d point : this.points) {
            Vector3d relativeVector = Vector3d.difference(point, centre);
            relativeVector = Rotations.rotateVector(axis, angle, relativeVector);
            point.set(Vector3d.add(relativeVector, centre));
        }
    }

    /**
     * Rotates the primitive around the specified axis by the given angle.
     * @param angle angle in radians
     * @param axis the axis to rotate around
     */
    public void rotate(double angle, Vector3d axis) {
        this.rotate(angle, axis, this.centre);
    }

    /**
     * Gets the normal vector of the object
     * @return the normal vector
     */
    public Vector3d getNormal() {
        return this.normal.copy();
    }

    /**
     * Gets the points that make up the primitive.
     * @return a set of points
     */
    @Override
    public Collection<Vector3d> getPoints() {
        return List.of(this.points);
    }

    @Override
    public Vector3d getCentre() {
        return this.centre.copy();
    }

    @Override
    public Rotatable copy() {
        // Create a deep copy of all the variables
        Vector3d[] pointsCopy = new Vector3d[this.points.length];
        for (int i = 0; i < pointsCopy.length; i++) {
            pointsCopy[i] = this.points[i].copy();
        }
        return new Rotatable(this.centre.copy(), this.normal.copy(), pointsCopy);
    }

    /**
     * Moves the primitive by the specified vector transformation.
     * @param vector the vector to use as offset
     */
    @Override
    public void offset(Vector3d vector) {
        this.setCentre(Vector3d.add(this.centre, vector));
    }
}
