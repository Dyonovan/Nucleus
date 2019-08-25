package com.teambr.nucleus.common;

import com.teambr.nucleus.util.ClientUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * This file was created for NeoTech
 * <p>
 * NeoTech is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 3/3/2017
 */
public interface IAdvancedToolTipProvider extends IToolTipProvider {

    /**
     * Get the tool tip to present when shift is pressed
     * @param stack The itemstack
     * @return      The list to display
     */
    @Nullable
    @OnlyIn(Dist.CLIENT)
    List<String> getAdvancedToolTip(@Nonnull ItemStack stack);

    /**
     * Defines if the tooltip will add the press shift for more info text
     *
     * Override this to false if you just want it to show up on shift, useful if press shift for info may already be
     * present
     *
     * @return True to display
     */
    @OnlyIn(Dist.CLIENT)
    default boolean displayShiftForInfo(ItemStack stack) {
        return true;
    }

    /**
     * Used to get the tip to display
     *
     * @param stack
     * @return The tip to display
     */
    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    default List<String> getToolTip(@Nonnull ItemStack stack) {
        return ClientUtils.isShiftPressed() ?
                getAdvancedToolTip(stack) :
                displayShiftForInfo(stack) ?
                        Collections.singletonList(ClientUtils.translate("nucleus.text.shift_info")) :
                        null;
    }
}
