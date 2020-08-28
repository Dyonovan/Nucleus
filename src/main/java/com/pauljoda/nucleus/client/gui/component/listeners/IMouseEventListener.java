package com.pauljoda.nucleus.client.gui.component.listeners;

import com.pauljoda.nucleus.client.gui.component.BaseComponent;

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
public interface IMouseEventListener {

    /**
     * Called when the mouse clicks on the component
     * @param component The component to be clicked
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param button Which button was clicked
     */
    void onMouseDown(BaseComponent component, double mouseX, double mouseY, int button);

    /**
     * Called when the mouse releases the component
     * @param component The component to be clicked
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param button Which button was clicked
     */
    void onMouseUp(BaseComponent component, double mouseX, double mouseY, int button);

    /**
     * Called when the mouse drags an item
     * @param component The component to be clicked
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param button Which button was clicked
     */
    void onMouseDrag(BaseComponent component, double mouseX, double mouseY, int button, double xDragAmount, double yDragAmount);
}
