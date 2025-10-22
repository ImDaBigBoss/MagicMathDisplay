package com.connexal.magicmathdisplay.demo;

import com.connexal.magicmathdisplay.renderer.Renderer;
import com.connexal.magicmathdisplay.renderer.Sequence;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

public abstract class Demo {
    private boolean isRunning = false;
    private BukkitTask task;

    protected abstract int getTicksPerFrame();

    protected abstract Sequence generateSequence();

    public void startDemo(Location location) {
        if (this.isRunning) {
            throw new IllegalStateException("SpinningCircleDemo has already been started.");
        }

        Sequence sequence;
        try {
            sequence = this.generateSequence();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to generate sequence.");
        }
        if (sequence == null || sequence.isEmpty()) {
            throw new IllegalStateException("Generated sequence is empty.");
        }

        // Set the running flag and initialize the task
        this.isRunning = true;
        this.task = Renderer.loopSequence(location, this.getTicksPerFrame(), sequence);
    }

    public void stopDemo() {
        if (!this.isRunning) {
            throw new IllegalStateException("SpinningCircleDemo is not running.");
        }

        // Cancel the task and reset the running flag
        this.task.cancel();
        this.isRunning = false;
    }
}
