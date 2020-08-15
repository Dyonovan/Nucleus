package com.teambr.nucleus.client.gui.component.display;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.teambr.nucleus.client.gui.GuiBase;
import com.teambr.nucleus.client.gui.component.BaseComponent;
import com.teambr.nucleus.client.gui.component.NinePatchRenderer;
import com.teambr.nucleus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.annotation.Nullable;
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
 * @since 2/13/2017
 */
public class GuiTab extends BaseComponent {
    // Class Variables
    protected static final int FOLDED_SIZE  = 24;

    // Variables
    protected int expandedWidth, expandedHeight, u, v;
    protected ItemStack stack;
    protected int currentWidth, currentHeight, dWidth, dHeight = FOLDED_SIZE;
    protected boolean isActive = false;

    protected List<BaseComponent> children;

    protected NinePatchRenderer tabRenderer;
    protected ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    /**
     * Creates a Gui Tab
     *
     * IMPORTANT: Texture should be a full nine patch renderer minus the left column of cells
     * See NinePatchRenderer construction for more info
     *
     * @param parent The parent GUI
     * @param x The x pos
     * @param y The y pos
     * @param u The texture u, this is the middle top cell for the nine patch renderer
     * @param v The texture v, this is the middle top cell for the nine patch renderer
     * @param exWidth The expanded width
     * @param exHeight The expanded height
     * @param displayStack The stack to display
     */
    public GuiTab(GuiBase<?> parent, int x, int y, int u, int v, int exWidth, int exHeight, @Nullable ItemStack displayStack) {
        super(parent, x, y);
        this.u = u;
        this.v = v;
        this.expandedWidth = exWidth;
        this.expandedHeight = exHeight;
        this.stack = displayStack;

        children = new ArrayList<>();
        tabRenderer = new NinePatchRenderer(u, v, 8, parent.textureLocation);
    }

    /**
     * Add a child to this tab
     * @param component The component to add
     * @return The tab, to enable chaining
     */
    public GuiTab addChild(BaseComponent component) {
        children.add(component);
        return this;
    }

    /**
     * Can the tab render the children
     *
     * @return True if expanded and can render
     */
    public boolean areChildrenActive() {
        return isActive && currentWidth == expandedWidth && currentHeight == expandedHeight;
    }

//    /**
//     * Moves the slots if need be
//     */
//    public void moveSlots() {
//        children.forEach((component -> {
//            if(component instanceof GuiComponentTabSlotHolder)
//                ((GuiComponentTabSlotHolder)component).moveSlots(areChildrenActive());
//        }));
//    }

    /**
     * Called when the mouse is pressed
     *
     * We are broken this out as GuiTabCollection will pass down
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public boolean mouseDownActivated(double x, double y, int button) {
        if(this.mouseEventListener != null)
            this.mouseEventListener.onMouseDown(this, x, y, button);
        if(areChildrenActive()) {
            for(BaseComponent component : children){
                if(component.isMouseOver(x, y)) {
                    component.mouseDown(x, y, button);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Called when the mouse button is over the component and released
     *
     * We are broken this out as GuiTabCollection will pass down
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public boolean mouseUpActivated(double x, double y, int button) {
        if(areChildrenActive()) {
            for(BaseComponent component : children){
                if(component.isMouseOver(x, y)) {
                    component.mouseUp(x, y, button);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Called when the user drags the component
     *
     * We are broken this out as GuiTabCollection will pass down
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public boolean mouseDragActivated(double x, double y, int button, double xAmount, double yAmount) {
        if(areChildrenActive()) {
            for(BaseComponent component : children){
                if(component.isMouseOver(x, y)) {
                    component.mouseDrag(x, y, button, xAmount, yAmount);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Called by the GuiTabCollection when the mouse scrolls
     * @param dir The scroll dir
     */
    public void mouseScrolledTab(int dir) {
        if(areChildrenActive()) {
            children.forEach((component -> component.mouseScrolled(dir)));
        }
    }

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Used when a key is pressed
     *
     * @param letter The letter
     * @param keyCode The code
     */
    @Override
    public void keyTyped(char letter, int keyCode) {
        children.forEach((component -> component.keyTyped(letter, keyCode)));
    }

    /**
     * Render the tooltip if you can
     *
     * @param mouseX Mouse X
     * @param mouseY Mouse Y
     */
    @Override
    public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
        if(areChildrenActive()) {
            children.forEach((component -> {
                if(component.isMouseOver(mouseX - xPos - parent.getGuiLeft(), mouseY - yPos - parent.getGuiTop()))
                    component.renderToolTip(matrixStack, mouseX, mouseY);
            }));
        } else
            super.renderToolTip(matrixStack, mouseX, mouseY);
    }

    /**
     * Called to render the component
     */
    @Override
    public void render(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();

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
        tabRenderer.render(matrixStack, this, 0, 0, currentWidth, currentHeight);

        // Render the stack, if available
        RenderUtils.restoreColor();
        if(stack != null) {
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            itemRenderer.renderItemAndEffectIntoGUI(stack, 4, 3);
            RenderUtils.restoreColor();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

        // Render the children
        if(areChildrenActive()) {
            children.forEach((component -> {
                RenderUtils.prepareRenderState();
                component.render(matrixStack,0, 0, mouseX, mouseY);
                RenderUtils.restoreColor();
                RenderUtils.restoreRenderState();
            }));
        }

        GlStateManager.popMatrix();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        // Render the children
        if(areChildrenActive()) {
            children.forEach((component -> {
                RenderUtils.prepareRenderState();
                component.renderOverlay(matrixStack,0, 0, mouseX, mouseY);
                RenderUtils.restoreColor();
                RenderUtils.restoreRenderState();
            }));
        }
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return currentWidth;
    }

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    @Override
    public int getHeight() {
        return currentHeight;
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

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public List<BaseComponent> getChildren() {
        return children;
    }

    public void setChildren(List<BaseComponent> children) {
        this.children = children;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
