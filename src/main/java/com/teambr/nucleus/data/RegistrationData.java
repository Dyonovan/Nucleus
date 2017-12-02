package com.teambr.nucleus.data;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 11/19/17
 */
public class RegistrationData {

    /*******************************************************************************************************************
     * Data                                                                                                            *
     *******************************************************************************************************************/

    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Item>  items  = new ArrayList<>();

    // Name, used mainly for debugging
    private String registryName;

    /**
     * Creates a registry holder
     * @param name Name of the registry, should be modid
     */
    public RegistrationData(String name) {
        this.registryName = name;
    }

    /*******************************************************************************************************************
     * Accessors                                                                                                       *
     *******************************************************************************************************************/

    /**
     * Returns a list of blocks
     * @return Blocks
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * Returns a list of items
     * @return Items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Gets the name for this registry
     * @return
     */
    public String getRegistryName() {
        return registryName;
    }
}
