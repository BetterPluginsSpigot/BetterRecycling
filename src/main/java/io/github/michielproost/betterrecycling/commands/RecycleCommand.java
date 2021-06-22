package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.shortcuts.PlayerBPCommand;
import be.betterplugins.core.messaging.messenger.Messenger;
import be.betterplugins.core.messaging.messenger.MsgEntry;
import io.github.michielproost.betterrecycling.model.RecycleResult;
import io.github.michielproost.betterrecycling.model.Recycler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
        // Get player's inventory.
        PlayerInventory inventory = player.getInventory();
        // Get handheld item.
        ItemStack handheld = inventory.getItemInMainHand();
        // Slot number of handheld item.
        int slot = inventory.getHeldItemSlot();
        // The handheld item's type.
        String handheldTypeName = handheld.getType().name().toLowerCase();
        // Recycle the handheld item.
        RecycleResult result = Recycler.recycle( handheld );
        // The resulting components.
        ItemStack[] components = result.getComponents();
        // The new handheld item.
        handheld = result.getLeftOver();
        // Item cannot be recycled.
        if (components.length == 0){
            messenger.sendMessage(
                    player,
                    "fail.recycle",
                    new MsgEntry( "<ItemTypeName>", handheldTypeName )
            );
            return true;
        }
        // Add recycled components to inventory.
        inventory.addItem( components );
        // Set new amount of ItemStack
        if (handheld.getAmount() > 0)
            inventory.setItem( slot, handheld );
        // Remove ItemStack.
        else
            inventory.setItem( slot, new ItemStack( Material.AIR ) );
        // Command was used correctly.
        return true;
    }

}