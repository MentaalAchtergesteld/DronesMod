package nl.mentaalachtergesteld.drones.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import nl.mentaalachtergesteld.drones.Drones;
import nl.mentaalachtergesteld.drones.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {

    private ExistingFileHelper existingFileHelper;
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Drones.ID, exFileHelper);
        existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SUSMIUM_BLOCK);
        blockWithItem(ModBlocks.RAW_SUSMIUM_BLOCK);
        blockWithItem(ModBlocks.SUSMIUM_ORE);
        blockWithItem(ModBlocks.QUESTION_MARK_BLOCK);
//        blockWithItem(ModBlocks.ITEM_REPAIR_STATION);

        simpleBlockWithItem(ModBlocks.ITEM_REPAIR_STATION.get(), new ModelFile.ExistingModelFile(new ResourceLocation(Drones.ID, "block/item_repair_station"), existingFileHelper));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
