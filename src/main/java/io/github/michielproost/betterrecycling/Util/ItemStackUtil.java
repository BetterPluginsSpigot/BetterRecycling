package io.github.michielproost.betterrecycling.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class with provides functions related to ItemStacks.
 * @author Michiel Proost
 */
public class ItemStackUtil {

    /**
     * Is the ItemStack durable?
     * @param stack The given ItemStack.
     * @return Whether or not the ItemStack is durable.
     */
    public static boolean isDurable( ItemStack stack )
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
        return Arrays.stream( itemStacks )
                .filter(Objects::nonNull)
                .toArray(ItemStack[]::new);
    }

    /**
     * Group material types together in a map based on the received ItemStacks.
     * @param itemStacks The stacks to group together.
     * @return The resulting map.
     */
    public static Map<String, Integer> groupMaterialTypes(ItemStack[] itemStacks)
    {
        // Map that links material types to their amount present in the ItemStacks.
        Map<String, Integer> materialAmountMap = new HashMap<>();
        // Loop through the ItemStacks.
        for (ItemStack stack: itemStacks)
        {
            // The material type.
            String type = stack.getType().name().toLowerCase();
            // Add one to amount for that specific type.
            if ( materialAmountMap.containsKey( type ) )
                materialAmountMap.put( type, materialAmountMap.get( type ) + 1 );
                // New type in map.
            else
                materialAmountMap.put( type, 1 );
        }
        // Return the map.
        return materialAmountMap;
    }

}