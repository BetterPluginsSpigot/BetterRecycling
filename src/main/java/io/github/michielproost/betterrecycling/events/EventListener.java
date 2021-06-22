package io.github.michielproost.betterrecycling.events;

import be.betterplugins.core.messaging.messenger.Messenger;
import be.betterplugins.core.messaging.messenger.MsgEntry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Listen to events and act accordingly.
 * @author Michiel Proost
 */
public class EventListener implements Listener {

    // The messenger.
    private final Messenger messenger;

    /**
     * Create a new EventListener.
     * @param messenger The messenger.
     */
    public EventListener(Messenger messenger)
    {
        this.messenger = messenger;
    }

    /**
     * Show welcoming message to every player that joins the server.
     * @param event The event.
     */
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
    {
        messenger.sendMessage(
                event.getPlayer(),
                "player.join",
                new MsgEntry( "<PlayerName>", event.getPlayer().getDisplayName() )
        );
    }

}