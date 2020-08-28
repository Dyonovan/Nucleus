package com.pauljoda.nucleus.client.gui.component.control;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.pauljoda.nucleus.helper.GuiHelper;
import com.pauljoda.nucleus.util.ClientUtils;
import com.pauljoda.nucleus.client.gui.GuiBase;
import com.pauljoda.nucleus.client.gui.component.BaseComponent;
import com.pauljoda.nucleus.util.RenderUtils;

import javax.annotation.Nullable;
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
public abstract class GuiComponentButton extends BaseComponent {
    //Variables
    protected int u, v, width, height;
    protected String label;

    private boolean isOver = false;

    /**
     * Constructor for the button component
     *
     * In your texture, you should put the hovered over texture directly below the main texture passed
     *
     * @param parent The parent gui
     * @param xPos The x position
     * @param yPos The y position
     * @param uPos The texture u position
     * @param vPos The texture v position
     * @param w The width
     * @param h The height
     * @param text The text to display, translated
     */
    public GuiComponentButton(GuiBase<?> parent, int xPos, int yPos, int uPos, int vPos, int w, int h, @Nullable String text) {
        super(parent, xPos, yPos);
        u = uPos;
        v = vPos;
        width = w;
        height = h;
        if(text != null)
            label = ClientUtils.translate(text);
        else
            label = null;
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called when button is pressed
     */
    protected abstract void doAction();

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

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

    /**
     * Called when the mouse is pressed
     *
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public void mouseDown(double mouseX, double mouseY, int button) {
        if(mouseX >= xPos && mouseX < xPos + width && mouseY >= yPos && mouseY < yPos + height) {
            GuiHelper.playButtonSound();
            doAction();
        }
    }

    /**
     * Used to enable the hovered over texture
     */
    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        if(mouseX >= xPos && mouseX < xPos + width && mouseY >= yPos && mouseY < yPos + height) {
            isOver = true;
            return true;
        }
        isOver = false;
        return false;
    }

    /**
     * Called to render the component
     */
    @Override
    public void render(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        RenderUtils.prepareRenderState();
        RenderUtils.bindTexture(parent.textureLocation);
        GlStateManager.translated(xPos, yPos, 0);
        blit(matrixStack, 0, 0, u, isOver ? v + height : v, width, height);
        RenderUtils.restoreRenderState();
        GlStateManager.popMatrix();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        if(label != null) {
            GlStateManager.pushMatrix();
            RenderUtils.prepareRenderState();
            RenderUtils.restoreColor();
            float size = fontRenderer.getStringWidth(label);
            GlStateManager.translated(xPos + (width / 2F - size / 2F), yPos + 6, 0);
            fontRenderer.drawString(matrixStack, label, 0, 0, Color.darkGray.getRGB());
            RenderUtils.restoreColor();
            RenderUtils.restoreRenderState();
            GlStateManager.popMatrix();
        }
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
