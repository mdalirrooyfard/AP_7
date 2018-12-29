package Model.Workshops;

public class Spinnery extends Workshop{
    public Spinnery(int item_x, int item_y){
        this.workShopName = "spinnery";
        this.inputs.add("wool");
        this.output = "string";
        upgradeCost = 100;
        this.item_x = item_x;
        this.item_y = item_y;
    }
}
