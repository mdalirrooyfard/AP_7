package Model;

public class Player
{
    private String name;
    private int lastLevel , id;
    private boolean isLastPlayer;
    public Player( String name, int id)
    {
        this.name = name;
        this.id = id;
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

    public int getId(){
        return id;
    }

    public boolean isLastPlayer()
    {
        return isLastPlayer;
    }

    public void setLastPlayer(boolean lastPlayer)
    {
        isLastPlayer = lastPlayer;
    }

    public void increaseLevel()
    {
        lastLevel++;
    }
}
