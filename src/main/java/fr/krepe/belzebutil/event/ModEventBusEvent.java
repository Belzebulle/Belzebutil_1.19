package fr.krepe.belzebutil.event;

import fr.krepe.belzebutil.recipies.LeadStationRecipe;
import net.minecraft.resources.ResourceLocation;
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

}
