package fr.krepe.util_kreperie.block;

import fr.krepe.util_kreperie.CreativeTab;
import fr.krepe.util_kreperie.item.ModItem;
import fr.krepe.util_kreperie.Util_Kreperie;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;


public class SpecialModBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Util_Kreperie.MOD_ID);

    //-----------

    public static final RegistryObject<Block> ROTTEN_COAL_BLOCK = registerRottenCoalBlock("rotten_coal_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(1f).requiresCorrectToolForDrops()));


    //-----------
    private static <T extends Block>RegistryObject<T> registerRottenCoalBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        ModItem.ITEMS.register(name, () -> new BlockItem(toReturn.get(),
                new Item.Properties().tab(CreativeTab.ModTab)){
            @Override
            public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {

                return 10 * ModItem.ROTTEN_COAL.get().getBurnTime(itemStack, recipeType);
            }
        });
        return toReturn;
    };

    public static void registerSpeBlock(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
