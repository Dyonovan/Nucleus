package com.pauljoda.nucleus.client.gui.component.control;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pauljoda.nucleus.client.gui.GuiBase;
import com.pauljoda.nucleus.client.gui.component.BaseComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

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
@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public abstract class GuiComponentScrollBar extends BaseComponent {
    // Variables
    protected int nubU, nubV, height, maxRange;
    protected int currentPosition = 0;
    protected boolean isMoving = false;

    /**
     * Creates a scroll bar
     * <p>
     * IMPORTANT: Scroll bar NUB for selected must be to the right of the normal one
     * NUB should be 12x15 pixels
     *
     * @param parent The parent GUI
     * @param x      The x pos
     * @param y      The y pos
     * @param nU     The nub texture u
     * @param nV     The nub texture v
     * @param height The height of this scroll bar
     */
    public GuiComponentScrollBar(GuiBase<?> parent, int x, int y, int nU, int nV, int height) {
        super(parent, x, y);
        nubU = nU;
        nubV = nV;
        this.height = height;
        maxRange = height - 17;
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called when the scroll box has moved.
     *
     * @param position The position, 0 - 1 of how far along it is
     */
    protected abstract void onScroll(float position);

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is pressed
     *
     * @param x      Mouse X Position
     * @param y      Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public boolean mouseClicked(double x, double y, int button) {
        isMoving = true;
        currentPosition = (int) ((y - yPos) - 7);
        if (currentPosition > maxRange)
            currentPosition = maxRange;
        else if (currentPosition < 0)
            currentPosition = 0;
        onScroll(currentPosition / maxRange);
        return false;
    }

    /**
     * Called when the user drags the component
     *
     * @param x      Mouse X Position
     * @param y      Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public boolean mouseDragged(double x, double y, int button, double xAmount, double yAmount) {
        currentPosition = (int) ((y - yPos) - 7);
        if (currentPosition > maxRange)
            currentPosition = maxRange;
        else if (currentPosition < 0)
            currentPosition = 0;
        onScroll(currentPosition / maxRange);
        return false;
    }

    /**
     * Called when the mouse button is over the component and released
     *
     * @param x      Mouse X Position
     * @param y      Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public boolean mouseReleased(double x, double y, int button) {
        isMoving = false;
        onScroll(currentPosition / maxRange);
        return false;
    }

    /**
     * Called to render the component
     */
    @Override
    public void render(GuiGraphics graphics, int guiLeft, int guiTop, int mouseX, int mouseY) {
        var matrixStack = graphics.pose();

        if (currentPosition > maxRange)
            currentPosition = maxRange;

        matrixStack.pushPose();
        matrixStack.translate(xPos + 1, yPos + currentPosition + 1, 0);
        if (isMoving && !Minecraft.getInstance().mouseHandler.isLeftPressed())
            isMoving = false;
        graphics.blit(parent.textureLocation, 0, 0, isMoving ? nubU + 12 : nubU, nubV, 12, 15);
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(GuiGraphics graphics, int guiLeft, int guiTop, int mouseX, int mouseY) {
        // No Op
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return 14;
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

    public int getNubU() {
        return nubU;
    }

    public void setNubU(int nubU) {
        this.nubU = nubU;
    }

    public int getNubV() {
        return nubV;
    }

    public void setNubV(int nubV) {
        this.nubV = nubV;
    }

    public void setHeight(int height) {
        maxRange = height - 17;
        this.height = height;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setCurrentPosition(float currentPosition) {
        this.currentPosition = (int) (maxRange * currentPosition);
    }
}
