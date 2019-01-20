package Model.Workshops;

import javafx.stage.Screen;

public class CakeBakery extends Workshop{

    public CakeBakery(int item_x, int item_y){
        this.workShopName = "cakeBakery";
        this.x = 40;
        this.y = Screen.getPrimary().getVisualBounds().getHeight() / 2 + 40;
        inputs.add("cookie");
        inputs.add("flour");
        this.item_x = item_x;
        this.item_y = item_y;
        output = "cake";
        upgradeCost = 100;
    }
}
