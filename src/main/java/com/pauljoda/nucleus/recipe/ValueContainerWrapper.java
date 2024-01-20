package com.pauljoda.nucleus.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Adds empty statements for container, implement this to have a custom wrapper
 */
public interface ValueContainerWrapper extends Container {
    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    default int getContainerSize() {
        return 1;
    }

    @Override
    default boolean isEmpty() {
        return false;
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param pSlot
     */
    @Override
    default ItemStack getItem(int pSlot) {
        return ItemStack.EMPTY;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param pSlot
     * @param pAmount
     */
    @Override
    default ItemStack removeItem(int pSlot, int pAmount) {
        return ItemStack.EMPTY;
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param pSlot
     */
    @Override
    default ItemStack removeItemNoUpdate(int pSlot) {
        return ItemStack.EMPTY;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param pSlot
     * @param pStack
     */
    @Override
    default void setItem(int pSlot, ItemStack pStack) {

    }

    /**
     * For block entities, ensures the chunk containing the block entity is saved to disk later - the game won't think it hasn't changed and skip it.
     */
    @Override
    default void setChanged() {

    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     *
     * @param pPlayer
     */
    @Override
    default boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    default void clearContent() {

    }
}
