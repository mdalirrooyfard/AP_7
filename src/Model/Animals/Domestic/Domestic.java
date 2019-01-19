package Model.Animals.Domestic;

import Model.Animals.Animal;
import Model.Constants;
import Model.DIRECTION;
import Model.Map;

import static Model.Constants.FULL_SATIETY;

public abstract class Domestic extends Animal {
    private int satiety = Constants.FULL_SATIETY;
    private int isEating = 0;

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    public void decreaseSatiety (int amount){
        satiety -= amount;
    }

    public void eat () {
        this.satiety = FULL_SATIETY;
    }

    public void move(){
        if(this.satiety >= 4){
            super.move();
        }
        else {
            DIRECTION direction = map.findNearestGrass((int)this.x , (int)this.y);
            super.smartMove(direction);
        }
    }

    //public abstract Item produce();

    public Domestic(double x, double y, Map map){
        super(x , y, map);
    }

    public int isEating() {
        return isEating;
    }

    public void setEating(int eating) {
        isEating = eating;
    }
}
