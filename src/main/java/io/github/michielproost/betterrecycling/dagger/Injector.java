package io.github.michielproost.betterrecycling.dagger;

import dagger.Component;
import io.github.michielproost.betterrecycling.BetterRecycling;
import io.github.michielproost.betterrecycling.commands.CommandHandler;
import io.github.michielproost.betterrecycling.dagger.modules.CommandHandlerModule;
import io.github.michielproost.betterrecycling.dagger.modules.PluginModule;
import io.github.michielproost.betterrecycling.events.RecycleInventory;

import javax.inject.Singleton;

@Singleton
@Component(modules = {PluginModule.class, CommandHandlerModule.class})
public interface Injector {

    BetterRecycling getPlugin();
    CommandHandler getCommandHandler();
    RecycleInventory getRecycleInventory();

}