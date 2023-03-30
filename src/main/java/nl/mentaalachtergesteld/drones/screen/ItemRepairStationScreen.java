package nl.mentaalachtergesteld.drones.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import nl.mentaalachtergesteld.drones.Drones;

public class ItemRepairStationScreen extends AbstractContainerScreen<ItemRepairStationMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Drones.ID, "textures/gui/container/item_repair_station.png");

    public ItemRepairStationScreen(ItemRepairStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width-imageWidth) / 2;
        int y = (height-imageHeight) /2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        renderFuelAmount(pPoseStack, x, y);
    }

    private void renderFuelAmount(PoseStack pPoseStack, int x, int y) {
        blit(pPoseStack, x+81, y+48-menu.getScaledFuelAmount(), 176, 13- menu.getScaledFuelAmount(), 14, menu.getScaledFuelAmount());
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
