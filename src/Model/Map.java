package Model;

import Model.Animals.Wild.Wild;
import Model.Items.Item;

import java.util.Vector;

public class Map
{
    private int length , width;
    private Cell[][] cells;

    public Map( int length , int width )
    {
        this.length = length;
        this.width = width;
        cells = new Cell[length][width];
        for ( int i = 0 ; i < length ; i++ )
            for( int j = 0 ; j < width ; j++ )
                cells[i][j] = new Cell();

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

    public void updateCells(Vector<Entity> stuffs )
    {
        clearCells();
        for( Entity e : stuffs )
            cells[(int)(e.getY())][(int)(e.getX())].add(e);
    }

    public boolean[] cellStatus( int x , int y )
    {
        return cells[y][x].status();
    }

    public DIRECTION findNearestItem( int x , int y )
    {
        if( isThereItem() && !cellStatus(x,y)[1] )
        {
            int level = 0;
            while( true )
            {
                level++;
                for( int i = -level ; i <= level ; i++ )
                {
                    if( x + i > -1 && x + i < width )
                    {
                        if( Math.abs(i) == level )
                        {
                            for( int j = -level ; j <= level ; j++ )
                                if( y + j < length && y + j > -1 )
                                    for( Entity e : cells[y + j][x + i].getStuffs() )
                                        if( e instanceof Item && !e.isDead() )
                                            return getDirection(x,y,x+i,y+j);
                        }
                        else
                        {
                            if (y - level > -1)
                                for (Entity e : cells[y - level][x + i].getStuffs())
                                    if( e instanceof Item && !e.isDead() )
                                        return getDirection(x,y,x+i,y-level);
                            if (y + level < length)
                                for (Entity e : cells[y + level][x + i].getStuffs())
                                    if( e instanceof Item && !e.isDead() )
                                        return getDirection(x,y,x+i,y+level);
                        }
                    }
                }
            }
        }
        return DIRECTION.NONE;
    }

    public DIRECTION findNearestGrass( int x , int y )
    {
        if( isThereGrass() && !cellStatus(x,y)[3] )
        {
            int level = 0;
            while( true )
            {
                level++;
                for( int i = -level ; i <= level ; i++ )
                {
                    if( x + i > -1 && x + i < width )
                    {
                        if( Math.abs(i) == level )
                        {
                            for( int j = -level ; j <= level ; j++ )
                                if( y + j < length && y + j > -1 )
                                    for( Entity e : cells[y + j][x + i].getStuffs() )
                                        if( e instanceof Grass && !e.isDead() )
                                            return getDirection(x,y,x+i,y+j);
                        }
                        else
                        {
                            if (y - level > -1)
                                for (Entity e : cells[y - level][x + i].getStuffs())
                                    if( e instanceof Grass && !e.isDead() )
                                        return getDirection(x,y,x+i,y-level);
                            if (y + level < length)
                                for (Entity e : cells[y + level][x + i].getStuffs())
                                    if( e instanceof Grass && !e.isDead() )
                                        return getDirection(x,y,x+i,y+level);
                        }
                    }
                }
            }
        }
        return DIRECTION.NONE;
    }

    public DIRECTION findNearestWild( int x , int y )
    {
        if( isThereWild() && !cellStatus(x,y)[0] )
        {
            int level = 0;
            while( true )
            {
                level++;
                for( int i = -level ; i <= level ; i++ )
                {
                    if( x + i > -1 && x + i < width )
                    {
                        if( Math.abs(i) == level )
                        {
                            for( int j = -level ; j <= level ; j++ )
                                if( y + j < length && y + j > -1 )
                                    for( Entity e : cells[y + j][x + i].getStuffs() )
                                        if( e instanceof Wild && !e.isDead() )
                                            return getDirection(x,y,x+i,y+j);
                        }
                        else
                        {
                            if (y - level > -1)
                                for (Entity e : cells[y - level][x + i].getStuffs())
                                    if( e instanceof Wild && !e.isDead() )
                                        return getDirection(x,y,x+i,y-level);
                            if (y + level < length)
                                for (Entity e : cells[y + level][x + i].getStuffs())
                                    if( e instanceof Wild && !e.isDead() )
                                        return getDirection(x,y,x+i,y+level);
                        }
                    }
                }
            }
        }
        return DIRECTION.NONE;
    }

    public boolean isThereItem()
    {
        for( Cell[] c : cells )
            for( Cell cell : c )
                for( Entity e : cell.getStuffs() )
                    if( e instanceof Item && !e.isDead() )
                        return true;
        return false;
    }

    public boolean isThereGrass()
    {
        for( Cell[] c : cells )
            for( Cell cell : c )
                for( Entity e : cell.getStuffs() )
                    if( e instanceof Grass && !e.isDead() )
                        return true;
        return false;
    }

    public boolean isThereWild()
    {
        for( Cell[] c : cells )
            for( Cell cell : c )
                for( Entity e : cell.getStuffs() )
                    if( e instanceof Wild && !e.isDead() )
                        return true;
        return false;
    }

    private DIRECTION getDirection( int startX , int startY , int destinationX , int destinationY )
    {
        if( destinationX == startX && destinationY < startY )
            return DIRECTION.UP;
        if( destinationX == startX && destinationY > startY )
            return DIRECTION.DOWN;
        if( destinationY == startY && destinationX < startX )
            return DIRECTION.LEFT;
        if( destinationY == startY && destinationX > startX )
            return DIRECTION.RIGHT;
        if( destinationX < startX && destinationY < startY )
            return DIRECTION.UP_LEFT;
        if( destinationX > startX && destinationY > startY )
            return DIRECTION.DOWN_RIGHT;
        if( destinationX > startX && destinationY < startY )
            return DIRECTION.UP_RIGHT;
        if( destinationX < startX && destinationY > startY )
            return DIRECTION.DOWN_LEFT;
        return DIRECTION.NONE;
    }
}
