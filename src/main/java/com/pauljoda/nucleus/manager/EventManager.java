package com.pauljoda.nucleus.manager;

import com.pauljoda.nucleus.event.CraftingEvents;
import com.pauljoda.nucleus.util.TimeUtils;
import net.minecraftforge.common.MinecraftForge;

public class EventManager {
    /**
     * Registers the events
     */
    public static void init() {
        registerEvent(new TimeUtils());
        registerEvent(new CraftingEvents());
    }

    /**
     * Registers an event to the event registry
     *
     * @param event The event to register
     */
    public static void registerEvent(Object event) {
        MinecraftForge.EVENT_BUS.register(event);
    }
}
