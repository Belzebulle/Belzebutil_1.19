package fr.krepe.belzebutil.recipies;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, fr.krepe.belzebutil.Belzebutil.MOD_ID);

    public static RegistryObject<RecipeSerializer<LeadStationRecipe>> LEAD_STATION_SERIALIZER =
            SERIALIZERS.register("lead_station", () -> LeadStationRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }

}
