package io.github.michielproost.betterrecycling;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * MockBukkit Test Class:
 * A class in which we can test loading the BetterRecycling plugin.
 * Author: Michiel Proost
 */
public class BetterRecyclingTests {

    private ServerMock server;
    private BetterRecycling plugin;

    @Before
    public void setUp()
    {
        server = MockBukkit.mock();

        // The BetterRecycling plugin.
        plugin = MockBukkit.load(BetterRecycling.class);
    }

    @Test
    public void onEnableTest()
    {
        plugin.onEnable();
    }

    @Test
    public void onDisableTest()
    {
        plugin.onDisable();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

}