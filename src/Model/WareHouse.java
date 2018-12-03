package Model;


import java.util.ArrayList;

public class WareHouse extends Entity{
    private ArrayList<Entity> collectedEntities = new ArrayList<>();

    public ArrayList<Entity> getCollectedEntities() {
        return collectedEntities;
    }

    public void add(Entity entity){ collectedEntities.add(entity); }

    public WareHouse (){
    }
}
