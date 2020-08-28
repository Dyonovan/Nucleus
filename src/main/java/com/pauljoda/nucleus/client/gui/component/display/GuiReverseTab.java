package com.pauljoda.nucleus.client.gui.component.display;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.pauljoda.nucleus.client.gui.GuiBase;
import com.pauljoda.nucleus.client.gui.component.NinePatchRenderer;
import com.pauljoda.nucleus.util.RenderUtils;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

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
public class GuiReverseTab extends GuiTab {

    /**
     * Creates a Gui Tab
     *
     * IMPORTANT: Texture should be a full nine patch renderer minus the right column of cells
     * See NinePatchRenderer construction for more info
     *
     * @param parent       The parent GUI
     * @param x            The x pos
     * @param y            The y pos
     * @param u            The texture u, this is the first cell for the nine patch renderer
     * @param v            The texture v, this is the first cell for the nine patch renderer
     * @param exWidth      The expanded width
     * @param exHeight     The expanded height
     * @param displayStack The stack to display
     */
    public GuiReverseTab(GuiBase<?> parent, int x, int y, int u, int v, int exWidth, int exHeight, @Nullable ItemStack displayStack) {
        super(parent, x, y, u, v, exWidth, exHeight, displayStack);
        tabRenderer = new NinePatchRenderer(u, v, 8, parent.textureLocation);
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

        // Set targets to stun
        double targetWidth  = isActive ? expandedWidth  : FOLDED_SIZE;
        double targetHeight = isActive ? expandedHeight : FOLDED_SIZE;

        // Move size
        if(currentWidth != targetWidth)
            dWidth += (targetWidth - dWidth);
        if(currentHeight != targetHeight)
            dHeight += (targetHeight - dHeight);

        // Set size
        currentWidth  = dWidth;
        currentHeight = dHeight;

        // Render the tab
        tabRenderer.render(matrixStack, this, -currentWidth, 0, currentWidth, currentHeight);

        // Render the stack, if available
        RenderUtils.restoreColor();
        if(stack != null) {
            RenderHelper.enableStandardItemLighting();
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0F, yPos, 0F);
            RenderUtils.restoreRenderState();
            itemRenderer.renderItemAndEffectIntoGUI(stack, guiLeft - 16, guiTop + 4);
            GlStateManager.popMatrix();
        }

        // Render the children
        if(areChildrenActive()) {
            matrixStack.translate(-expandedWidth, 0, 0);
            children.forEach((component -> {
                component.render(matrixStack, -expandedWidth, 0, mouseX, mouseY);
                RenderUtils.restoreColor();
            }));
        }

        matrixStack.pop();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        // Render the children
        if(areChildrenActive()) {
            matrixStack.translate(-expandedWidth, 0, 0);
            children.forEach((component -> {
                RenderUtils.prepareRenderState();
                component.renderOverlay(matrixStack, -expandedWidth, 0, mouseX, mouseY);
                RenderUtils.restoreColor();
                RenderUtils.restoreRenderState();
            }));
        }
    }

    /**
     * Used to check if the mouse if currently over the component
     *
     * You must have the getWidth() and getHeight() functions defined for this to work properly
     *
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @return True if mouse if over the component
     */
    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= xPos - currentWidth && mouseX < xPos && mouseY >= yPos && mouseY < yPos + getHeight();
    }
}
