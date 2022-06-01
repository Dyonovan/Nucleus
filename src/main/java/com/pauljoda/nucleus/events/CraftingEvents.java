package com.pauljoda.nucleus.events;

import com.pauljoda.nucleus.common.ICraftingListener;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * This file was created for NeoTech
 *
 * NeoTech is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/20/2017
 */
public class CraftingEvents {
    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        if(event.getCrafting().getItem() instanceof ICraftingListener) {
            ItemStack[] craftingList = new ItemStack[event.getInventory().getContainerSize()];
            for(int x = 0; x < craftingList.length; x++)
                craftingList[x] = event.getInventory().getItem(x);
            ((ICraftingListener)event.getCrafting().getItem()).onCrafted(craftingList, event.getCrafting());
        }
    }
}
