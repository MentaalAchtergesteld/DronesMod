package nl.mentaalachtergesteld.drones.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nl.mentaalachtergesteld.drones.Drones;
import nl.mentaalachtergesteld.drones.entity.ModEntities;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Drones.ID);

    public static final RegistryObject<Item> EXCLAMATION_MARK = ITEMS.register("exclamation_mark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> QUESTION_MARK = ITEMS.register("question_mark",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SUSMIUM = ITEMS.register("raw_susmium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUSMIUM_INGOT = ITEMS.register("susmium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRONE_SPAWN_EGG = ITEMS.register("drone_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DRONE, 0xFFFFFF, 0x000000, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
