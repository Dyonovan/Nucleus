package com.pauljoda.nucleus.common;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
public interface IToolTipProvider {

    /**
     * Used to get the tip to display
     * @return The tip to display
     */
    @Nullable
    @OnlyIn(Dist.CLIENT)
    List<String> getToolTip(@Nonnull ItemStack stack) ;
}
