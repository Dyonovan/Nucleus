package com.pauljoda.nucleus.common.items;

import com.pauljoda.nucleus.energy.implementations.EnergyBank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This file was created for Nucleus
 * <p>
 * NeoTech is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 3/1/2017
 */
public class EnergyContainingItem implements IEnergyStorage, ICapabilityProvider {
    // Variables
    private ItemStack  heldStack;
    private EnergyBank localEnergy;

    /**
     * Simplest constructor of EnergyBank
     *
     * @param size The max stored, also sets max in and out
     */
    public EnergyContainingItem(ItemStack stack, int size) {
        heldStack = stack;
        localEnergy = new EnergyBank(size);

        checkStackTag();
    }

    /**
     * Makes sure we always have a valid tag
     */
    protected void checkStackTag() {
        // Give the stack a tag
        if(!heldStack.hasTag()) {
            heldStack.setTag(new CompoundNBT());
            localEnergy.writeToNBT(heldStack.getTag());
        }
    }

    /*******************************************************************************************************************
     * IEnergyStorage                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive Maximum amount of energy to be inserted.
     * @param simulate   If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        checkStackTag();
        localEnergy.readFromNBT(heldStack.getTag());
        int energyReceived = localEnergy.receivePower(maxReceive, !simulate);
        localEnergy.writeToNBT(heldStack.getTag());
        return energyReceived;
    }

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract Maximum amount of energy to be extracted.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
     */
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        checkStackTag();
        localEnergy.readFromNBT(heldStack.getTag());
        int extractedEnergy = localEnergy.providePower(maxExtract, !simulate);
        localEnergy.writeToNBT(heldStack.getTag());
        return extractedEnergy;
    }

    /**
     * Returns the amount of energy currently stored.
     */
    @Override
    public int getEnergyStored() {
        checkStackTag();
        localEnergy.readFromNBT(heldStack.getTag());
        return localEnergy.getEnergyStored();
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    @Override
    public int getMaxEnergyStored() {
        checkStackTag();
        localEnergy.readFromNBT(heldStack.getTag());
        return localEnergy.getMaxEnergyStored();
    }

    /**
     * Returns if this storage can have energy extracted.
     * If this is false, then any calls to extractEnergy will return 0.
     */
    @Override
    public boolean canExtract() {
        return true;
    }

    /**
     * Used to determine if this storage can receive energy.
     * If this is false, then any calls to receiveEnergy will return 0.
     */
    @Override
    public boolean canReceive() {
        return true;
    }

    /*******************************************************************************************************************
     * ICapabilityProvider                                                                                             *
     *******************************************************************************************************************/

    private LazyOptional<?> capabilityEnergy = LazyOptional.of(() -> this);

    /**
     * Retrieves the handler for the capability requested on the specific side.
     * The return value CAN be null if the object does not support the capability.
     * The return value CAN be the same for multiple faces.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return True if this object supports the capability.
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if(capability == CapabilityEnergy.ENERGY)
            return (LazyOptional<T>) capabilityEnergy;
        return LazyOptional.empty();
    }
}
