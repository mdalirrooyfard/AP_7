package Model.Items;
import Model.Entity;

public class Item extends Entity {
    String kind ;

    Item(double x , double  y , String kind){
        this.x = x ;
        this.y = y ;
        this.kind = kind ;
    }
}
