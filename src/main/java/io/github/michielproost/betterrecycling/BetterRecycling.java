package io.github.michielproost.betterrecycling;

import be.betterplugins.core.messaging.logging.BPLogger;
import be.betterplugins.core.messaging.messenger.Messenger;
import be.dezijwegel.betteryaml.BetterLang;
import be.dezijwegel.betteryaml.OptionalBetterYaml;
import io.github.michielproost.betterrecycling.Util.UpdateChecker;
import io.github.michielproost.betterrecycling.commands.CommandHandler;
import io.github.michielproost.betterrecycling.events.EventListener;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

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

        // Plugin ID for bStats.
        int pluginId = 11816;
        Metrics metrics = new Metrics(this, pluginId );

        // Get configuration from BetterYaml.
        OptionalBetterYaml optionalConfig = new OptionalBetterYaml("config.yml", this);
        Optional<YamlConfiguration> loadResult = optionalConfig.getYamlConfiguration();

        // Configuration is found.
        if ( !loadResult.isPresent() )
        {
            // Configuration was not found.
            Bukkit.getLogger().severe("Configuration was not found. Disabling plugin.");
            // Disable plugin.
            getServer().getPluginManager().disablePlugin( this );
            // Move out of method.
            return;
        }

        // The configuration.
        YamlConfiguration config = loadResult.get();

        // The language.
        String language = config.getString( "language" );

        // Get localisation.
        BetterLang localisation = new BetterLang("lang.yml", language + ".yml", this);

        // Custom bStats charts.
        metrics.addCustomChart( new SimplePie("language",()-> language) );

        // Create messenger.
        Messenger messenger =
                new Messenger(
                        localisation.getMessages(),
                        new BPLogger(Level.WARNING),
                        ChatColor.BLUE + "[BR] " + ChatColor.DARK_GREEN
                );

        // Register listener.
        EventListener eventListener = new EventListener( messenger, config );
        this.getServer().getPluginManager().registerEvents(eventListener, this);

        // Register commands.
        CommandHandler commandHandler = new CommandHandler( messenger );
        this.getCommand("betterrecycling").setExecutor( commandHandler );

        // Start UpdateChecker in a separate thread to not completely block the server.
        Thread updateChecker = new UpdateChecker(this);
        updateChecker.start();

    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

}