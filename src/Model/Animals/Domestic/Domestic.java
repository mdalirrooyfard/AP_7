package Model.Animals.Domestic;

import Model.Animals.Animal;
import Model.Constants;
import Model.DIRECTION;
import Model.Map;


public abstract class Domestic extends Animal {
    private int satiety = Constants.MAX_DOMESTIC_SATIETY;
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

    public void increaseSatiety (int amount){
        satiety += amount;
    }


    public void eat () {
        this.satiety = Constants.MAX_DOMESTIC_SATIETY;
    }

    public void move(){
        if(this.satiety >= Constants.LEAST_DOMESTIC_SATIETY || !map.isThereGrass()){
            super.move();
        }
        else {
            DIRECTION direction = map.findNearestGrass((int)this.x , (int)this.y);
            try {
                super.smartMove(direction);
            }
            catch (StackOverflowError e){

            }
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
