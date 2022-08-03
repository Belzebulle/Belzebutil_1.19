package fr.krepe.util_kreperie.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.krepe.util_kreperie.screen.renderer.EnergyInfoArea;
import fr.krepe.util_kreperie.util.MouseUtil;
import fr.krepe.util_kreperie.Util_Kreperie;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class EnergyGeneratorScreen extends AbstractContainerScreen<EnergyGeneratorMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Util_Kreperie.MOD_ID, "textures/gui/energy_generator_gui.png");
    private EnergyInfoArea energyInfoArea;

    @Override
    protected void init() {
        super.init();
        energyInfoArea = new EnergyInfoArea(((width - imageWidth) / 2) +  16,
                ((height - imageHeight) / 2) + 14,9 ,42 , menu.blockEntity.energyStorage);
    }

    public EnergyGeneratorScreen(EnergyGeneratorMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float p_97788_, int xMouse, int yMouse) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()) {
            int k = this.menu.getScaledProgress();
            this.blit(pPoseStack, x + 81, y + 53 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        energyInfoArea.draw(pPoseStack);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int xMouse, int yMouse) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if(isMouseAboveArea(xMouse, yMouse, x, y , 16, 14 , 9, 41)){
            renderTooltip(poseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), xMouse - x, yMouse - y );
        }

    }

    @Override
    public void render(PoseStack pPosStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPosStack);
        super.render(pPosStack, mouseX, mouseY, delta);
        renderTooltip(pPosStack, mouseX, mouseY);
    }


    private boolean isMouseAboveArea(int xMouse, int yMouse, int x, int y, int offsetX ,int offsetY, int width, int height ){
        return MouseUtil.isMouseOver(xMouse, yMouse, x + offsetX, y + offsetY, width, height);
    }
}
