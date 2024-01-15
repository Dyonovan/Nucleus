package com.pauljoda.nucleus.capabilities;

import com.pauljoda.nucleus.common.Savable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

public abstract class InventoryContents implements Savable {
    // List of Inventory contents
    public NonNullList<ItemStack> inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY);

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * The initial size of the inventory
     *
     * @return How big to make the inventory on creation
     */
    protected abstract int getInventorySize();

    /*******************************************************************************************************************
     * Savable Methods                                                                                                 *
     *******************************************************************************************************************/

    /**
     * Loads the data from the given CompoundTag.
     *
     * @param tag The CompoundTag containing the data to be loaded.
     */
    @Override
    public void load(CompoundTag tag) {
        ContainerHelper.loadAllItems(tag, inventory);

        ListTag nbttaglist = new ListTag();

        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemstack = inventory.get(i);
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.putByte("Slot", (byte) i);
            itemstack.save(nbttagcompound);
            nbttaglist.add(nbttagcompound);
        }

        if (!nbttaglist.isEmpty()) {
            tag.put("Items", nbttaglist);
        }
    }

    /**
     * Saves the data of the object into the specified CompoundTag.
     *
     * @param tag The CompoundTag to store the data into.
     * @return The updated CompoundTag with the saved data.
     */
    @Override
    public CompoundTag save(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, inventory);
        return tag;
    }
}
