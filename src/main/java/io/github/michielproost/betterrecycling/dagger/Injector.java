package io.github.michielproost.betterrecycling.dagger;

import dagger.Component;
import io.github.michielproost.betterrecycling.BetterRecycling;
import io.github.michielproost.betterrecycling.commands.CommandRecycle;
import io.github.michielproost.betterrecycling.dagger.modules.CommandRecycleModule;
import io.github.michielproost.betterrecycling.dagger.modules.PluginModule;
import io.github.michielproost.betterrecycling.events.RecycleInventory;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PluginModule.class, CommandRecycleModule.class})
public interface Injector {

    BetterRecycling getPlugin();
    CommandRecycle getCommandRecycle();
    RecycleInventory getRecycleInventory();

}