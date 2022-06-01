package com.pauljoda.nucleus.util;

import com.pauljoda.nucleus.common.blocks.IToolable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * This file was created for Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/7/2017
 */
public class WorldUtils {

    /*******************************************************************************************************************
     * World Methods                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Returns the direction to the left of this. This is the direction it is facing turned left
     *
     * @param toTurn Starting point
     * @return The direction turned 90 left
     */
    public static Direction rotateLeft(Direction toTurn) {
        switch (toTurn) {
            case NORTH :
                return Direction.WEST;
            case EAST:
                return Direction.NORTH;
            case SOUTH:
                return Direction.EAST;
            case WEST:
                return Direction.SOUTH;
            case UP: // No rotation on y axis
            case DOWN:
            default :
                return toTurn;
        }
    }

    /**
     * Returns the direction to the right of this. This is the direction it is facing turned right
     *
     * @param toTurn Starting point
     * @return The direction turned 90 right
     */
    public static Direction rotateRight(Direction toTurn) {
        switch (toTurn) {
            case NORTH :
                return Direction.EAST;
            case EAST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.WEST;
            case WEST:
                return Direction.NORTH;
            case UP: // No rotation on y axis
            case DOWN:
            default :
                return toTurn;
        }
    }

    /**
     * Checks if there is air within the given list
     * @param world The world
     * @param blocks The list of offsets from the origin
     * @param origin The origin to compare against
     * @return True if any block in the list has air
     */
    public static boolean doesContainAirBlock(Level world, List<BlockPos> blocks, BlockPos origin) {
        for(BlockPos pos : blocks)
            if(world.isEmptyBlock(new BlockPos(
                    origin.getX() + pos.getX(),
                    origin.getY() + pos.getY(),
                    origin.getZ() + pos.getZ())))
                return true;
        return false;
    }

    /*******************************************************************************************************************
     * Spawning Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Drops and Array of ItemStacks into the world
     *
     * @param world Instance of ``World
     * @param stacks ItemStack Array to drop into the world
     * @param pos BlockPos to drop them from
     */
    public static void dropStacks(Level world, List<ItemStack> stacks, BlockPos pos) {
        stacks.forEach((ItemStack stack) -> dropStack(world, stack, pos));
    }

    /**
     * Drops a ItemStack into the world
     *
     * @param world Instance of ``World
     * @param stack temStack Array to drop into the world
     * @param pos BlockPos to drop them from
     */
    public static void dropStack(Level world, ItemStack stack, BlockPos pos) {
        if(stack != null && stack.getCount() > 0) {
            float rx = world.random.nextFloat() * 0.8F;
            float ry = world.random.nextFloat() * 0.8F;
            float rz = world.random.nextFloat() * 0.8F;

            ItemEntity itemEntity = new ItemEntity(world,
                    pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz,
                    stack.copy());

            float factor = 0.05F;

            itemEntity.setDeltaMovement(
                    world.random.nextGaussian() * factor,
                    world.random.nextGaussian() * factor + 0.2F,
                    world.random.nextGaussian() * factor
            );

            world.addFreshEntity(itemEntity);

            stack.setCount(0);
        }
    }

    /**
     * Helper method to drop items in an inventory, used on break mostly
     * @param itemHandler The itemhandler
     * @param world The world
     * @param pos The block pos
     */
    public static void dropStacksInInventory(IItemHandler itemHandler, Level world, BlockPos pos) {
        for(int slot = 0; slot < itemHandler.getSlots(); slot++) {
            ItemStack stack = itemHandler.getStackInSlot(slot);
            if(stack != null)
                dropStack(world, stack, pos);
        }
    }

    /*******************************************************************************************************************
     * IToolable Helpers                                                                                               *
     *******************************************************************************************************************/

    /**
     * Breaks the block and saves the NBT to the tag, calls getStackDropped to drop (just item)
     * @param world The world
     * @param pos The block pos
     * @param block The block object
     * @return True if successful
     */
    public static boolean breakBlockSavingNBT(Level world, BlockPos pos, @Nonnull IToolable block) {
        if(world.isClientSide) return false;
        if(world.getBlockEntity(pos) != null) {
            BlockEntity savableTile = world.getBlockEntity(pos);
            CompoundTag tag = savableTile.saveWithFullMetadata();
            ItemStack stack = block.getStackDroppedByWrench(world, pos);
            stack.setTag(tag);
            dropStack(world, stack, pos);
            world.removeBlockEntity(pos); // Cancel drop logic
            world.removeBlock(pos, false);
            return true;
        }
        return false;
    }

    /**
     * Call this after onBlockPlacedBy to write saved data to the stack if present
     * @param world The world
     * @param pos The block position
     * @param stack The stack that had the tag
     */
    public static void writeStackNBTToBlock(Level world, BlockPos pos, ItemStack stack) {
        if(stack.hasTag()) {
            if(world.getBlockEntity(pos) != null) {
                BlockEntity tile = world.getBlockEntity(pos);
                CompoundTag tag = stack.getTag();
                tag.putInt("x", pos.getX()); // Add back MC tags
                tag.putInt("y", pos.getY());
                tag.putInt("z", pos.getZ());
                tile.load(tag);
            }
        }
    }
}
