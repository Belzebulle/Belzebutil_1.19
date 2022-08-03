package fr.krepe.util_kreperie.event;

import fr.krepe.util_kreperie.Util_Kreperie;
import fr.krepe.util_kreperie.recipies.LeadStationRecipe;
import fr.krepe.util_kreperie.thirst.PlayerThirst;
import fr.krepe.util_kreperie.thirst.PlayerThirstProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import javax.annotation.Nonnull;


@Mod.EventBusSubscriber(modid = Util_Kreperie.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvent {
    @SubscribeEvent
    public static void registerRecipeType(@Nonnull final RegisterEvent event){
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper ->  {
            helper.register(new ResourceLocation(Util_Kreperie.MOD_ID, LeadStationRecipe.Type.ID),
                    LeadStationRecipe.Type.INSTANCE);
        });
    }

}
