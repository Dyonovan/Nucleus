package com.pauljoda.nucleus.helper;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.pauljoda.nucleus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.lwjgl.opengl.GL11;

import java.awt.*;

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
public class GuiHelper {

    /*******************************************************************************************************************
     * Sound                                                                                                           *
     *******************************************************************************************************************/

    /**
     * Used to play the button sound in a GUI
     */
    public static void playButtonSound() {
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    /*******************************************************************************************************************
     * Render Methods                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Used to set the color of the GL stack from an int value
     * @param color The color value eg 0x000000
     */
    public static void setGLColorFromInt(int color) {
        float red   = (color >> 16 & 255) / 255F;
        float green = (color >> 8 & 255) / 255F;
        float blue  = (color & 255) / 255F;
        RenderUtils.setColor(new Color(red, green, blue, 1.0F));
    }

    /**
     * Draws the given icon with optional cut
     *
     * @param icon The texture
     * @param x X pos
     * @param y Y pos
     * @param width keep width of icon
     * @param height keep height of icon
     * @param cut 0 is full icon, 16 is full cut
     */
    public static void drawIconWithCut(TextureAtlasSprite icon, int x, int y, int width, int height, int cut) {
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder renderer = tess.getBuilder();
        renderer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        renderer.vertex(x, y + height, 0).uv(icon.getU0(), icon.getV(height)).normal(0, -1, 0).endVertex();
        renderer.vertex(x + width, y + height, 0).uv(icon.getU(width), icon.getV(height)).normal(0, -1, 0).endVertex();
        renderer.vertex(x + width, y + cut, 0).uv(icon.getU(width), icon.getV(cut)).normal(0, -1, 0).endVertex();
        renderer.vertex(x, y + cut, 0).uv(icon.getU0(), icon.getV(cut)).normal(0, -1, 0).endVertex();
        tess.end();
    }

    /**
     * Renders a fluid from the given tank
     * @param tank The tank
     * @param x The x pos
     * @param y The y pos
     * @param maxHeight Max height
     * @param maxWidth Max width
     */
    public static void renderFluid(FluidTank tank, int x, int y, int maxHeight, int maxWidth) {
        FluidStack fluid = tank.getFluid();
        if(!fluid.isEmpty()) {
            GL11.glPushMatrix();
            int level = (fluid.getAmount() * maxHeight) / tank.getCapacity();
            TextureAtlasSprite icon = Minecraft.getInstance().getTextureAtlas(RenderUtils.MC_BLOCKS_RESOURCE_LOCATION)
                    .apply(fluid.getFluid().getAttributes().getStillTexture());
            RenderUtils.bindMinecraftBlockSheet();
            setGLColorFromInt(fluid.getFluid().getAttributes().getColor(fluid));

            double timesW = Math.floor(maxWidth / 16);
            int cutW = 16;

            for(int j = 0; j <= timesW; j++) {
                if(j == timesW)
                    cutW = maxWidth % 16;
                if(level >= 16) {
                    double times = Math.floor(level / 16);
                    for(int i = 1; i <= times; i++) {
                        drawIconWithCut(icon, x + (j * 16), y - (16 * i), cutW, 16, 0);
                    }
                    int cut = level % 16;
                    drawIconWithCut(icon, x + (j * 16), (int) (y - (16 * (times + 1))), cutW, 16, 16 - cut);
                } else {
                    int cut = level % 16;
                    drawIconWithCut(icon, x + (j * 16), y - 16, cutW, 16, 16 - cut);
                }
            }
            GL11.glPopMatrix();
        }
    }

    /*******************************************************************************************************************
     * Helper Methods                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Test if location is in bounds
     *
     * @param x xLocation
     * @param y yLocation
     * @param a Rectangle point a
     * @param b Rectangle point b
     * @param c Rectangle point c
     * @param d Rectangle point d
     *        (A,B)------------------
     *          -                  -
     *          -----------------(C,D)
     * @return True if in bounds
     */
    public static boolean isInBounds(double x, double y, int a, int b, int c, int d) {
        return x >= a && x <= c && y >= b && y <= d;
    }
}
