package com.pauljoda.nucleus.common.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
public class UpdatingBlockEntity extends BlockEntity {

    public UpdatingBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    /*******************************************************************************************************************
     * UpdatingTile                                                                                                    *
     *******************************************************************************************************************/

    /**
     * Called only on the client side tick. Override for client side operations
     */
    public void onClientTick() {}

    /**
     * Called only on the server side tick. Override for server side operations
     */
    public void onServerTick() {}

    /**
     * Call to mark this block for update in the world
     * @param flags 6 to avoid re-render, 3 to force client changes
     */
    @SuppressWarnings("ConstantConditions")
    public void markForUpdate(int flags) {
        getLevel().sendBlockUpdated(getBlockPos(), getLevel().getBlockState(getBlockPos()), getLevel().getBlockState(getBlockPos()), flags);
        setChanged();
    }

    /*******************************************************************************************************************
     * BlockEntity                                                                                                     *
     *******************************************************************************************************************/

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(level.getBlockEntity(pos) instanceof UpdatingBlockEntity updatingEntity) {
            if (level.isClientSide)
                updatingEntity.onClientTick();
            else
                updatingEntity.onServerTick();
        }
    }

    /**
     * We want the update tag to take in outside info
     * @return Our tag
     */
    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    /**
     * Cause tile to read new info
     */
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        CompoundTag tagCompound = packet.getTag();
        super.onDataPacket(net, packet);
        if(tagCompound != null)
            deserializeNBT(tagCompound);
    }

    /**
     * Case data packet to send our info
     */
    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket  getUpdatePacket() {
        CompoundTag nbtTag = new CompoundTag();
        saveAdditional(nbtTag);
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
