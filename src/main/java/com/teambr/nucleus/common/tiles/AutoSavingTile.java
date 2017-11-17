package com.teambr.nucleus.common.tiles;

import com.teambr.nucleus.common.tiles.nbt.NBTManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * This file was created for Nucleus - Java
 * <p>
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Buuz135
 * @since 17/11/2017
 */
public class AutoSavingTile extends TileEntity {

    /**
     * Reads the information in the NBTCompound and stores them in the variables that have {@link com.teambr.nucleus.annotation.NBTSave}
     */
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        NBTManager.getInstance().readTileEntity(this, compound);
        super.readFromNBT(compound);
    }

    /**
     * Writes all the information into the NBTCompound from the variables that have {@link com.teambr.nucleus.annotation.NBTSave}
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return NBTManager.getInstance().writeTileEntity(this, super.writeToNBT(compound));
    }
}
