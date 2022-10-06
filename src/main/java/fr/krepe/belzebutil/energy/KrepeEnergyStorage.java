package fr.krepe.belzebutil.energy;

import fr.krepe.belzebutil.block.entity.EnergyBlockEntity;
import fr.krepe.belzebutil.block.entity.LeadStationEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.EnergyStorage;

public abstract class KrepeEnergyStorage extends EnergyStorage {
    public KrepeEnergyStorage(int capacity) {
        super(capacity);
    }

    public KrepeEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public KrepeEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public KrepeEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
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

    public abstract void onEnergyChanged();

}