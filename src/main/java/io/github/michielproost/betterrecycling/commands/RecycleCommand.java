package io.github.michielproost.betterrecycling.commands;

import io.github.michielproost.betterrecycling.events.RecycleInventory;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;

/**
 *  Command: /br recycle
 *  Recycle the materials in the recycle inventory into their crafting components.
 * @author Michiel Proost
 */
public class RecycleCommand extends BRCommand {

    @Inject
    public RecycleCommand(RecycleInventory recycleInventory)
    {
        super( recycleInventory );
    }

    @Override
    public String getCommandName()
    {
        return "recycle";
    }

    @Override
    public boolean execute(CommandSender sender, String command)
    {
        // Recycle the materials.
        recycleInventory.recycle();
        // Command is used correctly.
        return true;
    }

}