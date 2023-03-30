package nl.mentaalachtergesteld.drones.datagen;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import nl.mentaalachtergesteld.drones.block.ModBlocks;
import nl.mentaalachtergesteld.drones.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.SUSMIUM_BLOCK.get());
        dropSelf(ModBlocks.RAW_SUSMIUM_BLOCK.get());
        dropSelf(ModBlocks.QUESTION_MARK_BLOCK.get());
        dropSelf(ModBlocks.ITEM_REPAIR_STATION.get());
        add(ModBlocks.SUSMIUM_ORE.get(),
                (block) -> createOreDrop(ModBlocks.SUSMIUM_ORE.get(), ModItems.RAW_SUSMIUM.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
