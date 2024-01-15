package com.pauljoda.nucleus.common.blocks.entity.item;

import com.pauljoda.nucleus.capabilities.InventoryContents;
import com.pauljoda.nucleus.capabilities.InventoryHolderCapability;
import com.pauljoda.nucleus.common.blocks.entity.Syncable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

/**
 * This file was created for Nucleus - Java
 * <p>
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public abstract class InventoryHandler extends Syncable {

    private final InventoryContents inventory;

    public InventoryHandler(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);

        inventory = initializeInventory();
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * The initial size of the inventory
     *
     * @return How big to make the inventory on creation
     */
    protected abstract int getInventorySize();

    /**
     * Used to define if an item is valid for a slot
     *
     * @param index The slot id
     * @param stack The stack to check
     * @return True if you can put this there
     */
    protected abstract boolean isItemValidForSlot(int index, ItemStack stack);

    /**
     * Initializes the inventory for an InventoryHandlerItem object.
     *
     * @return The initialized InventoryHolder object.
     */
    protected abstract InventoryContents initializeInventory();

    /*******************************************************************************************************************
     * Capabilities                                                                                                    *
     *******************************************************************************************************************/

    /**
     * Retrieves the item capability of the inventory.
     *
     * @return The item capability of the inventory.
     */
    public IItemHandlerModifiable getItemCapability() {
        return new InventoryHolderCapability(inventory) {
            @Override
            protected int getInventorySize() {
                return InventoryHandler.this.getInventorySize();
            }

            @Override
            protected boolean isItemValidForSlot(int index, ItemStack stack) {
                return InventoryHandler.this.isItemValidForSlot(index, stack);
            }
        };
    }

    /**
     * Retrieves the item capability of the inventory in the specified direction.
     *
     * @param direction The direction in which to retrieve the item capability.
     * @return The item capability of the inventory in the specified direction.
     */
    public IItemHandlerModifiable getItemCapabilitySided(Direction direction) {
        return getItemCapability();
    }

    /**
     * Retrieves the current contents of the inventory.
     *
     * @return The InventoryContents object representing the current state of the inventory.
     */
    public InventoryContents getInventoryContents() {
        return inventory;
    }

    /*******************************************************************************************************************
     * TileEntity                                                                                                      *
     *******************************************************************************************************************/

    /**
     * Used to save the inventory to an NBT tag
     *
     * @param compound The tag to save to
     */
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.load(compound);
    }

    /**
     * Used to read the inventory from an NBT tag compound
     *
     * @param compound The tag to read from
     */
    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        inventory.save(compound);
    }
}
