package io.github.michielproost.betterrecycling.Util;

import org.bukkit.inventory.ItemStack;

/**
 * Class that provides functions related to arrays.
 * @author Michiel Proost
 */
public class ArrayUtil {

    /**
     * Concatenate two ItemStack arrays.
     * @param first The first ItemStack array.
     * @param second The second ItemStack array.
     * @return The resulting array.
     */
    public static ItemStack[] concatenate(ItemStack[] first, ItemStack[] second)
    {
        // Get the lengths of the arrays.
        int firstLen = first.length;
        int secondLen = second.length;

        // The resulting array.
        ItemStack[] result = new ItemStack[firstLen + secondLen];

        // Calculate and return the resulting array.
        System.arraycopy( first, 0, result, 0 ,firstLen );
        System.arraycopy( second, 0, result, firstLen, secondLen);
        return result;
    }

}