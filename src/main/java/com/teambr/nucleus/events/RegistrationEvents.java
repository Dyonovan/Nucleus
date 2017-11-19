package com.teambr.nucleus.events;

import com.teambr.nucleus.annotation.IRegistrable;
import com.teambr.nucleus.common.CommonProxy;
import com.teambr.nucleus.common.IRegistersOreDictionary;
import com.teambr.nucleus.common.blocks.IRegistersTileEntity;
import com.teambr.nucleus.common.tiles.nbt.NBTManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

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
@SuppressWarnings({"ConstantConditions", "unchecked"})
public class RegistrationEvents {

    // Variable for mod id
    private final String modID;

    /**
     * Constructor for registration
     * @param modID The mod id
     */
    public RegistrationEvents(String modID) {
        this.modID = modID;
    }

    /**
     * Adds the items to the registry, including block ItemBlocks and ore dict tag
     * @param event Registation event
     */
    @SubscribeEvent
    public void addItems(RegistryEvent.Register<Item> event) {
        // Create ItemBlocks
        CommonProxy.BLOCKS.get(modID).forEach(block ->
                event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName())));

        // Add Items
        CommonProxy.ITEMS.get(modID).forEach(item -> {
            // Register the item itself
            ((IRegistrable<Item>) item).registerObject(event.getRegistry());

            // If has ore dict, register
            if(item instanceof IRegistersOreDictionary &&
                    ((IRegistersOreDictionary)item).getOreDictionaryTag() != null)
                OreDictionary.registerOre(((IRegistersOreDictionary)item).getOreDictionaryTag(), item);
        });

        if (FMLCommonHandler.instance().getSide().isClient()) {
            CommonProxy.BLOCKS.get(modID).forEach(block -> ((IRegistrable<?>) block).registerRender());
            CommonProxy.ITEMS.get(modID).forEach(item   -> ((IRegistrable<?>) item).registerRender());
        }
    }

    /**
     * Registers blocks, their tile, and ore dict tag if possible
     * @param event Registration event
     */
    @SubscribeEvent
    public void addBlocks(RegistryEvent.Register<Block> event) {
        CommonProxy.BLOCKS.get(modID).forEach(block -> {
            // Register the block itself
            ((IRegistrable<Block>) block).registerObject(event.getRegistry());

            // Register the tile, if present
            if (block instanceof IRegistersTileEntity &&
                    ((IRegistersTileEntity) block).getTileEntityClass() != null) {
                GameRegistry.registerTileEntity(((IRegistersTileEntity) block).getTileEntityClass(),
                        block.getRegistryName().toString());
                NBTManager.getInstance().scanTileClassForAnnotations(((IRegistersTileEntity) block).getTileEntityClass());
            }

            // Add ore dict tag if present
            if(block instanceof IRegistersOreDictionary &&
                    ((IRegistersOreDictionary)block).getOreDictionaryTag() != null)
                OreDictionary.registerOre(((IRegistersOreDictionary)block).getOreDictionaryTag(), block);
        });
    }
}
