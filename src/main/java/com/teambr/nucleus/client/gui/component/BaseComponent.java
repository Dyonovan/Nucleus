package com.teambr.nucleus.client.gui.component;

import com.teambr.nucleus.client.gui.GuiBase;
import com.teambr.nucleus.client.gui.component.listeners.IKeyboardListener;
import com.teambr.nucleus.client.gui.component.listeners.IMouseEventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
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
public abstract class BaseComponent extends Screen {
    // Variables
    protected int xPos, yPos;
    protected GuiBase<?> parent;

    protected List<String> toolTip = new ArrayList<>();

    protected IMouseEventListener mouseEventListener;
    protected IKeyboardListener   keyboardEventListener;

    protected FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

    public BaseComponent(GuiBase<?> parentGui,int x, int y) {
        this(parentGui, new StringTextComponent("neotech:component"), x, y);
    }

    /**
     * Main constructor for all components
     * @param parentGui The parent Gui
     * @param x The x position
     * @param y The y position
     */
    public BaseComponent(GuiBase<?> parentGui, ITextComponent titleIn, int x, int y) {
        super(titleIn);
        parent = parentGui;
        xPos = x;
        yPos = y;
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called to render the component
     */
    public abstract void render(int guiLeft, int guiTop, int mouseX, int mouseY);

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    public abstract void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY);

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    public abstract int getWidth();

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    public abstract int getHeight();

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Used to determine if a dynamic tooltip is needed at runtime
     * @param mouseX Mouse X Pos
     * @param mouseY Mouse Y Pos
     * @return A list of string to display
     */
    @Nullable
    public List<String> getDynamicToolTip(int mouseX, int mouseY) {
        return null;
    }

    /**
     * Render the tooltip if you can
     *
     * @param mouseX Mouse X
     * @param mouseY Mouse Y
     */
    public void renderToolTip(int mouseX, int mouseY) {
        if(toolTip != null && !toolTip.isEmpty())
            renderTooltip(toolTip, mouseX, mouseY, Minecraft.getInstance().fontRenderer);
        else if(getDynamicToolTip(mouseX, mouseY) != null)
            renderTooltip(getDynamicToolTip(mouseX, mouseY), mouseX, mouseY, Minecraft.getInstance().fontRenderer);

    }

    /**
     * Used to get what area is being displayed, mainly used for JEI
     */
    public Rectangle getArea(int guiLeft, int guiTop) {
        return new Rectangle(xPos + guiLeft, yPos + guiTop, getWidth(), getHeight());
    }

    /*******************************************************************************************************************
     * Input Methods                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is pressed
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDown(double x, double y, int button) {
        if(mouseEventListener != null)
            mouseEventListener.onMouseDown(this, x, y, button);
    }

    /**
     * Called when the mouse button is over the component and released
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseUp(double x, double y, int button) {
        if(mouseEventListener != null)
            mouseEventListener.onMouseUp(this, x, y, button);
    }

    /**
     * Called when the user drags the component
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDrag(double x, double y, int button, double xDragAmount, double yDragAmount) {
        if(mouseEventListener != null)
            mouseEventListener.onMouseDrag(this, x, y, button, xDragAmount, yDragAmount);
    }

    /**
     * Called when the mouse is scrolled
     * @param dir 1 for positive, -1 for negative
     */
    public void mouseScrolled(int dir) {}

    /**
     * Used to check if the mouse if currently over the component
     *
     * You must have the getWidth() and getHeight() functions defined for this to work properly
     *
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @return True if mouse if over the component
     */
    public boolean isMouseOver(int mouseX, int mouseY) {
        return  mouseX >= xPos && mouseX < xPos + getWidth() && mouseY >= yPos && mouseY < yPos + getHeight();
    }

    /**
     * Used when a key is pressed
     *
     * @param letter The letter
     * @param keyCode The code
     */
    public void keyTyped(char letter, int keyCode) {
        if(keyboardEventListener != null)
            keyboardEventListener.charTyped(this, letter, keyCode);
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public GuiBase getParent() {
        return parent;
    }

    public void setParent(GuiBase parent) {
        this.parent = parent;
    }

    public List<String> getToolTip() {
        return toolTip;
    }

    public void setToolTip(List<String> toolTip) {
        this.toolTip = toolTip;
    }

    public IMouseEventListener getMouseEventListener() {
        return mouseEventListener;
    }

    public void setMouseEventListener(IMouseEventListener mouseEventListener) {
        this.mouseEventListener = mouseEventListener;
    }

    public IKeyboardListener getKeyboardEventListener() {
        return keyboardEventListener;
    }

    public void setKeyboardEventListener(IKeyboardListener keyboardEventListener) {
        this.keyboardEventListener = keyboardEventListener;
    }
}
