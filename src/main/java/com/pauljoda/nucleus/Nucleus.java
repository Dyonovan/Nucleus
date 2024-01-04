package com.pauljoda.nucleus;

import com.mojang.logging.LogUtils;
import com.pauljoda.nucleus.common.CommonEvents;
import com.pauljoda.nucleus.manager.EventManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Nucleus.MODID)
public class Nucleus {
    public static final String MODID = "nucleus_pauljoda";
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * The INSTANCE of the proxy
     */
    public static CommonEvents proxy;


    public Nucleus(IEventBus modEventBus) {
        EventManager.init();
    }
}
