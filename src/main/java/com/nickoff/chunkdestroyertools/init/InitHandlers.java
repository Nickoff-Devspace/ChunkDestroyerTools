package com.nickoff.chunkdestroyertools.init;

import com.nickoff.chunkdestroyertools.tools.DiggerHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class InitHandlers {

    public static final DiggerHandler DIGGER_HANDLER = new DiggerHandler();

    public static void register()
    {
        IEventBus ebus = MinecraftForge.EVENT_BUS;

        ebus.register(DIGGER_HANDLER);
    }
}
