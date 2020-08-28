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
public interface IKeyboardListener {

    /**
     * Called when the keyboard is pressed
     * @param component The component getting the input
     * @param character The character pressed
     * @param keyCode The key code
     */
    void charTyped(BaseComponent component, char character, int keyCode);
}
