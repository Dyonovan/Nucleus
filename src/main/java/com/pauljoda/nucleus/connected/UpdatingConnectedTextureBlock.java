package com.pauljoda.nucleus.connected;

import com.pauljoda.nucleus.common.UpdatingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public abstract class UpdatingConnectedTextureBlock extends UpdatingBlock implements ConnectedTexture {

    public UpdatingConnectedTextureBlock() {
        this(Properties.of().strength(2.0F));
    }

    public UpdatingConnectedTextureBlock(Properties props) {
        super(props);

        registerDefaultState(getStateDefinition().any()
                .setValue(CONNECTED_DOWN, false)
                .setValue(CONNECTED_UP, false)
                .setValue(CONNECTED_NORTH, false)
                .setValue(CONNECTED_SOUTH, false)
                .setValue(CONNECTED_EAST, false)
                .setValue(CONNECTED_WEST, false));
    }

    /*******************************************************************************************************************
     * Block                                                                                                           *
     *******************************************************************************************************************/

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CONNECTED_UP).add(CONNECTED_DOWN).add(CONNECTED_NORTH).add(CONNECTED_SOUTH).add(CONNECTED_EAST).add(CONNECTED_WEST);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection,
                                  BlockState pNeighborState, LevelAccessor pLevel,
                                  BlockPos pPos, BlockPos pNeighborPos) {
        return pState.setValue(CONNECTED_UP, canConnect(pLevel, pPos, Direction.UP))
                .setValue(CONNECTED_DOWN, canConnect(pLevel, pPos, Direction.DOWN))
                .setValue(CONNECTED_NORTH, canConnect(pLevel, pPos, Direction.NORTH))
                .setValue(CONNECTED_SOUTH, canConnect(pLevel, pPos, Direction.SOUTH))
                .setValue(CONNECTED_EAST, canConnect(pLevel, pPos, Direction.EAST))
                .setValue(CONNECTED_WEST, canConnect(pLevel, pPos, Direction.WEST));
    }

    @Override
    public boolean canConnect(LevelAccessor level, BlockPos pos, Direction dir) {
        var otherState = level.getBlockState(pos.offset(dir.getNormal()));
        return level.getBlockState(pos.offset(dir.getNormal())).getBlock() == this;
    }
}