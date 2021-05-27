package io.github.michielproost.betterrecycling.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Command: /recycle
 * Opens a GUI in which you can recycle materials into their crafting components.
 */
public class CommandRecycle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            // The player who issued the command.
            Player player = (Player) sender;
            // The player's inventory.
            Inventory inventory = player.getInventory();

            Bukkit.getLogger().info("The following items are in the player's inventory:\n");
            // Print each ItemStack to the console.
            for (ItemStack stack: inventory.getContents())
            {
                Bukkit.getLogger().info(
                        stack.toString() + " : " + stack.getAmount()
                );
            }
        }
        // Command is used correctly.
        return true;
    }

}