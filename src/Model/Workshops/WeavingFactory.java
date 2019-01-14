package Model.Workshops;

import javafx.stage.Screen;

public class WeavingFactory extends Workshop{
    public WeavingFactory(int item_x, int item_y){
        this.workShopName = "weavingFactory";
        this.inputs.add("string");
        this.output = "cloth" ;
        upgradeCost = 100;
        this.x = Screen.getPrimary().getVisualBounds().getWidth() - 400;
        this.y = Screen.getPrimary().getVisualBounds().getHeight() / 2 - 120;
        this.item_x = item_x;
        this.item_y = item_y;
    }


}
