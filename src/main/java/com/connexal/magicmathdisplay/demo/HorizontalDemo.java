package com.connexal.magicmathdisplay.demo;

import com.connexal.magicmathdisplay.math.Rotations;
import com.connexal.magicmathdisplay.math.Vector3d;
import com.connexal.magicmathdisplay.renderer.Frame;
import com.connexal.magicmathdisplay.renderer.Sequence;
import com.connexal.magicmathdisplay.renderer.primitives.Rotatable;

public class HorizontalDemo extends Demo {
    private static final int TICKS_PER_FRAME = 2; // Each frame lasts 2 ticks
    private static final int TOTAL_FRAMES = 10 * (20 / TICKS_PER_FRAME);

    private final Rotatable rotatable;
    private final Vector3d pos1;
    private final Vector3d pos2;

    public HorizontalDemo(Rotatable rotatable, Vector3d pos1, Vector3d pos2) {
        this.rotatable = rotatable;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    @Override
    protected int getTicksPerFrame() {
        return TICKS_PER_FRAME;
    }

    @Override
    protected Sequence generateSequence() {
        Sequence.SequenceBuilder sequenceBuilder = Sequence.builder();

        // Interpolate position between pos1 and pos2
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            double t = (double) i / (TOTAL_FRAMES - 1);
            t = 0.5 - (0.5 * Math.cos(t * (Math.PI * 2))); // Ease in-out

            Vector3d currentPos = Vector3d.interpolate(pos1, pos2, t);
            Rotatable tmp = this.rotatable.copy();
            tmp.setCentre(currentPos);

            sequenceBuilder.addFrame(Frame.builder().addPrimitive(tmp).build());
        }

        return sequenceBuilder.buildVelocity(true);
    }
}
