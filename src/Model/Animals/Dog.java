package Model.Animals;

import Model.DIRECTION;
import Model.Map;

import static Model.Constants.DOG_BUY_COST;

public class Dog extends Animal {
    public Dog (double x, double y , Map map){
        super(x , y);
        this.map = map ;
        this.setBuyCost(DOG_BUY_COST);
    }
    @Override
    public void move(){
        if(!map.isThereWild()){
            super.move();
        }

        else {
            DIRECTION direction = map.findNearestWild((int)this.x , (int)this.y);
            super.smartMove(direction);
        }
    }
}
