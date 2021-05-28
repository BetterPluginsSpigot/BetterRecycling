package io.github.michielproost.betterrecycling.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

/**
 * Listener: RecycleInventory
 * An inventory in which the player can recycle materials into their crafting components.
 * @author Michiel Proost
 */
public class RecycleInventory implements Listener {

    private final Inventory recycleInventory;

    @Inject
    public RecycleInventory()
    {
        // Create an empty inventory with no owner.
        recycleInventory = Bukkit.createInventory(null, 36, "RecycleBin");
    }

    /**
     * Initialize the inventory with ItemStacks.
     * @param itemStacks The stacks to put in the inventory.
     */
    public void InitializeInventory(ItemStack[] itemStacks)
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
    public void print()
    {
        Bukkit.getLogger().info("The following items are in the recycle inventory:\n");
        for (ItemStack stack: recycleInventory.getStorageContents()) {
            // ItemStack exists.
            if (stack != null)
            {
                // Print itemStack to console.
                Bukkit.getLogger().info(
                        stack.getType().name() + " : " + stack.getAmount()
                );
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        // The player who joined.
        Player player = event.getPlayer();
        // Broadcast message to the whole server.
        Bukkit.broadcastMessage("Welcome to the server " + player.getDisplayName() + "!");
    }

}