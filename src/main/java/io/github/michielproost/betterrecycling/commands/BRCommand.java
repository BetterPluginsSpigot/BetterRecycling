package io.github.michielproost.betterrecycling.commands;

import io.github.michielproost.betterrecycling.events.RecycleInventory;
import org.bukkit.command.CommandSender;

/**
 * Abstract class that defines the rules for every BetterRecycling (BR) command.
 * @author Michiel Proost, Dieter Nuytemans
 */
public abstract class BRCommand {

    public final RecycleInventory recycleInventory;

    /**
     * Create a new BetterRecycling (BR) command.
     * @param recycleInventory The inventory in which the player can recycle materials into their crafting components.
     */
    public BRCommand(RecycleInventory recycleInventory)
    {
        this.recycleInventory = recycleInventory;
    }

    /**
     * Get the command's name.
     * @return The command's name.
     */
    public abstract String getCommandName();

    /**
     * Execute the command.
     * @param sender The sender of the command.
     * @param command The name of the command.
     * @return Whether or not the command has been used correctly.
     */
    public abstract boolean execute(CommandSender sender, String command);

}