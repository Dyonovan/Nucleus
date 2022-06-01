package com.pauljoda.nucleus.common.container;

import com.pauljoda.nucleus.common.container.slots.IPhantomSlot;
import com.pauljoda.nucleus.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

/**
 * This file was created for Nucleus
 *
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public abstract class BaseContainer extends AbstractContainerMenu {
    // Variables
    protected Inventory playerInventory;
    protected IItemHandler inventory;

    protected int inventorySize;

    /**
     * Creates the container object
     * @param playerInventory The players inventory
     * @param inventory The tile/object inventory
     */
    public BaseContainer(@Nullable MenuType<?> type, int id,
                         Inventory playerInventory, IItemHandler inventory) {
        super(type, id);
        this.playerInventory = playerInventory;
        this.inventory = inventory;

        inventorySize = inventory.getSlots();
    }

    /**
     * Get the size of the inventory that isn't the players
     * @return The inventory size that doesn't count the player inventory
     */
    public int getInventorySizeNotPlayer() {
        return inventorySize;
    }

    /**
     * Adds the player offset with Y offset
     *
     * @param offsetY How far down
     */
    protected void addPlayerInventorySlots(int offsetY) {
        addPlayerInventorySlots(8, offsetY);
    }

    /**
     * Adds player inventory at location, includes space between normal and hotbar
     *
     * @param offsetX X offset
     * @param offsetY Y offset
     */
    protected void addPlayerInventorySlots(int offsetX, int offsetY) {
        for(int row = 0; row < 3; row++) {
            for(int column = 0; column < 9; column++)
                addSlot(new Slot(playerInventory,
                        column + row * 9 + 9,
                        offsetX + column * 18,
                        offsetY + row * 18));
        }

        for(int slot = 0; slot < 9; slot++)
            addSlot(new Slot(playerInventory, slot, offsetX + slot * 18, offsetY + 58));
    }

    /**
     * Adds a line of slots
     *
     * @param xOffset X offset
     * @param yOffset Y offset
     * @param start start slot number
     * @param count how many slots
     */
    protected void addInventoryLine(int xOffset, int yOffset, int start, int count) {
        addInventoryLine(xOffset, yOffset, start, count, 0);
    }

    /**
     * Adds a line of inventory slots with a margin around them
     *
     * @param xOffset X Offset
     * @param yOffset Y Offset
     * @param start The start slot id
     * @param count The count of slots
     * @param margin How much to pad the slots
     */
    protected void addInventoryLine(int xOffset, int yOffset, int start, int count, int margin) {
        int slotID = start;
        for(int x = 0; x < count; x++) {
            addSlot(new SlotItemHandler(inventory, slotID, xOffset + x * (18 + margin), yOffset));
            slotID++;
        }
    }

    /**
     * Adds an inventory grid to the container
     *
     * @param xOffset X pixel offset
     * @param yOffset Y pixel offset
     * @param width How many wide
     * @param start The start slot id
     */
    protected void addInventoryGrid(int xOffset, int yOffset, int width, int start) {
        int height = (int) Math.ceil(inventorySize / width);
        int slotID = start;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                addSlot(new SlotItemHandler(inventory, slotID, xOffset + x * 18, yOffset + y * 18));
                slotID++;
            }
        }
    }

    /**
     * The logic for when a phantom slot is clicked
     * @param slot The slot
     * @param mouseButton The mouse button
     * @param modifier The modifier
     * @param player The player
     * @return The stack
     */
    private void slotClickPhantom(Slot slot, int mouseButton, ClickType modifier, Player player) {
        ItemStack stack = ItemStack.EMPTY;

        if(mouseButton == 2) {
            if(((IPhantomSlot)slot).canAdjust())
                slot.set(ItemStack.EMPTY);
        } else if(mouseButton == 0 || mouseButton == 1) {
            Inventory playerInv = player.getInventory();
            slot.setChanged();
            ItemStack stackSlot = slot.getItem();
            ItemStack stackHeld = playerInv.getSelected();

            if(!stackSlot.isEmpty())
                stack = stackSlot.copy();

            if(stackSlot.isEmpty()) {
                if(!stackHeld.isEmpty() && slot.mayPlace(stackHeld))
                    fillPhantomSlot(slot, stackHeld, mouseButton, modifier);
            } else if(stackHeld.isEmpty()) {
                adjustPhantomSlot(slot, mouseButton, modifier);
                slot.onTake(player, playerInv.getSelected());
            } else if(slot.mayPlace(stackHeld)) {
                if(InventoryUtils.canStacksMerge(stackSlot, stackHeld))
                    adjustPhantomSlot(slot, mouseButton, modifier);
                else
                    fillPhantomSlot(slot, stackHeld, mouseButton, modifier);
            }
        }
    }

    /**
     * Used to adjust the items in the phantom slot
     * @param slot The slot being clicked
     * @param mouseButton The mouse button
     * @param modifier The modifier
     */
    private void adjustPhantomSlot(Slot slot, int mouseButton, ClickType modifier) {
        if(!((IPhantomSlot)slot).canAdjust())
            return;

        ItemStack stackSlot = slot.getItem();
        int stackSize = 0;
        if(modifier == ClickType.QUICK_MOVE)
            stackSize = (mouseButton == 0) ? (stackSlot.getCount() + 1) / 2 : stackSlot.getCount() * 2;
        else
            stackSize =  (mouseButton == 0) ? stackSlot.getCount() - 1 : stackSlot.getCount() + 1;

        if(stackSize > slot.getMaxStackSize())
            stackSize = slot.getMaxStackSize();

        stackSlot.setCount(stackSize);

        if(stackSlot.getCount() <= 0)
            slot.set(ItemStack.EMPTY);
    }

    /**
     * Fills the phantom slot with the given slot, not consuming the held stack
     * @param slot The slot
     * @param stackHeld The stack held
     * @param mouseButton The mouse button
     * @param modifier The modifier
     */
    private void fillPhantomSlot(Slot slot, ItemStack stackHeld, int mouseButton, ClickType modifier) {
        if(!((IPhantomSlot)slot).canAdjust())
            return;

        int stackSize = (mouseButton == 0) ? stackHeld.getCount() : 1;

        if(stackSize > slot.getMaxStackSize())
            stackSize = slot.getMaxStackSize();

        ItemStack phantomStack = stackHeld.copy();
        phantomStack.setCount(stackSize);

        slot.set(phantomStack);
    }

    /*******************************************************************************************************************
     * Container                                                                                                       *
     *******************************************************************************************************************/

    /**
     * The logic for when a slot is clicked
     * @param slotId The slot
     * @param dragType The mouse button
     * @param clickTypeIn The modifier
     * @param player The player
     */
    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        Slot slot = (slotId < 0) ? null : slots.get(slotId);
        if(slot != null) {
            if(slot instanceof IPhantomSlot) {
                slotClickPhantom(slot, dragType, clickTypeIn, player);
                return;
            }
        }
        super.clicked(slotId, dragType, clickTypeIn, player);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        if(index < 0 || index > slots.size())
            return super.quickMoveStack(playerIn, index);
        Slot slot = slots.get(index);
        if(slot.hasItem()) {
            ItemStack itemToTransfer = slot.getItem();
            ItemStack copy = itemToTransfer.copy();

            if(index < getInventorySizeNotPlayer()) {
                if (!moveItemStackTo(itemToTransfer, getInventorySizeNotPlayer(), slots.size(), true))
                    return ItemStack.EMPTY;
            } else if(!moveItemStackTo(itemToTransfer, 0, getInventorySizeNotPlayer(), false))
                return ItemStack.EMPTY;

            if(itemToTransfer.isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();

            if(itemToTransfer.getCount() != copy.getCount())
                return copy;
        }
        return ItemStack.EMPTY;
    }
}
