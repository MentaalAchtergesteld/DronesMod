package nl.mentaalachtergesteld.drones.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nl.mentaalachtergesteld.drones.Drones;

@Mod.EventBusSubscriber(modid = Drones.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab DRONES_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        DRONES_TAB = event.registerCreativeModeTab(new ResourceLocation(Drones.ID, "drones_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.SUSMIUM_INGOT.get()))
                        .title(Component.translatable("creativemodetab.drones_tab")));
    }
}
