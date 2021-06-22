package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.shortcuts.PlayerBPCommand;
import be.betterplugins.core.messaging.messenger.Messenger;
import be.betterplugins.core.messaging.messenger.MsgEntry;
import io.github.michielproost.betterrecycling.model.Recycler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Command: /br recycle | r
 * Recycle the player's handheld item into its corresponding crafting components.
 * @author Michiel Proost
 */
public class RecycleCommand extends PlayerBPCommand {

    /**
     * Recycle the player's handheld item.
     * @param messenger The messenger.
     */
    public RecycleCommand(Messenger messenger) {
        super( messenger );
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
        // Get handheld item.
        ItemStack handheld = player.getInventory().getItemInMainHand();
        // The handheld item's type.
        String handheldTypeName = handheld.getType().name().toLowerCase();
        // Recycle the handheld item.
        ItemStack[] recycled = Recycler.recycle( handheld );
        // Item cannot be recycled.
        if (recycled.length == 0){
            messenger.sendMessage(
                    player,
                    "fail.recycle",
                    new MsgEntry( "<ItemTypeName>", handheldTypeName )
            );
            return true;
        }
        // Get player's inventory.
        Inventory inventory = player.getInventory();
        // Add recycled components to inventory.
        inventory.addItem( recycled );
        // Remove handheld item from player's inventory.
        inventory.removeItem( handheld );
        // Command was used correctly.
        return true;
    }

}