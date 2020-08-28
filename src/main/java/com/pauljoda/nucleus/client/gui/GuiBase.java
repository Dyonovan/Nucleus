package com.pauljoda.nucleus.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.pauljoda.nucleus.client.gui.component.BaseComponent;
import com.pauljoda.nucleus.client.gui.component.display.GuiComponentText;
import com.pauljoda.nucleus.client.gui.component.display.GuiTabCollection;
import com.pauljoda.nucleus.util.ClientUtils;
import com.pauljoda.nucleus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
public abstract class GuiBase<T extends Container> extends ContainerScreen<T> {
    // Variables
    protected GuiComponentText titleComponent;

    public ResourceLocation textureLocation;

    protected List<BaseComponent> components = new ArrayList<>();
    protected GuiTabCollection rightTabs;
    protected GuiTabCollection leftTabs;

    /**
     * Main constructor for Guis
     * @param inventory The container
     * @param width The width of the gui
     * @param height The height of the gui
     * @param title The title of the gui
     * @param texture The location of the background texture
     */
    public GuiBase(T inventory, PlayerInventory playerInventory, ITextComponent title, int width, int height, ResourceLocation texture) {
        super(inventory, playerInventory, title);
        this.xSize = width;
        this.ySize = height;
        this.textureLocation = texture;

        rightTabs = new GuiTabCollection(this, xSize + 1);
        leftTabs = new GuiTabCollection(this, -1);

        titleComponent = new GuiComponentText(this,
                xSize / 2 - (Minecraft.getInstance().fontRenderer.getStringWidth(ClientUtils.translate(title.getString())) / 2),
                3, title.getString(), null);
        components.add(titleComponent);

        addComponents();
        addRightTabs(rightTabs);
        addLeftTabs(leftTabs);

        components.add(rightTabs);
        components.add(leftTabs);
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    protected abstract void addComponents();

    /*******************************************************************************************************************
     * GuiBase Methods                                                                                                 *
     *******************************************************************************************************************/

    /**
     * Adds the tabs to the right. Overwrite this if you want tabs on your GUI
     *
     * @param tabs List of tabs, put GuiTabs in
     */
    protected void addRightTabs(GuiTabCollection tabs) {}

    /**
     * Add the tabs to the left. Overwrite this if you want tabs on your GUI
     *
     * @param tabs List of tabs, put GuiReverseTabs in
     */
    protected void addLeftTabs(GuiTabCollection tabs) {}

    /*******************************************************************************************************************
     * Gui                                                                                                             *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is clicked
     *  @param mouseX The X Position
     * @param mouseY The Y Position
     * @param mouseButton The button pressed
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        components.forEach((baseComponent -> {
            if(baseComponent.isMouseOver(mouseX - guiLeft, mouseY - guiTop)) {
                baseComponent.mouseDown(mouseX - guiLeft, mouseY - guiTop, mouseButton);
            }
        }));
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when the mouse releases a button
     *
     * @param mouseX The X Position
     * @param mouseY The Y Position
     * @param state The button released
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        components.forEach((baseComponent -> {
            if(baseComponent.isMouseOver(mouseX - guiLeft, mouseY - guiTop)) {
                baseComponent.mouseUp(mouseX - guiLeft, mouseY - guiTop, state);
            }
        }));
        return super.mouseReleased(mouseX, mouseY, state);
    }

    /**
     * Used to track when the mouse is clicked and dragged
     *
     * @param mouseX The Current X Position
     * @param mouseY The Current Y Position
     * @param clickedMouseButton The button being dragged
     */
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int clickedMouseButton, double xDragAmount, double yDragAmount) {
        components.forEach((baseComponent -> {
            if(baseComponent.isMouseOver(mouseX - guiLeft, mouseY - guiTop)) {
                baseComponent.mouseDrag(mouseX - guiLeft, mouseY - guiTop, clickedMouseButton, xDragAmount, yDragAmount);
            }
        }));
        return super.mouseDragged(mouseX, mouseY, clickedMouseButton, xDragAmount, yDragAmount);
    }

