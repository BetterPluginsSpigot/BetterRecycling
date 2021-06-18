package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.messaging.messenger.Messenger;
import io.github.michielproost.betterrecycling.events.RecycleInventory;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 *  Command: /br open
 *  Opens a GUI in which you can recycle materials into their crafting components.
 * @author Michiel Proost
 */
public class OpenCommand extends InventoryCommand {

    @Inject
    public OpenCommand(Messenger messenger, RecycleInventory recycleInventory) {
        super(messenger, recycleInventory);
    }

    @Override
    public @NotNull String getCommandName() {
        return "open";
    }

    @Override
    public @NotNull List<String> getAliases() {
        return Collections.singletonList("o");
    }

    @Override
    public @NotNull String getPermission() {
        return "betterrecycling.open";
    }

    @Override
    public boolean execute(@NotNull Player player, @NotNull Command command, String[] strings)
    {
        // Open the recycle inventory.
        recycleInventory.openInventory( player );
        // Print contents.
        System.out.println( recycleInventory );
        // Command is used correctly.
        return true;
    }

}