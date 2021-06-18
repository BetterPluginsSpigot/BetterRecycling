package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.BPCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to handle the BetterRecycling commands.
 * @author Michiel Proost
 */
public class CommandHandler implements CommandExecutor {

    // Map every command to its name.
    private final Map<String, BPCommand> commands;

    @Inject
    public CommandHandler(OpenCommand open, RecycleCommand recycle)
    {
        // Create map.
        this.commands = new HashMap<String, BPCommand>()
        {{
            put(open.getCommandName(), open);
            put(recycle.getCommandName(), recycle);
        }};
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (args.length > 0)
        {
            // Get the appropriate command.
            String commandName = args[0].toLowerCase();
            BPCommand bpCommand = this.commands.get( commandName );
            // Execute command.
            return bpCommand.execute(sender, cmd, args);
        } else {
            Bukkit.getLogger().info("You need to provide an argument to issue this command.");
            return true;
        }
    }

}