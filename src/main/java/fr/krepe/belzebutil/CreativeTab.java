package fr.krepe.belzebutil;

import fr.krepe.belzebutil.item.ModItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTab {
    public static final CreativeModeTab ModTab_Armor = new CreativeModeTab("weebos_tab_armor") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.LEAD_CHESTPLATE.get());
        }
    };

    public static final CreativeModeTab ModTab = new CreativeModeTab("weebos_tab") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.SUPER_LEAD_INGOT.get());
        }
    };
}
