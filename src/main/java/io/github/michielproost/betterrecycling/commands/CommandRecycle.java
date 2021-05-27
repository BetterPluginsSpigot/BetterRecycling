package io.github.michielproost.betterrecycling.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
            // ItemStacks of player.
            ArrayList<ItemStack> itemStacks = new ArrayList<>();

            Bukkit.getLogger().info("The following items are in the player's inventory:\n");
            for (ItemStack stack: inventory.getStorageContents())
            {
                // ItemStack exists.
                if (stack != null)
                {
                    // Print itemStack to console.
                    Bukkit.getLogger().info(
                            stack.getType().name() + " : " + stack.getAmount()
                    );
                    // Add to player's ItemStacks.
                    itemStacks.add(stack);
                }
            }
            //TODO. Further implement /recycle method.
        } else {
            // The sender is not an instance of a player.
            sender.sendMessage("You have to be an instance of a player in order to utilize this method.");
            Bukkit.getLogger().info("Invalid CommandsSender attempted to use the Recycle command.");
        }
        // Command is used correctly.
        return true;
    }

}