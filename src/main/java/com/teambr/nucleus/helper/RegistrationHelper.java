package com.teambr.nucleus.helper;

import com.teambr.nucleus.annotation.IRegisterable;
import com.teambr.nucleus.annotation.RegisteringBlock;
import com.teambr.nucleus.annotation.RegisteringItem;
import com.teambr.nucleus.common.IRegistersOreDictionary;
import com.teambr.nucleus.common.blocks.IRegistersTileEntity;
import com.teambr.nucleus.common.tiles.nbt.NBTManager;
import com.teambr.nucleus.data.RegistrationData;
import com.teambr.nucleus.util.AnnotationUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Buuz135 + Paul Davis
 * @since 11/14/2019
 */
@SuppressWarnings({"ConstantConditions", "unchecked"})
public class RegistrationHelper {

    /**
     * Adds the items to the registry, including block ItemBlocks and ore dict tag
     *
     * @param event Registration event
     */
    public static void addItems(RegistryEvent.Register<Item> event, RegistrationData data) {
        // Create ItemBlocks
        data.getBlocks().forEach(block ->
                event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName())));

        // Add Items
        data.getItems().forEach(item -> {
            // Register the item itself
            ((IRegisterable<Item>) item).registerObject(event.getRegistry());

            // If has ore dict, register
            if (item instanceof IRegistersOreDictionary &&
                    ((IRegistersOreDictionary) item).getOreDictionaryTag() != null)
                OreDictionary.registerOre(((IRegistersOreDictionary) item).getOreDictionaryTag(), item);
        });

        if (FMLCommonHandler.instance().getSide().isClient()) {
            data.getBlocks().forEach(block -> ((IRegisterable<?>) block).registerRender());
            data.getItems().forEach(item -> ((IRegisterable<?>) item).registerRender());
        }
    }

    /**
     * Registers blocks, their tile, and ore dict tag if possible
     *
     * @param event Registration event
     */
    public static void addBlocks(RegistryEvent.Register<Block> event, RegistrationData data) {
        data.getBlocks().forEach(block -> {
            // Register the block itself
            ((IRegisterable<Block>) block).registerObject(event.getRegistry());

            // Register the tile, if present
            if (block instanceof IRegistersTileEntity &&
                    ((IRegistersTileEntity) block).getTileEntityClass() != null) {
                GameRegistry.registerTileEntity(((IRegistersTileEntity) block).getTileEntityClass(),
                        block.getRegistryName().toString());
                NBTManager.getInstance().scanTileClassForAnnotations(((IRegistersTileEntity) block).getTileEntityClass());
            }

            // Add ore dict tag if present
            if (block instanceof IRegistersOreDictionary &&
                    ((IRegistersOreDictionary) block).getOreDictionaryTag() != null)
                OreDictionary.registerOre(((IRegistersOreDictionary) block).getOreDictionaryTag(), block);
        });
    }

    /**
     * Fills the {@link RegisteringBlock} provided with the items and blocks needed for later registering
     * @param event PreInit event
     * @param data New data object to fill
     */
    public static void fillRegistrationData(FMLPreInitializationEvent event, RegistrationData data) {
        // Find Blocks, either by annotated class or field
        AnnotationUtils.getAnnotatedClasses(event.getAsmData(), RegisteringBlock.class) // By Class, only use when block class makes one instance of a block
                .stream().filter(IRegisterable.class::isAssignableFrom).forEach(aClass -> {
            try {
                Block block = (Block) aClass.newInstance();
                data.getBlocks().add(block);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
        AnnotationUtils.getAllAnnotatedFields(event.getAsmData(), RegisteringBlock.class) // Gets fields annotated
                .stream().filter(Block.class::isInstance).forEach(field -> {
            try {
                Block block = (Block) field.get(field.getDeclaringClass());
                data.getBlocks().add(block);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        // Find items, either by annotated class or field
        AnnotationUtils.getAnnotatedClasses(event.getAsmData(), RegisteringItem.class) // By Class, only use when item class makes one instance of a block
                .stream().filter(IRegisterable.class::isAssignableFrom).forEach(aClass -> {
            try {
                Item item = (Item) aClass.newInstance();
                data.getItems().add(item);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
        AnnotationUtils.getAllAnnotatedFields(event.getAsmData(), RegisteringItem.class) // By field
                .stream().filter(Item.class::isInstance).forEach(field -> {
            try {
                Item item = (Item) field.get(field.getDeclaringClass());
                data.getItems().add(item);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
