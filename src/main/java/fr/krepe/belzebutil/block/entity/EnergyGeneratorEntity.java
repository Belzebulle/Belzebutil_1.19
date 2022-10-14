package fr.krepe.belzebutil.block.entity;

import fr.krepe.belzebutil.block.ModBlockEntities;
import fr.krepe.belzebutil.energy.KrepeEnergyStorage;
import fr.krepe.belzebutil.item.ModItems;
import fr.krepe.belzebutil.network.ModMessages;
import fr.krepe.belzebutil.network.packet.PacketSyncEnergyToClient;
import fr.krepe.belzebutil.screen.EnergyGeneratorMenu;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

import static fr.krepe.belzebutil.block.custom.EnergyGenerator.LIT;

public class EnergyGeneratorEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
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
    private int maxProgress = 2000;

    public EnergyGeneratorEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, ContainerData data) {
        super(pType, pWorldPosition, pBlockState);
        this.data = data;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }

    public EnergyGeneratorEntity(BlockPos pWorldPosition, BlockState pBlockState){
        super(ModBlockEntities.ENERGY_GENERATOR_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return EnergyGeneratorEntity.this.progress;
                    case 1: return EnergyGeneratorEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0: EnergyGeneratorEntity.this.progress = value; break;
                    case 1: EnergyGeneratorEntity.this.maxProgress = value; break;
                }
            }

            public int getCount() { return 2; }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Energy Generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new EnergyGeneratorMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
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
        tag.putInt("energy_generator.progress", progress);
        tag.putInt("energy" , energyStorage.getEnergyStored());

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("energy_generator.progress");
        energyStorage.setEnergy(nbt.getInt("energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, EnergyGeneratorEntity pBlockEntity) {
        if (pLevel.isClientSide) return;

        Item item = pBlockEntity.itemHandler.getStackInSlot(0).getItem();
        pBlockEntity.progress-- ;
        craftItem(pBlockEntity, item);

        if (isCrafting(pBlockEntity)){
            pLevel.setBlock(pPos, pState.setValue(LIT, true), 3);
        } else {
            pLevel.setBlock(pPos, pState.setValue(LIT, false), 3);
        }

        if(isNextToLeadStation(pLevel, pPos)){
            LeadStationEntity leadStationEntity = (LeadStationEntity) pLevel.getBlockEntity(posNextToLeadStation(pLevel, pPos));
            if(canReceiveEnergy(leadStationEntity)){
                if(pBlockEntity.energyStorage.getEnergyStored() >= 10 )
                    pBlockEntity.energyStorage.transferEnergy(10, leadStationEntity);
            }
        }
    }

    private static boolean isNextToLeadStation(Level pLevel, BlockPos pPos) {
        return posNextToLeadStation(pLevel, pPos) != null;
    }

    private static BlockPos posNextToLeadStation(Level pLevel, BlockPos pPos){
        for (   Direction direction : Direction.values()) {
            if(pLevel.getBlockEntity(pPos.relative(direction)) instanceof LeadStationEntity){
                return pPos.relative(direction);
            }
        }
        return null;
    }

    public static boolean isCrafting(EnergyGeneratorEntity entity) {
        return entity.data.get(0) > 0;
    }

    private static boolean canReceiveEnergy(LeadStationEntity entity) {
        return entity.energyStorage.getEnergyStored() < entity.energyStorage.getMaxEnergyStored();
    }

    private static boolean hasItem(EnergyGeneratorEntity pBlockEntity) {
        return !pBlockEntity.itemHandler.getStackInSlot(0).isEmpty();
    }
    private static boolean isCoalItem(EnergyGeneratorEntity entity){
        return entity.itemHandler.getStackInSlot(0).getItem() == ModItems.ROTTEN_COAL.get() || entity.itemHandler.getStackInSlot(0).getItem() == ModItems.ENERGY_SLIME_BALL.get();
    }

    private static void craftItem(EnergyGeneratorEntity entity, Item item) {

        if (entity.progress > entity.maxProgress) return;

        if(hasItem(entity) && isCoalItem(entity) && entity.progress <= 0){
            entity.itemHandler.extractItem(0,1, false);
            entity.progress = entity.maxProgress;
        }
        if (entity.progress >= 0 && entity.progress < entity.maxProgress){
                entity.energyStorage.receiveEnergy(10, false);
        }
    }


    private static void extractEnergy(EnergyGeneratorEntity entity) {
        entity.energyStorage.extractEnergy(50, false);
    }

    private static void extractEnergy(EnergyGeneratorEntity entity, int extractValue) {
        entity.energyStorage.extractEnergy(extractValue, false);
    }

    private static boolean hasEnoughEnergy(EnergyGeneratorEntity entity) {
        return entity.energyStorage.getEnergyStored() >= 10;
    }

    private void resetProgress() {
        this.progress = 0;
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

/*


 */