package Model.Animals;

import Model.DIRECTION;
import Model.Entity;
import Model.Map;

public abstract class Animal extends Entity {
    protected Map map;
    public Animal (double x , double y){
        this.x = x;
        this.y = y;
    }
    public void move(){
        int direction = (int)(Math.random() * 3 + 1);

        // up direction
        if(direction == 1){
            if(this.y == 0){
                // go down if you can't go up
                this.y ++ ;
            }
            else{
                this.y -- ;
            }
        }

        // right direction
        if(direction == 2){
            if(this.x == map.getWidth()-1){
                // go left if you can't go right
                this.x -- ;
            }
            else{
                this.x ++ ;
            }
        }

        // down direction
        if(direction == 3){
            if(this.y == map.getLength()-1){
                // go up if you can't go down
                this.y -- ;
            }
            else{
                this.y ++ ;
            }
        }

        // left direction
        if(direction == 4){
            if(this.x == 0){
                // go right if you can't go left
                this.x ++ ;
            }
            else{
                this.x -- ;
            }
        }
    }

    public void smartMove (DIRECTION direction){
        switch (direction){
            case UP:{
                this.y-- ;
                break;
            }
            case DOWN:{
                this.y++ ;
                break;
            }
            case RIGHT:{
                this.x++ ;
                break;
            }
            case LEFT:{
                this.x-- ;
                break;
            }
            default:{
                break;
            }
        }
    }
}
