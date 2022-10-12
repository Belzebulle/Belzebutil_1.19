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

import java.util.List;
import java.util.Map;

// renderer for a slime entity return a blue cube model

public class ESlimeRenderer extends EntityRenderer<ESlimeEntity> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Belzebutil.MOD_ID, "textures/entity/slime.png");
    private final SlimeModel<ESlimeEntity> model =

    public ESlimeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public static ModelPart getSlimeModel() {
        List<ModelPart.Cube> cubes = List.of(
                new ModelPart.Cube(0, 0, 0, 16, 16, 16, 0, 0, 0, 0 , 0 , false, 64, 64),
                new ModelPart.Cube(0, 0, 0, 16, 16, 16, 0, 0, 0, 0 , 0 , false, 64, 64),
                new ModelPart.Cube(0, 0, 0, 16, 16, 16, 0, 0, 0, 0 , 0 , false, 64, 64),
                new ModelPart.Cube(0, 0, 0, 16, 16, 16, 0, 0, 0, 0 , 0 , false, 64, 64),
                new ModelPart.Cube(0, 0, 0, 16, 16, 16, 0, 0, 0, 0 , 0 , false, 64, 64),
                new ModelPart.Cube(0, 0, 0, 16, 16, 16, 0, 0, 0, 0 , 0 , false, 64, 64)
        );

        Map<String, ModelPart> parts = Map.of(
                "body", new ModelPart(cubes.subList(0, 1), Map.of()),
                "head", new ModelPart(cubes.subList(1, 2), Map.of()),
                "leftEye", new ModelPart(cubes.subList(2, 3), Map.of()),
                "rightEye", new ModelPart(cubes.subList(3, 4), Map.of()),
                "mouth", new ModelPart(cubes.subList(4, 5), Map.of())
        );

        return new ModelPart(cubes, parts);
    }

    @Override
    public void render(ESlimeEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_LOCATION));
        matrixStackIn.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(ESlimeEntity entity) {
        return TEXTURE_LOCATION;
    }
}