    /**
     * Handle the mouse input
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double mouseDelta) {
        if(mouseDelta != 0)
            components.forEach((baseComponent -> baseComponent.mouseScrolled(mouseDelta > 0 ? 1 : -1)));
        return super.mouseScrolled(mouseX, mouseY, mouseDelta);
    }

    /**
     * Called when a key is typed
     *
     * @param typedChar The letter pressed, as a char
     * @param keyCode The Java key code
     */
    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        components.forEach((baseComponent -> baseComponent.keyTyped(typedChar, keyCode)));
        return super.charTyped(typedChar, keyCode);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderUtils.prepareRenderState();
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    /**
     * Used to draw above the background. This will be called after the background has been drawn
     *
     * Used mostly for adding text
     *
     * @param mouseX The mouse X Position
     * @param mouseY The mouse Y Position
     */
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        matrixStack.push();
        RenderUtils.prepareRenderState();
        RenderUtils.bindTexture(textureLocation);
        components.forEach((baseComponent -> {
            RenderUtils.prepareRenderState();
            baseComponent.renderOverlay(matrixStack, 0, 0, mouseX - guiLeft, mouseY - guiTop);
            RenderUtils.restoreRenderState();
            RenderUtils.restoreColor();
        }));
        RenderUtils.restoreRenderState();
        RenderSystem.enableAlphaTest();
        RenderHelper.enableStandardItemLighting();
        drawTopLayer(matrixStack, mouseX, mouseY);
        matrixStack.pop();
    }

    /**
     * Called to draw the background
     *
     * Usually used to create the base on which to render things
     *
     * @param partialTicks partial ticks
     * @param mouseX The mouse X
     * @param mouseY The mouse Y
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        matrixStack.push();
        RenderUtils.prepareRenderState();
        matrixStack.translate(guiLeft, guiTop, 0);

        RenderUtils.bindTexture(textureLocation);
        blit(matrixStack, 0, 0, 0, 0, xSize + 1, ySize + 1);

        components.forEach((baseComponent -> {
            RenderUtils.prepareRenderState();
            baseComponent.render(matrixStack, guiLeft, guiTop, mouseX - guiLeft, mouseY - guiTop);
            RenderUtils.restoreRenderState();
            RenderUtils.restoreColor();
        }));
        RenderUtils.restoreRenderState();
        RenderHelper.enableStandardItemLighting();
        matrixStack.pop();
    }

    /**
     * The main draw call. The super will handle calling the background and foreground layers. Then our extra code will run
     *
     * Used mainly to attach tool tips as they will always be on the top
     *
     * @param mouseX The Mouse X Position
     * @param mouseY The mouse Y Position
     */
    public void drawTopLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        components.forEach((baseComponent -> {
            if(baseComponent.isMouseOver(mouseX - guiLeft, mouseY - guiTop))
                baseComponent.renderToolTip(matrixStack, mouseX, mouseY);
        }));
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    /**
     * Used to get the guiLeft
     *
     * @return Where the gui starts
     */
    public int getGuiLeft() {
        return guiLeft;
    }

    /**
     * Used to get guiTop
     *
     * @return Where the gui starts
     */
    public int getGuiTop() {
        return guiTop;
    }

    /**
     * For some reason this isn't in vanilla
     *
     * @return The size of the gui
     */
    public int getXSize() {
        return xSize;
    }

    /**
     * For some reason this isn't in vanilla
     *
     * @return The size of the gui
     */
    public int getYSize() {
        return ySize;
    }

    /*******************************************************************************************************************
     * JEI                                                                                                             *
     *******************************************************************************************************************/

    /**
     * Returns a list of Rectangles that represent the areas covered by the GUI
     *
     * @return A list of covered areas
     */
    public List<Rectangle> getCoveredAreas() {
        List<Rectangle> areas = new ArrayList<>();
        areas.add(new Rectangle(guiLeft, guiTop, xSize, ySize));
        components.forEach((baseComponent -> {
            if(baseComponent instanceof GuiTabCollection) {
                GuiTabCollection tabCollection = (GuiTabCollection) baseComponent;
                areas.addAll(tabCollection.getAreasCovered(guiLeft, guiTop));
            } else
                areas.add(new Rectangle(baseComponent.getArea(guiLeft, guiTop)));
        }));
        return areas;
    }
}
