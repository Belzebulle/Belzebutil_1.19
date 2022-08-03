package fr.krepe.util_kreperie.block;

import fr.krepe.util_kreperie.Util_Kreperie;
import fr.krepe.util_kreperie.block.entity.EnergyBlockEntity;
import fr.krepe.util_kreperie.block.entity.EnergyGeneratorEntity;
import fr.krepe.util_kreperie.block.entity.LeadStationEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Util_Kreperie.MOD_ID);

    public static final RegistryObject<BlockEntityType<LeadStationEntity>> LEAD_STATION_ENTITY =
            BLOCK_ENTITIES.register("lead_station_entity", () ->
                    BlockEntityType.Builder.of(LeadStationEntity::new,
                            ModBlock.LEAD_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergyGeneratorEntity>> ENERGY_GENERATOR_ENTITY =
            BLOCK_ENTITIES.register("energy_generator_entity", () ->
                    BlockEntityType.Builder.of(EnergyGeneratorEntity::new,
                            ModBlock.ENERGY_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergyBlockEntity>> ENERGY_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("energy_block_entity", () ->
                    BlockEntityType.Builder.of(EnergyBlockEntity::new,
                            ModBlock.ENERGY_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
