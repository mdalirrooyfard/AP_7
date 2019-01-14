package Model.Workshops;

import java.util.ArrayList;

public class WeavingFactory extends Workshop{
    public WeavingFactory(int item_x, int item_y){
        this.workShopName = "weavingFactory";
        this.inputs.add("string");
        this.output = "cloth" ;
        upgradeCost = 100;
        this.item_x = item_x;
        this.item_y = item_y;
    }


}
