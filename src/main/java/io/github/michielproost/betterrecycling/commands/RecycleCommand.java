package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.shortcuts.PlayerBPCommand;
import be.betterplugins.core.messaging.messenger.Messenger;
import be.betterplugins.core.messaging.messenger.MsgEntry;
import io.github.michielproost.betterrecycling.util.ItemStackUtil;
import io.github.michielproost.betterrecycling.model.RecycleResult;
import io.github.michielproost.betterrecycling.model.Recycler;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Command: /br recycle | r
 * Recycle the player's handheld item into its corresponding crafting components.
 * @author Michiel Proost
 */
public class RecycleCommand extends PlayerBPCommand {

    private final Recycler recycler;

    /**
     * Recycle the player's handheld item.
     * @param messenger The messenger.
     */
    public RecycleCommand(Messenger messenger) {
        super( messenger );
        this.recycler = new Recycler( messenger );
    }

    @Override
    public @NotNull String getCommandName() {
        return "recycle";
    }

    @Override
    public @NotNull List<String> getAliases() {
        return Collections.singletonList( "r" );
    }

    @Override
    public @NotNull String getPermission() {
        return "betterrecycling";
    }

    @Override
    public boolean execute(@NotNull Player player, @NotNull Command cmd, String[] args)
    {
        // Has required permission.
        if ( !player.hasPermission( getPermission( ) ) )
        {
            messenger.sendMessage(
                    player,
                    "permission.required",
                    new MsgEntry( "<Command>", "/recycle")
            );
            return true;
        }

        // Get player's inventory.
        PlayerInventory inventory = player.getInventory();
        // Get handheld item.
        ItemStack handheld = inventory.getItemInMainHand();
        // Slot number of handheld item.
        int slot = inventory.getHeldItemSlot();
        // The handheld item's type.
        String handheldTypeName = handheld.getType().name().toLowerCase();
        // The handheld item's amount.
        int handheldAmount = handheld.getAmount();

        // Recycle the handheld item.
        RecycleResult result = recycler.recycle( handheld, player );
        // The resulting components.
        ItemStack[] components = result.getComponents();
        // Group material types.
        Map<String, Integer> componentMap = ItemStackUtil.groupMaterialTypes( components );
        // Convert to string.
        String componentMapToString = componentMap.keySet().stream()
                .map( key -> componentMap.get( key ) + " " + key )
                .collect(Collectors.joining(", ", "[", "]"));

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

        // Set new amount of ItemStack.
        if (handheld.getAmount() > 0)
            inventory.setItem( slot, handheld );
        // Remove ItemStack.
        else
            inventory.setItem( slot, new ItemStack( Material.AIR ) );
        // Add recycled components to inventory.
        inventory.addItem( components );
        // Display message to player that recycling was successful.
        messenger.sendMessage(
            player,
            "success.recycle",
            new MsgEntry("<HandheldItemName>", handheldAmount + " " + handheldTypeName ),
            new MsgEntry( "<ComponentNames>", componentMapToString ),
            new MsgEntry( "<NumberOfLeftovers>", handheld.getAmount() )
        );

        // Command was used correctly.
        return true;
    }

}