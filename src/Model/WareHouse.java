package Model;


import Model.Items.Item;

import java.util.ArrayList;

public class WareHouse extends Entity{

    private ArrayList<Item> collectedItems = new ArrayList<>();
    private int currentVolume ;

    public WareHouse (int volume){
        this.setVolume(volume);
        this.currentVolume = getVolume();
        this.setUpgradeCost(Constants.WAREHOUSE_BASE_UPGRADE_COST);
    }

    public void decreaseCurrentVolume(int amount){
        this.currentVolume -= amount;
    }

    public void increaseCurrentVolume(int amount){
        this.currentVolume += amount;
    }
    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }


    public ArrayList<Item> getCollectedItems() {
        return collectedItems;
    }

    public void add(Item item){ collectedItems.add(item); }

    @Override
    public void upgrade() {
        super.upgrade();
        this.setVolume( this.getVolume() * 2 );
        this.setBuyCost( this.getBuyCost() * 2 );
        this.setUpgradeCost(this.getUpgradeCost() + 50);
    }
}
