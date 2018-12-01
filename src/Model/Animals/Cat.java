package Model.Animals;

import Model.Map;

public class Cat extends Animal {
    public Cat (double x, double y , Map map){
        super(x , y);
        this.map = map ;
    }
    @Override
    public void upgrade (){
        super.upgrade();
        //TODO complete
    }
}
