package Model.Workshops;

public class CakeBakery extends Workshop{

    public CakeBakery(int item_x, int item_y){
        this.workShopName = "cakeBakery";
        inputs.add("cookie");
        inputs.add("flour");
        this.item_x = item_x;
        this.item_y = item_y;
        output = "cake";
        upgradeCost = 100;
    }
}
