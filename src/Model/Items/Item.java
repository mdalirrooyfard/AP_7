package Model.Items;
import Model.Entity;

public class Item extends Entity {
    private String kind ;
    private boolean isTakenByCat = false;

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
    }
}
