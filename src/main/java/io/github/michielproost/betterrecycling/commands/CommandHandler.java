package io.github.michielproost.betterrecycling.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to handle the BetterRecycling commands.
 * @author Michiel Proost, Dieter Nuytemans
 */
public class CommandHandler implements CommandExecutor {

    // Map every command to its name.
    private final Map<String, BRCommand> commands;

    @Inject
    public CommandHandler(OpenCommand open, RecycleCommand recycle)
    {
        // Create map.
        this.commands = new HashMap<String, BRCommand>()
        {{
            put(open.getCommandName(), open);
            put(recycle.getCommandName(), recycle);
        }};
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        String commandName;
        if (args.length > 0)
        {
            // Get the appropriate command.
            commandName = args[0].toLowerCase();
            BRCommand brCommand = this.commands.get( commandName );
            // Execute command.
            return brCommand.execute(sender, brCommand.getCommandName() );

        } else {
            Bukkit.getLogger().info("You need to provide an argument to issue this command.");
            return true;
        }
    }

}