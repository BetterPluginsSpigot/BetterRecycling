package io.github.michielproost.betterrecycling.dagger.modules;

import dagger.Module;
import dagger.Provides;
import io.github.michielproost.betterrecycling.BetterRecycling;

import javax.inject.Singleton;

@Module
public class PluginModule {

    public final BetterRecycling plugin;

    public PluginModule(BetterRecycling betterRecycling)
    {
        this.plugin = betterRecycling;
    }

    @Provides
    public BetterRecycling providePlugin()
    {
        return plugin;
    }

}