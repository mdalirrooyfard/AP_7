package Model.Animals.Wild;

import Model.Animals.Animal;
import Model.Map;

public abstract class Wild extends Animal {
    boolean isInCage = false;
    public boolean isInCage() {
        return isInCage;
    }

    public void setInCage(boolean inCage) {
        isInCage = inCage;
    }

    public Wild(double x, double y, Map map){
        super(x , y, map);
    }

    @Override
    public void move() {
        super.move();
    }

}
