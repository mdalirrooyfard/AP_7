package Model;

import java.lang.reflect.Array;
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
            cells[(int)Math.round(e.getX())][(int)Math.round(e.getY())].add(e);
    }

    public int cellStatus( int x , int y )
    {
        return cells[x][y].status();
    }



}
