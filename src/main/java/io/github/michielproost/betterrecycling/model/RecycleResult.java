package io.github.michielproost.betterrecycling.model;

import org.bukkit.inventory.ItemStack;

/**
 * Container that stores the results of a recycle operation.
 * @author Michiel Proost
 */
public class RecycleResult {

    public final ItemStack leftOver;
    private final ItemStack[] components;

    /**
     * Create a new container after a recycle operation.
     * @param leftOver The leftover ItemStack after the recycle operation.
     * @param components The resulting components after the recycle operation.
     */
    public RecycleResult( ItemStack leftOver, ItemStack[] components )
    {
        this.leftOver = leftOver;
        this.components = components;
    }

    /**
     * Get the leftover ItemStack.
     * @return The leftover ItemStack.
     */
    public ItemStack getLeftOver() {
        return leftOver;
    }

    /**
     * Get the resulting components.
     * @return The resulting components.
     */
    public ItemStack[] getComponents() {
        return components;
    }

}