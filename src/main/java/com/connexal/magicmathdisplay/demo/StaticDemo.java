package com.connexal.magicmathdisplay.demo;

import com.connexal.magicmathdisplay.renderer.Frame;
import com.connexal.magicmathdisplay.renderer.Sequence;
import com.connexal.magicmathdisplay.renderer.primitives.Primitive;

public class StaticDemo extends Demo {
    private static final int TICKS_PER_FRAME = 20; // Each frame lasts 20 ticks

    private final Primitive primitive;

    public StaticDemo(Primitive primitive) {
        this.primitive = primitive;
    }

    @Override
    protected int getTicksPerFrame() {
        return TICKS_PER_FRAME;
    }

    @Override
    protected Sequence generateSequence() {
        return Sequence.builder().addFrame(Frame.builder().addPrimitive(this.primitive).build()).build();
    }
}
