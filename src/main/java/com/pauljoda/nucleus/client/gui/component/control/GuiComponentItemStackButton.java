package com.pauljoda.nucleus.client.gui.component.control;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pauljoda.nucleus.client.gui.GuiBase;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

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
    public void renderOverlay(PoseStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        super.renderOverlay(matrixStack, guiLeft, guiTop, mouseX, mouseY);
        matrixStack.pushPose();
        matrixStack.translate(xPos, yPos, 1);

        Minecraft.getInstance().getItemRenderer().renderGuiItem(displayStack, (width / 2) - 8, (height / 2) - 8);

        matrixStack.popPose();
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
