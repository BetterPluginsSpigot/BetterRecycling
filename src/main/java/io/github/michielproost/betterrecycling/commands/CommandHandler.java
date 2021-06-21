package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.BPCommand;
import be.betterplugins.core.messaging.messenger.Messenger;
import org.bukkit.Bukkit;
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
    private final Map<String, BPCommand> commands;

    /**
     * Create a new CommandHandler.
     * @param recycle Recycle the player's handheld item.
     * @param messenger The messenger.
     */
    public CommandHandler(Messenger messenger, RecycleCommand recycle)
    {
        // Initialize the messenger.
        this.messenger = messenger;
        // Create map.
        this.commands = new HashMap<String, BPCommand>()
        {{
            put(recycle.getCommandName(), recycle);
        }};
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        // An argument has to be given.
        if (args.length > 0)
        {
            // Get the appropriate command.
            String commandName = args[0].toLowerCase();
            BPCommand bpCommand = this.commands.get( commandName );
            // Execute command.
            return bpCommand.execute(sender, cmd, args);
        } else {
            // No argument was given.
            messenger.sendMessage(sender, "required.argument");
            return true;
        }
    }

}