package Model.Items;
import Model.Constants;
import Model.Entity;

import java.io.Serializable;

public class Item extends Entity implements Serializable {
    private String kind ;
    private boolean isTakenByCat = false;
    @Override
    public double getShowX() {
        return getX()*Constants.ANIMAL_SHOW_SCALE + Constants.ANIMAL_DISPLACEMENT_X;
    }
    @Override
    public double getShowY() {
        return getY()*Constants.ANIMAL_SHOW_SCALE + Constants.ANIMAL_DISPLACEMENT_Y;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public boolean isTakenByCat() {
        return isTakenByCat;
    }

    public void setTakenByCat(boolean takenByCat) {
        isTakenByCat = takenByCat;
    }

    public Item(double x , double  y , String kind){
        this.x = x ;
        this.y = y ;
        this.kind = kind ;
        setCostsAndVolume(kind);
    }

    public void setCostsAndVolume(String kind){
        switch (kind){
            case "egg":{
                this.setVolume(Constants.EGG_VOLUME);
                this.setBuyCost(Constants.EGG_BUY_COST);
                this.setSellCost(Constants.EGG_SALE_COST);
                break;
            }

            case "wool":{
                this.setVolume(Constants.WOOL_VOLUME);
                this.setBuyCost(Constants.WOOL_BUY_COST);
                this.setSellCost(Constants.WOOL_SALE_COST);
                break;
            }

            case "plume":{
                this.setVolume(Constants.PLUME_VOLUME);
                this.setBuyCost(Constants.PLUME_BUY_COST);
                this.setSellCost(Constants.PLUME_SALE_COST);
                break;
            }

            case "milk":{
                this.setVolume(Constants.MILK_VOLUME);
                this.setBuyCost(Constants.MILK_BUY_COST);
                this.setSellCost(Constants.MILK_SALE_COST);
                break;
            }

            case "horn":{
                this.setVolume(Constants.HORN_VOLUME);
                this.setBuyCost(Constants.HORN_BUY_COST);
                this.setSellCost(Constants.HORN_SALE_COST);
                break;
            }

            case "driedEggs":{
                this.setVolume(Constants.DRIEDEGGS_VOLUME);
                this.setBuyCost(Constants.DRIEDEGGS_BUY_COST);
                this.setSellCost(Constants.DRIEDEGGS_SELL_COST);
                break;
            }

            case "cake":{
                this.setVolume(Constants.CAKE_VOLUME);
                this.setBuyCost(Constants.CAKE_BUY_COST);
                this.setSellCost(Constants.CAKE_SALE_COST);
                break;
            }

            case "flouryCake":{
                this.setVolume(Constants.FLOURYCAKE_VOLUME);
                this.setBuyCost(Constants.FLOURYCAKE_BUY_COST);
                this.setSellCost(Constants.FLOURYCAKE_SALE_COST);
                break;
            }

            case "string":{
                this.setVolume(Constants.STRING_VOLUME);
                this.setBuyCost(Constants.STRING_BUY_COST);
                this.setSellCost(Constants.STRING_SALE_COST);
                break;
            }

            case "cloth":{
                this.setVolume(Constants.CLOTH_VOLUME);
                this.setBuyCost(Constants.CLOTH_BUY_COST);
                this.setSellCost(Constants.CLOTH_SALE_COST);
                break;
            }

            case "carnivalDress":{
                this.setVolume(Constants.CARNIVAL_DRESS_VOLUME);
                this.setBuyCost(Constants.CARNIVAL_DRESS_BUY_COST);
                this.setSellCost(Constants.CARNIVAL_DRESS_SALE_COST);
                break;
            }

            case "sourCream":{
                this.setVolume(Constants.SOUR_CREAM_VOLUME);
                this.setBuyCost(Constants.SOUR_CREAM_BUY_COST);
                this.setSellCost(Constants.SOUR_CREAM_SALE_COST);
                break;
            }

            case "curd":{
                this.setVolume(Constants.CURD_VOLUME);
                this.setBuyCost(Constants.CURD_BUY_COST);
                this.setSellCost(Constants.CURD_SALE_COST);
                break;
            }

            case "cheese":{
                this.setVolume(Constants.CHEESE_VOLUME);
                this.setBuyCost(Constants.CHEESE_COST);
                this.setSellCost(Constants.CHEESE_SALE_COST);
                break;
            }

            case "coloredPlume":{
                this.setVolume(Constants.COLORED_PLUME_VOLUME);
                this.setBuyCost(Constants.COLORED_PLUME_BUY_COST);
                this.setSellCost(Constants.COLORED_PLUME_SALE_COST);
                break;
            }

            case "adornment":{
                this.setVolume(Constants.ADORNMENT_VOLUME);
                this.setBuyCost(Constants.ADORNMENT_BUY_COST);
                this.setSellCost(Constants.ADORNMENT_SALE_COST);
                break;
            }

            case "brightHorn":{
                this.setVolume(Constants.BRIGHT_HORN_VOLUME);
                this.setBuyCost(Constants.BRIGHT_HORN_BUY_COST);
                this.setSellCost(Constants.BRIGHT_HORN_SALE_COST);
                break;
            }

            case "intermediate":{
                this.setVolume(Constants.INTERMEDIATE_VOLUME);
                this.setBuyCost(Constants.INTERMEDIATE_BUY_COST);
                this.setSellCost(Constants.INTERMEDIATE_SALE_COST);
                break;
            }

            case "souvenir":{
                this.setVolume(Constants.SOUVENIR_VOLUME);
                this.setBuyCost(Constants.SOUVENIR_BUY_COST);
                this.setSellCost(Constants.SOUVENIR_SALE_COST);
                break;
            }

            case "flour":{
                this.setVolume(Constants.FLOUR_VOLUME);
                this.setBuyCost(Constants.FLOUR_BUY_COST);
                this.setSellCost(Constants.FLOUR_SALE_COST);
                break;
            }

            case "cheeseFerment":{
                this.setVolume(Constants.CHEESE_FERMENT_VOLUME);
                this.setBuyCost(Constants.CHEESE_FERMENT_BUY_COST);
                this.setSellCost(Constants.CHEESE_FERMENT_SALE_COST);
                break;
            }

            case "varnish":{
                this.setVolume(Constants.VARNISH_VOLUME);
                this.setBuyCost(Constants.VARNISH_BUY_COST);
                this.setSellCost(Constants.VARNISH_SALE_COST);
                break;
            }

            case "megaPie":{
                this.setVolume(Constants.MEGA_PIE_VOLUME);
                this.setBuyCost(Constants.MEGA_PIE_BUY_COST);
                this.setSellCost(Constants.MEGA_PIE_SALE_COST);
                break;
            }

            case "cagedGrizzly":{
                this.setVolume(Constants.CAGED_WILD_VOLUME);
                this.setBuyCost(Constants.CAGED_GRIZZLY_BUY_COST);
                this.setSellCost(Constants.CAGED_GRIZZLY_SALE_COST);
                break;
            }

            case "cagedLion":{
                this.setVolume(Constants.CAGED_WILD_VOLUME);
                this.setBuyCost(Constants.CAGED_LION_BUY_COST);
                this.setSellCost(Constants.CAGED_LION_SALE_COST);
                break;
            }

            case "cagedBrownBear":{
                this.setVolume(Constants.CAGED_WILD_VOLUME);
                this.setBuyCost(Constants.CAGED_BROWN_BEAR_BUY_COST);
                this.setSellCost(Constants.CAGED_BROWN_BEAR_SALE_COST);
                break;
            }

            case "cagedJaguar":{
                this.setVolume(Constants.CAGED_WILD_VOLUME);
                this.setBuyCost(Constants.CAGED_JAGUAR_BUY_COST);
                this.setSellCost(Constants.CAGED_JAGUAR_SALE_COST);
                break;
            }

            case "cagedWhiteBear":{
                this.setVolume(Constants.CAGED_WILD_VOLUME);
                this.setBuyCost(Constants.CAGED_WHITE_BEAR_BUY_COST);
                this.setSellCost(Constants.CAGED_WHITE_BEAR_SALE_COST);
                break;
            }

            case "cookie":{
                this.setVolume(Constants.COOKIE_VOLUME);
                this.setBuyCost(Constants.COOKIE_COST);
                this.setSellCost(Constants.COOKIE_SALE_COST);
            }

            case "eggPowder":{
                this.setVolume(Constants.EGG_POWDER_VOLUME);
                this.setBuyCost(Constants.EGG_POWDER_BUY_COST);
                this.setSellCost(Constants.EGG_POWDER_SALE_COST);
            }

            default:{       //spruce wild animal
                this.setVolume(Constants.SPRUCE_WILD_VOLUME);
                this.setBuyCost(Constants.SPRUCE_WILD_BUY_COST);
                this.setSellCost(Constants.SPRUCE_WILD_SALE_COST);
            }
        }
    }
}
