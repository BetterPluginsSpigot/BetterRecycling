package io.github.michielproost.betterrecycling;

import be.betterplugins.core.messaging.logging.BPLogger;
import be.betterplugins.core.messaging.messenger.Messenger;
import be.dezijwegel.betteryaml.BetterLang;
import be.dezijwegel.betteryaml.OptionalBetterYaml;
import io.github.michielproost.betterrecycling.commands.CommandHandler;
import io.github.michielproost.betterrecycling.dagger.DaggerInjector;
import io.github.michielproost.betterrecycling.dagger.Injector;
import io.github.michielproost.betterrecycling.dagger.modules.CommandHandlerModule;
import io.github.michielproost.betterrecycling.dagger.modules.PluginModule;
import io.github.michielproost.betterrecycling.events.RecycleInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Inject;
import java.io.File;
import java.util.Optional;
import java.util.logging.Level;

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
     * Get BetterLang localization.
     * @param configuration The YAML configuration from BetterYaml.
     * @return The BetterLang localization.
     */
    public BetterLang getLocalisation(YamlConfiguration configuration)
    {
        String language = configuration.getString("language");
        return new BetterLang("lang.yml", language + ".yml", this);
    }

    @Override
    public void onEnable()
    {
        super.onEnable();

        // Get configuration from BetterYaml.
        OptionalBetterYaml optionalConfig = new OptionalBetterYaml("config.yml", this);
        Optional<YamlConfiguration> loadResult = optionalConfig.getYamlConfiguration();

        // Configuration is found.
        if ( loadResult.isPresent() )
        {
            // The configuration.
            YamlConfiguration config = loadResult.get();

            // Get localisation.
            BetterLang localisation = getLocalisation( config );

            // Create messenger.
            Messenger messenger =
                    new Messenger( localisation.getMessages(), new BPLogger(Level.WARNING), "[BR] " + ChatColor.DARK_AQUA );

            // Dependency injection with Dagger.
            Injector injector = DaggerInjector.builder()
                    .pluginModule( new PluginModule(this) )
                    .commandHandlerModule( new CommandHandlerModule( messenger ) )
                    .build();

            // Register listener.
            RecycleInventory recycleInventory = injector.getRecycleInventory();
            this.getServer().getPluginManager().registerEvents(recycleInventory, this);

            // Register commands.
            CommandHandler commandHandler = injector.getCommandHandler();
            this.getCommand("betterrecycling").setExecutor( commandHandler );
        } else {
            // Configuration was not found.
            Bukkit.getLogger().severe("Configuration was not found. Disabling plugin.");
            // Disable plugin.
            getServer().getPluginManager().disablePlugin( this );
        }
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

}