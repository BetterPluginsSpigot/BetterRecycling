package io.github.michielproost.betterrecycling.events;

import be.betterplugins.core.messaging.messenger.Messenger;
import io.github.michielproost.betterrecycling.model.Recycler;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Listener: RecycleInventory
 * An inventory in which the player can recycle materials into their crafting components.
 * @author Michiel Proost
 */
public class RecycleInventory implements Listener {

    private final Inventory recycleInventory;
    private final Messenger messenger;

    /**
     * Create a new recycle inventory.
     * @param messenger The messenger.
     */
    public RecycleInventory(Messenger messenger)
    {
        // Create an empty inventory with no owner.
        recycleInventory = Bukkit.createInventory(null, 36, "RecycleBin");
        // Initialize the messenger.
        this.messenger = messenger;
    }

    /**
     * Get the non-empty storage contents of the inventory.
     * @return The non-empty storage contents of the inventory.
     */
    public ItemStack[] getNonEmptyStorageContents(){
        return Recycler.getNonEmptyStorageContents(
                recycleInventory.getStorageContents()
        );
    }

    /**
     * Add ItemStacks to the recycle inventory.
     * @param itemStacks The stacks to put in the inventory.
     */
    public void addItems(ItemStack[] itemStacks)
    {
        // Put each ItemStack in the recycle bin.
        recycleInventory.setStorageContents( itemStacks );
    }

    /**
     * Add an ItemStack to the recycle inventory.
     * @param itemStack The stack to put in the inventory.
     */
    public void addItem(ItemStack itemStack)
    {
        recycleInventory.addItem( itemStack );
    }

    /**
     * Print all ItemStacks in the recycle inventory.
     */
    @Override
    public String toString()
    {
        // The non-empty storage contents.
        ItemStack[] contents = Recycler.getNonEmptyStorageContents( recycleInventory.getStorageContents() );
        // Build the message.
        StringBuilder builder = new StringBuilder( "\nThe following items are in the recycle inventory:\n" );
        for (ItemStack stack: contents) {
            // Add itemStack to message.
            builder.append(stack.getType().name())
                    .append(" : ")
                    .append(stack.getAmount())
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * Open the inventory.
     * @param entity A human entity (NPC or player).
     */
    public void openInventory(final HumanEntity entity)
    {
        entity.openInventory( recycleInventory );
    }

    /**
     * Message shown when a new player joins the server.
     * @param event The event.
     */
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
    {
        messenger.sendMessage(
                new ArrayList<Player>(Bukkit.getOnlinePlayers()), "player_join"
        );
    }

}