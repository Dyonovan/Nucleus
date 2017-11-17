package com.teambr.nucleus.common.blocks;

import com.teambr.nucleus.annotation.IRegistrable;
import com.teambr.nucleus.client.ModelHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * This file was created for Nucleus
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Buuz135 + Paul Davis
 * @since 11/14/2019
 */
public class RegistrableBlock extends Block implements IRegistrable<Block> {

    // Held instance of class
    public static RegistrableBlock INSTANCE;

    /**
     * Basic constructor
     * @param materialIn Block material
     */
    public RegistrableBlock(Material materialIn) {
        super(materialIn);
        INSTANCE = this;
    }

    /**
     * Registers an object to the ForgeRegistry
     *
     * @param registry The Block Forge Registry
     */
    @Override
    public void registerObject(IForgeRegistry<Block> registry) {
        registry.register(this);
    }

    /**
     * Register the renderers for the block/item
     */
    @Override
    public void registerRender() {
        ModelHelper.registerSimpleRenderBlock(this);
    }
}
