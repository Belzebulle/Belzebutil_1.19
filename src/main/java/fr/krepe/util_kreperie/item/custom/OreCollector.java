package fr.krepe.util_kreperie.item.custom;

import fr.krepe.util_kreperie.block.ModBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


public class OreCollector extends DiggerItem {
    public OreCollector(Tier tier, int i, float v, Properties properties) {
        super((float)i, v, tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide) {
            BlockPos blockClickedPos = context.getClickedPos();
            Player player = context.getPlayer();

            Block blockClicked = context.getLevel().getBlockState(blockClickedPos).getBlock();

            Map enchant = EnchantmentHelper.getEnchantments(player.getItemInHand(InteractionHand.MAIN_HAND));

            int x = 1;
            if(enchant.containsKey(Enchantments.BLOCK_FORTUNE)) {
                x = (int) enchant.get(Enchantments.BLOCK_FORTUNE);
            }

            if(isValuableBlockStone(blockClicked)){

                /*
                if (Blocks.IRON_ORE.equals(blockClicked)) {
                    player.getInventory().add(new ItemStack(Items.RAW_IRON, x));
                }
                if (Blocks.COAL_ORE.equals(blockClicked)) {
                    player.getInventory().add(new ItemStack(Items.COAL, x));
                }
                if (Blocks.COPPER_ORE.equals(blockClicked)) {
                    player.getInventory().add(new ItemStack(Items.RAW_COPPER, x));
                }
                if (Blocks.GOLD_ORE.equals(blockClicked)) {
                    player.getInventory().add(new ItemStack(Items.RAW_GOLD, x));
                }
                if (Blocks.REDSTONE_ORE.equals(blockClicked)) {
                    player.getInventory().add(new ItemStack(Items.REDSTONE, x));
                }
                if (Blocks.LAPIS_ORE.equals(blockClicked)) {
                    player.getInventory().add(new ItemStack(Items.LAPIS_LAZULI, x));
                }
                if (Blocks.EMERALD_ORE.equals(blockClicked)) {
                    player.getInventory().add(new ItemStack(Items.EMERALD, x));
                }
                if (Blocks.DIAMOND_ORE.equals(blockClicked)) {
                    player.getInventory().add(new ItemStack(Items.DIAMOND, x));
                }
                player.getItemInHand(InteractionHand.MAIN_HAND).hurt(x, RandomSource.create(), null);
                 */

                context.getLevel().destroyBlock(blockClickedPos, true);
                context.getLevel().setBlockAndUpdate(blockClickedPos, Blocks.STONE.defaultBlockState());
                player.getItemInHand(InteractionHand.MAIN_HAND).hurt(x, RandomSource.create(), null);
            }
            if(isValuableBlockDeepslate(blockClicked)){

                context.getLevel().destroyBlock(blockClickedPos, true);
                context.getLevel().setBlockAndUpdate(blockClickedPos, Blocks.DEEPSLATE.defaultBlockState());
                player.getItemInHand(InteractionHand.MAIN_HAND).hurt(2 * x, RandomSource.create(), null);
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Collect one ore without break the stone").withStyle(ChatFormatting.AQUA));
        }else{
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, p_41422_, components, flag);
    }

    private boolean isValuableBlockStone(Block block){
        return block == Blocks.COAL_ORE || block == Blocks.IRON_ORE || block == Blocks.REDSTONE_ORE ||
                block == Blocks.COPPER_ORE || block == Blocks.EMERALD_ORE || block == Blocks.DIAMOND_ORE ||
                block == Blocks.GOLD_ORE || block == Blocks.LAPIS_ORE || block == ModBlock.LEAD_ORE.get();
    }

    private boolean isValuableBlockDeepslate(Block block){
        return block == Blocks.DEEPSLATE_COAL_ORE || block == Blocks.DEEPSLATE_IRON_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE ||
                block == Blocks.DEEPSLATE_COPPER_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE ||
                block == Blocks.DEEPSLATE_GOLD_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE || block == ModBlock.DEEPSLATE_LEAD_ORE.get();
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolAction);
    }

}
