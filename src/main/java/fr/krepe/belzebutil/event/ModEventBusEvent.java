package fr.krepe.belzebutil.event;

import fr.krepe.belzebutil.Belzebutil;
import fr.krepe.belzebutil.entity.ESlimeEntity;
import fr.krepe.belzebutil.entity.ModEntityTypes;
import fr.krepe.belzebutil.recipies.LeadStationRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import javax.annotation.Nonnull;


@Mod.EventBusSubscriber(modid = fr.krepe.belzebutil.Belzebutil.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvent {
    @SubscribeEvent
    public static void registerRecipeType(@Nonnull final RegisterEvent event){
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper ->  {
            helper.register(new ResourceLocation(fr.krepe.belzebutil.Belzebutil.MOD_ID, LeadStationRecipe.Type.ID),
                    LeadStationRecipe.Type.INSTANCE);
        });
    }

    @Mod.EventBusSubscriber(modid = Belzebutil.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.E_SLIME.get(), ESlimeEntity.setAttributes());
        }
    }

}
