package io.github.michielproost.betterrecycling.model;

import io.github.michielproost.betterrecycling.Util.ArrayUtil;
import io.github.michielproost.betterrecycling.Util.Conversions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.*;

/**
 * Class which can recycle items into their corresponding crafting components.
 * @author Michiel Proost
 */
public class Recycler {

    /**
     * Recycle the ItemStacks into their corresponding crafting components.
     * @param input The contents to be recycled.
     * @return The corresponding crafting components.
     */
    public static ItemStack[] recycle( ItemStack[] input )
    {
        // Store crafting components.
        ItemStack[] recycled = new ItemStack[0];
        // Loop through all ItemStacks in the input.
        for (ItemStack stack: input)
        {
            // Add crafting components to
            recycled = ArrayUtil.concatenate( recycled, recycle( stack ) );
        }
        // Return crafting components.
        return recycled;
    }

    /**
     * Recycle an individual ItemStack into its corresponding crafting components.
     * @param stack The contents to be recycled.
     * @return The corresponding crafting components. Returns null if the item can't be recycled.
     */
    public static ItemStack[] recycle( ItemStack stack )
    {
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
                // Get ingredient map.
                Map<Character, ItemStack> ingredientMap = shapedRecipe.getIngredientMap();
                // Add crafting components to list.
                recycledList.addAll( ingredientMap.values() );
            }
        }
        // Convert list to array.
        ItemStack[] recycledArray = Conversions.ListToArray( recycledList );
        // Remove empty contents and return array.
        return getNonEmptyStorageContents( recycledArray );
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
