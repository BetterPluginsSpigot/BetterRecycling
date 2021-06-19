package io.github.michielproost.betterrecycling.model;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Recycler {

    /**
     * Recycle every material in the inventory into their crafting components.
     * @param contents The contents to be recycled.
     */
    public static ItemStack[] recycle( ItemStack[] contents )
    {
        // Store the components of the recycled materials.
        List<ItemStack> list = new ArrayList<>();
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
                    list.addAll(
                            map.values()
                    );
                    for (Character key: map.keySet()) {
                        Bukkit.getLogger().info("Key: " + key);
                        Bukkit.getLogger().info("Value: " + map.get(key));
                    }
                }
            }
        }
        // Return the non-empty recycled components.
        ItemStack[] recycledComponents = new ItemStack[ list.size() ];
        ItemStack[] recycled = list.toArray( recycledComponents );
        return getNonEmptyStorageContents( recycled );
    }

    /**
     * Get all the non-empty storage contents in the inventory.
     * @param itemStacks The item stacks, including non-empty stacks.
     * @return All the non-empty storage contents in the inventory.
     */
    public static ItemStack[] getNonEmptyStorageContents(ItemStack[] itemStacks)
    {
        // Store the non-empty contents.
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack stack: itemStacks) {
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

}
