package com.pauljoda.nucleus.common.items;

import com.pauljoda.nucleus.capabilities.item.InventoryContents;
import com.pauljoda.nucleus.capabilities.item.InventoryHolderCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

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

    private final InventoryContents inventory;
    private final IItemHandlerModifiable capability;

    /**
     * Creates a handler with given stack
     *
     * @param stack Stack to attach to
     */
    public InventoryHandlerItem(ItemStack stack, CompoundTag compound) {
        heldStack = stack;

        inventory = initializeInventory();

        inventory.load(compound);
        capability = new InventoryHolderCapability(inventory) {
            @Override
            protected int getInventorySize() {
                return inventory.getInventorySize();
            }

            @Override
            protected boolean isItemValidForSlot(int index, ItemStack stack) {
                return InventoryHandlerItem.this.isItemValidForSlot(index, stack);
            }
        };

        checkStackTag();
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

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
        return capability;
    }

    /**
     * Retrieves the inventory contents.
     *
     * @return The inventory contents.
     */
    public InventoryContents getInventory() {
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

    /**
     * Overrides the stack in the given slot. This method is used by the
     * standard Forge helper methods and classes. It is not intended for
     * general use by other mods, and the handler may throw an error if it
     * is called unexpectedly.
     *
     * @param slot  Slot to modify
     * @param stack ItemStack to set slot to (may be empty).
     * @throws RuntimeException if the handler is called in a way that the handler
     *                          was not expecting.
     **/
    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        capability.setStackInSlot(slot, stack);
    }

    /**
     * Returns the number of slots available
     *
     * @return The number of slots available
     **/
    @Override
    public int getSlots() {
        return capability.getSlots();
    }

    /**
     * Returns the ItemStack in a given slot.
     * <p>
     * The result's stack size may be greater than the itemstack's max size.
     * <p>
     * If the result is empty, then the slot is empty.
     *
     * <p>
     * <strong>IMPORTANT:</strong> This ItemStack <em>MUST NOT</em> be modified. This method is not for
     * altering an inventory's contents. Any implementers who are able to detect
     * modification through this method should throw an exception.
     * </p>
     * <p>
     * <strong><em>SERIOUSLY: DO NOT MODIFY THE RETURNED ITEMSTACK</em></strong>
     * </p>
     *
     * @param slot Slot to query
     * @return ItemStack in given slot. Empty Itemstack if the slot is empty.
     **/
    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return capability.getStackInSlot(slot);
    }

    /**
     * <p>
     * Inserts an ItemStack into the given slot and return the remainder.
     * The ItemStack <em>should not</em> be modified in this function!
     * </p>
     *
     * @param slot     Slot to insert into.
     * @param stack    ItemStack to insert. This must not be modified by the item handler.
     * @param simulate If true, the insertion is only simulated
     * @return The remaining ItemStack that was not inserted (if the entire stack is accepted, then return an empty ItemStack).
     * May be the same as the input ItemStack if unchanged, otherwise a new ItemStack.
     * The returned ItemStack can be safely modified after.
     **/
    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return capability.insertItem(slot, stack, simulate);
    }

    /**
     * Extracts an ItemStack from the given slot.
     * <p>
     * The returned value must be empty if nothing is extracted,
     * otherwise its stack size must be less than or equal to {@code amount} and {@link ItemStack#getMaxStackSize()}.
     * </p>
     *
     * @param slot     Slot to extract from.
     * @param amount   Amount to extract (may be greater than the current stack's max limit)
     * @param simulate If true, the extraction is only simulated
     * @return ItemStack extracted from the slot, must be empty if nothing can be extracted.
     * The returned ItemStack can be safely modified after, so item handlers should return a new or copied stack.
     **/
    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return capability.extractItem(slot, amount, simulate);
    }

    /**
     * Retrieves the maximum stack size allowed to exist in the given slot.
     *
     * @param slot Slot to query.
     * @return The maximum stack size allowed in the slot.
     */
    @Override
    public int getSlotLimit(int slot) {
        return capability.getSlotLimit(slot);
    }

    /**
     * <p>
     * <p>
     * It should be used instead of simulated insertions in cases where the contents and state of the inventory are
     * irrelevant, mainly for the purpose of automation and logic (for instance, testing if a minecart can wait
     * to deposit its items into a full inventory, or if the items in the minecart can never be placed into the
     * inventory and should move on).
     * </p>
     * <ul>
     * <li>isItemValid is false when insertion of the item is never valid.</li>
     * <li>When isItemValid is true, no assumptions can be made and insertion must be simulated case-by-case.</li>
     * <li>The actual items in the inventory, its fullness, or any other state are <strong>not</strong> considered by isItemValid.</li>
     * </ul>
     *
     * @param slot  Slot to query for validity
     * @param stack Stack to test with for validity
     * @return true if the slot can insert the ItemStack, not considering the current state of the inventory.
     * false if the slot can never insert the ItemStack in any situation.
     */
    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return capability.isItemValid(slot, stack);
    }
}
