package Model;

import java.util.ArrayList;

public class Cell
{
    private ArrayList<Entity> stuffs = new ArrayList<>();

    Cell( ArrayList<Entity> stuffs )
    {
        this.stuffs = stuffs;
    }

    public ArrayList<Entity> getStuffs()
    {
        return stuffs;
    }
    public void add(Entity newStuff)
    {
        stuffs.add(newStuff);
    }
    public void remove(Entity stuff)
    {
        stuffs.remove(stuff);
    }

}
