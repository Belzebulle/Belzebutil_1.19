package fr.krepe.util_kreperie.block;

import fr.krepe.util_kreperie.CreativeTab;
import fr.krepe.util_kreperie.block.custom.*;
import fr.krepe.util_kreperie.item.ModItem;
import fr.krepe.util_kreperie.Util_Kreperie;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Util_Kreperie.MOD_ID);

    //-----------
    public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LEAD_BLOCK = registerBlock("lead_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5.0F , 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> RAW_LEAD_BLOCK = registerBlock("raw_lead_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(4.5F , 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LEAD_ORE = registerBlock("lead_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3.0F , 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5F , 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> LEAD_STATION = registerBlock("lead_station",
            () -> new LeadStation(
                    BlockBehaviour.Properties.of(Material.METAL)
                            .strength(3.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ENERGY_GENERATOR = registerBlock("energy_generator",
            () -> new EnergyGenerator(
                    BlockBehaviour.Properties.of(Material.METAL)
                            .strength(3.5F).requiresCorrectToolForDrops()
                            .lightLevel(state -> state.getValue(EnergyGenerator.LIT) ? 10 : 0)));


    public static final RegistryObject<Block> ENERGY_BLOCK = registerBlock("energy_block",
            () -> new EnergyBlock(
                    BlockBehaviour.Properties.of(Material.METAL)
                            .strength(3.5F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> FARMLAND_PLAIN = registerBlock("farmland_plain",
            () -> new FarmlandPlain(BlockBehaviour.Properties.of(Material.GRASS).randomTicks()
                    .strength(0.5F).sound(SoundType.GRAVEL)));

    public static final RegistryObject<Block> X_CROP = BLOCKS.register("x_crop",
            () -> new xCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));


    //-----------
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T>block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    };
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItem.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(CreativeTab.ModTab)));
    };
    public static void registerBlock(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
