package Model;

import javafx.stage.Screen;

import static Model.Constants.WELL_BASE_MONEY;
import static Model.Constants.WELL_BASE_UPGRADE_COST;

public class Well extends Entity
{
    private int currentVolume;
    private boolean isWorking = false;

    public Well(int volume)
    {
        this.setVolume(volume);
        this.setCurrentVolume(volume);
        this.x = Screen.getPrimary().getVisualBounds().getWidth() / 2 - 100;
        this.y = 30;
        this.setBuyCost(WELL_BASE_MONEY);
        this.setUpgradeCost(WELL_BASE_UPGRADE_COST);
    }

    @Override
    public double getShowX() {
        return getX();
    }

    @Override
    public double getShowY() {
        return getY();
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public void increase (int amount){
        currentVolume += amount ;
    }

    public void full (){
        currentVolume = this.getVolume() ;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    @Override
    public void upgrade()
    {
        super.upgrade();
        this.setVolume( this.getVolume() + 5);
        this.full();
        this.setBuyCost( this.getBuyCost() * 2 );
        this.setUpgradeCost(this.getUpgradeCost() + 50);
    }
}
