package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.shortcuts.PlayerBPCommand;
import be.betterplugins.core.messaging.messenger.Messenger;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Command: /br help | h
 * Displays manual for BetterRecycling commands.
 * @author Michiel Proost
 */
public class HelpCommand extends PlayerBPCommand {

    private final Set<PlayerBPCommand> commandSet;

    /**
     * Displays manual for BetterRecycling commands.
     * @param messenger The messenger.
     */
    public HelpCommand(Messenger messenger, Map<String, PlayerBPCommand> commands) {
        // Initialize messenger.
        super(messenger);
        // Set of BetterRecycling commands.
        this.commandSet = new HashSet<>( commands.values() );
    }

    @Override
    public @NotNull String getCommandName() {
        return "help";
    }

    @Override
    public @NotNull List<String> getAliases() {
        return Collections.singletonList("h");
    }

    @Override
    public @NotNull String getPermission() {
        return "betterrecycling.help";
    }

    @Override
    public boolean execute(@NotNull Player player, @NotNull Command command, @NotNull String[] strings) {
        // Display intro.
        messenger.sendMessage(player, "help.intro");
        // Display explanation of every command.
        for (PlayerBPCommand cmd: commandSet){
            messenger.sendMessage(player, "help." + cmd.getCommandName());
        }
        // Command was used correctly.
        return true;
    }

}