package Model.Workshops;

import Model.Entity;

import java.util.ArrayList;
import java.util.Vector;

public class Workshop extends Entity {
    protected int item_x;
    protected int item_y;
    protected int count;
    protected Vector<String> inputs = new Vector<>();
    protected String output;
    protected int workingTime = 10;
    protected int currentTime;
    protected boolean isWorking;
    protected String workShopName;
    //todo workshop places

    @Override
    public double getShowX() {
        return getX();
    }
    @Override
    public double getShowY() {
        return getY();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void upgrade (){
        super.upgrade();
        workingTime = (int)(workingTime * 0.8);
        upgradeCost = (int)(upgradeCost * 1.5);
    }
    public void currentTimeDecrease(int amount){
        currentTime -= amount;
    }

    public void setItem_x(int item_x) {
        this.item_x = item_x;
    }

    public int getItem_x() {
        return item_x;
    }

    public void setItem_y(int item_y) {
        this.item_y = item_y;
    }

    public int getItem_y() {
        return item_y;
    }

    public Vector<String> getInputs() {
        return inputs;
    }

    public String getOutput() {
        return output;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(int workingTime) {
        this.workingTime = workingTime;
    }

    public String getWorkShopName() {
        return workShopName;
    }
}
