package com.connexal.magicmathdisplay.demo;

import com.connexal.magicmathdisplay.math.Rotations;
import com.connexal.magicmathdisplay.math.Vector3d;
import com.connexal.magicmathdisplay.renderer.Frame;
import com.connexal.magicmathdisplay.renderer.Sequence;
import com.connexal.magicmathdisplay.renderer.primitives.Rotatable;

public class SpinningDemo extends Demo {
    private static final int TICKS_PER_FRAME = 2; // Each frame lasts 2 ticks
    private static final int TOTAL_FRAMES = 10 * (20 / TICKS_PER_FRAME);

    private final Rotatable rotatable;

    public SpinningDemo(Rotatable rotatable) {
        this.rotatable = rotatable;
    }

    @Override
    protected int getTicksPerFrame() {
        return TICKS_PER_FRAME;
    }

    @Override
    protected Sequence generateSequence() {
        // Rotate the object in double time around an axis while also rotating this axis around the up vector
        double anglePerFrameAxis = (2 * Math.PI) / TOTAL_FRAMES;
        double anglePerFrameObject = (4 * Math.PI) / TOTAL_FRAMES;

        Vector3d axis = new Vector3d(1, 1, 0).normalize();

        Sequence.SequenceBuilder sequenceBuilder = Sequence.builder();
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            Rotatable tmp = this.rotatable.copy();
            Vector3d currentAxis = Rotations.rotateVector(Vector3d.up(), anglePerFrameAxis * i, axis);
            tmp.rotate(anglePerFrameObject * i, currentAxis);

            Frame frame = Frame.builder().addPrimitive(tmp).build();
            sequenceBuilder.addFrame(frame);
        }

        return sequenceBuilder.buildVelocity(true);
    }
}
