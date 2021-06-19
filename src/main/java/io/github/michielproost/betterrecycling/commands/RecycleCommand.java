package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.BPCommand;
import be.betterplugins.core.messaging.messenger.Messenger;
import io.github.michielproost.betterrecycling.events.RecycleInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
    public boolean execute(@NotNull Player player, @NotNull Command cmd, String[] args)
    {
        // Argument is given.
        if (args[2] != null)
        {
            // Get the appropriate command.
            String commandName = args[2].toLowerCase();

            // Execute the appropriate command.
            switch (commandName){
                // Recycle materials in recycle inventory.
                case "inventory": {
                    return recycleInventory();
                }
                // Recycle player's handheld item.
                case "own": {
                    return recycleHandheld( player );
                }
            }
        } else {
            // Recycle materials in recycle inventory.
            return recycleInventory();
        }
        // Command was used correctly.
        return true;
    }

    /**
     * Recycle the materials in the recycle inventory.
     * @return Whether or not the command was used correctly.
     */
    private boolean recycleInventory()
    {
        // Print contents.
        System.out.println( recycleInventory );
        // Recycle the materials.
        RecycleInventory.recycle( recycleInventory.getNonEmptyStorageContents() );
        // Command is used correctly.
        return true;
    }

    /**
     * Recycle the player's handheld item and put the materials in its inventory.
     * @param player The player who issued the command.
     * @return Whether or not the command was used correctly.
     */
    private boolean recycleHandheld( Player player )
    {
        // Get handheld item.
        ItemStack handheld = player.getInventory().getItemInMainHand();
        // Convert to array.
        ItemStack[] handheldArray = {handheld};
        // Recycle the handheld item.
        RecycleInventory.recycle( handheldArray );
        // Command is used correctly.
        return true;
    }

}