package Model.Workshops;

import javafx.stage.Screen;

public class EggPowderPlant extends Workshop{
    public EggPowderPlant(int item_x, int item_y){
        this.workShopName = "eggPowderPlant";
        this.inputs.add("egg");
        this.output = "eggPowder";
        upgradeCost = 100;
        this.x = 150;
        this.y = Screen.getPrimary().getVisualBounds().getHeight() / 2 - 260;
        this.item_x = item_x;
        this.item_y = item_y;
    }
}
