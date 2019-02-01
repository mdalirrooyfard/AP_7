package Model;


import Model.Items.Item;

import java.util.Vector;

public class WareHouse extends Entity{

    private Vector<Item> collectedItems = new Vector<>();
    @Override
    public double getShowX() {
        return getX();
    }
    @Override
    public double getShowY() {
        return getY();
    }
    private int currentVolume ;

    public WareHouse (int volume){
        this.x = Constants.WIDTH/2 - 110;
        this.y = Constants.HEIGHT - 150;
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


    public Vector<Item> getCollectedItems() {
        return collectedItems;
    }

    public void add(Item item){ collectedItems.add(item); }

    @Override
    public void upgrade() {
        super.upgrade();
        int itemsVolume = volume - currentVolume;
        volume *= 2;
        buyCost *= 2;
        upgradeCost += 50;
        currentVolume = volume - itemsVolume;
    }
}
