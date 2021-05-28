package io.github.michielproost.betterrecycling;

import io.github.michielproost.betterrecycling.commands.CommandRecycle;
import io.github.michielproost.betterrecycling.dagger.DaggerInjector;
import io.github.michielproost.betterrecycling.dagger.Injector;
import io.github.michielproost.betterrecycling.dagger.modules.CommandRecycleModule;
import io.github.michielproost.betterrecycling.dagger.modules.PluginModule;
import io.github.michielproost.betterrecycling.events.RecycleInventory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import javax.inject.Inject;
import java.io.File;

/**
 * BetterRecycling Plugin:
 * A plugin in which you can recycle materials into their crafting components.
 * @author Michiel Proost
 */
public class BetterRecycling extends JavaPlugin {

    /**
     * Constructor required for MockBukkit.
     */
    @Inject
    public BetterRecycling()
    {
        super();
    }

    /**
     * Constructor required for MockBukkit.
     */
    protected BetterRecycling(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable()
    {
        super.onEnable();

        // Dependency injection with Dagger.
        Injector injector = DaggerInjector.builder()
                .pluginModule( new PluginModule(this) )
                .commandRecycleModule( new CommandRecycleModule() )
                .build();

        // Register commands.
        CommandRecycle commandRecycle = injector.getCommandRecycle();
        this.getCommand("recycle").setExecutor( commandRecycle );

        // Register listener.
        RecycleInventory recycleInventory = injector.getRecycleInventory();
        this.getServer().getPluginManager().registerEvents(recycleInventory, this);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

}