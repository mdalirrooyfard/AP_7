package Model;

import static Model.Constants.WELL_BASE_MONEY;
import static Model.Constants.WELL_BASE_UPGRADE_COST;
import static Model.Constants.WELL_CAPACITY;

public class Well extends Entity{
    private int currentVolume;
    private int upgradeCost;

    public Well(){
        this.setVolume(WELL_CAPACITY);
        this.setCurrentVolume(WELL_CAPACITY);
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

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }
}
