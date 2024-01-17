package com.pauljoda.nucleus.common.blocks.entity;

import com.pauljoda.nucleus.capabilities.energy.EnergyBank;
import com.pauljoda.nucleus.common.blocks.entity.fluid.FluidAndItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * <p>
 * From the latin "pan" for all, combines energy, item, and fluid handlers to one class
 *
 * @author Paul Davis - pauljoda
 * @since 8/30/20
 */
public abstract class PanHandler extends FluidAndItemHandler {

    // Sync Values
    public static final int UPDATE_ENERGY_ID = 1000;
    public static final int UPDATE_DIFFERENCE_ID = 1001;

    // Energy Storage
    protected final EnergyBank energyStorage;

    // Energy Change Values
    public int lastEnergy, lastDifference, currentDifference = 0;

    /**
     * Main Constructor
     */
    public PanHandler(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
        energyStorage = initializeEnergyStorage();
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Used to define the default size of this energy bank
     *
     * @return The default size of the energy bank
     */
    protected abstract int getDefaultEnergyStorageSize();

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
     * Tile Methods                                                                                                    *
     *******************************************************************************************************************/

    /**
     * This method is called on the server tick and is used to handle energy difference,
     * update the client if there is a change in the difference, and store the current
     * difference and energy for the next tick.
     */

    @Override
    public void onServerTick() {
        super.onServerTick();

        // Handle Energy Difference
        currentDifference = energyStorage.getEnergyStored() - lastEnergy;

        // Update client
        if (currentDifference != lastDifference)
            sendValueToClient(UPDATE_DIFFERENCE_ID, currentDifference);

        // Store for next round
        lastDifference = currentDifference;
        lastEnergy = energyStorage.getEnergyStored();
    }

    /**
     * Loads the data from the given CompoundTag.
     *
     * @param compound The CompoundTag containing the data to be loaded.
     */
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        // Write the current stored
        energyStorage.load(compound);

        // Check for bad tags
        if (energyStorage.getMaxEnergyStored() == 0)
            energyStorage.setCapacity(getDefaultEnergyStorageSize());
        if (energyStorage.getMaxReceive() == 0)
            energyStorage.setMaxReceive(getDefaultEnergyStorageSize());
        if (energyStorage.getMaxExtract() == 0)
            energyStorage.setMaxExtract(getDefaultEnergyStorageSize());
    }

    /**
     * Saves additional data of the EnergyHandler object into the specified CompoundTag.
     *
     * @param compound The CompoundTag to store the data into.
     */
    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        energyStorage.save(compound);
    }

    /**
     * Retrieves the energy capability of the EnergyHandler object.
     *
     * @return The energy capability of the EnergyHandler object.
     */
    public IEnergyStorage getEnergyCapability() {
        return energyStorage;
    }

    /*******************************************************************************************************************
     * Syncable                                                                                                        *
     *******************************************************************************************************************/

    /**
     * Used to set the value of a field
     *
     * @param id    The field id
     * @param value The value of the field
     */
    @Override
    public void setVariable(int id, double value) {
        switch (id) {
            case UPDATE_ENERGY_ID:
                energyStorage.setEnergy((int) value);
                break;
            case UPDATE_DIFFERENCE_ID:
                currentDifference = (int) value;
                break;
            default:
        }
    }

    /**
     * Used to get the field on the server, this will fetch the server value and overwrite the current
     *
     * @param id The field id
     * @return The value on the server, now set to ourselves
     */
    @Override
    public Double getVariable(int id) {
        switch (id) {
            case UPDATE_ENERGY_ID:
                return (double) energyStorage.getEnergyStored();
            case UPDATE_DIFFERENCE_ID:
                return (double) currentDifference;
            default:
                return 0.0;
        }
    }
}