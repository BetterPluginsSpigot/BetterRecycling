package io.github.michielproost.betterrecycling.util;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Class which provides functions to change something from one form to another.
 * @author Michiel Proost
 */
public class Conversions {

    /**
     * Convert list containing ItemStacks to array.
     * @param list List containing ItemStacks.
     * @return The corresponding array.
     */
    public static ItemStack[] ListToArray( List<ItemStack> list)
    {
        // Create an empty array with size of list.
        ItemStack[] arrayWithSizeList = new ItemStack[list.size()];
        // Convert list to array.
        return list.toArray( arrayWithSizeList );
    }

}