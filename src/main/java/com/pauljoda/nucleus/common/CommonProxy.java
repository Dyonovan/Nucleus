package com.pauljoda.nucleus.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

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
     * @return Null on server, Minecraft client world on client
     */
    public World getClientWorld() {
        return null;
    }

    public PlayerEntity getPlayer() {
        return null;
    }
}
