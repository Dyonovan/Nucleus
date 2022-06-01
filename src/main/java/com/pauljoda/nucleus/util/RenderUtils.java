package com.pauljoda.nucleus.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;

/**
 * This file was created for Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public class RenderUtils {
    // Resource Locations
    public static final ResourceLocation MC_BLOCKS_RESOURCE_LOCATION =
            InventoryMenu.BLOCK_ATLAS;
    /*public static final ResourceLocation MC_ITEMS_RESOURCE_LOCATION =
            new ResourceLocation("textures/atlas/items.png");*/

    /*******************************************************************************************************************
     * Render Helpers                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Used to bind a texture to the render manager
     * @param resource The resource to bind
     */
    public static void bindTexture(ResourceLocation resource) {
        Minecraft.getInstance().getTextureManager().bindForSetup(resource);
    }

    /**
     * Used to bind the MC item sheet
     * No longer used as items are now part of Block Sheet
     */
    @Deprecated
    public static void bindMinecraftItemSheet() {
        bindTexture(MC_BLOCKS_RESOURCE_LOCATION);
    }

    /**
     * Used to bind the MC blocks sheet
     */
    public static void bindMinecraftBlockSheet() {
        bindTexture(MC_BLOCKS_RESOURCE_LOCATION);
    }
    
    /**
     * Set the GL color. You should probably reset it after this
     *
     * @param color The color to set
     */
    public static void setColor(Color color) {
        RenderSystem.setShaderColor(color.getRed() / 255F, color.getGreen() / 255F,
                color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    /**
     * Sets the color back to full white (normal)
     */
    public static void restoreColor() {
        setColor(new Color(255, 255, 255));
    }

    /**
     * Used to prepare the rendering state. For basic stuff that you want things to behave on
     */
    public static void prepareRenderState() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    /**
     * Un-does the prepare state
     */
    public static void restoreRenderState() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }
}