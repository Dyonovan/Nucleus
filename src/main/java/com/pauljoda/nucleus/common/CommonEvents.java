package com.pauljoda.nucleus.common;

import com.pauljoda.nucleus.Nucleus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Nucleus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class CommonEvents {

    /**
     * This method is called on initialization.
     */
    @SubscribeEvent
    public void ServerLoad(ServerStartingEvent event) {
    }
}
