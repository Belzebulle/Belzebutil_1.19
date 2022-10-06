package fr.krepe.belzebutil.network.packet;

import fr.krepe.belzebutil.block.entity.EnergyBlockEntity;
import fr.krepe.belzebutil.block.entity.EnergyGeneratorEntity;
import fr.krepe.belzebutil.block.entity.LeadStationEntity;
import fr.krepe.belzebutil.screen.EnergyGeneratorMenu;
import fr.krepe.belzebutil.screen.LeadStationMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncEnergyToClient {
    private final int energy;
    private final BlockPos pos;

    public PacketSyncEnergyToClient(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public PacketSyncEnergyToClient(FriendlyByteBuf buf) {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES

            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof LeadStationEntity blockEntity) {
                blockEntity.setEnergyLevel(energy);

                if(Minecraft.getInstance().player.containerMenu instanceof LeadStationMenu menu &&
                        menu.blockEntity.getBlockPos().equals(pos)) {
                    blockEntity.setEnergyLevel(energy);
                }
            }

            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof EnergyGeneratorEntity blockEntity) {
                blockEntity.setEnergyLevel(energy);

                if(Minecraft.getInstance().player.containerMenu instanceof EnergyGeneratorMenu menu &&
                        menu.blockEntity.getBlockPos().equals(pos)) {
                    blockEntity.setEnergyLevel(energy);
                }
            }

            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof EnergyBlockEntity blockEntity) {
                blockEntity.setEnergyLevel(energy);
/*
                if(Minecraft.getInstance().player.containerMenu instanceof EnergyGeneratorMenu menu &&
                        menu.blockEntity.getBlockPos().equals(pos)) {
                    blockEntity.setEnergyLevel(energy);
                }
 */
            }
        });
        return true;
    }
}
