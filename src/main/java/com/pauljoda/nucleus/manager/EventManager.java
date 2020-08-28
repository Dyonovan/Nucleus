package com.pauljoda.nucleus.manager;

import com.pauljoda.nucleus.util.TimeUtils;
import com.pauljoda.nucleus.events.CraftingEvents;
import net.minecraftforge.common.MinecraftForge;

/**
 * This file was created for Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
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
     * @param event The event to register
     */
    public static void registerEvent(Object event) {
        MinecraftForge.EVENT_BUS.register(event);
    }
}
