package fr.krepe.util_kreperie.block.entity;

import fr.krepe.util_kreperie.block.ModBlockEntity;
import fr.krepe.util_kreperie.energy.KrepeEnergyStorage;
import fr.krepe.util_kreperie.network.ModMessages;
import fr.krepe.util_kreperie.network.packet.PacketSyncEnergyToClient;
import fr.krepe.util_kreperie.recipies.LeadStationRecipe;
import fr.krepe.util_kreperie.screen.LeadStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

import static fr.krepe.util_kreperie.block.custom.LeadStation.LIT;

public class LeadStationEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };


    public final KrepeEnergyStorage energyStorage = new KrepeEnergyStorage(60000, 200) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 0;

    public LeadStationEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, ContainerData data) {
        super(pType, pWorldPosition, pBlockState);
        this.data = data;
    }

    public void setEnergyLevel(int energyLevel) { this.energyStorage.setEnergy(energyLevel); }

    public LeadStationEntity(BlockPos pWorldPosition, BlockState pBlockState){
        super(ModBlockEntity.LEAD_STATION_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return LeadStationEntity.this.progress;
                    case 1: return LeadStationEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0: LeadStationEntity.this.progress = value; break;
                    case 1: LeadStationEntity.this.maxProgress = value; break;
                }
            }
            public int getCount() { return 2; }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Lead Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new LeadStationMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }


    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("lead_station.progress", progress);
        tag.putInt("energy" , energyStorage.getEnergyStored());

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("lead_station.progress");
        energyStorage.setEnergy(nbt.getInt("energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, LeadStationEntity pBlockEntity) {
        if(pLevel.isClientSide) return;
        if(hasRecipe(pBlockEntity) && hasEnoughEnergy(pBlockEntity)) {
            pBlockEntity.progress++;
            extractEnergy(pBlockEntity);
            setChanged(pLevel, pPos, pState);
            craftItem(pBlockEntity);
            pLevel.setBlock(pPos, pState.setValue(LIT, true), 2);
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
            pLevel.setBlock(pPos, pState.setValue(LIT, false), 2);
        }
    }
    private static void extractEnergy(LeadStationEntity entity) {
        entity.energyStorage.extractEnergy(50, false);
    }

    private static boolean hasEnoughEnergy(LeadStationEntity entity) {
        return entity.energyStorage.getEnergyStored() >= 10;
    }

    private static boolean hasRecipe(LeadStationEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<LeadStationRecipe> match = level.getRecipeManager()
                .getRecipeFor(LeadStationRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            if (match.get().getBurntime() != 0) {
                entity.maxProgress = match.get().getBurntime();
            }
        }

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }
    
    private static void craftItem(LeadStationEntity entity) {

        if(entity.progress < entity.maxProgress) return;

        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<LeadStationRecipe> match = level.getRecipeManager()
                .getRecipeFor(LeadStationRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.extractItem(2,1, false);

            entity.itemHandler.setStackInSlot(3, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(3).getCount() + 1));

            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = saveWithoutMetadata();
        load(compound);

        return compound;
    }

}
