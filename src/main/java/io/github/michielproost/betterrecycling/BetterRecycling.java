package io.github.michielproost.betterrecycling;

import be.betterplugins.core.messaging.logging.BPLogger;
import be.betterplugins.core.messaging.messenger.Messenger;
import be.dezijwegel.betteryaml.BetterLang;
import be.dezijwegel.betteryaml.OptionalBetterYaml;
import io.github.michielproost.betterrecycling.commands.CommandHandler;
import io.github.michielproost.betterrecycling.commands.OpenCommand;
import io.github.michielproost.betterrecycling.commands.RecycleCommand;
import io.github.michielproost.betterrecycling.events.RecycleInventory;
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

        // Get localisation.
        BetterLang localisation = getLocalisation( config );

        // Create messenger.
        Messenger messenger =
                new Messenger(
                        localisation.getMessages(),
                        new BPLogger(Level.WARNING),
                        ChatColor.BLUE + "[BR] " + ChatColor.DARK_GREEN );

        // Register listener.
        RecycleInventory recycleInventory = new RecycleInventory( messenger );
        this.getServer().getPluginManager().registerEvents(recycleInventory, this);

        // Register commands.
        CommandHandler commandHandler = new CommandHandler(
                new OpenCommand( messenger, recycleInventory),
                new RecycleCommand( messenger, recycleInventory )
        );
        this.getCommand("betterrecycling").setExecutor( commandHandler );
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

}