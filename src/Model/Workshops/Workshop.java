package Model.Workshops;

import Model.Entity;

import java.util.ArrayList;

public class Workshop extends Entity {
    protected ArrayList<String> inputs;
    protected String output;
    protected int workingTime = 1000;
    protected int currentTime;
    protected boolean isWorking;
    protected String workShopName;

    public void upgrade (){
        super.upgrade();
        workingTime = (int)(workingTime * 0.8);
    }
    public void currentTimeDecrease(int amount){
        currentTime -= amount;
    }

    public ArrayList<String> getInputs() {
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
