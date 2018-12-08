package Model;


import java.util.ArrayList;

public class WareHouse extends Entity{
    private int upgradeCost;

    private ArrayList<Entity> collectedEntities = new ArrayList<>();
    private int currentVolume ;

    public WareHouse (){
        this.setVolume(Constants.WAREHOUSE_VOLUME);
        this.currentVolume = getVolume();
        this.setUpgradeCost(Constants.WAREHOUSE_BASE_UPGRADE_COST);
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }


    public ArrayList<Entity> getCollectedEntities() {
        return collectedEntities;
    }

    public void add(Entity entity){ collectedEntities.add(entity); }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.setVolume( this.getVolume() * 2 );
        this.setBuyCost( this.getBuyCost() * 2 );
        this.setUpgradeCost(this.getUpgradeCost() + 50);
    }
}
