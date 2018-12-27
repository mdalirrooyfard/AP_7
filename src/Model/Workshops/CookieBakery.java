package Model.Workshops;

public class CookieBakery extends Workshop{
    public CookieBakery(){
        this.workShopName = "cookieBakery";
        this.inputs.add("eggPowder");
        this.output = "cookie";
        upgradeCost = 100;

    }
}
