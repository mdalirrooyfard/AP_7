package Model.Animals.Wild;

import Model.Animals.Animal;

public abstract class Wild extends Animal {
    boolean isInCage = false;
    public boolean isInCage() {
        return isInCage;
    }

    public void setInCage(boolean inCage) {
        isInCage = inCage;
    }

    public Wild(double x, double y){
        super(x , y);
    }

    @Override
    public void move() {
        super.move();
    }

}
