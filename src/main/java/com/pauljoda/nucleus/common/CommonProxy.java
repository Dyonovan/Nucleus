package com.pauljoda.nucleus.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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
     * Called on init
     */
    public void init() {
    }

    /**
     * Used to get client world but not crash server
     *
     * @return Null on server, Minecraft client world on client
     */
    public Level getClientWorld() {
        return null;
    }

    public Player getPlayer() {
        return null;
    }
}
