package Model.Workshops;

public class EggPowderPlant extends Workshop{
    public EggPowderPlant(){
        this.workShopName = "eggPowderPlant";
        this.inputs.add("egg");
        this.output = "eggPowder";
        upgradeCost = 100;
    }
}
