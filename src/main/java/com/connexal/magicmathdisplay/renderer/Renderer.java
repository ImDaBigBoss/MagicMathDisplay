package com.connexal.magicmathdisplay.renderer;

import com.connexal.magicmathdisplay.MagicMathDisplay;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

public class Renderer {
    private static final int RENDER_DISTANCE = 64;
    private static final Colour DEFAULT_COLOUR = Colour.WHITE;

    /**
     * Renders a single frame in the specified world for the given duration.
     * @param location the location to render the frame at
     * @param durationTicks the duration to display the frame in ticks
     * @param points the points to render in the frame
     */
    private static void renderFrame(Location location, int durationTicks, double[][] points) {
        for (double[] point : points) {
            Location pointLocation = location.clone().add(point[0], point[1], point[2]);
            Location endLocation = pointLocation.clone().add(point[3], point[4], point[5]);

            Particle.TRAIL.builder()
                    .location(pointLocation)
                    .count(1)
                    .receivers(Renderer.RENDER_DISTANCE, true)
                    .offset(0, 0, 0)
                    .data(new Particle.Trail(endLocation, Color.fromRGB(DEFAULT_COLOUR.toRGBInt()), durationTicks))
                    .spawn();
        }
    }

    /**
     * Renders a single frame in the specified world for the given duration.
     * @param location the location to render the frame at
     * @param durationTicks the duration to display the frame in ticks
     * @param frame the frame to render
     */
    public static void renderFrame(Location location, int durationTicks, Frame frame) {
        Renderer.renderFrame(location, durationTicks, frame.getPoints());
    }

    /**
     * Renders a sequence of frames in the specified world, each for the given duration.
     * @param location the location to render the sequence at
     * @param frameDurationTicks the duration to display each frame in ticks
     * @param sequence the sequence of frames to render
     */
    public static void renderSequence(Location location, int frameDurationTicks, Sequence sequence) {
        if (sequence.isEmpty()) {
            return;
        }

        final AtomicInteger frameCounter = new AtomicInteger(0);

        MagicMathDisplay.getInstance().getServer().getScheduler().runTaskTimer(MagicMathDisplay.getInstance(), task -> {
            if (frameCounter.get() >= sequence.getTotalFrames()) {
                task.cancel();
                return;
            }

            double[][] frame = sequence.getFrame(frameCounter.getAndIncrement());
            Renderer.renderFrame(location, frameDurationTicks, frame);
        }, 0L, frameDurationTicks);
    }

    /**
     * Renders a sequence of frames in the specified world, each for the given duration.
     * When the end of the sequence is reached, it loops back to the beginning.
     * @param location the location to render the sequence at
     * @param frameDurationTicks the duration to display each frame in ticks
     * @param sequence the sequence of frames to render
     * @return the BukkitTask representing the scheduled task or null if the sequence is empty
     */
    public static BukkitTask loopSequence(Location location, int frameDurationTicks, Sequence sequence) {
        if (sequence.isEmpty()) {
            return null;
        }

        final AtomicInteger frameCounter = new AtomicInteger(0);

        return MagicMathDisplay.getInstance().getServer().getScheduler().runTaskTimer(MagicMathDisplay.getInstance(), () -> {
            if (frameCounter.get() >= sequence.getTotalFrames()) {
                frameCounter.set(0);
            }

            double[][] frame = sequence.getFrame(frameCounter.getAndIncrement());
            Renderer.renderFrame(location, frameDurationTicks, frame);
        }, 0L, frameDurationTicks);
    }

    public static void cleanupRenderedEntities() {
        // No need to do this yet as we only use particles which are not persistent
    }
}
