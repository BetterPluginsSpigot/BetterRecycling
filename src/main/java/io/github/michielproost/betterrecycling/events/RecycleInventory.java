package io.github.michielproost.betterrecycling.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

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

    /**
     * Open the inventory.
     * @param entity A human entity (NPC or player).
     */
    public void openInventory(final HumanEntity entity)
    {
        entity.openInventory( recycleInventory );
    }

    /**
     * Recycle every material in the inventory into their crafting components.
     */
    public void recycle()
    {
        // Loop through all ItemStacks in the recycle inventory.
        for (ItemStack stack: recycleInventory.getStorageContents())
        {
            // ItemStack exists.
            if (stack != null)
            {
                // Get recipes of ItemStack.
                List<Recipe> recipes = Bukkit.getRecipesFor( stack );
                for (Recipe recipe: recipes)
                {
                    // Shaped (normal) crafting recipe.
                    if (recipe instanceof ShapedRecipe)
                    {
                        ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                        Map<Character, ItemStack> map = shapedRecipe.getIngredientMap();
                        for (Character key: map.keySet()) {
                            Bukkit.getLogger().info("Key: " + key);
                            Bukkit.getLogger().info("Value: " + map.get(key));
                        }
                    }
                }
            }
        }
    }

}