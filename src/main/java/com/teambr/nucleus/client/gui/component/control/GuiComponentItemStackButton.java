package com.teambr.nucleus.client.gui.component.control;

import com.mojang.blaze3d.platform.GlStateManager;
import com.teambr.nucleus.client.gui.GuiBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

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
public abstract class GuiComponentItemStackButton extends GuiComponentButton {
    // Variables
    protected ItemStack displayStack;

    /**
     * Constructor for itemstack button
     * @param stack The stack to display
     */
    public GuiComponentItemStackButton(GuiBase<?> parent, int x, int y, int u, int v, int w, int h, ItemStack stack) {
        super(parent, x, y, u, v, w, h, null);
        displayStack = stack;
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
        super.renderOverlay(guiLeft, guiTop, mouseX, mouseY);
        GlStateManager.pushMatrix();
        GlStateManager.pushLightingAttributes();
        GlStateManager.translated(xPos, yPos, 1);

        RenderHelper.enableGUIStandardItemLighting();

        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(displayStack, (width / 2) - 8, (height / 2) - 8);

        RenderHelper.disableStandardItemLighting();

        GlStateManager.popAttributes();
        GlStateManager.popMatrix();
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public ItemStack getDisplayStack() {
        return displayStack;
    }

    public void setDisplayStack(ItemStack displayStack) {
        this.displayStack = displayStack;
    }
}
