package nl.mentaalachtergesteld.drones.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nl.mentaalachtergesteld.drones.Drones;
import nl.mentaalachtergesteld.drones.entity.custom.DroneEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Drones.ID);

    public static final RegistryObject<EntityType<DroneEntity>> DRONE =
            ENTITY_TYPES.register("drone",
                    () -> EntityType.Builder.of(DroneEntity::new, MobCategory.CREATURE)
                            .sized(.25f, .12f)
                            .build(new ResourceLocation(Drones.ID, "drone").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
