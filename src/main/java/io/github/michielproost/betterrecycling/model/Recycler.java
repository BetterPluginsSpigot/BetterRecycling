package io.github.michielproost.betterrecycling.model;

import be.betterplugins.core.messaging.messenger.Messenger;
import io.github.michielproost.betterrecycling.Util.ArrayUtil;
import io.github.michielproost.betterrecycling.Util.Conversions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Class which can recycle items into their corresponding crafting components.
 * @author Michiel Proost
 */
public class Recycler {

    /**
     * Recycle an individual ItemStack into its corresponding crafting components.
     * @param stack The contents to be recycled.
     * @return The result of the recycle operation (leftover + corresponding components).
     */
    public static RecycleResult recycle( ItemStack stack )
    {
        // Is the item durable?
        if (isDurable( stack ))
        {
            // Get durability.
            double durability = getDurability( stack );
            // Print durability.
            Bukkit.getLogger().info("Durability of " + stack.getType().name() + ": " + durability);
        }
        // Store the corresponding crafting components in a list.
        List<ItemStack> recycledList = new ArrayList<>();
        // Get recipes of ItemStack.
        List<Recipe> recipes = Bukkit.getRecipesFor( stack );
        // Loop through all the recipes.
        for (Recipe recipe: recipes)
        {
            // Shaped (normal) crafting recipe.
            if (recipe instanceof ShapedRecipe)
            {
                // Cast to shaped recipe.
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                // Enough resources to recycle?
                if ( canRecycle( stack, shapedRecipe ) )
                {
                    // Remove required amount from ItemStack.
                    stack.setAmount(
                            stack.getAmount() - recipe.getResult().getAmount()
                    );
                    // Get ingredient map.
                    Map<Character, ItemStack> ingredientMap = shapedRecipe.getIngredientMap();
                    // Add crafting components to list.
                    recycledList.addAll( ingredientMap.values() );
                }
            }
        }
        // Convert list to array.
        ItemStack[] recycledArray = Conversions.ListToArray( recycledList );
        // Remove empty contents and return array.
        ItemStack[] recycled = getNonEmptyStorageContents( recycledArray );
        // Return result of recycle operation.
        return new RecycleResult( stack, recycled );
    }

    /**
     * Given a certain recipe, can we recycle the ItemStack into its corresponding components?
     * @param stack The ItemStack.
     * @param recipe The given recipe.
     * @return Whether or not we can recycle the ItemStack into its corresponding components?
     */
    public static boolean canRecycle(ItemStack stack, Recipe recipe)
    {
        // The required amount of ItemStacks.
        int recipeResultAmount = recipe.getResult().getAmount();
        // The given amount of ItemStacks.
        int stackAmount = stack.getAmount();
        // Return whether the ItemStack can be recycled, given the recipe.
        return stackAmount >= recipeResultAmount;
    }

    /**
     * Is the ItemStack durable?
     * @param stack The given ItemStack.
     * @return Whether or not the ItemStack is durable.
     */
    public static boolean isDurable(ItemStack stack)
    {
        // Get maximum durability.
        short maxDurability = stack.getType().getMaxDurability();
        // The metadata of the ItemStack.
        ItemMeta itemMeta = stack.getItemMeta();
        return itemMeta instanceof Damageable && maxDurability > 0;
    }

    /**
     * Get the durability of an ItemStack.
     * @param stack The given ItemStack.
     * @return The durability of the ItemStack.
     */
    public static double getDurability( ItemStack stack )
    {
        // Get maximum durability.
        short maxDurability = stack.getType().getMaxDurability();
        // Get damageable.
        Damageable damageable = (Damageable) stack.getItemMeta();
        // Get damage.
        int damage = damageable.getDamage();
        // Calculate & return durability.
        return 1 - ( (double) damage / (double) maxDurability );
    }

    /**
     * Get all the non-empty ItemStacks.
     * @param itemStacks The ItemStacks, including non-empty ItemStacks.
     * @return All the non-empty ItemStacks.
     */
    public static ItemStack[] getNonEmptyStorageContents(ItemStack[] itemStacks)
    {
        return Arrays   .stream( itemStacks )
                        .filter(Objects::nonNull)
                        .toArray(ItemStack[]::new);
    }

}
