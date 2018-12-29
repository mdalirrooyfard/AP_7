package Model.Workshops;

public class SewingFactory extends Workshop{
    public SewingFactory (int item_x, int item_y){
        this.workShopName = "sewingFactory";
        this.inputs.add("cloth");
        this.inputs.add("adornment");
        this.output = "carnivalDress";
        upgradeCost = 100;
        this.item_x = item_x;
        this.item_y = item_y;
    }
}
