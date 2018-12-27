package Model.Workshops;

public class SewingFactory extends Workshop{
    public SewingFactory (){
        this.workShopName = "sewingFactory";
        this.inputs.add("cloth");
        this.inputs.add("adornment");
        this.output = "carnivalDress";
        upgradeCost = 100;
    }
}
