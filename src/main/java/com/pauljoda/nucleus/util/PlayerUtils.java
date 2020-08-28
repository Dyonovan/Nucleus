package com.pauljoda.nucleus.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

/**
 * This file was created for Nucleus
 *
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/14/2017
 */
public class PlayerUtils {

    /**
     * Checks if the player is holding the given item in either hand
     * @param player The player entity
     * @param item The item to check
     * @return True if either hand contains the item
     */
    public static boolean isPlayerHoldingEither(PlayerEntity player, Item item) {
        return !(player == null || item == null) &&
                ((!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == item) ||
                        (!player.getHeldItemOffhand().isEmpty() && player.getHeldItemOffhand().getItem() == item));
    }

    /**
     * Gets what hand this item is in, this does an object match so you must send the object, not match by values
     * @param stack The object
     * @return What hand its in
     */
    public static Hand getHandStackIsIn(PlayerEntity player, ItemStack stack) {
        if(player == null || stack.isEmpty())
            return Hand.MAIN_HAND;

        if(!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().equals(stack))
            return Hand.MAIN_HAND;
        else
            return Hand.OFF_HAND;
    }
}
