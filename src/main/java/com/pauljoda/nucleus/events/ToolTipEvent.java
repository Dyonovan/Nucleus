package com.pauljoda.nucleus.events;

import com.pauljoda.nucleus.common.IToolTipProvider;
import net.minecraft.block.Block;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

/**
 * This file was created for Nucleus - Java
 *
 * Nucleus - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class ToolTipEvent {
    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent event) {
        IToolTipProvider itemWithTip = null;

        if(Block.getBlockFromItem(event.getItemStack().getItem()) instanceof IToolTipProvider)
            itemWithTip = (IToolTipProvider) Block.getBlockFromItem(event.getItemStack().getItem());
        else if(event.getItemStack().getItem() instanceof IToolTipProvider)
            itemWithTip = (IToolTipProvider) event.getItemStack().getItem();


        if(itemWithTip != null) {
            List<String> tipList = itemWithTip.getToolTip(event.getItemStack());
            if(tipList != null)
                for (String tip : tipList)
                    event.getToolTip().add(new StringTextComponent(tip));
        }
    }
}
