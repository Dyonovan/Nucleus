package com.pauljoda.nucleus.client;

import com.pauljoda.nucleus.common.CommonProxy;
import com.pauljoda.nucleus.events.ToolTipEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
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
public class ClientProxy extends CommonProxy {
    /**
     * Called on init
     */
    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());
    }

    @Override
    public Level getClientWorld() {
        return Minecraft.getInstance().level;
    }

    @Override
    public LocalPlayer getPlayer() {
        return Minecraft.getInstance().player;
    }
}
