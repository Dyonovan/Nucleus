package com.pauljoda.nucleus.client.gui.widget.control;

import com.pauljoda.nucleus.client.gui.MenuBase;
import com.pauljoda.nucleus.client.gui.widget.BaseWidget;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

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
public abstract class MenuWidgetTextBox extends BaseWidget {
    // Variables
    protected int width, height;
    protected EditBox textField;

    /**
     * Creates the text box
     *
     * @param parent       The parent gui
     * @param x            The x pos
     * @param y            The y pos
     * @param boxWidth     The text box width
     * @param boxHeight    The text box height, usually 16
     * @param defaultLabel The default label, will translate, can be null
     */
    public MenuWidgetTextBox(MenuBase<?> parent, int x, int y, int boxWidth, int boxHeight, @Nullable
    Component defaultLabel) {
        super(parent, x, y);
        this.width = boxWidth;
        this.height = boxHeight;

        textField = new EditBox(fontRenderer, x, y, width, height, defaultLabel == null ? (Component) Component.EMPTY : defaultLabel);
        textField.setValue(ClientUtils.translate(defaultLabel.getString()));
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called when the value in the text box changes
     *
     * @param value The current value
     */
    protected abstract void fieldUpdated(String value);

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
        textField.mouseClicked(x, y, button);
        if (button == 1 && textField.isFocused()) {
            textField.setValue("");
            fieldUpdated(textField.getValue());
        }
        return false;
    }

    /**
     * Used when a key is pressed
     *
     * @param letter  The letter
     * @param keyCode The code
     */
    @Override
    public boolean charTyped(char letter, int keyCode) {
        if (textField.isFocused()) {
            textField.charTyped(letter, keyCode);
            fieldUpdated(textField.getValue());
        }
        return false;
    }


    /**
     * Called to render the component
     */
    @Override
    public void render(GuiGraphics graphics, int guiLeft, int guiTop, int mouseX, int mouseY) {
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        textField.render(graphics, mouseX, mouseY, Minecraft.getInstance().getDeltaFrameTime());
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        matrixStack.popPose();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(GuiGraphics graphics, int guiLeft, int guiTop, int mouseX, int mouseY) {
        // NO OP
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

    public EditBox getTextField() {
        return textField;
    }

    public void setTextField(EditBox textField) {
        this.textField = textField;
    }
}
