package com.connexal.magicmathdisplay;

import com.connexal.magicmathdisplay.command.DemoCommand;
import com.connexal.magicmathdisplay.renderer.Renderer;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicMathDisplay extends JavaPlugin {
    private static MagicMathDisplay instance;

    @Override
    public void onEnable() {
        instance = this;

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register("mmddemo", new DemoCommand());
        });
    }

    @Override
    public void onDisable() {
        Renderer.cleanupRenderedEntities();
    }

    public static MagicMathDisplay getInstance() {
        return instance;
    }
}
