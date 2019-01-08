package Model;

public class Player implements Comparable<Player>
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

    @Override
    public int compareTo(Player player)
    {
        return Integer.compare(Integer.parseInt(name),Integer.parseInt(player.getName()));
    }
}
