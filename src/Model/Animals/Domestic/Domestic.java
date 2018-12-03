package Model.Animals.Domestic;

import Model.Animals.Animal;
import Model.DIRECTION;
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
        if(this.satiety >= 4){
            super.move();
        }
        else {
            DIRECTION direction = map.findNearestGrass((int)this.x , (int)this.y);
            super.smartMove(direction);
        }
    }

    //public abstract Item produce();

    public Domestic(double x, double y){
        super(x , y);
    }
}
