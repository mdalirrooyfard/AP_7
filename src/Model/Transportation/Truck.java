package Model.Transportation;

import Model.Constants;

public class Truck extends Transportations{
    private int spentMoney = 0;
    public Truck(int volume){
        this.setVolume(volume);
        this.setCurrentVolume(volume);
        this.setWorkingTime(Constants.TRUCK_WORKING_TIME);
        this.setUpgradeCost(Constants.TRUCK_BASE_UPGRADE_COST);
    }

    public int getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(int spentMoney) {
        this.spentMoney = spentMoney;
    }

    public void increaseSpentMoney (int amount){
        this.spentMoney += amount;
    }
}
