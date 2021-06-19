package io.github.michielproost.betterrecycling.dagger.modules;

import be.betterplugins.core.messaging.messenger.Messenger;
import dagger.Module;
import dagger.Provides;
import io.github.michielproost.betterrecycling.BetterRecycling;
import io.github.michielproost.betterrecycling.commands.CommandHandler;
import io.github.michielproost.betterrecycling.commands.OpenCommand;
import io.github.michielproost.betterrecycling.commands.RecycleCommand;
import io.github.michielproost.betterrecycling.events.RecycleInventory;

@Module
public class CommandHandlerModule {

    public final Messenger messenger;

    /**
     * Create command handler module.
     * @param messenger The messenger.
     */
    public CommandHandlerModule(Messenger messenger)
    {
        this.messenger = messenger;
    }

    @Provides
    public RecycleInventory provideRecycleInventory()
    {
        return new RecycleInventory();
    }

    @Provides
    public OpenCommand provideOpenCommand(RecycleInventory recycleInventory)
    {
        return new OpenCommand( messenger, recycleInventory );
    }

    @Provides
    public RecycleCommand provideRecycleCommand(RecycleInventory recycleInventory)
    {
        return new RecycleCommand( messenger, recycleInventory );
    }

    @Provides
    public CommandHandler provideCommandHandler(OpenCommand openCommand, RecycleCommand recycleCommand)
    {
        return new CommandHandler(openCommand, recycleCommand);
    }

}