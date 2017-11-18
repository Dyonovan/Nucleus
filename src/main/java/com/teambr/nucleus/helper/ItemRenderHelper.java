package com.teambr.nucleus.helper;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

/**
 * This file was created for AssistedProgression
 * <p>
 * AssistedProgression is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 11/13/17
 */
public class ItemRenderHelper {

    /**
     * Registers an item model
     * @param item The item
     */
    public static void registerItem(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
                new ModelResourceLocation(new ResourceLocation(item.getRegistryName().toString().toLowerCase()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(new ResourceLocation(item.getRegistryName().toString().toLowerCase()), "inventory"));
    }

    /**
     * Short hand for registerBlockModel with default meta
     */
    public static void registerBlockModel(Block block, String variants) {
        registerBlockModel(block, variants, 0);
    }

    /**
     * Used to set a block item to reflect to the world model
     * @param block The model
     * @param variants The variants
     * @param meta The meta
     */
    public static void registerBlockModel(Block block, String variants, int meta) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                meta, new ModelResourceLocation(new ResourceLocation(block.getRegistryName().toString().toLowerCase()), variants));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),
                meta, new ModelResourceLocation(new ResourceLocation(block.getRegistryName().toString().toLowerCase()), variants));
    }
}
