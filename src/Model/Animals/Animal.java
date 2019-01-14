package Model.Animals;

import Model.DIRECTION;
import Model.Entity;
import Model.Map;

public abstract class Animal extends Entity {
    protected Map map;
    protected String name;
    protected DIRECTION direction;

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Animal (double x , double y, Map map){
        this.x = x;
        this.y = y;
        this.map = map;
    }
    public void move(){
        int dir = (int)(Math.random() * 7 + 1);

        // up direction
        if(dir == 1){
            if(this.y == 0){
                // go down if you can't go up
                this.y ++ ;
                this.direction = DIRECTION.UP;
            }
            else{
                this.y -- ;
                this.direction = DIRECTION.DOWN;
            }
        }

        // right direction
        if(dir == 2){
            if(this.x == map.getWidth()-1){
                // go left if you can't go right
                this.x -- ;
                this.direction = DIRECTION.LEFT;
            }
            else{
                this.x ++ ;
                this.direction = DIRECTION.RIGHT;
            }
        }

        // down direction
        if(dir == 3){
            if(this.y == map.getLength()-1){
                // go up if you can't go down
                this.y -- ;
                this.direction = DIRECTION.UP;
            }
            else{
                this.y ++ ;
                this.direction = DIRECTION.DOWN;
            }
        }

        // left direction
        if(dir == 4){
            if(this.x == 0){
                // go right if you can't go left
                this.x ++ ;
                this.direction = DIRECTION.RIGHT;
            }
            else{
                this.x -- ;
                this.direction = DIRECTION.LEFT;
            }
        }
        //up-left
        if(dir == 5){
            if(this.x == 0 || this.y == 0){
                while (dir == 5){
                    dir = (int)(Math.random() * 7 + 1);
                }
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

    /*private boolean canMoveInThisDirection(DIRECTION direction)
    {
        boolean canMove =
    }*/
}
