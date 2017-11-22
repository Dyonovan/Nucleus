package com.teambr.nucleus;

import com.teambr.nucleus.common.CommonProxy;
import com.teambr.nucleus.events.ToolTipEvent;
import com.teambr.nucleus.lib.Reference;
import com.teambr.nucleus.manager.ConfigManager;
import com.teambr.nucleus.manager.EventManager;
import com.teambr.nucleus.manager.GuiManager;
import com.teambr.nucleus.network.PacketManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;

/**
 * This file was created for com.teambr.nucleus.Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
@Mod(
        name = Reference.MOD_NAME,
        modid = Reference.MOD_ID,
        version = Reference.VERSION,
        dependencies = Reference.DEPENDENCIES
)
public class Nucleus {

    /**
     * Public INSTANCE of this mod
     */
    @Mod.Instance
    public static Nucleus INSTANCE;

    /**
     * The INSTANCE of the proxy
     */
    @SidedProxy(clientSide = "com.teambr.nucleus.client.ClientProxy",
                serverSide = "com.teambr.nucleus.common.CommonProxy")
    public static CommonProxy proxy;

    /**
     * The location of the config folder
     */
    public static String configFolderLocation;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        // Create Config Folder Location
        configFolderLocation = event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_NAME;

        // Load the Config Manager
        ConfigManager.init(configFolderLocation);

        // Send to proxy
        proxy.preInit(event);

        // Register GUI Handler
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiManager());
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        // Init Network Packets
        PacketManager.initPackets();

        // Register Events
        EventManager.init();

        // Register Tool tips
        if(event.getSide() == Side.CLIENT)
            EventManager.registerEvent(new ToolTipEvent());

        // Send to proxy
        proxy.init(event);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        // Send to proxy
        proxy.postInit(event);
    }
}
