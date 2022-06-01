package com.pauljoda.nucleus.client.gui.component.display;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pauljoda.nucleus.client.gui.GuiBase;
import com.pauljoda.nucleus.util.RenderUtils;

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
public abstract class GuiComponentTextureAnimated extends GuiComponentTexture {
    // Variables
    protected ANIMATION_DIRECTION animationDirection;

    /**
     * Tells the component which way to render the texture
     */
    public enum ANIMATION_DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Creates a textured area
     *
     * @param parent      The parent GUI
     * @param x           The x pos
     * @param y           The y pos
     * @param texU        The texture u
     * @param texV        The texture v
     * @param imageWidth  The image width
     * @param imageHeight The image height
     */
    public GuiComponentTextureAnimated(GuiBase<?> parent, int x, int y, int texU, int texV, int imageWidth, int imageHeight, ANIMATION_DIRECTION dir) {
        super(parent, x, y, texU, texV, imageWidth, imageHeight);
        this.animationDirection = dir;
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Get the current scale, scaled to the width
     * @param scale What to scale to
     * @return How far along 0-scale in current animation
     */
    protected abstract int getCurrentProgress(int scale);

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called to render the component
     */
    @Override
    public void render(PoseStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        matrixStack.pushPose();
        matrixStack.translate(xPos, yPos, 0);
        RenderUtils.bindTexture(parent.textureLocation);

        switch (animationDirection) {
            case RIGHT:
                int progressRight = Math.min(width, getCurrentProgress(width));
                blit(matrixStack, 0, 0, u, v, progressRight, height);
                break;
            case DOWN:
                int progressDown = Math.min(height, getCurrentProgress(height));
                blit(matrixStack, 0, 0, u, v, width, progressDown);
                break;
            case LEFT:
                int progressLeft = Math.min(width, getCurrentProgress(width));
                blit(matrixStack, -width + progressLeft, 0, u, v, progressLeft, height);
                break;
            case UP:
                int progressUp = Math.min(height, getCurrentProgress(height));
                blit(matrixStack, 0, height - progressUp, u, v + height - progressUp, width, progressUp);
                break;
        }
        matrixStack.popPose();
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public ANIMATION_DIRECTION getAnimationDirection() {
        return animationDirection;
    }

    public void setAnimationDirection(ANIMATION_DIRECTION animationDirection) {
        this.animationDirection = animationDirection;
    }
}
