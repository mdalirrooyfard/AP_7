package Model;

import Model.Animals.Domestic.*;
import Model.Animals.*;
import Model.Items.Item;

import java.util.ArrayList;

public class Cell
{
    private ArrayList<Entity> stuffs;

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
        if( stuffs.size() > 0 )
            stuffs.remove(stuff);
    }
    public void clear()
    {
        stuffs.clear();
    }
    public int status()
    {
        for( Entity e : stuffs )
            if( e instanceof Dog )
                return 1;
        for( Entity e : stuffs )
            if( e instanceof Item || e instanceof Domestic )
                return 2;
        return 0;
    }

}
