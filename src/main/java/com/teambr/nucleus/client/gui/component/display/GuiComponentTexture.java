package com.teambr.nucleus.client.gui.component.display;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teambr.nucleus.client.gui.GuiBase;
import com.teambr.nucleus.client.gui.component.BaseComponent;
import com.teambr.nucleus.util.RenderUtils;

/**
 * This file was created for Nucleus
 *
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public class GuiComponentTexture extends BaseComponent {
    protected int u, v, width, height;

    /**
     * Creates a textured area
     * @param parent The parent GUI
     * @param x The x pos
     * @param y The y pos
     * @param texU The texture u
     * @param texV The texture v
     * @param imageWidth The image width
     * @param imageHeight The image height
     */
    public GuiComponentTexture(GuiBase<?> parent, int x, int y, int texU, int texV, int imageWidth, int imageHeight) {
        super(parent, x, y);
        this.u = texU;
        this.v = texV;
        this.width = imageWidth;
        this.height = imageHeight;
    }

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called to render the component
     */
    @Override
    public void render(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        matrixStack.push();
        matrixStack.translate(xPos, yPos, 0);
        RenderUtils.bindTexture(parent.textureLocation);
        blit(matrixStack, 0, 0, u, v, width, height);
        matrixStack.pop();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        // No Op
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    @Override
    public int getHeight() {
        return height;
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
