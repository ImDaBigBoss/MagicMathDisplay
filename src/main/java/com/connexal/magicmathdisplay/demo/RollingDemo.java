package com.connexal.magicmathdisplay.demo;

import com.connexal.magicmathdisplay.math.Vector3d;
import com.connexal.magicmathdisplay.renderer.Frame;
import com.connexal.magicmathdisplay.renderer.Sequence;
import com.connexal.magicmathdisplay.renderer.primitives.Rotatable;

public class RollingDemo extends Demo {
    private static final int TICKS_PER_FRAME = 2; // Each frame lasts 2 ticks
    private static final int TOTAL_FRAMES = 100 * (20 / TICKS_PER_FRAME);

    private final Rotatable rotatable;
    private final int objectRadius;

    public RollingDemo(Rotatable rotatable, int objectRadius) {
        this.rotatable = rotatable;
        this.objectRadius = objectRadius;
    }

    @Override
    protected int getTicksPerFrame() {
        return TICKS_PER_FRAME;
    }

    @Override
    protected Sequence generateSequence() {
        // Offset the object so it can be moved around the centre point
        Rotatable template = this.rotatable.copy();
        Vector3d localCentre = template.getCentre().copy();
        template.setNormal(Vector3d.north());
        template.offset(template.getNormal().scale(objectRadius * 2));

        double rotationIncrement = (2 * Math.PI * this.objectRadius) / TOTAL_FRAMES;
        double rollIncrement = (this.objectRadius * (rotationIncrement / 2));

        Sequence.SequenceBuilder sequenceBuilder = Sequence.builder();
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            Rotatable tmp = template.copy();

            // Do the rotation around the centre point
            tmp.rotate(rotationIncrement * i, Vector3d.up(), localCentre);
            // Do the rotation of the object around itself
            tmp.rotate(rollIncrement * i, Vector3d.difference(localCentre, tmp.getCentre()));

            sequenceBuilder.addFrame(Frame.builder().addPrimitive(tmp).build());
        }

        return sequenceBuilder.buildVelocity(true);
    }
}
