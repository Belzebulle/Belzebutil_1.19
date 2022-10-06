package fr.krepe.belzebutil.item;

import fr.krepe.belzebutil.CreativeTab;
import fr.krepe.belzebutil.block.ModBlock;
import fr.krepe.belzebutil.item.armor.ModArmorItemLight;
import fr.krepe.belzebutil.item.armor.ModArmorItemLead;
import fr.krepe.belzebutil.item.armor.ModArmorItemScuba;
import fr.krepe.belzebutil.item.custom.OreCollector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, fr.krepe.belzebutil.Belzebutil.MOD_ID);

    public static final RegistryObject<Item> DIAMANTIUM = ITEMS.register("diamantium", () -> new Item(new Item.Properties().tab(CreativeTab.ModTab)));
    public static final RegistryObject<Item> SWORD_DIAMANTIUM = ITEMS.register("diamantium_sword",
            () -> new SwordItem(ModItemTier.DIAMANTIUM, 2, 3f, new Item.Properties().tab(CreativeTab.ModTab)));

    public static final RegistryObject<Item> X_SEED = ITEMS.register("x_seeds",
            () -> new ItemNameBlockItem(ModBlock.X_CROP.get(),
                    new Item.Properties().tab(CreativeTab.ModTab)));

    public static final RegistryObject<Item> X_EAT = ITEMS.register("x_eat", () -> new Item(new Item.Properties().tab(CreativeTab.ModTab)
            .food(new FoodProperties.Builder().nutrition(2).saturationMod(2f).build())));

    // Amethyst Item
    public static final RegistryObject<Item> AMETHYST_INGOT = ITEMS.register("amethyst_ingot", () -> new Item(new Item.Properties().tab(CreativeTab.ModTab)));

    public static final RegistryObject<Item> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", () -> new ArmorItem(
            ModArmorTier.AMETHYST,
            EquipmentSlot.HEAD,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));
    public static final RegistryObject<Item> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", () -> new ArmorItem(
            ModArmorTier.AMETHYST,
            EquipmentSlot.CHEST,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));
    public static final RegistryObject<Item> AMETHYST_LEGGINGS = ITEMS.register("amethyst_leggings", () -> new ArmorItem(
            ModArmorTier.AMETHYST,
            EquipmentSlot.LEGS,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));
    public static final RegistryObject<Item> AMETHYST_BOOTS = ITEMS.register("amethyst_boots", () -> new ArmorItem(
            ModArmorTier.AMETHYST,
            EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));

    // lead Item

    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().tab(CreativeTab.ModTab)));
    public static final RegistryObject<Item> SUPER_LEAD_INGOT = ITEMS.register("super_lead_ingot", () -> new Item(new Item.Properties().tab(CreativeTab.ModTab)));
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties().tab(CreativeTab.ModTab)));
    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties().tab(CreativeTab.ModTab)));

    // - Armure

    public static final RegistryObject<Item> LEAD_HELMET = ITEMS.register("lead_helmet", () -> new ModArmorItemLead(
            ModArmorTier.LEAD,
            EquipmentSlot.HEAD,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));
    public static final RegistryObject<Item> LEAD_CHESTPLATE = ITEMS.register("lead_chestplate", () -> new ArmorItem(
            ModArmorTier.LEAD,
            EquipmentSlot.CHEST,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));
    public static final RegistryObject<Item> LEAD_LEGGINGS = ITEMS.register("lead_leggings", () -> new ArmorItem(
            ModArmorTier.LEAD,
            EquipmentSlot.LEGS,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));
    public static final RegistryObject<Item> LEAD_BOOTS = ITEMS.register("lead_boots", () -> new ArmorItem(
            ModArmorTier.LEAD,
            EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));

    public static final RegistryObject<Item> LEAD_PICKAXE= ITEMS.register("lead_pickaxe", () -> new OreCollector(
            ModItemTier.LEAD,
            1,
            -2.8f,
            new Item.Properties().tab(CreativeTab.ModTab)
    ));


    // Light Boots

    public static final RegistryObject<Item> LONG_FEATHER = ITEMS.register("long_feather", () -> new Item(new Item.Properties().tab(CreativeTab.ModTab)));

    public static final RegistryObject<Item> LIGHT_BOOTS = ITEMS.register("light_boots", () -> new ModArmorItemLight(
            ModArmorTier.LIGHT,
            EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));

    // Scuba

    public static final RegistryObject<Item> SCUBA_HELMET = ITEMS.register("scuba_helmet", () -> new ModArmorItemScuba(
            ModArmorTier.COPPER,
            EquipmentSlot.HEAD,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));
    public static final RegistryObject<Item> SCUBA_CHESTPLATE = ITEMS.register("scuba_chestplate", () -> new ArmorItem(
            ModArmorTier.COPPER,
            EquipmentSlot.CHEST,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));
    public static final RegistryObject<Item> SCUBA_LEGGINGS = ITEMS.register("scuba_leggings", () -> new ArmorItem(
            ModArmorTier.COPPER,
            EquipmentSlot.LEGS,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));
    public static final RegistryObject<Item> SCUBA_BOOTS = ITEMS.register("scuba_boots", () -> new ArmorItem(
            ModArmorTier.COPPER,
            EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeTab.ModTab_Armor)
    ));

    // Melange rotten & coal
    public static final RegistryObject<Item> ROTTEN_COAL = ITEMS.register("rotten_coal",
            () -> new Item(new Item.Properties().tab(CreativeTab.ModTab)){
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 2000;
                }
            });

    public static void registerItem(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
