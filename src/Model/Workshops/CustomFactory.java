package Model.Workshops;

import java.util.Vector;

public class CustomFactory extends Workshop{
    public CustomFactory(String name , Vector<String> inputs , String output, int item_x, int item_y){
        this.workShopName = name;
        this.output = output;
        this.inputs = inputs;
        upgradeCost = 100;
        this.x = 500;
        this.y = 600;
        this.item_x = item_x;
        this.item_y = item_y;
    }
}
