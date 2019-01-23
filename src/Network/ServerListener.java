package Network;

import Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerListener implements Runnable{
    private Client client;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ServerListener(Client client){
        this.client = client;
        this.socket = client.getSocket();
        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (true) {
                Command command = (Command) objectInputStream.readObject();
                //todo fill the switch
                switch (command.getType()) {
                    case BUY_FROM_MARKET:
                        break;
                    case SEND_MASSAGE:
                        //todo we know what to do!
                        break;
                    case SEND_PLAYER:
                        Player player = (Player) command.getContent();
                        client.setPlayer(player);
                        break;
                    case SELL_TO_MARKET:
                        break;
                    default:
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
