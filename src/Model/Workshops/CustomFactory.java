package Model.Workshops;

import java.util.ArrayList;

public class CustomFactory extends Workshop{
    public CustomFactory(String name , ArrayList<String> inputs , String output){
        this.workShopName = name;
        this.output = output;
        this.inputs = inputs;
        upgradeCost = 100;
    }
}
