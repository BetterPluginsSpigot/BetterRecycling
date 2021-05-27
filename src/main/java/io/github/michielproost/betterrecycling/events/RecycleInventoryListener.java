package io.github.michielproost.betterrecycling.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Listener: RecycleInventory
 * An invetory in which the player can recycle materials into their crafting components.
 * Author: Michiel Proost
 */
public class RecycleInventoryListener implements Listener {

    private final Inventory recycleBin;

    /**
     * Create a recycle bin.
     */
    public RecycleInventoryListener()
    {
        // Create an empty inventory with no owner.
        recycleBin = Bukkit.createInventory(null, 36, "RecycleBin");
    }

    /**
     * Initialize the inventory with ItemStacks.
     * @param itemStacks The stacks to put in the inventory.
     */
    public void InitializeInventory(ItemStack[] itemStacks)
    {
        // Put each ItemStack in the recycle bin.
        recycleBin.setStorageContents(itemStacks);
    }

}