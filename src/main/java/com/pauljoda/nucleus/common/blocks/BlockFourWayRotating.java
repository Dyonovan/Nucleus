package com.pauljoda.nucleus.common.blocks;

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
public class BlockFourWayRotating extends Block {

    // Instance of the property for rotation
    public static DirectionProperty FOUR_WAY =
            DirectionProperty.create("facing", Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));


    /**
     * Main constructor for the block
     */
    public BlockFourWayRotating(Properties props) {
        super(props);
        setDefaultState(getStateContainer().getBaseState().with(FOUR_WAY, Direction.NORTH));
    }

    /*******************************************************************************************************************
     * Block Methods                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when placing block to determine direction
     * @param context Item use
     * @return State for placement
     */
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FOUR_WAY, context.getPlacementHorizontalFacing().getOpposite());
    }


    /*******************************************************************************************************************
     * Blockstate properties                                                                                           *
     *******************************************************************************************************************/

    /**
     * Add properties to our block on load
     * @param builder Property holder
     */
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FOUR_WAY); // Add rotation
    }
}
