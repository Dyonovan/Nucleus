package com.pauljoda.nucleus.common.items;

import com.pauljoda.nucleus.capabilities.InventoryHolder;
import com.pauljoda.nucleus.common.container.IInventoryCallback;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
public abstract class InventoryHandlerItem implements IItemHandlerModifiable {

    // Variables
    private ItemStack heldStack;

    private InventoryHolder inventory;

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
    protected abstract InventoryHolder initializeInventory();

    /*******************************************************************************************************************
     * InventoryHandler                                                                                                *
     *******************************************************************************************************************/

    /**
     * Retrieves the item handler capability associated with the inventory handler.
     *
     * @return The item handler capability.
     */
    public IItemHandlerModifiable getItemHandlerCapability() {
        return inventory;
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
