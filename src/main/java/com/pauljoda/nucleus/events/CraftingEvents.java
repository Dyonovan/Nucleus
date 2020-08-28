package com.pauljoda.nucleus.events;

import com.pauljoda.nucleus.common.ICraftingListener;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
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
        if((event.getCrafting().getItem() instanceof BlockItem &&
                Block.getBlockFromItem(event.getCrafting().getItem()) instanceof ICraftingListener) ||
                event.getCrafting().getItem() instanceof ICraftingListener) {
            ItemStack[] craftingList = new ItemStack[event.getInventory().getSizeInventory()];
            for(int x = 0; x < craftingList.length; x++)
                craftingList[x] = event.getInventory().getStackInSlot(x);

            if(!(event.getCrafting().getItem() instanceof BlockItem)) // Is a block class
                ((ICraftingListener)Block.getBlockFromItem(event.getCrafting().getItem())).onCrafted(craftingList, event.getCrafting());
            else // Is an item class
                ((ICraftingListener)event.getCrafting().getItem()).onCrafted(craftingList, event.getCrafting());
        }
    }
}
