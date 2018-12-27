package Model.Workshops;

public class CakeBakery extends Workshop{

    public CakeBakery(){
        this.workShopName = "cakeBakery";
        inputs.add("cookie");
        inputs.add("flour");
        output = "cake";
        upgradeCost = 100;
    }
}
