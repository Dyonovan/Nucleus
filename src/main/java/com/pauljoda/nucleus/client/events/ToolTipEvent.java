package com.pauljoda.nucleus.client.events;

import com.pauljoda.nucleus.common.IToolTipProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class ToolTipEvent {
    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent event) {
        IToolTipProvider itemWithTip = null;

        if (event.getItemStack().getItem() instanceof IToolTipProvider)
            itemWithTip = (IToolTipProvider) event.getItemStack().getItem();

        else if (event.getItemStack().getItem() instanceof BlockItem blockItem &&
                blockItem.getBlock() instanceof IToolTipProvider)
            itemWithTip = (IToolTipProvider) blockItem.getBlock();


        if (itemWithTip != null) {
            List<String> tipList = itemWithTip.getToolTip(event.getItemStack());
            if (tipList != null)
                for (String tip : tipList)
                    event.getToolTip().add(Component.translatable(tip));
        }
    }
}