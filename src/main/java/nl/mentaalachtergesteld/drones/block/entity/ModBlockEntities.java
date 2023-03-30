package nl.mentaalachtergesteld.drones.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nl.mentaalachtergesteld.drones.Drones;
import nl.mentaalachtergesteld.drones.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Drones.ID);

    public static final RegistryObject<BlockEntityType<ItemRepairStationBlockEntity>> ITEM_REPAIR_STATION =
            BLOCK_ENTITIES.register("item_repair_station", () ->
                    BlockEntityType.Builder.of(ItemRepairStationBlockEntity::new,
                            ModBlocks.ITEM_REPAIR_STATION.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
