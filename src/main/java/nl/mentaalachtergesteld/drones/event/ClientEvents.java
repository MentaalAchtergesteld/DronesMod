package nl.mentaalachtergesteld.drones.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nl.mentaalachtergesteld.drones.Drones;
import nl.mentaalachtergesteld.drones.block.entity.ModBlockEntities;
import nl.mentaalachtergesteld.drones.block.entity.renderer.ItemRepairStationBlockEntityRenderer;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Drones.ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
    }

    @Mod.EventBusSubscriber(modid = Drones.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.ITEM_REPAIR_STATION.get(),
                    ItemRepairStationBlockEntityRenderer::new);
        }
    }
}
