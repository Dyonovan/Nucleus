package com.pauljoda.nucleus.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockUtils {

    /**
     * Returns a collection of {@link BlockPos} that are not Air
     *
     * @param size   the amount of blocks from the center block
     * @param facing the block the player is looking at {@link Direction}
     * @param pos    the current block position {@link BlockPos}
     * @param world  the current world {@link Level}
     * @return An {@link ArrayList} of {@link BlockPos}
     */
    public static List<BlockPos> getBlockList(int size, Direction facing, BlockPos pos, Level world) {

        BlockPos pos1;
        BlockPos pos2;
        List<BlockPos> actualList = new ArrayList<>();

        if (facing.getAxis().isHorizontal()) {
            if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                pos1 = pos.relative(Direction.UP, size).relative(Direction.EAST, size);
                pos2 = pos.relative(Direction.DOWN, size).relative(Direction.WEST, size);
            } else {
                pos1 = pos.relative(Direction.UP, size).relative(Direction.SOUTH, size);
                pos2 = pos.relative(Direction.DOWN, size).relative(Direction.NORTH, size);
            }

            while (pos2.getY() < pos.getY() - 1) {
                pos1 = pos1.relative(Direction.UP);
                pos2 = pos2.relative(Direction.UP);
            }
        } else {
            pos1 = pos.relative(Direction.NORTH, size).relative(Direction.WEST, size);
            pos2 = pos.relative(Direction.SOUTH, size).relative(Direction.EAST, size);
        }

        BlockPos.betweenClosed(pos1, pos2).forEach((blockPos) -> {
            if (!world.isEmptyBlock(blockPos))
                actualList.add(blockPos);
        });

        return actualList;
    }
}