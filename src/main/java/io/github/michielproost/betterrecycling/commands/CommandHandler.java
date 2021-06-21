package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.BPCommand;
import be.betterplugins.core.commands.shortcuts.PlayerBPCommand;
import be.betterplugins.core.messaging.messenger.Messenger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to handle the BetterRecycling commands.
 * @author Michiel Proost
 */
public class CommandHandler implements CommandExecutor {

    // The messenger.
    private final Messenger messenger;
    // Map every command to its name.
    private final Map<String, PlayerBPCommand> commands;
    // Help command.
    private final HelpCommand helpCommand;

    /**
     * Create a new CommandHandler.
     * @param messenger The messenger.
     */
    public CommandHandler(Messenger messenger)
    {
        // Initialize the messenger.
        this.messenger = messenger;

        PlayerBPCommand recycle = new RecycleCommand( messenger );

        // Create map.
        this.commands = new HashMap<String, PlayerBPCommand>()
        {{
            // RecycleCommand:
            put(recycle.getCommandName(), recycle);
            for (String alias: recycle.getAliases()){
                put(alias, recycle);
            }
        }};

        this.helpCommand = new HelpCommand( messenger, commands );
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        // Get name of desired command.
        String commandName = args.length == 0 ? "help" : args[0].toLowerCase();
        // Check if command exists.
        if (commands.containsKey( commandName ))
        {
            // Get appropriate command.
            PlayerBPCommand playerBPCommand = commands.get( commandName );
            // Execute command.
            playerBPCommand.execute(sender, cmd, args);
        }
        else
        {
            // Execute help command.
            return helpCommand.execute( sender, cmd, args );
        }
        // Command is used correctly.
        return true;
    }

}