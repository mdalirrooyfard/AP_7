package Model.Animals.Domestic;

import Model.Animals.Animal;
import Model.Items.Item;

public abstract class Domestic extends Animal {
    private int satiety ;

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

    }

    public void move(){

    }

    //public abstract Item produce();

    public Domestic(double x, double y){
        super(x , y);
    }
}
