package Model.Animals.Domestic;

import Model.Map;

import static Model.Constants.COW_BUY_COST;
import static Model.Constants.COW_SELL_COST;

public class Cow extends Domestic {
    public Cow (double x, double y , Map map){
        super(x , y, map);
        this.setSellCost(COW_SELL_COST);
        this.setBuyCost(COW_BUY_COST);
    }
}
