package Model.Transportation;

import Model.Entity;
import Model.Items.Item;

import java.util.ArrayList;

public class Transportations extends Entity {
    private ArrayList<Item> items = new ArrayList<>();
    private boolean isMoving = false ;
    private int workingTime ;
    private int currentTime ;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void decreaseCurrentTime(int amount){
        this.currentTime -= amount;
    }
    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public void decreaseCurrentVolume(int amount){
        this.currentVolume -= amount;
    }

    public void increaseCurrentVolume(int amount){
        this.currentVolume += amount;
    }

    private int currentVolume;

    public int getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(int workingTime) {
        this.workingTime = workingTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
    public void add(Item item){
        items.add(item);
    }

    public void remove(Item item){
        items.remove(item);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.setWorkingTime(this.workingTime * 2/3);
        this.setVolume(this.volume * 2);
        this.setUpgradeCost(this.upgradeCost  + 50);
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}
