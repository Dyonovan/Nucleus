package com.pauljoda.nucleus.common.blocks.entity.energy;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.energy.EnergyStorage;

/**
 * This class represents a storage bank for energy.
 */
public class EnergyBank extends EnergyStorage {
    private static final String ENERGY_STORED = "EnergyStored";
    private static final String CAPACITY = "Capacity";
    private static final String MAX_EXTRACT = "MaxExtract";
    private static final String MAX_INSERT = "MaxInsert";

    /**
     * Constructs an EnergyBank with a given capacity.
     *
     * @param capacity Amount of energy the bank can store, in energy units.
     */
    public EnergyBank(int capacity) {
        super(capacity);
    }

    /**
     * Set the energy value in the bank.
     *
     * @param energy the amount of energy to be set in the bank.
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Get current energy value in the bank.
     *
     * @return current amount of energy in the bank.
     */
    public int getEnergy() {
        return this.energy;
    }

    /**
     * Set the maximum capacity of the bank
     *
     * @param capacity the new maximum capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the current maximum capacity of the bank
     *
     * @return current maximum capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Set the maximum amount of energy the bank can receive at a time
     *
     * @param maxReceive the new maximum energy the bank can receive at a time
     */
    public void setMaxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
    }

    /**
     * Get the current maximum amount of energy the bank can receive at a time
     *
     * @return current maximum energy the bank can receive at a time
     */
    public int getMaxReceive() {
        return this.maxReceive;
    }

    /**
     * Set the maximum amount of energy the bank can extract at a time
     *
     * @param maxExtract the new maximum energy the bank can extract at a time
     */
    public void setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
    }

    /**
     * Get the current maximum amount of energy the bank can extract at a time
     *
     * @return current maximum energy the bank can extract at a time
     */
    public int getMaxExtract() {
        return this.maxExtract;
    }

    /**
     * Write the current state of the EnergyBank to a CompoundTag.
     *
     * @param compound The CompoundTag to write the EnergyBank's state to.
     */
    public void writeToNBT(CompoundTag compound) {
        compound.putInt(ENERGY_STORED, energy);
        compound.putInt(CAPACITY, capacity);
        compound.putInt(MAX_INSERT, maxReceive);
        compound.putInt(MAX_EXTRACT, maxExtract);
    }

    /**
     * Read and set the current state of the EnergyBank from a CompoundTag.
     *
     * @param compound The CompoundTag to read the EnergyBank's state from.
     */
    public void readFromNBT(CompoundTag compound) {
        energy = compound.getInt(ENERGY_STORED);
        capacity = compound.getInt(CAPACITY);
        maxReceive = compound.getInt(MAX_INSERT);
        maxExtract = compound.getInt(MAX_EXTRACT);
    }
}