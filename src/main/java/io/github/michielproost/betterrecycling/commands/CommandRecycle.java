package io.github.michielproost.betterrecycling.commands;

import io.github.michielproost.betterrecycling.events.RecycleInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Command: /recycle
 * Opens a GUI in which you can recycle materials into their crafting components.
 * @author Michiel Proost
 */
public class CommandRecycle implements CommandExecutor {

    private final RecycleInventory recycleInventory;

    @Inject
    public CommandRecycle(RecycleInventory recycleInventory){
        this.recycleInventory = recycleInventory;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            // The player who issued the command.
            Player player = (Player) sender;
            // The player's inventory.
            Inventory inventory = player.getInventory();
            // The inventory's storage contents.
            ItemStack[] itemStacks = inventory.getStorageContents();
            // Put items in recycle inventory as test.
            // recycleInventory.InitializeInventory( itemStacks );
            // Print items in recycle inventory.
            // recycleInventory.print();
            // Open the recycle inventory.
            recycleInventory.openInventory( player );
        } else {
            // The sender is not an instance of a player.
            sender.sendMessage("You have to be an instance of a player in order to utilize this method.");
            Bukkit.getLogger().info("Invalid CommandsSender attempted to use the Recycle command.");
        }
        // Command is used correctly.
        return true;
    }

}