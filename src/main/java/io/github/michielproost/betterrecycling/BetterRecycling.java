package io.github.michielproost.betterrecycling;

import io.github.michielproost.betterrecycling.commands.CommandRecycle;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.Objects;

/**
 * BetterRecycling Plugin:
 * A plugin in which you can recycle materials into their crafting components.
 * Author: Michiel Proost
 */
public class BetterRecycling extends JavaPlugin {

    // Command to recycle materials.
    private final CommandRecycle commandRecycle;

    public BetterRecycling()
    {
        super();
        this.commandRecycle = new CommandRecycle();
    }

    /*
    Extra constructor that will be initialized before each test.
     */
    protected BetterRecycling(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
        this.commandRecycle = new CommandRecycle();
    }

    @Override
    public void onEnable()
    {
        Objects.requireNonNull(
                this.getCommand("recycle")
        ).setExecutor( commandRecycle );
    }

    @Override
    public void onDisable()
    {
        // TODO. Unregister commands, events & runnables.
    }

}