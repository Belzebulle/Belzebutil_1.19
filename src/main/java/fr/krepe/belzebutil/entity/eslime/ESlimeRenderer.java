package fr.krepe.belzebutil.entity.eslime;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.krepe.belzebutil.Belzebutil;
import fr.krepe.belzebutil.entity.ESlimeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class ESlimeRenderer extends EntityRenderer<ESlimeEntity> {

    protected ESlimeRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(ESlimeEntity instance) {
        return new ResourceLocation(Belzebutil.MOD_ID, "textures/entity/slime.png");
    }
}

