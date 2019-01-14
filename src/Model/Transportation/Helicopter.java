package Model.Transportation;

import Model.Constants;
import javafx.stage.Screen;

public class Helicopter extends Transportations{
    public Helicopter(int volume){
        this.setVolume(volume);
        this.setCurrentVolume(volume);
        this.x = Screen.getPrimary().getVisualBounds().getWidth() / 2 + 200;
        this.y = Screen.getPrimary().getVisualBounds().getHeight() / 2 + 70;
        this.setWorkingTime(Constants.HELICOPTER_WORKING_TIME);
        this.setUpgradeCost(Constants.HELICOPTER_BASE_UPGRADE_COST);
    }
}
