package Model.Animals;

import Model.DIRECTION;
import Model.Map;

public class Dog extends Animal {
    public Dog (double x, double y , Map map){
        super(x , y);
        this.map = map ;
    }
    @Override
    public void move(){
        if(this.level == 1 || (this.level == 2 && !map.isThereWild())){
            super.move();
        }

        else {
            DIRECTION direction = map.findNearestWild((int)this.x , (int)this.y);
            super.smartMove(direction);
        }
    }
}
