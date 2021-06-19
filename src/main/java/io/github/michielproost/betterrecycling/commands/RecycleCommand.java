package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.messaging.messenger.Messenger;
import io.github.michielproost.betterrecycling.events.RecycleInventory;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 *  Command: /br recycle
 *  Recycle the materials in the recycle inventory into their crafting components.
 * @author Michiel Proost
 */
public class RecycleCommand extends InventoryCommand {

    public RecycleCommand(Messenger messenger, RecycleInventory recycleInventory) {
        super(messenger, recycleInventory);
    }

    @Override
    public @NotNull String getCommandName() {
        return "recycle";
    }

    @Override
    public @NotNull List<String> getAliases() {
        return Collections.singletonList("r");
    }

    @Override
    public @NotNull String getPermission() {
        return "betterrecycling.recycle";
    }

    @Override
    public boolean execute(Player player, Command command, String[] strings)
    {
        // Print contents.
        System.out.println( recycleInventory );
        // Recycle the materials.
        recycleInventory.recycle();
        // Command is used correctly.
        return true;
    }
}