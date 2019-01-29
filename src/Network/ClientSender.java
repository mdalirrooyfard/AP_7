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

    public synchronized void sendMessage(String data){
        Command command = new Command(CommandTypes.SEND_MASSAGE, data);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
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

    public void sendViewProfileRequest(String username){
        Command command = new Command(CommandTypes.VIEW_PROFILE, username);
        try {
            objectOutputStream.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPrivateChatRequest(String receiver){
        Command command = new Command(CommandTypes.PRIVATE_CHAT, receiver);
        try {
            objectOutputStream.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPrivateMessage(String message, String sender, String receiver){
        Command command = new Command(CommandTypes.SEND_INDIVIDUAL_MESSAGE, message, sender, receiver);
        try {
            objectOutputStream.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendListRequest(){
        Command command = new Command(CommandTypes.SEND_LIST);
        try {
            objectOutputStream.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
