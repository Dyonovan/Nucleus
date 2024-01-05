package com.pauljoda.nucleus.client.gui.widget;

import com.pauljoda.nucleus.util.RenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

/**
 * This file was created for Nucleus
 * <p>
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
     * <p>
     * Texture must be in the following pattern. Cell size is how many pixels each box is (relative to x256)
     * <p>
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
     * <p>
     * Corners will render one to one
     * Edges will be stretched on their axis
     * Middle will be expanded in both axis (must be solid color)
     *
     * @param U       The texture U location
     * @param V       The texture V location
     * @param size    The cell size
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
     * <p>
     * This can be overwritten in a new INSTANCE of the class to disable certain parts from rendering or to give them a
     * different behavior. One INSTANCE would be for a tab, you can prevent the left edge from rendering in that way
     */

    // Corners
    protected void renderTopLeftCorner(GuiGraphics graphics) {
        graphics.blit(patchLocation, 0, 0, u, v, cellSize, cellSize);
    }

    protected void renderTopRightCorner(GuiGraphics graphics, int width) {
        graphics.blit(patchLocation, width - cellSize, 0, u + cellSize + cellSize, v, cellSize, cellSize);
    }

    protected void renderBottomLeftCorner(GuiGraphics graphics, int height) {
        graphics.blit(patchLocation, 0, height - cellSize, u, v + cellSize + cellSize, cellSize, cellSize);
    }

    protected void renderBottomRightCorner(GuiGraphics graphics, int width, int height) {
        graphics.blit(patchLocation, width - cellSize, height - cellSize, u + cellSize + cellSize, v + cellSize + cellSize, cellSize, cellSize);
    }

    // Edges
    protected void renderTopEdge(GuiGraphics graphics, int width) {
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(cellSize, 0, 0);
        matrixStack.scale(width - (cellSize * 2), 1, 0);
        graphics.blit(patchLocation, 0, 0, u + cellSize, v, 1, cellSize);
        matrixStack.popPose();
    }

    protected void renderBottomEdge(GuiGraphics graphics, int width, int height) {
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(cellSize, height - cellSize, 0);
        matrixStack.scale(width - (cellSize * 2), 1, 0);
        graphics.blit(patchLocation, 0, 0, u + cellSize, v + cellSize + cellSize, 1, cellSize);
        matrixStack.popPose();
    }

    protected void renderLeftEdge(GuiGraphics graphics, int height) {
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(0, cellSize, 0);
        matrixStack.scale(1, height - (cellSize * 2), 0);
        graphics.blit(patchLocation, 0, 0, u, v + cellSize, cellSize, 1);
        matrixStack.popPose();
    }

    protected void renderRightEdge(GuiGraphics graphics, int width, int height) {
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(width - cellSize, cellSize, 0);
        matrixStack.scale(1, height - (cellSize * 2), 0);
        graphics.blit(patchLocation, 0, 0, u + cellSize + cellSize, v + cellSize, cellSize, 1);
        matrixStack.popPose();
    }

    // Background
    protected void renderBackground(GuiGraphics graphics, int width, int height) {
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(cellSize - 1, cellSize - 1, 0);
        matrixStack.scale(width - (cellSize * 2) + 2, height - (cellSize * 2) + 2, 0);
        graphics.blit(patchLocation, 0, 0, u + cellSize, v + cellSize, 1, 1);
        matrixStack.popPose();
    }

    public void render(GuiGraphics graphics, int x, int y, int width, int height) {
        render(graphics, x, y, width, height, null);
    }

    /**
     * Main render call. This must be called in the parent gui to render the box.
     * <p>
     * WARNING: Will bind texture to sheet, make sure you rebind afterwards or do this first
     *
     * @param x      Screen X Position
     * @param y      Screen Y Position
     * @param width  Width
     * @param height Height
     * @param color  Color to render
     */
    public void render(GuiGraphics graphics, int x, int y, int width, int height, Color color) {
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        if (color != null)
            RenderUtils.setColor(color);
        if (patchLocation != null)
            RenderUtils.bindTexture(patchLocation);
        matrixStack.translate(x, y, 0);
        renderBackground(graphics, width, height);
        renderTopEdge(graphics, width);
        renderBottomEdge(graphics, width, height);
        renderRightEdge(graphics, width, height);
        renderLeftEdge(graphics, height);
        renderTopLeftCorner(graphics);
        renderTopRightCorner(graphics, width);
        renderBottomLeftCorner(graphics, height);
        renderBottomRightCorner(graphics, width, height);
        matrixStack.popPose();
    }
}
