package com.teambr.nucleus.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for Lux-et-Umbra-Redux
 * <p>
 * Lux-et-Umbra-Redux is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since 10/8/2016
 */
public class BlockUtils {

    /**
     * Returns a collection of {@link net.minecraft.util.math.BlockPos} that are not Air
     *
     * @param size              the amount of blocks from the center block
     * @param facing            the block the player is looking at {@link net.minecraft.util.math.RayTraceResult}
     * @param world             {@link net.minecraft.world.World}
     * @return                  An {@link ArrayList} of {@link net.minecraft.util.math.BlockPos}
     */
    public static List<BlockPos> getBlockList(int size, Direction facing, BlockPos pos, World world) {

        BlockPos pos1;
        BlockPos pos2;
        List<BlockPos> actualList = new ArrayList<>();

        if (facing.getAxis().isHorizontal()) {
            if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                pos1 = pos.offset(Direction.UP, size).offset(Direction.EAST, size);
                pos2 = pos.offset(Direction.DOWN, size).offset(Direction.WEST, size);
            } else {
                pos1 = pos.offset(Direction.UP, size).offset(Direction.SOUTH, size);
                pos2 = pos.offset(Direction.DOWN, size).offset(Direction.NORTH, size);
            }

            while(pos2.getY() < pos.getY() - 1) {
                pos1 = pos1.offset(Direction.UP);
                pos2 = pos2.offset(Direction.UP);
            }
        } else {
            pos1 = pos.offset(Direction.NORTH, size).offset(Direction.WEST, size);
            pos2 = pos.offset(Direction.SOUTH, size).offset(Direction.EAST, size);
        }

        BlockPos.getAllInBox(pos1, pos2).forEach((blockPos) -> {
            if (!world.isAirBlock(blockPos))
                actualList.add(blockPos);
        });

        return actualList;
    }
}
