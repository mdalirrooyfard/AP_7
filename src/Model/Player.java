package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Player implements Serializable
{
    private String name,userName;
    //private Socket socket;
    private int lastLevel , id , money;
    private boolean isLastPlayer , isClient;

    public Player( String name, int id)
    {
        this.name = name;
        this.id = id;
        lastLevel = 1;
        money = 0;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /*public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }*/

    public void setLastLevel(int lastLevel)
    {
        this.lastLevel = lastLevel;
    }

    public void setId(int id)
    {
        this.id = id;
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
}
