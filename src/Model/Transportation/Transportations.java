package Model.Transportation;

import Model.Entity;
import Model.Items.Item;

import java.util.ArrayList;

public class Transportations extends Entity {
    private ArrayList<Item> items = new ArrayList<>();
    private int workingTime ;
    private int currentTime ;

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
    }
}
