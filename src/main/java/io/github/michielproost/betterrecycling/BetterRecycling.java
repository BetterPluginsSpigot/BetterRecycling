package io.github.michielproost.betterrecycling;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

/**
 * BetterRecycling Plugin:
 * A plugin in which you can recycle materials into their crafting components.
 * Author: Michiel Proost
 */
public class BetterRecycling extends JavaPlugin {

    public BetterRecycling()
    {
        super();
    }

    /*
    Extra constructor that will be initialized before each test.
     */
    protected BetterRecycling(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable()
    {
        // TODO. Register commands, events & runnables.
    }

    @Override
    public void onDisable()
    {
        // TODO. Unregister commands, events & runnables.
    }

}