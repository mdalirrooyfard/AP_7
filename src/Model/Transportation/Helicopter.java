package Model.Transportation;

import Model.Constants;
import javafx.stage.Screen;

public class Helicopter extends Transportations
{
    private int spentMoney = 0;

    public Helicopter(int volume)
    {
        this.setVolume(volume);
        this.setCurrentVolume(volume);
        this.x = Screen.getPrimary().getVisualBounds().getWidth() / 2 + 180;
        this.y = Screen.getPrimary().getVisualBounds().getHeight() - 210;
        this.setWorkingTime(Constants.HELICOPTER_WORKING_TIME);
        this.setUpgradeCost(Constants.HELICOPTER_BASE_UPGRADE_COST);
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

    public void decreaseSpentMoney(int amount){
        this.spentMoney -= amount;
    }
}
