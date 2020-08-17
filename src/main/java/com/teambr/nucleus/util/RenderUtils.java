package com.teambr.nucleus.util;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.ResourceLocation;
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
            AtlasTexture.LOCATION_BLOCKS_TEXTURE;
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
        Minecraft.getInstance().getTextureManager().bindTexture(resource);
    }

    /**
     * Used to bind the MC item sheet
     */
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
        GlStateManager.color4f(color.getRed() / 255F, color.getGreen() / 255F,
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
        RenderHelper.disableStandardItemLighting();
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
        RenderHelper.enableStandardItemLighting();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    /*******************************************************************************************************************
     * Shape Rendering                                                                                                 *
     *******************************************************************************************************************/

    /**
     * Used to draw a 3d cube, provide opposite corners
     *
     * @param x1 First X Position
     * @param y1 First Y Position
     * @param z1 First Z Position
     * @param x2 Second X Position
     * @param y2 Second Y Position
     * @param z2 Second Z Position
     * @param u Min U
     * @param v Min V
     * @param u1 Max U
     * @param v1 Max V
     */
    public static void renderCubeWithTexture(double x1, double y1, double z1, double x2, double y2, double z2,
                                 float u, float v, float u1, float v1) {
        BufferBuilder tes = Tessellator.getInstance().getBuffer();

        VertexFormat POSITION_TEX_NORMAL_F = new VertexFormat(
                ImmutableList.<VertexFormatElement>builder()
                        .add(DefaultVertexFormats.POSITION_3F)
                        .add(DefaultVertexFormats.TEX_2F)
                        .add(new VertexFormatElement(0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.NORMAL, 3))
                        .build());

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMAL_F);
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z1).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z1).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z1).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMAL_F);
        tes.pos(x1, y1, z2).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z2).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z2).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMAL_F);
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x1, y1, z2).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z1).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMAL_F);
        tes.pos(x2, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z1).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z2).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMAL_F);
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z1).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y1, z2).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMAL_F);
        tes.pos(x1, y2, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z2).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z1).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();
    }
}