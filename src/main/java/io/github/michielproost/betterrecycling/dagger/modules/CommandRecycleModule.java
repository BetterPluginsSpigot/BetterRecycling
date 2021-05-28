package io.github.michielproost.betterrecycling.dagger.modules;

import dagger.Module;
import dagger.Provides;
import io.github.michielproost.betterrecycling.commands.CommandRecycle;
import io.github.michielproost.betterrecycling.events.RecycleInventory;

import javax.inject.Singleton;

@Module
public class CommandRecycleModule {

    @Provides
    public RecycleInventory provideRecycleInventory()
    {
        return new RecycleInventory();
    }

    @Provides
    public CommandRecycle provideCommandRecycle(RecycleInventory recycleInventory)
    {
        return new CommandRecycle( recycleInventory );
    }

}