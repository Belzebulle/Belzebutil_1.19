package fr.krepe.belzebutil.entity.eslime;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.krepe.belzebutil.Belzebutil;
import fr.krepe.belzebutil.entity.ESlimeEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

// model for a slime entity
public class ESlimeModel extends AnimatedGeoModel<ESlimeEntity> {
    @Override
    public ResourceLocation getModelResource(ESlimeEntity object) {
        return new ResourceLocation(Belzebutil.MOD_ID, "geo/slime.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(ESlimeEntity object) {
        return new ResourceLocation(Belzebutil.MOD_ID, "textures/entity/slime.png");
    }
    @Override
    public ResourceLocation getAnimationResource(ESlimeEntity animatable) {
        return new ResourceLocation(Belzebutil.MOD_ID, "animations/slime.animation.json");
    }
}