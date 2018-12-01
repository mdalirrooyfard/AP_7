package Model.Animals;

import Model.Entity;
import Model.Map;

public abstract class Animal extends Entity {
    protected Map map;
    public Animal (double x , double y){
        this.x = x;
        this.y = y;
    }
    public void move(){

    }
}
