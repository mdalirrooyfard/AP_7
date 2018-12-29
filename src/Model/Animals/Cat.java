package Model.Animals;

import Model.Constants;
import Model.DIRECTION;
import Model.Map;

import static Model.Constants.CAT_BUY_COST;

public class Cat extends Animal {

    public Cat (double x, double y , Map map){
        super(x , y , map);
        this.setBuyCost(CAT_BUY_COST);
        this.setUpgradeCost(Constants.CAT_BASE_UPGRADE_COST);
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
