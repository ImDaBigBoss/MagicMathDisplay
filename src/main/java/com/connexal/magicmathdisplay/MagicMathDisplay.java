package com.connexal.magicmathdisplay;

import com.connexal.magicmathdisplay.renderer.Renderer;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicMathDisplay extends JavaPlugin {
    private static MagicMathDisplay instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        Renderer.cleanupRenderedEntities();
    }

    public static MagicMathDisplay getInstance() {
        return instance;
    }
}
