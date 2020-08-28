package com.pauljoda.nucleus.client.gui;

import net.minecraft.nbt.CompoundNBT;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Must only be used for containers
 *
 * @author Paul Davis - pauljoda
 * @since 8/29/2019
 */
public interface ISyncingTileScreen {

    /**
     * Accept the server values, this is typically the tile's nbt data
     * @param tag NBT data
     */
    void acceptServerValues(CompoundNBT tag);
}
