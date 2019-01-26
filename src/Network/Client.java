package Network;

import Model.Player;

import java.net.Socket;

public class Client {
    private Socket socket;
    private Player player;
    private ClientListener clientListener;

    public  Client(Socket socket){
        this.socket = socket;
        //this.clientListener = new ClientListener(socket);
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Socket getSocket(){
        return socket;
    }

    public Player getPlayer() {
        return player;
    }

    public ClientListener getClientListener() {
        return clientListener;
    }
}
