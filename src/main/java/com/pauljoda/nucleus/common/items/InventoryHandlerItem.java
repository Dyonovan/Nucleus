package com.pauljoda.nucleus.common.items;

import com.pauljoda.nucleus.capabilities.item.InventoryContents;
import com.pauljoda.nucleus.capabilities.item.InventoryHolderCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 11/13/17
 */
public abstract class InventoryHandlerItem {

    // Variables
    private ItemStack heldStack;

    private final InventoryContents inventory;

    /**
     * Creates a handler with given stack
     *
     * @param stack Stack to attach to
     */
    public InventoryHandlerItem(ItemStack stack, CompoundTag compound) {
        heldStack = stack;

        inventory = initializeInventory();

        inventory.load(compound);
        checkStackTag();
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
     * InventoryHandler                                                                                                *
     *******************************************************************************************************************/

    /**
     * Retrieves the item handler capability associated with the inventory handler.
     *
     * @return The item handler capability.
     */
    public IItemHandlerModifiable getItemHandlerCapability() {
        return new InventoryHolderCapability(inventory) {
            @Override
            protected int getInventorySize() {
                return InventoryHandlerItem.this.getInventorySize();
            }

            @Override
            protected boolean isItemValidForSlot(int index, ItemStack stack) {
                return InventoryHandlerItem.this.isItemValidForSlot(index, stack);
            }
        };
    }

    /**
     * Makes sure we always have a valid tag
     */
    protected void checkStackTag() {
        // Give the stack a tag
        if (!heldStack.hasTag()) {
            heldStack.setTag(new CompoundTag());
            inventory.save(heldStack.getTag());
        } else
            inventory.load(heldStack.getTag());
    }
}
