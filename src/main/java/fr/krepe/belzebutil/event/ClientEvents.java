package fr.krepe.belzebutil.event;

import fr.krepe.belzebutil.network.ModMessages;
import fr.krepe.belzebutil.network.packet.PacketSyncWaterDrinking;
import fr.krepe.belzebutil.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = fr.krepe.belzebutil.Belzebutil.MOD_ID, value = Dist.CLIENT)
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
