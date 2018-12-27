package Model.Workshops;

public class Spinnery extends Workshop{
    public Spinnery(){
        this.workShopName = "spinnery";
        this.inputs.add("wool");
        this.output = "string";
        upgradeCost = 100;
    }
}
