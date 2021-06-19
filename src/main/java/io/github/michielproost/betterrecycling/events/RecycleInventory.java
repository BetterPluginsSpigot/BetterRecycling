package io.github.michielproost.betterrecycling.events;

import be.betterplugins.core.messaging.messenger.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import javax.inject.Inject;
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

    @Inject
    public RecycleInventory(Messenger messenger)
    {
        // Create an empty inventory with no owner.
        recycleInventory = Bukkit.createInventory(null, 36, "RecycleBin");
        // Initialize the messenger.
        this.messenger = messenger;
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
    @Override
    public String toString()
    {
        // The non-empty storage contents.
        ItemStack[] contents = getNonEmptyStorageContents();
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
     * Get all the non-empty storage contents in the inventory.
     * @return All the non-empty storage contents in the inventory.
     */
    private ItemStack[] getNonEmptyStorageContents()
    {
        // Store the non-empty contents.
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack stack: recycleInventory.getStorageContents()) {
            // ItemStack exists.
            if (stack != null)
            {
                // Add to list.
                list.add( stack );
            }
        }
        // Return the non-empty contents.
        ItemStack[] contents = new ItemStack[ list.size() ];
        return list.toArray( contents );
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
        // The non-empty storage contents.
        ItemStack[] contents = getNonEmptyStorageContents();
        // Loop through all ItemStacks in the recycle inventory.
        for (ItemStack stack: contents)
        {
            // Get recipes of ItemStack.
            List<Recipe> recipes = Bukkit.getRecipesFor( stack );
            for (Recipe recipe: recipes)
            {
                Bukkit.getLogger().info(recipe.getClass().getName());
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

    /**
     * Check for clicks on events.
     */
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event)
    {
        if (event.getInventory() != recycleInventory)
        {
            final ItemStack clickedItem = event.getCurrentItem();
            // ItemStack exists.
            if (clickedItem != null)
            {
                Bukkit.getLogger().info("Item clicked: " + clickedItem.getType().name());
            }
        }
    }

    /**
     * Message shown when a new player joins the server.
     * @param event The event.
     */
    @EventHandler
    public void onStartUp(final PlayerJoinEvent event)
    {
        messenger.sendMessage(
                new ArrayList<Player>(Bukkit.getOnlinePlayers()), "hello_world"
        );
    }

}