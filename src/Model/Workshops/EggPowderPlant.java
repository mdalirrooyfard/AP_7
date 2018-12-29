package Model.Workshops;

public class EggPowderPlant extends Workshop{
    public EggPowderPlant(int item_x, int item_y){
        this.workShopName = "eggPowderPlant";
        this.inputs.add("egg");
        this.output = "eggPowder";
        upgradeCost = 100;
        this.item_x = item_x;
        this.item_y = item_y;
    }
}
