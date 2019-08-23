package com.teambr.nucleus.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

import java.util.Arrays;

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
public class BlockSixWayRotation extends Block {

    // Instance of the property for rotation
    public static DirectionProperty SIX_WAY =
            DirectionProperty.create("facing", Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH,
                    Direction.WEST, Direction.DOWN, Direction.UP));


    /**
     * Main constructor for the block
     */
    public BlockSixWayRotation(Properties props) {
        super(props);
        setDefaultState(getStateContainer().getBaseState().with(SIX_WAY, Direction.NORTH));
    }

    /*******************************************************************************************************************
     * Block Methods                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Set the state for placement
     * @param context Use context
     * @return State to place
     */
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(SIX_WAY, context.getNearestLookingDirection().getOpposite());
    }

    /*******************************************************************************************************************
     * BlockState Methods                                                                                              *
     *******************************************************************************************************************/

    /**
     * Add properties to our block on load
     * @param builder Property holder
     */
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIX_WAY); // Add rotation
    }
}
