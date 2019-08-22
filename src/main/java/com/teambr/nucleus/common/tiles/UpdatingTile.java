package com.teambr.nucleus.common.tiles;


import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This file was created for Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public class UpdatingTile extends TileEntity implements ITickable {


    public UpdatingTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    /*******************************************************************************************************************
     * UpdatingTile                                                                                                    *
     *******************************************************************************************************************/

    /**
     * Called only on the client side tick. Override for client side operations
     */
    protected void onClientTick() {}

    /**
     * Called only on the server side tick. Override for server side operations
     */
    protected void onServerTick() {}

    /**
     * Call to mark this block for update in the world
     * @param flags 6 to avoid re-render, 3 to force client changes
     */
    @SuppressWarnings("ConstantConditions")
    public void markForUpdate(int flags) {
        getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), flags);
        markDirty();
    }

    /*******************************************************************************************************************
     * ITickable                                                                                                       *
     *******************************************************************************************************************/

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        if(hasWorld() && getWorld().isRemote)
            onClientTick();
        else
            onServerTick();
    }

    /*******************************************************************************************************************
     * TileEntity                                                                                                      *
     *******************************************************************************************************************/

    /**
     * We want the update tag to take in outside info
     * @return Our tag
     */
    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    /**
     * Cause tile to read new info
     */
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    /**
     * Case data packet to send our info
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        write(tag);
        return new SUpdateTileEntityPacket(getPos(), 1, tag);
    }
}
