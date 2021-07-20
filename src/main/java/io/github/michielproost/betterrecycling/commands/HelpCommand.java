package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.shortcuts.PlayerBPCommand;
import be.betterplugins.core.messaging.messenger.Messenger;
import be.betterplugins.core.messaging.messenger.MsgEntry;
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
    public HelpCommand( Messenger messenger, Map<String, PlayerBPCommand> commands ) {
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
    public boolean execute(@NotNull Player player, @NotNull Command command, @NotNull String[] args) {
        // Has required permission.
        if ( !player.hasPermission( getPermission( ) ) ) {
            messenger.sendMessage(
                    player,
                    "permission.required",
                    new MsgEntry( "<Command>", "/recycle " + getCommandName() )
            );
            return true;
        }

        // Display intro.
        messenger.sendMessage(
                player,
                "help.intro",
                new MsgEntry( "<PlayerName>", player.getDisplayName() )
        );

        // Display explanation of every command.
        for (PlayerBPCommand cmd: commandSet){
            String subCommand = cmd.getCommandName().equals( "recycle" ) ? "" : " " + cmd.getCommandName();
            messenger.sendMessage(
                    player,
                    "help." + cmd.getCommandName(),
                    new MsgEntry( "<Command>", "/recycle" + subCommand)
            );
        }

        // Command was used correctly.
        return true;
    }

}