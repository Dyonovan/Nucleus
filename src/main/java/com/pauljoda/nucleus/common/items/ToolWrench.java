package com.pauljoda.nucleus.common.items;

import com.pauljoda.nucleus.common.blocks.IToolable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.ToolType;

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
public class ToolWrench extends Item {

    public ToolWrench() {
        super(new Properties()
        .maxStackSize(1)
        .addToolType(ToolType.get("wrench"), 1));
    }

    /**
     * Called when item is right clicked
     * @param context The context of the action
     * @return Result of action
     */
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if(context.getWorld().getBlockState(context.getPos()).getBlock() instanceof IToolable)
            return ((IToolable) context.getWorld().getBlockState(context.getPos()).getBlock())
                    .onWrench(context);
        else
            return ActionResultType.FAIL;

    }
}
