package fr.krepe.util_kreperie;

import com.mojang.logging.LogUtils;
import fr.krepe.util_kreperie.block.ModBlock;
import fr.krepe.util_kreperie.block.ModBlockEntity;
import fr.krepe.util_kreperie.block.SpecialModBlock;
import fr.krepe.util_kreperie.item.ModItem;
import fr.krepe.util_kreperie.network.ModMessages;
import fr.krepe.util_kreperie.recipies.ModRecipes;
import fr.krepe.util_kreperie.screen.EnergyGeneratorScreen;
import fr.krepe.util_kreperie.screen.ModMenuTypes;
import fr.krepe.util_kreperie.screen.LeadStationScreen;
import fr.krepe.util_kreperie.world.features.ModConfiguredFeatures;
import fr.krepe.util_kreperie.world.features.ModPlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Util_Kreperie.MOD_ID)
public class Util_Kreperie
{
    public static final String MOD_ID = "util_kreperie";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Util_Kreperie()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItem.registerItem(eventBus);
        ModBlock.registerBlock(eventBus);
        SpecialModBlock.registerSpeBlock(eventBus);
        ModBlockEntity.register(eventBus);

        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);

        ModConfiguredFeatures.register(eventBus);
        ModPlacedFeatures.register(eventBus);


        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {


        MenuScreens.register(ModMenuTypes.LEAD_STATION_MENU.get(), LeadStationScreen::new);
        MenuScreens.register(ModMenuTypes.ENERGY_GENERATOR_MENU.get(), EnergyGeneratorScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModMessages.register();
    }


}
