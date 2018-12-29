package Model.Animals.Domestic;

import Model.Map;

import static Model.Constants.SHEEP_BUY_COST;
import static Model.Constants.SHEEP_SELL_COST;

public class Sheep extends Domestic {
    public Sheep(double x, double y , Map map){
        super(x , y, map);
        this.setSellCost(SHEEP_SELL_COST);
        this.setBuyCost(SHEEP_BUY_COST);
    }
}
