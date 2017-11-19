package com.teambr.nucleus.common;

import com.google.common.collect.ArrayListMultimap;
import com.teambr.nucleus.annotation.IRegistrable;
import com.teambr.nucleus.annotation.RegisteringBlock;
import com.teambr.nucleus.annotation.RegisteringItem;
import com.teambr.nucleus.util.AnnotationUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * This file was created for Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
@SuppressWarnings("ConstantConditions")
public class CommonProxy {

    public static final ArrayListMultimap<String, Block> BLOCKS = ArrayListMultimap.create();
    public static final ArrayListMultimap<String, Item> ITEMS = ArrayListMultimap.create();

    /**
     * Called on preInit
     */
    public void preInit(FMLPreInitializationEvent event) {
        findRegisteringBlocks(event);
        findRegisteringItems(event);
    }

    /**
     * Called on init
     */
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.nucleus.api.waila.WailaModPlugin.registerServerCallback");
    }

    /**
     * Called on postInit
     */
    public void postInit(FMLPostInitializationEvent event) {}


    /**
     * Find all fields with the {@link RegisteringBlock} annotation and hold them for later
     * @param event PreInit event
     */
    private void findRegisteringBlocks(FMLPreInitializationEvent event) {
        AnnotationUtils.getAnnotatedClasses(event.getAsmData(), RegisteringBlock.class)
                .stream().filter(IRegistrable.class::isAssignableFrom).forEach(aClass -> {
            try {
                Block block = (Block) aClass.newInstance();
                BLOCKS.put(block.getRegistryName().getResourceDomain(), block);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        event.getModLog().info("Found " + BLOCKS.size() + " RegisteringBlocks");
    }

    /**
     * Find all fields with the {@link RegisteringItem} annotation and hold them for later
     * @param event PreInit event
     */
    private void findRegisteringItems(FMLPreInitializationEvent event) {
        AnnotationUtils.getAnnotatedClasses(event.getAsmData(), RegisteringItem.class)
                .stream().filter(IRegistrable.class::isAssignableFrom).forEach(aClass -> {
            try {
                Item item = (Item) aClass.newInstance();
                ITEMS.put(item.getRegistryName().getResourceDomain(), item);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        event.getModLog().info("Found " + ITEMS.size() + " RegisteringItems");
    }
}
