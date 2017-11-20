package com.teambr.nucleus.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
@SuppressWarnings("ConstantConditions")
public class CommonProxy {

    /**
     * Called on preInit
     */
    public void preInit(FMLPreInitializationEvent event) { }

    /**
     * Called on init
     */
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.nucleus.api.waila.WailaModPlugin.registerServerCallback");
    }

    /**
     * Called on postInit
     */
    public void postInit(FMLPostInitializationEvent event) {}
}
