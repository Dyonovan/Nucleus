package com.pauljoda.nucleus.client.gui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pauljoda.nucleus.util.RenderUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

/**
 * This file was created for Nucleus
 *
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/12/2017
 */
public class NinePatchRenderer {
    // Variables
    protected int u, v, cellSize;
    protected ResourceLocation patchLocation;

    /**
     * Creates a renderer with given options
     *
     * Texture must be in the following pattern. Cell size is how many pixels each box is (relative to x256)
     *
     * *---*---*---*
     * |   |   |   |
     * |   |   |   |
     * *---*---*---*
     * |   |   |   |
     * |   |   |   |
     * *---*---*---*
     * |   |   |   |
     * |   |   |   |
     * *---*---*---*
     *
     * Corners will render one to one
     * Edges will be stretched on their axis
     * Middle will be expanded in both axis (must be solid color)
     *
     *
     * @param U The texture U location
     * @param V The texture V location
     * @param size The cell size
     * @param texture The texture location
     */
    public NinePatchRenderer(int U, int V, int size, ResourceLocation texture) {
        u = U;
        v = V;
        cellSize = size;
        patchLocation = texture;
    }

    /**
     * Partial Rendering Code
     *
     * This can be overwritten in a new INSTANCE of the class to disable certain parts from rendering or to give them a
     * different behavior. One INSTANCE would be for a tab, you can prevent the left edge from rendering in that way
     */

    // Corners
    protected void renderTopLeftCorner(PoseStack matrixStack, Screen gui) {
        gui.blit(matrixStack, 0, 0, u, v, cellSize, cellSize);
    }

    protected void renderTopRightCorner(PoseStack matrixStack, Screen gui, int width) {
        gui.blit(matrixStack, width - cellSize, 0, u + cellSize + cellSize, v, cellSize, cellSize);
    }

    protected void renderBottomLeftCorner(PoseStack matrixStack, Screen gui, int height) {
        gui.blit(matrixStack, 0, height - cellSize, u, v + cellSize + cellSize, cellSize, cellSize);
    }

    protected void renderBottomRightCorner(PoseStack matrixStack, Screen gui, int width, int height) {
        gui.blit(matrixStack, width - cellSize, height - cellSize, u + cellSize + cellSize, v + cellSize + cellSize, cellSize, cellSize);
    }

    // Edges
    protected void renderTopEdge(PoseStack matrixStack, Screen gui, int width) {
        matrixStack.pushPose();
        matrixStack.translate(cellSize, 0, 0);
        matrixStack.scale(width - (cellSize * 2), 1, 0);
        gui.blit(matrixStack, 0, 0, u + cellSize, v, 1, cellSize);
        matrixStack.popPose();
    }

    protected void renderBottomEdge(PoseStack matrixStack, Screen gui, int width, int height) {
        matrixStack.pushPose();
        matrixStack.translate(cellSize, height - cellSize, 0);
        matrixStack.scale(width - (cellSize * 2), 1, 0);
        gui.blit(matrixStack, 0, 0, u + cellSize, v + cellSize + cellSize, 1, cellSize);
        matrixStack.popPose();
    }

    protected void renderLeftEdge(PoseStack matrixStack, Screen gui, int height) {
        matrixStack.pushPose();
        matrixStack.translate(0, cellSize, 0);
        matrixStack.scale(1, height - (cellSize * 2), 0);
        gui.blit(matrixStack, 0, 0, u, v + cellSize, cellSize, 1);
        matrixStack.popPose();
    }

    protected void renderRightEdge(PoseStack matrixStack, Screen gui, int width, int height) {
        matrixStack.pushPose();
        matrixStack.translate(width - cellSize, cellSize, 0);
        matrixStack.scale(1, height - (cellSize * 2), 0);
        gui.blit(matrixStack, 0, 0, u + cellSize + cellSize, v + cellSize, cellSize, 1);
        matrixStack.popPose();
    }

    // Background
    protected void renderBackground(PoseStack matrixStack, Screen gui, int width, int height) {
        matrixStack.pushPose();
        matrixStack.translate(cellSize - 1, cellSize - 1, 0);
        matrixStack.scale(width - (cellSize * 2) + 2, height - (cellSize * 2) + 2, 0);
        gui.blit(matrixStack, 0, 0, u + cellSize, v + cellSize, 1, 1);
        matrixStack.popPose();
    }

    public void render(PoseStack matrixStack, Screen gui, int x, int y, int width, int height) {
        render(matrixStack, gui, x, y, width, height, null);
    }

    /**
     * Main render call. This must be called in the parent gui to render the box.
     *
     * WARNING: Will bind texture to sheet, make sure you rebind afterwards or do this first
     *
     * @param gui The screen being rendered onto
     * @param x Screen X Position
     * @param y Screen Y Position
     * @param width Width
     * @param height Height
     * @param color Color to render
     */
    public void render(PoseStack matrixStack, Screen gui, int x, int y, int width, int height, Color color) {
        matrixStack.pushPose();
        if (color != null)
            RenderUtils.setColor(color);
        if (patchLocation != null)
            RenderUtils.bindTexture(patchLocation);
        matrixStack.translate(x, y, 0);
        renderBackground(matrixStack, gui, width, height);
        renderTopEdge(matrixStack, gui, width);
        renderBottomEdge(matrixStack, gui, width, height);
        renderRightEdge(matrixStack, gui, width, height);
        renderLeftEdge(matrixStack, gui, height);
        renderTopLeftCorner(matrixStack, gui);
        renderTopRightCorner(matrixStack, gui, width);
        renderBottomLeftCorner(matrixStack, gui, height);
        renderBottomRightCorner(matrixStack, gui, width, height);
        matrixStack.popPose();
    }
}
