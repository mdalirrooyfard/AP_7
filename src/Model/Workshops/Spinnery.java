package Model.Workshops;

import javafx.stage.Screen;

public class Spinnery extends Workshop{
    public Spinnery(int item_x, int item_y){
        this.workShopName = "spinnery";
        this.inputs.add("wool");
        this.output = "string";
        upgradeCost = 100;
        this.x = Screen.getPrimary().getVisualBounds().getWidth() - 350;
        this.y = Screen.getPrimary().getVisualBounds().getHeight() / 2 - 270;
        this.item_x = item_x;
        this.item_y = item_y;
    }
}
