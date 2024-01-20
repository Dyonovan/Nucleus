package com.pauljoda.nucleus.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

/**
 * A type of recipe that does not return the standard ItemStack output from an input
 *
 * @param <C>
 */
public interface CustomRecipe<C extends Container> extends Recipe<C> {

    @Override
    default ItemStack assemble(C pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     *
     * @param pWidth
     * @param pHeight
     */
    @Override
    default boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    default ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }
}
