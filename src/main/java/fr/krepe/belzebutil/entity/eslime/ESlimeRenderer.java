package fr.krepe.belzebutil.entity.eslime;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.krepe.belzebutil.Belzebutil;
import fr.krepe.belzebutil.entity.ESlimeEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

// renderer for a slime entity return a blue cube model

public class ESlimeRenderer extends GeoEntityRenderer<ESlimeEntity> {
    public ESlimeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ESlimeModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(ESlimeEntity instance) {
        return new ResourceLocation(Belzebutil.MOD_ID, "textures/entity/slime.png");
    }

    @Override
    public RenderType getRenderType(ESlimeEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8f, 0.8f, 0.8f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}