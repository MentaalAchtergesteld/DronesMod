package nl.mentaalachtergesteld.drones.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nl.mentaalachtergesteld.drones.Drones;
import nl.mentaalachtergesteld.drones.entity.ModEntities;
import nl.mentaalachtergesteld.drones.entity.custom.DroneEntity;

@Mod.EventBusSubscriber(modid = Drones.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DRONE.get(), DroneEntity.setAttributes());
    }
}
