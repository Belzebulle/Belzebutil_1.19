package fr.krepe.belzebutil.block.custom;

import fr.krepe.belzebutil.block.ModBlock;
import fr.krepe.belzebutil.item.ModItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class xCropBlock extends CropBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0,6);

    public xCropBlock(Properties p_52247_) {
        super(p_52247_);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItem.X_SEED.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if(!pLevel.isClientSide && pHand == InteractionHand.MAIN_HAND){
            if(pState.getValue(AGE) == 6){
                pPlayer.getInventory().add(new ItemStack(ModItem.X_EAT.get()));
                pLevel.setBlockAndUpdate(pPos, ModBlock.X_CROP.get().defaultBlockState());
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }
}
