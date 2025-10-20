package com.connexal.magicmathdisplay.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sequence of frames in an animation.
 */
public class Sequence {
    private final double[][][] framesArray;

    private Sequence(double[][][] framesArray) {
        this.framesArray = framesArray;
    }

    /**
     * Returns the frame at the specified index.
     * @param index the index of the frame to retrieve
     * @return the frame as a 2D array of doubles
     */
    public double[][] getFrame(int index) {
        return this.framesArray[index];
    }

    public int getTotalFrames() {
        return this.framesArray.length;
    }

    public boolean isEmpty() {
        return this.getTotalFrames() == 0;
    }

    /**
     * Creates a new SequenceBuilder instance.
     * @return a new SequenceBuilder
     */
    public static SequenceBuilder builder() {
        return new SequenceBuilder();
    }

    /**
     * Builder class for constructing Sequence instances.
     */
    public static class SequenceBuilder {
        private final List<double[][]> frames;

        private SequenceBuilder() {
            this.frames = new ArrayList<>();
        }

        /**
         * Adds a frame to the sequence.
         * @param frame the frame to add
         * @return the current SequenceBuilder instance
         */
        public SequenceBuilder addFrame(Frame frame) {
            this.frames.add(frame.getPoints());
            return this;
        }

        public Sequence buildVelocity(boolean looping) {
            int numFrames = this.frames.size();
            if (numFrames < 2) {
                return this.build(); // Not enough frames to infer velocity
            }

            // Make sure each frame has the same number of points
            for (int i = 1; i < numFrames; i++) {
                if (this.frames.get(i).length != this.frames.getFirst().length) {
                    throw new IllegalStateException("All frames must have the same number of points to infer velocity.");
                }
            }

            // Calculate velocities by taking differences between consecutive frames
            double[][][] framesArray = new double[numFrames][][];
            for (int i = 0; i < numFrames; i++) {
                double[][] currentFrame = this.frames.get(i);
                double[][] nextFrame = this.frames.get((i + 1 + numFrames) % numFrames);
                if (!looping && i == 0) {
                    // First frame, no previous frame to compare to
                    nextFrame = currentFrame;
                }

                double[][] velocityFrame = new double[currentFrame.length][6];
                for (int j = 0; j < currentFrame.length; j++) {
                    // Position
                    velocityFrame[j][0] = currentFrame[j][0];
                    velocityFrame[j][1] = currentFrame[j][1];
                    velocityFrame[j][2] = currentFrame[j][2];
                    // Velocity
                    velocityFrame[j][3] = nextFrame[j][0] - currentFrame[j][0];
                    velocityFrame[j][4] = nextFrame[j][1] - currentFrame[j][1];
                    velocityFrame[j][5] = nextFrame[j][2] - currentFrame[j][2];
                }

                framesArray[i] = velocityFrame;
            }

            return new Sequence(framesArray);
        }

        /**
         * Builds and returns the Sequence instance.
         * @return the constructed Sequence containing all added frames
         */
        public Sequence build() {
            double[][][] framesArray = new double[frames.size()][][];
            for (int i = 0; i < frames.size(); i++) {
                framesArray[i] = frames.get(i);
            }
            return new Sequence(framesArray);
        }
    }
}
