package Model.Animals;

import Model.Map;

public class Dog extends Animal {
    public Dog (double x, double y , Map map){
        super(x , y);
        this.map = map ;
    }
}
