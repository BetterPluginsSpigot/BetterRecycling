package io.github.michielproost.betterrecycling.commands;

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
    private final HelpCommand help;
    // Recycle command.
    private final RecycleCommand recycle;

    /**
     * Create a new CommandHandler.
     * @param messenger The messenger.
     */
    public CommandHandler( Messenger messenger )
    {
        // Initialize the messenger.
        this.messenger = messenger;

        // Recycle command.
        this.recycle = new RecycleCommand( messenger );

        // Create map.
        this.commands = new HashMap<String, PlayerBPCommand>()
        {{
            // RecycleCommand:
            put( recycle.getCommandName(), recycle );
        }};

        // Help command.
        this.help = new HelpCommand( messenger, commands );
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             String[] args)
    {
        // No subcommands.
        if (args.length == 0)
            // Execute recycle command.
            return recycle.execute( sender, cmd, args );
        else
            // Execute help command.
            return help.execute( sender, cmd, args );
    }

}