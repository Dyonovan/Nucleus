package com.teambr.nucleus.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.lwjgl.glfw.GLFW;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
public class ClientUtils {

    /**
     * Used to translate the text to a given language
     *
     * @param text The text to translate
     * @return The translated text
     */
    public static String translate(String text) {
        return I18n.format(text);
    }

    /**
     * Used to translate a number to a standard format based on Locale
     *
     * @param number Number to format
     * @return A formated number string, eg 1,000,000
     */
    public static String formatNumber(double number) {
        return NumberFormat.getNumberInstance(Locale.forLanguageTag(Minecraft.getInstance().gameSettings.language)).format(number);
    }

    /**
     * Get if key is currently pressed
     * @param key {@link GLFW} key that is pressed
     * @return True if pressed
     */
    public static boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), key) == GLFW.GLFW_PRESS;
    }

    /**
     * Checks for CTRL key, Macs use Command so this will enable that key as well
     *
     * @return True if CTRL is pressed
     */
    public static boolean isCtrlPressed() {
        return isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL) || isKeyPressed(GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    /**
     * Checks for the Shift key pressed
     *
     * @return True if pressed
     */
    public static boolean isShiftPressed() {
        return isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT) || isKeyPressed(GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    /**
     * Convert a string into a list of strings with clip to max length
     * @param inputString Long string to trim
     * @param maxWidth Max length of string
     * @return Clipped list of strings
     */
    public List<String> wrapStringToLength(String inputString, int maxWidth) {
        ArrayList<String> formattedList = new ArrayList<>();

        int fullWidth = inputString.length();
        if(fullWidth > maxWidth) {
            for(int position = 0; position < inputString.length(); position += maxWidth)
                formattedList.add(inputString.substring(position, Math.min(inputString.length(), position + maxWidth)));
        } else {
            formattedList.add(inputString);
        }

        return formattedList;
    }
}
