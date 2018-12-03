package Model;

import Model.Items.*;
import Model.Animals.Wild.*;

import java.util.ArrayList;

public class Map
{
    private int length , width;
    private Cell[][] cells;
    Map( int length , int width )
    {
        this.length = length;
        this.width = length;
        cells = new Cell[length][width];
    }

    public int getLength()
    {
        return length;
    }

    public int getWidth()
    {
        return width;
    }

    public Cell[][] getCells()
    {
        return cells;
    }

    public void clearCells()
    {
        for( int i = 0 ; i < length ; i++ )
            for( int j = 0 ; j < width ; j++ )
                cells[i][j].clear();
    }

    public void updateCells(ArrayList<Entity> stuffs )
    {
        for( Entity e : stuffs )
            cells[(int)Math.round(e.getY())][(int)Math.round(e.getX())].add(e);
    }

    public boolean[] cellStatus( int x , int y )
    {
        return cells[x][y].status();
    }

    public DIRECTION findNearestItem( int x , int y )
    {
        if( isThereItem() && !cellStatus(x,y)[1] )
        {
            boolean isItemFound = false;
            int level = 0 , Item_x = -1 , Item_y = -1;
            while( !isItemFound )
            {
                level++;
                Out:
                for( int i = level ; i > -1 ; i-- )
                {
                    if( y - i > -1 && x - level + i > -1 )
                        for( Entity e : cells[y - i][x - level + i].getStuffs() )
                        {
                            if( e instanceof Item )
                            {
                                isItemFound = true;
                                Item_x = x - level + i;
                                Item_y = y - i;
                                break Out;
                            }
                        }
                    if( y - i > -1 && x + level - i < width )
                        for( Entity e : cells[y - i][x + level - i].getStuffs() )
                        {
                            if( e instanceof Item )
                            {
                                isItemFound = true;
                                Item_x = x + level - i;
                                Item_y = y - i;
                                break Out;
                            }
                        }
                    if( y + i < length && x - level + i > -1 )
                        for( Entity e : cells[y + i][x - level + i].getStuffs() )
                        {
                            if( e instanceof Item )
                            {
                                isItemFound = true;
                                Item_x = x - level + i;
                                Item_y = y + i;
                                break Out;
                            }
                        }
                    if( y + i < length && x + level - i < width )
                        for( Entity e : cells[y + i][x + level - i].getStuffs() )
                        {
                            if( e instanceof Item )
                            {
                                isItemFound = true;
                                Item_x = x + level - i;
                                Item_y = y + i;
                                break Out;
                            }
                        }
                }
            }
            getDirection( x, y , Item_x , Item_y );
        }
        return DIRECTION.NONE;
    }

    public DIRECTION findNearestGrass( int x , int y )
    {
        if( isThereGrass() && !cellStatus(x,y)[3] )
        {
            boolean isGrassFound = false;
            int level = 0 , Grass_x = -1 , Grass_y = -1;
            while( !isGrassFound )
            {
                level++;
                Out:
                for( int i = level ; i > -1 ; i-- )
                {
                    if( y - i > -1 && x - level + i > -1 )
                        for( Entity e : cells[y - i][x - level + i].getStuffs() )
                        {
                            if( e instanceof Grass )
                            {
                                isGrassFound = true;
                                Grass_x = x - level + i;
                                Grass_y = y - i;
                                break Out;
                            }
                        }
                    if( y - i > -1 && x + level - i < width )
                        for( Entity e : cells[y - i][x + level - i].getStuffs() )
                        {
                            if( e instanceof Grass )
                            {
                                isGrassFound = true;
                                Grass_x = x + level - i;
                                Grass_y = y - i;
                                break Out;
                            }
                        }
                    if( y + i < length && x - level + i > -1 )
                        for( Entity e : cells[y + i][x - level + i].getStuffs() )
                        {
                            if( e instanceof Grass )
                            {
                                isGrassFound = true;
                                Grass_x = x - level + i;
                                Grass_y = y + i;
                                break Out;
                            }
                        }
                    if( y + i < length && x + level - i < width )
                        for( Entity e : cells[y + i][x + level - i].getStuffs() )
                        {
                            if( e instanceof Grass )
                            {
                                isGrassFound = true;
                                Grass_x = x + level - i;
                                Grass_y = y + i;
                                break Out;
                            }
                        }
                }
            }
            getDirection( x, y , Grass_x , Grass_y );
        }
        return DIRECTION.NONE;
    }

    public DIRECTION findNearestWild( int x , int y )
    {
        if( isThereWild() && !cellStatus(x,y)[0] )
        {
            boolean isWildFound = false;
            int level = 0 , Wild_x = -1 , Wild_y = -1;
            while( !isWildFound )
            {
                level++;
                Out:
                for( int i = level ; i > -1 ; i-- )
                {
                    if( y - i > -1 && x - level + i > -1 )
                        for( Entity e : cells[y - i][x - level + i].getStuffs() )
                        {
                            if( e instanceof Wild )
                            {
                                isWildFound = true;
                                Wild_x = x - level + i;
                                Wild_y = y - i;
                                break Out;
                            }
                        }
                    if( y - i > -1 && x + level - i < width )
                        for( Entity e : cells[y - i][x + level - i].getStuffs() )
                        {
                            if( e instanceof Wild )
                            {
                                isWildFound = true;
                                Wild_x = x + level - i;
                                Wild_y = y - i;
                                break Out;
                            }
                        }
                    if( y + i < length && x - level + i > -1 )
                        for( Entity e : cells[y + i][x - level + i].getStuffs() )
                        {
                            if( e instanceof Wild )
                            {
                                isWildFound = true;
                                Wild_x = x - level + i;
                                Wild_y = y + i;
                                break Out;
                            }
                        }
                    if( y + i < length && x + level - i < width )
                        for( Entity e : cells[y + i][x + level - i].getStuffs() )
                        {
                            if( e instanceof Wild )
                            {
                                isWildFound = true;
                                Wild_x = x + level - i;
                                Wild_y = y + i;
                                break Out;
                            }
                        }
                }
            }
            getDirection( x, y , Wild_x , Wild_y );
        }
        return DIRECTION.NONE;
    }

    public boolean isThereItem()
    {
        for( Cell[] c : cells )
            for( Cell cell : c )
                for( Entity e : cell.getStuffs() )
                    if( e instanceof Item )
                        return true;
        return false;
    }

    public boolean isThereGrass()
    {
        for( Cell[] c : cells )
            for( Cell cell : c )
                for( Entity e : cell.getStuffs() )
                    if( e instanceof Grass )
                        return true;
        return false;
    }

    public boolean isThereWild()
    {
        for( Cell[] c : cells )
            for( Cell cell : c )
                for( Entity e : cell.getStuffs() )
                    if( e instanceof Wild )
                        return true;
        return false;
    }

    public DIRECTION getDirection( int startX , int startY , int destinationX , int destinationY )
    {
        if( destinationY < startY )
            return DIRECTION.UP;
        else if( destinationY > startY )
            return DIRECTION.DOWN;
        else if( destinationX < startX )
            return DIRECTION.LEFT;
        else
            return DIRECTION.RIGHT;
    }
}
