package Model.Transportation;

import Model.Entity;
import Model.Items.Item;

import java.util.ArrayList;
import java.util.Vector;

public class Transportations extends Entity {
    private Vector<Item> items = new Vector<>();
    private boolean isMoving = false ;
    private int workingTime ;
    private int currentTime ;
    private int spentMoney = 0;

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

    @Override
    public double getShowX() {
        return getX();
    }
    @Override
    public double getShowY() { return getY(); }
    public Vector<Item> getItems() {
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
