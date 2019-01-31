package Model;

import java.io.Serializable;
import java.util.HashSet;

public class Player implements Serializable,  Comparable<Player>
{
    private String name,userName;
    private int lastLevel , id , money , levelThatPlaysNow;
    private boolean isLastPlayer , isClient;
    private HashSet<String> friends = new HashSet<>();

    public Player( String name, int id)
    {
        this.name = name;
        this.id = id;
        lastLevel = 1;
        money = 0;
        this.userName = name + id;
    }

    public void increaseMoney(int amount){
        money += amount;
    }

    public void addFriend(String username){
        friends.add(username);
    }

    public HashSet<String> getFriends() {
        return friends;
    }
    public int getLevelThatPlaysNow()
    {
        return levelThatPlaysNow;
    }

    public void setLevelThatPlaysNow(int levelThatPlaysNow)
    {
        this.levelThatPlaysNow = levelThatPlaysNow;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setLastLevel(int lastLevel)
    {
        this.lastLevel = lastLevel;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }

    public boolean isClient()
    {
        return isClient;
    }

    public void setClient(boolean client)
    {
        isClient = client;
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

    @Override
    public int compareTo(Player o) {
        return this.lastLevel - o.getLastLevel();
    }
}
