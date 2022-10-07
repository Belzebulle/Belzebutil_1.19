package fr.krepe.belzebutil.block;

import fr.krepe.belzebutil.block.entity.EnergyBlockEntity;
import fr.krepe.belzebutil.block.entity.EnergyGeneratorEntity;
import fr.krepe.belzebutil.block.entity.LeadStationEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, fr.krepe.belzebutil.Belzebutil.MOD_ID);

    public static final RegistryObject<BlockEntityType<LeadStationEntity>> LEAD_STATION_ENTITY =
            BLOCK_ENTITIES.register("lead_station_entity", () ->
                    BlockEntityType.Builder.of(LeadStationEntity::new,
                            ModBlocks.LEAD_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergyGeneratorEntity>> ENERGY_GENERATOR_ENTITY =
            BLOCK_ENTITIES.register("energy_generator_entity", () ->
                    BlockEntityType.Builder.of(EnergyGeneratorEntity::new,
                            ModBlocks.ENERGY_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergyBlockEntity>> ENERGY_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("energy_block_entity", () ->
                    BlockEntityType.Builder.of(EnergyBlockEntity::new,
                            ModBlocks.ENERGY_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
