package io.github.michielproost.betterrecycling.dagger.modules;

import dagger.Module;
import dagger.Provides;
import io.github.michielproost.betterrecycling.commands.CommandHandler;
import io.github.michielproost.betterrecycling.commands.OpenCommand;
import io.github.michielproost.betterrecycling.commands.RecycleCommand;
import io.github.michielproost.betterrecycling.events.RecycleInventory;

@Module
public class CommandHandlerModule {

    @Provides
    public RecycleInventory provideRecycleInventory()
    {
        return new RecycleInventory();
    }

    @Provides
    public OpenCommand provideOpenCommand(RecycleInventory recycleInventory)
    {
        return new OpenCommand( recycleInventory );
    }

    @Provides
    public RecycleCommand provideRecycleCommand(RecycleInventory recycleInventory)
    {
        return new RecycleCommand( recycleInventory );
    }

    @Provides
    public CommandHandler provideCommandHandler(OpenCommand openCommand, RecycleCommand recycleCommand)
    {
        return new CommandHandler(openCommand, recycleCommand);
    }

}