package fr.krepe.belzebutil.entity;

import fr.krepe.belzebutil.Belzebutil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Belzebutil.MOD_ID);

    public static final RegistryObject<EntityType<ESlimeEntity>> E_SLIME =
            ENTITY_TYPES.register("e_slime",
                    () -> EntityType.Builder.of(ESlimeEntity::new, MobCategory.MONSTER)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Belzebutil.MOD_ID, "e_slime").toString()));
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}