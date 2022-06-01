package com.pauljoda.nucleus.common.tiles;

import com.pauljoda.nucleus.common.tiles.fluid.FluidAndItemHandler;
import com.pauljoda.nucleus.energy.implementations.EnergyBank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * From the latin "pan" for all, combines energy, item, and fluid handlers to one class
 *
 * @author Paul Davis - pauljoda
 * @since 8/30/20
 */
public abstract class PanHandler extends FluidAndItemHandler implements IEnergyStorage {

    // Sync Values
    public static final int UPDATE_ENERGY_ID     = 1000;
    public static final int UPDATE_DIFFERENCE_ID = 1001;

    // Energy Storage
    public EnergyBank energyStorage;

    // IC2 Update Variable
    protected boolean firstRun = true;

    // Energy Change Values
    public int lastEnergy, lastDifference, currentDifference = 0;

    /**
     * Main Constructor
     */
    public PanHandler(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
        energyStorage = new EnergyBank(getDefaultEnergyStorageSize());
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Used to define the default size of this energy bank
     * @return The default size of the energy bank
     */
    protected abstract int getDefaultEnergyStorageSize();

    /**
     * Is this tile an energy provider
     * @return True to allow energy out
     */
    protected abstract boolean isProvider();

    /**
     * Is this tile an energy receiver
     * @return True to accept energy
     */
    protected abstract boolean isReceiver();

    /*******************************************************************************************************************
     * Tile Methods                                                                                                    *
     *******************************************************************************************************************/

    @Override
    protected void onServerTick() {
        super.onServerTick();

        // Handle Energy Difference
        currentDifference = energyStorage.getCurrentStored() - lastEnergy;

        // Update client
        if(currentDifference != lastDifference)
            sendValueToClient(UPDATE_DIFFERENCE_ID, currentDifference);

        // Store for next round
        lastDifference = currentDifference;
        lastEnergy     = energyStorage.getCurrentStored();
    }

    @Override
    public void load(@Nonnull CompoundTag compound) {
        super.load(compound);
        energyStorage.writeToNBT(compound);
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag compound) {
        super.saveAdditional(compound);

        energyStorage.readFromNBT(compound);

        // Check for bad tags
        if(energyStorage.getMaxStored() == 0)
            energyStorage.setMaxStored(getDefaultEnergyStorageSize());
        if(energyStorage.getMaxInsert() == 0)
            energyStorage.setMaxInsert(getDefaultEnergyStorageSize());
        if(energyStorage.getMaxExtract() == 0)
            energyStorage.setMaxExtract(getDefaultEnergyStorageSize());
    }

    private LazyOptional<?> lazyOptional = LazyOptional.of(() -> this);

    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
        if(capability == CapabilityEnergy.ENERGY)
            return (LazyOptional<T>) lazyOptional;
        return super.getCapability(capability, facing);
    }

    /**
     * Used to set the value of a field
     * @param id The field id
     * @param value The value of the field
     */
    @Override
    public void setVariable(int id, double value) {
        switch (id) {
            case UPDATE_ENERGY_ID :
                energyStorage.setCurrentStored((int) value);
                break;
            case UPDATE_DIFFERENCE_ID :
                currentDifference = (int) value;
                break;
            default :
        }
    }

    /**
     * Used to get the field on the server, this will fetch the server value and overwrite the current
     * @param id The field id
     * @return The value on the server, now set to ourselves
     */
    @Override
    public Double getVariable(int id) {
        switch (id) {
            case UPDATE_ENERGY_ID :
                return (double) energyStorage.getCurrentStored();
            case UPDATE_DIFFERENCE_ID :
                return (double) currentDifference;
            default :
                return 0.0;
        }
    }

    /*******************************************************************************************************************
     * ForgeEnergy                                                                                                     *
     *******************************************************************************************************************/

    /**
     * Used to determine if this storage can receive energy.
     * If this is false, then any calls to receiveEnergy will return 0.
     */
    @Override
    public boolean canReceive() {
        return isReceiver();
    }

    /**
     * Returns if this storage can have energy extracted.
     * If this is false, then any calls to extractEnergy will return 0.
     */
    @Override
    public boolean canExtract() {
        return isProvider();
    }

    /**
     * Returns the amount of energy currently stored.
     */
    @Override
    public int getEnergyStored() {
        return energyStorage.getCurrentStored();
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive
     *            Maximum amount of energy to be inserted.
     * @param simulate
     *            If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(isReceiver()) {
            int returnValue = energyStorage.receivePower(maxReceive, !simulate);
            if(!simulate)
                sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored());
            return returnValue;
        }
        return 0;
    }

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract
     *            Maximum amount of energy to be extracted.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
     */
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if(isProvider()) {
            int returnValue = energyStorage.providePower(maxExtract, !simulate);
            if(!simulate)
                sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored());
            return returnValue;
        }
        return 0;
    }
}