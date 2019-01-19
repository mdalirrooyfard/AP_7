package Model.Animals;

import Model.Constants;
import Model.DIRECTION;
import Model.Entity;
import Model.Map;

public abstract class Animal extends Entity {
    protected Map map;
    protected String name;
    protected DIRECTION direction;

    @Override
    public double getShowX() {
        return getX()*Constants.ANIMAL_SHOW_SCALE + Constants.ANIMAL_DISPLACEMENT_X;
    }
    @Override
    public double getShowY() {
        return getY()*Constants.ANIMAL_SHOW_SCALE + Constants.ANIMAL_DISPLACEMENT_Y;
    }

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
    public void move()
    {
        int dir = (int)(Math.random() * 7 + 1);
        while ( !canMoveInThisDirection(dir) )
            dir = (int)(Math.random() * 7 + 1);
        switch (dir)
        {
            case 7:
                x += Constants.scale;
                y -= Constants.scale;
                direction = DIRECTION.UP_RIGHT;
                break;
            case 6:
                x -= Constants.scale;
                y += Constants.scale;
                direction = DIRECTION.DOWN_LEFT;
                break;
            case 5:
                x -= Constants.scale;
                y -= Constants.scale;
                direction = DIRECTION.UP_LEFT;
                break;
            case 8:
                x += Constants.scale;
                y += Constants.scale;
                direction = DIRECTION.DOWN_RIGHT;
                break;
            case 1:
                y -= Constants.scale;
                direction = DIRECTION.UP;
                break;
            case 3:
                y += Constants.scale;
                direction = DIRECTION.DOWN;
                break;
            case 4:
                x -= Constants.scale;
                direction = DIRECTION.LEFT;
                break;
            case 2:
                x += Constants.scale;
                direction = DIRECTION.RIGHT;
                break;
            default:
                direction = DIRECTION.NONE;
        }
    }

    public void smartMove (DIRECTION direction)
    {
        switch (direction){
            case UP:
                this.y -= Constants.scale ;
                break;

            case DOWN:
                this.y += Constants.scale ;
                break;

            case RIGHT:
                this.x += Constants.scale;
                break;

            case LEFT:
                this.x -= Constants.scale;
                break;

            case UP_LEFT:
                this.x -= Constants.scale;
                this.y -= Constants.scale;
                break;

            case DOWN_LEFT:
                this.x -= Constants.scale;
                this.y += Constants.scale;
                break;
            case UP_RIGHT:
                this.x += Constants.scale;
                this.y -= Constants.scale;
                break;
            case DOWN_RIGHT:
                this.x += Constants.scale;
                this.y += Constants.scale;
                break;
            default:
                break;

        }
    }

    private boolean canMoveInThisDirection(int dir)
    {
        switch (dir)
        {
            case 7:
                if( x + Constants.scale < map.getWidth() && y - Constants.scale >= 0 )
                    return true;
            case 6:
                if( x - Constants.scale >= 0 && y + Constants.scale < map.getLength() )
                    return true;
            case 5:
                if( x - Constants.scale >= 0 && y - Constants.scale >= 0 )
                    return true;
            case 8:
                if( x + Constants.scale < map.getWidth() && y + Constants.scale < map.getLength() )
                    return true;
            case 1:
                if( y - Constants.scale >= 0 )
                    return true;
            case 3:
                if( y + Constants.scale < map.getLength() )
                    return true;
            case 4:
                if( x - Constants.scale >= 0 )
                    return true;
            case 2:
                if( x + Constants.scale < map.getWidth() )
                    return true;
        }
        return false;
    }
}
