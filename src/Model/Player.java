package Model;

public class Player
{
    private String name;
    private int lastLevel;
    public Player( String name )
    {
        this.name = name;
        lastLevel = 1;
    }

    public String getName()
    {
        return name;
    }

    public int getLastLevel()
    {
        return lastLevel;
    }
}
