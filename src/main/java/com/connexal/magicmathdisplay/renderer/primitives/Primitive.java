package com.connexal.magicmathdisplay.renderer.primitives;

import com.connexal.magicmathdisplay.math.Vector3d;

import java.util.Collection;

public interface Primitive {
    /**
     * Gets the points that make up the primitive.
     * @return a set of points
     */
    Collection<Vector3d> getPoints();

    /**
     * Gets the centre point of the primitive.
     * @return the centre point
     */
    Vector3d getCentre();

    /**
     * Creates and returns a copy of this primitive.
     * @return a copy of this primitive
     */
    Primitive copy();

    /**
     * Moves the primitive by the specified vector transformation.
     * @param vector the vector to use as offset
     */
    void offset(Vector3d vector);
}
