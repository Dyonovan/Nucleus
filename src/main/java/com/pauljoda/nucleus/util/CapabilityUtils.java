package com.pauljoda.nucleus.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapability;

import javax.annotation.Nullable;

public class CapabilityUtils {

    /**
     * Retrieves the capability from the block entity in the given direction.
     *
     * @param entity     The block entity from which to retrieve the capability {@link BlockEntity}
     * @param capability The capability to retrieve {@link BlockCapability}
     * @param context    The direction {@link Direction} in which to retrieve the capability
     * @return The retrieved capability, or null if none
     */
    @Nullable
    public static <T, C> T getBlockCapability(BlockEntity entity, BlockCapability<T, C> capability, C context) {
        if (entity.getLevel() == null) return null;

        return entity.getLevel().getCapability(capability, entity.getBlockPos(), context);
    }

    /**
     * Retrieves the capability from the level at the provided block position.
     *
     * @param level      The level from which to retrieve the capability {@link Level}
     * @param capability The capability to retrieve {@link BlockCapability}
     * @param pos        The block position {@link BlockPos} at which to retrieve the capability
     * @param context    The context for retrieving the capability
     * @return The retrieved capability, or null if none
     */
    @Nullable
    public static <T, C> T getBlockCapability(Level level, BlockCapability<T, C> capability, BlockPos pos, C context) {
        return level.getCapability(capability, pos, context);
    }
}