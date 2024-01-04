package com.pauljoda.nucleus.data;


import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

/**
 * This class is responsible for generating loot tables for different types of blocks
 * in the game.
 * <p>
 * The class extends the functionality provided by VanillaBlockLoot to define custom
 * loot tables for certain blocks.
 *
 * @author Paul Davis - pauljoda
 * @since 6/7/2022
 */
public abstract class BaseLootTableGenerator extends VanillaBlockLoot {

    /**
     * This method creates a standard loot table for a given block with a specific block entity type.
     *
     * @param block The block for which the loot table is to be created.
     * @param type  The type of the block entity.
     * @param tags  An array of tags that is copy data from the block entity NBT data.
     */
    private void createStandardTable(Block block, BlockEntityType<?> type, String... tags) {
        LootPoolSingletonContainer.Builder<?> lti = LootItem.lootTableItem(block);

        // Copy the name of the block entity over to the loot item.
        lti.apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY));

        // Copy over NBT data from the block entity to the loot item.
        for (String tag : tags) {
            lti.apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy(tag, "BlockEntityTag." + tag, CopyNbtFunction.MergeStrategy.REPLACE));
        }

        // Add contents to the loot item from the block entity.
        lti.apply(SetContainerContents.setContents(type).withEntry(DynamicLoot.dynamicEntry(new ResourceLocation("minecraft", "contents"))));

        // Create a loot pool that rolls once and add the loot item to it.
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(lti);

        // Add the loot pool to the loot table of the block.
        add(block, LootTable.lootTable().withPool(builder));
    }
}