package Model.Workshops;

import java.util.ArrayList;

public class WeavingFactory extends Workshop{
    public WeavingFactory(){
        this.workShopName = "weavingBakery";
        this.inputs.add("string");
        this.output = "cloth" ;
        upgradeCost = 100;
    }


}
