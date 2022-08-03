package fr.krepe.util_kreperie.money;

import net.minecraft.nbt.CompoundTag;

public class PlayerMoney {
    private int money;
    private final int MIN_MONEY = 0;
    private final int MAX_MONEY = 10000;

    public int getThirst(){
        return money;
    }

    public void addThirst(int add){
        this.money = Math.min(money + add, MAX_MONEY);
    }

    public void subThirst(int sub){
        this.money = Math.max(money - sub, MIN_MONEY);
    }

    public void copyFrom(PlayerMoney source){
        this.money = source.money;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("money", money);
    }

    public void loadNBTData(CompoundTag nbt){
        money = nbt.getInt("money");
    }
}
