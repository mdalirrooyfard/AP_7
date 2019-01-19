package Model;

import javafx.stage.Screen;

import static Model.Constants.WELL_BASE_MONEY;
import static Model.Constants.WELL_BASE_UPGRADE_COST;

public class Well extends Entity{
    private int currentVolume;

    public Well(int volume){
        this.setVolume(volume);
        this.setCurrentVolume(volume);
        this.x = Screen.getPrimary().getVisualBounds().getWidth() / 2 - 100;
        this.y = Screen.getPrimary().getVisualBounds().getHeight() / 2 - 270;
        this.setBuyCost(WELL_BASE_MONEY);
        this.setUpgradeCost(WELL_BASE_UPGRADE_COST);
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public void decrease (int amount){
        currentVolume -= amount ;
    }

    public void full (){
        currentVolume = this.getVolume() ;
    }
    @Override
    public void upgrade(){
        super.upgrade();
        this.setVolume( this.getVolume() * 2 );
        this.full();
        this.setBuyCost( this.getBuyCost() * 2 );
        this.setUpgradeCost(this.getUpgradeCost() + 50);
    }


}
