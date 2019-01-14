package Model.Workshops;

import javafx.stage.Screen;

public class SewingFactory extends Workshop{
    public SewingFactory (int item_x, int item_y){
        this.workShopName = "sewingFactory";
        this.inputs.add("cloth");
        this.inputs.add("adornment");
        this.output = "carnivalDress";
        upgradeCost = 100;
        this.x = Screen.getPrimary().getVisualBounds().getWidth() - 300;
        this.y = Screen.getPrimary().getVisualBounds().getHeight() / 2 + 30;
        this.item_x = item_x;
        this.item_y = item_y;
    }
}
