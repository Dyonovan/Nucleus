package com.pauljoda.nucleus.common.items;

import com.pauljoda.nucleus.common.blocks.IToolable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

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
                .stacksTo(1));
    }

    /**
     * Called when item is right clicked
     * @param context The context of the action
     * @return Result of action
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof IToolable)
            return ((IToolable) context.getLevel().getBlockState(context.getClickedPos()).getBlock())
                    .onWrench(context);
        else
            return InteractionResult.FAIL;
    }
}
