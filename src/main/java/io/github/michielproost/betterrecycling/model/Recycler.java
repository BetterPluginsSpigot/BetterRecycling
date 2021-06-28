package io.github.michielproost.betterrecycling.model;

import be.betterplugins.core.messaging.messenger.Messenger;
import be.betterplugins.core.messaging.messenger.MsgEntry;
import io.github.michielproost.betterrecycling.Util.Conversions;
import io.github.michielproost.betterrecycling.Util.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class which can recycle items into their corresponding crafting components.
 * @author Michiel Proost
 */
public class Recycler {

    // The messenger.
    private final Messenger messenger;

    /**
     * Create a new Recycler.
     * @param messenger The messenger.
     */
    public Recycler( Messenger messenger ){
        this.messenger = messenger;
    }

    /**
     * Recycle an individual ItemStack into its corresponding crafting components.
     * @param stack The contents to be recycled.
     * @param player The player.
     * @return The result of the recycle operation (leftover + corresponding components).
     */
    public RecycleResult recycle(ItemStack stack, Player player)
    {
        // Default durability.
        double durability = 1.0;
        // Is the item durable?
        if ( ItemStackUtil.isDurable( stack ) )
        {
            // Get durability.
            durability = ItemStackUtil.getDurability( stack );
            // Get damageable.
            Damageable damageable = (Damageable) stack.getItemMeta();
            // Set damage to zero.
            damageable.setDamage( 0 );
            // Set new ItemMeta.
            stack.setItemMeta( (ItemMeta) damageable );
        }
        // Store the corresponding crafting components in a list.
        List<ItemStack> recycledList = new ArrayList<>();
        // Get recipes of ItemStack.
        List<Recipe> recipes = Bukkit.getRecipesFor( stack );
        // Counters.
        short countExpectedComponents = 0;
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
                    for (ItemStack component: ingredientMap.values())
                    {
                        // Higher durability -> higher chance of getting component.
                        if (Math.random() <= durability || component == null)
                            recycledList.add( component );
                        if (component != null)
                            countExpectedComponents++;
                    }
                }
            }
            // Shapeless recipe (arrangement doesn't matter).
            if (recipe instanceof ShapelessRecipe)
            {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
                // Enough resources to recycle?
                if ( canRecycle( stack, shapelessRecipe ))
                {
                    // Remove required amount from ItemStack.
                    stack.setAmount(
                            stack.getAmount() - recipe.getResult().getAmount()
                    );
                    // Get ingredient list.
                    List<ItemStack> ingredientList = shapelessRecipe.getIngredientList();
                    // Add crafting components to list.
                    for (ItemStack component: ingredientList)
                    {
                        // Higher durability -> higher chance of getting component.
                        if (Math.random() <= durability || component == null)
                            recycledList.add( component );
                        if (component != null)
                            countExpectedComponents++;
                    }
                }
            }
        }
        // Convert list to array.
        ItemStack[] recycledArray = Conversions.ListToArray( recycledList );
        // Remove empty contents and return array.
        ItemStack[] recycled = ItemStackUtil.getNonEmptyStorageContents( recycledArray );
        // Is item durable?
        if (durability < 1.0) {
            double percentLostDouble = 1.0 - (double) recycled.length / (double) countExpectedComponents;
            int percentLostInt = (int) (percentLostDouble * 100.0);
            this.messenger.sendMessage(
                    player,
                    "durable.percentlost",
                    new MsgEntry( "<PercentLost>", percentLostInt)
            );
        }
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

}