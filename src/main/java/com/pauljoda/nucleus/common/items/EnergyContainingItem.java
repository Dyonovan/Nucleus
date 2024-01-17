package com.pauljoda.nucleus.common.items;

import com.pauljoda.nucleus.capabilities.energy.EnergyBank;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

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
public abstract class EnergyContainingItem implements IEnergyStorage {
    // Variables
    private final ItemStack heldStack;
    private final EnergyBank localEnergy;

    /**
     * Simplest constructor of EnergyBank
     */
    public EnergyContainingItem(@NotNull ItemStack stack) {
        heldStack = stack;
        localEnergy = initializeEnergyStorage();

        checkStackTag();
    }

    /**
     * Makes sure we always have a valid tag
     */
    @SuppressWarnings("DataFlowIssue")
    protected void checkStackTag() {
        // Give the stack a tag
        if (!heldStack.hasTag()) {
            heldStack.setTag(new CompoundTag());
            localEnergy.save(heldStack.getTag());
        }
    }

    /**
     * Initializes the energy storage for the EnergyHandler class.
     * <p>
     * Be sure to override methods for canExtract etc if only wanting to extract vs insert, default
     * implementation will do both
     *
     * @return The initialized EnergyBank object.
     */
    protected abstract EnergyBank initializeEnergyStorage();

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
        localEnergy.load(heldStack.getTag());
        int energyReceived = localEnergy.receiveEnergy(maxReceive, !simulate);
        localEnergy.save(heldStack.getTag());
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
        localEnergy.load(heldStack.getTag());
        int extractedEnergy = localEnergy.extractEnergy(maxExtract, !simulate);
        localEnergy.save(heldStack.getTag());
        return extractedEnergy;
    }

    /**
     * Returns the amount of energy currently stored.
     */
    @Override
    public int getEnergyStored() {
        checkStackTag();
        localEnergy.load(heldStack.getTag());
        return localEnergy.getEnergyStored();
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    @Override
    public int getMaxEnergyStored() {
        checkStackTag();
        localEnergy.load(heldStack.getTag());
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
}
