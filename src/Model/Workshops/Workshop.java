package Model.Workshops;

import Model.Entity;

public class Workshop extends Entity {
    private int workingTime;
    private int currentTime;
    private boolean isWorking;
    private boolean isActive = false;
    private String workShopName;

    public void upgrade (){
        super.upgrade();
        workingTime = (int)(workingTime * 0.8);
    }
    public void currentTimeDecrease(int amount){
        currentTime -= amount;
    }
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
}
