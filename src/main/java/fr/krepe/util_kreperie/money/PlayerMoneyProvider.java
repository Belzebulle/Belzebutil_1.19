package fr.krepe.util_kreperie.money;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerMoneyProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerMoney> PLAYER_MONEY = CapabilityManager.get(new CapabilityToken<PlayerMoney>() {

    });
    private PlayerMoney money = null;
    private final LazyOptional<PlayerMoney> optional = LazyOptional.of(this::createPlayerMoney);

    private PlayerMoney createPlayerMoney() {
        if(this.money == null){
            this.money = new PlayerMoney();
        }
        return this.money;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_MONEY){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerMoney().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerMoney().loadNBTData(nbt);
    }
}
