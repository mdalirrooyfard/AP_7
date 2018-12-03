package Model.Animals;

import Model.DIRECTION;
import Model.Map;

public class Cat extends Animal {
    public Cat (double x, double y , Map map){
        super(x , y);
        this.map = map ;
    }
    @Override
    public void upgrade (){
        super.upgrade();
        //TODO complete
    }

    @Override
    public void move() {
        if(this.level == 1 || (this.level == 2 && !map.isThereItem())){
            super.move();
        }

        else {
            DIRECTION direction = map.findNearestItem((int)this.x , (int)this.y);
            super.smartMove(direction);
        }
    }
}
