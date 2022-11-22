package fr.krepe.belzebutil.energy;

import fr.krepe.belzebutil.block.entity.LeadStationEntity;
import fr.krepe.belzebutil.entity.ESlimeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.energy.EnergyStorage;

import java.util.ArrayList;
import java.util.List;

public abstract class BelzeEnergyStorage extends EnergyStorage {
    public BelzeEnergyStorage(int capacity) {
        super(capacity);
    }

    public BelzeEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public BelzeEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public BelzeEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extractedEnergy = super.extractEnergy(maxExtract, simulate);
        if(extractedEnergy != 0) {
            onEnergyChanged();
        }

        return extractedEnergy;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receiveEnergy = super.receiveEnergy(maxReceive, simulate);
        if(receiveEnergy != 0) {
            onEnergyChanged();
        }

        return receiveEnergy;
    }

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

    public int transferEnergy(int energy, BlockEntity entity){
        if(entity instanceof LeadStationEntity leadStationEntity){
            extractEnergy(energy, false);
            leadStationEntity.energyStorage.receiveEnergy(energy,false);
        }
        return energy;
    }

    public int hasESlimeNear(Level pLevel, BlockPos pPos){
        if(!hasHostileEntity(pLevel, pPos)) return 0;
        List<ESlimeEntity> list = slimeList(pLevel, pPos);
        return list.size();
    }

    public boolean hasHostileEntity(Level pLevel, BlockPos pPos) {
        return pLevel.getEntitiesOfClass(ESlimeEntity.class, new AABB(pPos).inflate(5)).size() > 0;
    }

    private List<ESlimeEntity> slimeList(Level pLevel, BlockPos pPos){
        return new ArrayList<>(pLevel.getEntitiesOfClass(ESlimeEntity.class, new AABB(pPos).inflate(5)));
    }


    public abstract void onEnergyChanged();

}