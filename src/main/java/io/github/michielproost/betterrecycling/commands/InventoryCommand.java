package io.github.michielproost.betterrecycling.commands;

import be.betterplugins.core.commands.shortcuts.PlayerBPCommand;
import be.betterplugins.core.messaging.messenger.Messenger;
import io.github.michielproost.betterrecycling.events.RecycleInventory;

/**
 * Abstract class for commands that wish to use the recycle inventory.
 * @author Michiel Proost
 */
public abstract class InventoryCommand extends PlayerBPCommand {

    public final RecycleInventory recycleInventory;

    /**
     * Create a recycle inventory which the extended command classes can use.
     * @param messenger The messenger.
     * @param recycleInventory The recycle inventory.
     */
    public InventoryCommand(Messenger messenger, final RecycleInventory recycleInventory)
    {
        super(messenger);
        this.recycleInventory = recycleInventory;
    }
}
