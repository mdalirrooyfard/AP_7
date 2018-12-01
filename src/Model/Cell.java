package Model;

import Model.Animals.Domestic;
import Model.Animals.Wild;
import Model.Items.Item;

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
    public void clear()
    {
        stuffs.clear();
    }
    public int status()
    {
        for( Entity e : stuffs )
            if( e instanceof Wild )
                return 1;
        for( Entity e : stuffs )
            if( e instanceof Item || e instanceof Domestic )
                return 2;
        return 0;
    }

}
