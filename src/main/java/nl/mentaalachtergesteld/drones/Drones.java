package nl.mentaalachtergesteld.drones;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent.BuildContents;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nl.mentaalachtergesteld.drones.block.ModBlocks;
import nl.mentaalachtergesteld.drones.block.entity.ModBlockEntities;
import nl.mentaalachtergesteld.drones.entity.ModEntities;
import nl.mentaalachtergesteld.drones.entity.client.DroneRenderer;
import nl.mentaalachtergesteld.drones.item.ModCreativeModeTabs;
import nl.mentaalachtergesteld.drones.item.ModItems;
import nl.mentaalachtergesteld.drones.networking.ModMessages;
import nl.mentaalachtergesteld.drones.screen.ItemRepairStationScreen;
import nl.mentaalachtergesteld.drones.screen.ModMenuTypes;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(Drones.ID)
public class Drones {

    public Drones() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener((FMLCommonSetupEvent event) -> commonSetup(event));
        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();

        // Register the item to a creative tab
        modEventBus.addListener((BuildContents event) -> addCreative(event));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
       });
    }

    private void addCreative(BuildContents event) {
        if (event.getTab() == ModCreativeModeTabs.DRONES_TAB) {
            event.accept(ModItems.EXCLAMATION_MARK);
            event.accept(ModItems.QUESTION_MARK);
            event.accept(ModItems.RAW_SUSMIUM);
            event.accept(ModItems.SUSMIUM_INGOT);
            event.accept(ModItems.DRONE_SPAWN_EGG);
            event.accept(ModBlocks.RAW_SUSMIUM_BLOCK);
            event.accept(ModBlocks.SUSMIUM_ORE);
            event.accept(ModBlocks.SUSMIUM_BLOCK);
            event.accept(ModBlocks.QUESTION_MARK_BLOCK);
            event.accept(ModBlocks.ITEM_REPAIR_STATION);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.MOD, value = { Dist.CLIENT })
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.ITEM_REPAIR_STAION_MENU.get(), ItemRepairStationScreen::new);
            EntityRenderers.register(ModEntities.DRONE.get(), DroneRenderer::new);
        }
    }

    public static final String ID = "drones";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
}