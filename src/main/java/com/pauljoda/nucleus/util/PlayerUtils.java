package com.pauljoda.nucleus.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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
    public static boolean isPlayerHoldingEither(Player player, Item item) {
        return !(player == null || item == null) &&
                ((!player.getUseItem().isEmpty() && player.getOffhandItem().getItem() == item) ||
                        (!player.getUseItem().isEmpty() && player.getOffhandItem().getItem() == item));
    }

    /**
     * Gets what hand this item is in, this does an object match so you must send the object, not match by values
     * @param stack The object
     * @return What hand its in
     */
    public static InteractionHand getHandStackIsIn(Player player, ItemStack stack) {
        if(player == null || stack.isEmpty())
            return InteractionHand.MAIN_HAND;

        if(!player.getUseItem().isEmpty() && player.getOffhandItem().equals(stack))
            return InteractionHand.MAIN_HAND;
        else
            return InteractionHand.OFF_HAND;
    }
}
