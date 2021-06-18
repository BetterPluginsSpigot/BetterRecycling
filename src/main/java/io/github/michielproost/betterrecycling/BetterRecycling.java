package io.github.michielproost.betterrecycling;

import io.github.michielproost.betterrecycling.commands.CommandHandler;
import io.github.michielproost.betterrecycling.dagger.DaggerInjector;
import io.github.michielproost.betterrecycling.dagger.Injector;
import io.github.michielproost.betterrecycling.dagger.modules.CommandHandlerModule;
import io.github.michielproost.betterrecycling.dagger.modules.PluginModule;
import io.github.michielproost.betterrecycling.events.RecycleInventory;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

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

    /**
     * Implement BetterYaml.
     * @return The YAML configuration.
     */
    public YamlConfiguration getConfig()
    {
        // BetterYaml-config implementation
        YamlConfiguration config = new YamlConfiguration();
        try
        {
            BetterYaml betterYaml = new BetterYaml("config.yml", this, true);
            config = betterYaml.getYamlConfiguration();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return config;
    }

    @Override
    public void onEnable()
    {
        super.onEnable();

        // Dependency injection with Dagger.
        Injector injector = DaggerInjector.builder()
                .pluginModule( new PluginModule(this) )
                .commandHandlerModule( new CommandHandlerModule( ) )
                .build();

        // Register listener.
        RecycleInventory recycleInventory = injector.getRecycleInventory();
        this.getServer().getPluginManager().registerEvents(recycleInventory, this);

        // Register commands.
        CommandHandler commandHandler = injector.getCommandHandler();
        this.getCommand("betterrecycling").setExecutor( commandHandler );
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

}