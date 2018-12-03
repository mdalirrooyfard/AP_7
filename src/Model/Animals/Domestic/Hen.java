package Model.Animals.Domestic;

import Model.Map;

import static Model.Constants.HEN_BUY_COST;
import static Model.Constants.HEN_SELL_COST;

public class Hen extends Domestic {
    public Hen (double x, double y , Map map){
        super(x , y);
        this.map = map ;
        this.setSellCost(HEN_SELL_COST);
        this.setBuyCost(HEN_BUY_COST);
    }

}
