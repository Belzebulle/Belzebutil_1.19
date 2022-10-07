package fr.krepe.belzebutil.item.custom;

import fr.krepe.belzebutil.block.ModBlocks;
import fr.krepe.belzebutil.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;



public class OreCollector extends DiggerItem {

    private static final List<Block> ORES_LIST = List.of(
            Blocks.COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.GOLD_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.LAPIS_ORE,
            Blocks.REDSTONE_ORE,
            ModBlocks.LEAD_ORE.get()
    );

    private static final List<Block> DEEPSLATE_ORES_LIST = List.of(
            Blocks.DEEPSLATE_COAL_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,
            Blocks.DEEPSLATE_REDSTONE_ORE,
            ModBlocks.DEEPSLATE_LEAD_ORE.get()
    );


    public OreCollector(Tier tier, int i, float v, Properties properties) {
        super((float) i, v, tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide) {
            BlockPos blockClickedPos = context.getClickedPos();
            Player player = context.getPlayer();

            Block blockClicked = context.getLevel().getBlockState(blockClickedPos).getBlock();

            Map enchant = EnchantmentHelper.getEnchantments(player.getItemInHand(InteractionHand.MAIN_HAND));

            int x = 1;
            if (enchant.containsKey(Enchantments.BLOCK_FORTUNE)) {
                x += (int) enchant.get(Enchantments.BLOCK_FORTUNE);
            }

            if (isValuableOre(blockClicked)) {
                dropItem(context.getLevel(), blockClickedPos, player, x);
                context.getLevel().setBlockAndUpdate(blockClickedPos, Blocks.STONE.defaultBlockState());
                player.getItemInHand(InteractionHand.MAIN_HAND).hurt(4 * x, RandomSource.create(), null);
            }

            if (isValuableDeepslateOre(blockClicked)) {
                dropItem(context.getLevel(), blockClickedPos, player, x);
                context.getLevel().setBlockAndUpdate(blockClickedPos, Blocks.DEEPSLATE.defaultBlockState());
                player.getItemInHand(InteractionHand.MAIN_HAND).hurt(8 * x, RandomSource.create(), null);
                // log blockClicked
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.literal("Collect one ore without break the stone").withStyle(ChatFormatting.AQUA));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, p_41422_, components, flag);
    }

    private boolean isValuableOre(Block block) {
        return ORES_LIST.contains(block);
    }

    private boolean isValuableDeepslateOre(Block block) {
        return DEEPSLATE_ORES_LIST.contains(block);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolAction);
    }

    public void dropItem(Level level, BlockPos pos, Player player, int x) {
        level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(getItemFromOre(level.getBlockState(pos).getBlock()), x)));
    }

    private ItemLike getItemFromOre(Block block) {
        if (block == Blocks.COAL_ORE) {
            return Items.COAL;
        }
        if (block == Blocks.IRON_ORE) {
            return Items.RAW_IRON;
        }
        if (block == Blocks.GOLD_ORE) {
            return Items.RAW_GOLD;
        }
        if (block == Blocks.DIAMOND_ORE) {
            return Items.DIAMOND;
        }
        if (block == Blocks.EMERALD_ORE) {
            return Items.EMERALD;
        }
        if (block == Blocks.LAPIS_ORE) {
            return Items.LAPIS_LAZULI;
        }
        if (block == Blocks.REDSTONE_ORE) {
            return Items.REDSTONE;
        }
        if (block == ModBlocks.LEAD_ORE.get()) {
            return ModItems.RAW_LEAD.get();
        }
        if (block == Blocks.DEEPSLATE_COAL_ORE) {
            return Items.COAL;
        }
        if (block == Blocks.DEEPSLATE_IRON_ORE) {
            return Items.RAW_IRON;
        }
        if (block == Blocks.DEEPSLATE_GOLD_ORE) {
            return Items.RAW_GOLD;
        }
        if (block == Blocks.DEEPSLATE_DIAMOND_ORE) {
            return Items.DIAMOND;
        }
        if (block == Blocks.DEEPSLATE_EMERALD_ORE) {
            return Items.EMERALD;
        }
        if (block == Blocks.DEEPSLATE_LAPIS_ORE) {
            return Items.LAPIS_LAZULI;
        }
        if (block == Blocks.DEEPSLATE_REDSTONE_ORE) {
            return Items.REDSTONE;
        }
        if (block == ModBlocks.DEEPSLATE_LEAD_ORE.get()) {
            return ModItems.RAW_LEAD.get();
        }
        return null;
    }
}

