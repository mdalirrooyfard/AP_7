package Model.Transportation;

import Model.Constants;
import javafx.stage.Screen;

public class Truck extends Transportations{
    private int spentMoney = 0;
    public Truck(int volume){
        this.setVolume(volume);
        this.setCurrentVolume(volume);
        this.x = Screen.getPrimary().getVisualBounds().getWidth() / 2 - 200;
        this.y = Screen.getPrimary().getVisualBounds().getHeight() / 2 + 70;
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
