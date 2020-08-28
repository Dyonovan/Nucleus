package com.pauljoda.nucleus.common.blocks;

import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 11/15/17
 */
public interface IRegistersTileEntity {
    /**
     * Used to get the class for the tile this object registers to
     * @return The class for the tile entity, null for nothing (useful with base classes where one child doesn't register)
     */
    @Nullable
    Class<? extends TileEntity> getTileEntityClass();
}
