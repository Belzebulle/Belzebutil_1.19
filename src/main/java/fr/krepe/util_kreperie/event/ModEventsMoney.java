package fr.krepe.util_kreperie.event;

import fr.krepe.util_kreperie.Util_Kreperie;
import fr.krepe.util_kreperie.money.PlayerMoney;
import fr.krepe.util_kreperie.money.PlayerMoneyProvider;
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

@Mod.EventBusSubscriber(modid = Util_Kreperie.MOD_ID)
public class ModEventsMoney {
    @SubscribeEvent
    public static void onAttachCapabilityPlayer(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            if(!event.getObject().getCapability(PlayerMoneyProvider.PLAYER_MONEY).isPresent()){
                event.addCapability(new ResourceLocation(Util_Kreperie.MOD_ID, "properties"), new PlayerMoneyProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            event.getOriginal().getCapability(PlayerMoneyProvider.PLAYER_MONEY).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerMoneyProvider.PLAYER_MONEY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapability(RegisterCapabilitiesEvent event){
        event.register(PlayerMoney.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER){
            event.player.getCapability(PlayerMoneyProvider.PLAYER_MONEY).ifPresent(thirst -> {

            });
        }
    }
}
