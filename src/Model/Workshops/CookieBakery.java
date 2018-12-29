package Model.Workshops;

public class CookieBakery extends Workshop{
    public CookieBakery(int item_x, int item_y){
        this.workShopName = "cookieBakery";
        this.inputs.add("eggPowder");
        this.output = "cookie";
        upgradeCost = 100;
        this.item_x = item_x;
        this.item_y = item_y;

    }
}
