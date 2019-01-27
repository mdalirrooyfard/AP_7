package Network;

import Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSender {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;

    public ClientSender(Socket socket){
        this.socket = socket;
        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String data){
        Command command = new Command(CommandTypes.SEND_MASSAGE, data);
        try {
            objectOutputStream.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPlayer(Player player){
        Command command = new Command(CommandTypes.SEND_PLAYER, player);
        try {
            objectOutputStream.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
