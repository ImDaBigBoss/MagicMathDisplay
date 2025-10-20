package com.connexal.magicmathdisplay.renderer;

import com.connexal.magicmathdisplay.math.Vector3d;
import com.connexal.magicmathdisplay.renderer.primitives.Primitive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a single frame in a 3D rendering context, containing a set of points.
 */
public class Frame {
    private final double[][] pointsArray;

    private Frame(double[][] pointsArray) {
        this.pointsArray = pointsArray;
    }

    /**
     * Returns the set of points in this frame.
     * @return array of points represented as arrays of doubles
     */
    public double[][] getPoints() {
        return this.pointsArray;
    }


    /**
     * Creates a new FrameBuilder instance.
     * @return a new FrameBuilder
     */
    public static FrameBuilder builder() {
        return new FrameBuilder();
    }

    /**
     * Builder class for constructing Frame instances.
     */
    public static class FrameBuilder {
        private final List<double[]> points;

        private FrameBuilder() {
            this.points = new ArrayList<>();
        }

        /**
         * Adds a point to the frame.
         * @param point the point to add
         * @return the current FrameBuilder instance
         */
        public FrameBuilder addPoint(Vector3d point) {
            this.points.add(new double[] { point.getX(), point.getY(), point.getZ(), 0.0, 0.0, 0.0 });
            return this;
        }

        /**
         * Adds a point with velocity to the frame.
         * @param point the point to add
         * @param velocity the velocity of the point
         * @return the current FrameBuilder instance
         */
        public FrameBuilder addPoint(Vector3d point, Vector3d velocity) {
            this.points.add(new double[] { point.getX(), point.getY(), point.getZ(), velocity.getX(), velocity.getY(), velocity.getZ() });
            return this;
        }

        /**
         * Adds a primitive's points to the frame.
         * @param primitive the primitive to add
         * @return the current FrameBuilder instance
         */
        public FrameBuilder addPrimitive(Primitive primitive) {
            for (Vector3d point : primitive.getPoints()) {
                this.addPoint(point);
            }

            return this;
        }

        public FrameBuilder addPrimitive(Primitive currentState, Primitive nextState) {
            List<Vector3d> currentPoints = (List<Vector3d>) currentState.getPoints();
            List<Vector3d> nextPoints = (List<Vector3d>) nextState.getPoints();

            int numPoints = currentPoints.size();
            if (numPoints != nextPoints.size()) {
                throw new IllegalArgumentException("Current state and next state must have the same number of points.");
            }

            for (int i = 0; i < numPoints; i++) {
                Vector3d currentPoint = currentPoints.get(i);
                Vector3d nextPoint = nextPoints.get(i);
                Vector3d velocity = Vector3d.difference(nextPoint, currentPoint);
                this.addPoint(currentPoint, velocity);
            }

            return this;
        }

        /**
         * Builds the Frame instance.
         * @return a new Frame containing the added points
         */
        public Frame build() {
            double[][] pointsArray = new double[points.size()][6];
            for (int i = 0; i < points.size(); i++) {
                pointsArray[i] = points.get(i);
            }
            return new Frame(pointsArray);
        }
    }
}
