package fr.krepe.belzebutil.block.entity;

import fr.krepe.belzebutil.block.ModBlockEntities;
import fr.krepe.belzebutil.energy.KrepeEnergyStorage;
import fr.krepe.belzebutil.network.ModMessages;
import fr.krepe.belzebutil.network.packet.PacketSyncEnergyToClient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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
        super(ModBlockEntities.ENERGY_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
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
        if (cap == ForgeCapabilities.ENERGY) {
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
        if (pLevel.isClientSide) return;
        if (!hasHostileEntity(pLevel, pPos)) return;
        // for each slime get size
        List<Slime> list = slimeList(pLevel, pPos);

        System.out.println("Slime size 1 detected");

        pBlockEntity.energyStorage.receiveEnergy(10*list.size(), false);
    }

    public static boolean hasHostileEntity(Level pLevel, BlockPos pPos) {
        return pLevel.getEntitiesOfClass(Slime.class, new AABB(pPos).inflate(5), entity -> entity.getSize() == 1).size() > 0;
    }

    private static List<Slime> slimeList(Level pLevel, BlockPos pPos){
        return new ArrayList<>(pLevel.getEntitiesOfClass(Slime.class, new AABB(pPos).inflate(5), entity -> entity.getSize() == 1));
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