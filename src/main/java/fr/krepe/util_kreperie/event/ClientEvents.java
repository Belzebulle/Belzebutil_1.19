package fr.krepe.util_kreperie.event;

import fr.krepe.util_kreperie.Util_Kreperie;
import fr.krepe.util_kreperie.network.ModMessages;
import fr.krepe.util_kreperie.network.packet.PacketSyncWaterDrinking;
import fr.krepe.util_kreperie.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Util_Kreperie.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents{

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.DRINKING_KEY);
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if(KeyBinding.DRINKING_KEY.consumeClick()){
                ModMessages.sendToServer(new PacketSyncWaterDrinking());
            }
            // touche pour up down money + event pour acheter / menu a afficher
        }
    }
}
