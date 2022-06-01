package com.pauljoda.nucleus;

import com.pauljoda.nucleus.client.ClientProxy;
import com.pauljoda.nucleus.common.CommonProxy;
import com.pauljoda.nucleus.lib.Reference;
import com.pauljoda.nucleus.manager.Config;
import com.pauljoda.nucleus.manager.EventManager;
import com.pauljoda.nucleus.network.PacketManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

/**
 * This file was created for com.teambr.nucleus.Nucleus - Java
 * <p>
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
@SuppressWarnings("deprecation")
@Mod(Reference.MOD_ID)
public class Nucleus {

    /**
     * Public INSTANCE of this mod
     */
    public static Nucleus INSTANCE;

    /**
     * The INSTANCE of the proxy
     */
    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    /**
     * The location of the config folder
     */
    public static String configFolderLocation;

    public Nucleus() {
        INSTANCE = this;

        // Setup Proxy
        proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        proxy.init();

        // Register config
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);

        // Init network
        PacketManager.initPackets();

        // Register Events
        EventManager.init();
    }
}
