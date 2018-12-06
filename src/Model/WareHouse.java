package Model;


import java.util.ArrayList;

public class WareHouse extends Entity{
    private ArrayList<Entity> collectedEntities = new ArrayList<>();
    private int currentVolume ;

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

    public WareHouse (){
        this.setVolume(Constants.WAREHOUSE_VOLUME);
        this.currentVolume = getVolume();
    }
}
