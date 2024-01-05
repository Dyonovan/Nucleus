package com.pauljoda.nucleus.client.gui.widget.display;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.pauljoda.nucleus.client.gui.MenuBase;
import com.pauljoda.nucleus.client.gui.widget.BaseWidget;
import com.pauljoda.nucleus.util.RenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public class MenuWidgetColoredZone extends BaseWidget {
    // Variables
    protected int width, height;
    protected Color color;

    /***
     * Creates the colored zone
     * @param parent The parent GUI
     * @param x The x pos
     * @param y The y pos
     * @param w The width
     * @param h The height
     * @param color The color
     */
    public MenuWidgetColoredZone(MenuBase<?> parent, int x, int y, int w, int h, Color color) {
        super(parent, x, y);
        this.width = w;
        this.height = h;
        this.color = color;
    }

    /**
     * Override this to change the color
     *
     * @return The color, by default the passed color
     */
    protected Color getDynamicColor() {
        return color;
    }

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called to render the component
     */
    @Override
    public void render(GuiGraphics graphics, int guiLeft, int guiTop, int mouseX, int mouseY) {
        color = getDynamicColor();
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableBlend();
        //RenderSystem.enableAlphaTest();
        RenderSystem.disableDepthTest();
        matrixStack.translate(xPos, yPos, 10);
        RenderUtils.setColor(color);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(0, 0, 0);
        GL11.glVertex3d(0, height, 0);
        GL11.glVertex3d(width, height, 0);
        GL11.glVertex3d(width, 0, 0);
        GL11.glEnd();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        matrixStack.popPose();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(GuiGraphics graphics, int guiLeft, int guiTop, int mouseX, int mouseY) {
        // Op OP, we want bars and stuff to render on top of this
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
