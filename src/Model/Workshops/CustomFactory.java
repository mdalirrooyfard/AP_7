package Model.Workshops;

import Model.Constants;

public class CustomFactory extends Workshop
{
    public CustomFactory(int item_x, int item_y)
    {
        upgradeCost = 100;
        this.x = Constants.WIDTH / 2;
        this.y = 30;
        this.item_x = item_x;
        this.item_y = item_y;
        this.workShopName = "customFactory";
    }


}
