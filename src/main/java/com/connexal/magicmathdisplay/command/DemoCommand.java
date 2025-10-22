package com.connexal.magicmathdisplay.command;

import com.connexal.magicmathdisplay.MagicMathDisplay;
import com.connexal.magicmathdisplay.demo.*;
import com.connexal.magicmathdisplay.math.Vector3d;
import com.connexal.magicmathdisplay.renderer.primitives.Rotatable;
import com.connexal.magicmathdisplay.renderer.primitives.RotatableBuilder;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

import java.util.*;

public class DemoCommand implements BasicCommand {
    private final Map<String, Demo> demos = new HashMap<>();

    public DemoCommand() {
        MagicMathDisplay.getInstance().getServer().getScheduler().runTaskAsynchronously(MagicMathDisplay.getInstance(), () -> {
            Rotatable exampleCircle = RotatableBuilder.circle(Vector3d.zero(), 5, 100, Vector3d.up());
            Rotatable exampleRectangle = RotatableBuilder.rectangle(Vector3d.zero(), 5, 2, 100, Vector3d.up());
            Rotatable exampleSphere = RotatableBuilder.sphere(Vector3d.zero(), 5, 300);
            Rotatable exampleStar = RotatableBuilder.star(Vector3d.zero(), 10, 10, 20);

            demos.put("circle", new StaticDemo(exampleCircle.copy()));
            demos.put("rectangle", new StaticDemo(exampleRectangle.copy()));
            demos.put("sphere", new StaticDemo(exampleSphere.copy()));
            demos.put("star", new StaticDemo(exampleStar.copy()));
            demos.put("spinning_circle", new SpinningDemo(exampleCircle.copy()));
            demos.put("spinning_rectangle", new SpinningDemo(exampleRectangle.copy()));
            demos.put("spinning_star", new SpinningDemo(exampleStar.copy()));
            demos.put("spinning_sphere", new SpinningDemo(exampleSphere.copy()));
            demos.put("rolling_sphere", new RollingDemo(exampleSphere.copy(), 5));
            demos.put("horizontal_circle", new HorizontalDemo(exampleCircle.copy(), Vector3d.north().scale(5), Vector3d.south().scale(5)));
        });
    }

    @Override
    public void execute(CommandSourceStack ctx, String[] strings) {
        if (!(ctx.getExecutor() instanceof Player)) {
            ctx.getSender().sendMessage("This command can only be run by a player.");
            return;
        }
        if (strings.length != 2) {
            ctx.getSender().sendMessage("Usage /mmddemo <demo_name> <start|stop>");
            return;
        }

        String demoName = strings[0];
        Demo demo = demos.get(demoName);
        if (demo == null) {
            ctx.getSender().sendMessage("Demo not found: " + demoName);
            return;
        }

        if (strings[1].equalsIgnoreCase("stop")) {
            try {
                demo.stopDemo();
            } catch (Exception e) {
                ctx.getSender().sendMessage("Error stopping demo: " + e.getMessage());
                return;
            }
            ctx.getSender().sendMessage("Stopped demo: " + demoName);
        } else if (strings[1].equalsIgnoreCase("start")) {
            try {
                demo.startDemo(ctx.getLocation());
            } catch (Exception e) {
                ctx.getSender().sendMessage("Error starting demo: " + e.getMessage());
                return;
            }
            ctx.getSender().sendMessage("Started demo: " + demoName);
        } else {
            ctx.getSender().sendMessage("Invalid action. Use start or stop.");
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack ctx, String[] args) {
        if (args.length == 1) {
            return demos.keySet();
        } else if (args.length == 2) {
            return List.of("start", "stop");
        }
        return Collections.emptyList();
    }
}
