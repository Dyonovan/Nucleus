package com.teambr.nucleus.common.tiles;

import com.teambr.nucleus.common.container.SidedInventoryWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

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
public abstract class InventorySided extends InventoryHandler {

    // Side Handlers
    private LazyOptional<?> handlerTop    = LazyOptional.of(() -> new SidedInventoryWrapper(this, Direction.UP));
    private LazyOptional<?> handlerDown   = LazyOptional.of(() -> new SidedInventoryWrapper(this, Direction.DOWN));
    private LazyOptional<?> handlerNorth  = LazyOptional.of(() -> new SidedInventoryWrapper(this, Direction.NORTH));
    private LazyOptional<?> handlerSouth  = LazyOptional.of(() -> new SidedInventoryWrapper(this, Direction.SOUTH));
    private LazyOptional<?> handlerEast   = LazyOptional.of(() -> new SidedInventoryWrapper(this, Direction.EAST));
    private LazyOptional<?> handlerWest   = LazyOptional.of(() -> new SidedInventoryWrapper(this, Direction.WEST));
    private LazyOptional<?> handlerNull   = LazyOptional.of(() -> this);

    public InventorySided(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Get the slots for the given face
     * @param face The face
     * @return What slots can be accessed
     */
    public abstract int[] getSlotsForFace(Direction face);

    /**
     * Can insert the item into the inventory
     * @param slot The slot
     * @param itemStackIn The stack to insert
     * @param dir The dir
     * @return True if can insert
     */
    public abstract boolean canInsertItem(int slot, ItemStack itemStackIn, Direction dir);

    /**
     * Can this extract the item
     * @param slot The slot
     * @param stack The stack
     * @param dir The dir
     * @return True if can extract
     */
    public abstract boolean canExtractItem(int slot, ItemStack stack, Direction dir);

    /*******************************************************************************************************************
     * TileEntity                                                                                                      *
     *******************************************************************************************************************/

    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(facing == null)
                return (LazyOptional<T>) handlerNull;
            switch (facing) {
                case UP :
                    return (LazyOptional<T>) handlerTop;
                case DOWN :
                    return (LazyOptional<T>) handlerDown;
                case NORTH :
                    return (LazyOptional<T>) handlerNorth;
                case SOUTH :
                    return (LazyOptional<T>) handlerSouth;
                case EAST :
                    return (LazyOptional<T>) handlerEast;
                case WEST :
                    return (LazyOptional<T>) handlerWest;
                default :
            }
        }
        return super.getCapability(capability, facing);
    }
}
