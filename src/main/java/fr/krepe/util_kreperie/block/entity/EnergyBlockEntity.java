package fr.krepe.util_kreperie.block.entity;

import fr.krepe.util_kreperie.block.ModBlockEntity;
import fr.krepe.util_kreperie.energy.KrepeEnergyStorage;
import fr.krepe.util_kreperie.network.ModMessages;
import fr.krepe.util_kreperie.network.packet.PacketSyncEnergyToClient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EnergyBlockEntity extends BlockEntity implements MenuProvider {

    public final KrepeEnergyStorage energyStorage = new KrepeEnergyStorage(60000, 200) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new PacketSyncEnergyToClient(this.energy, worldPosition));
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 2000;
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    
    public EnergyBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, BlockEntityType<?> type, ContainerData data) {
        super(pType, pWorldPosition, pBlockState);
        this.data = data;
    }




    public void setEnergyLevel(int energyLevel) {
        this.energyStorage.setEnergy(energyLevel);
    }

    public EnergyBlockEntity(BlockPos pWorldPosition, BlockState pBlockState){
        super(ModBlockEntity.ENERGY_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return EnergyBlockEntity.this.progress;
                    case 1: return EnergyBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0: EnergyBlockEntity.this.progress = value; break;
                    case 1: EnergyBlockEntity.this.maxProgress = value; break;
                }
            }

            public int getCount() { return 2; }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Energy Block");
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        return super.getCapability(cap, side);
    }


    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putInt("energy" , energyStorage.getEnergyStored());

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        energyStorage.setEnergy(nbt.getInt("energy"));
    }


    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, EnergyBlockEntity pBlockEntity) {
    }

    private static void extractEnergy(EnergyBlockEntity entity) {
        entity.energyStorage.extractEnergy(50, false);
    }

    private static void extractEnergy(EnergyBlockEntity entity, int extractValue) {
        entity.energyStorage.extractEnergy(extractValue, false);
    }

    private static boolean hasEnoughEnergy(EnergyBlockEntity entity) {
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

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return null;
    }
}

/*


 */