package Network;

import Model.Items.Item;
import Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

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
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLevel(int level, String sender){
        Command command = new Command(CommandTypes.SEND_LEVEL, level, sender);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMoney(int money, String sender){
        Command command = new Command(CommandTypes.SEND_MONEY, money, sender);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(Command command){
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendViewProfileRequest(String username){
        Command command = new Command(CommandTypes.VIEW_PROFILE, username);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPrivateChatRequest(String receiver){
        Command command = new Command(CommandTypes.PRIVATE_CHAT, receiver);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPrivateMessage(String message, String sender, String receiver){
        Command command = new Command(CommandTypes.SEND_INDIVIDUAL_MESSAGE, message, sender, receiver);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendListRequest(){
        Command command = new Command(CommandTypes.SEND_LIST);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLeaderBoardRequest(){
        Command command = new Command(CommandTypes.SEND_LEADER_BOARD);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendWildAnimal(String userName){
        Command command = new Command(CommandTypes.SEND_WILD_ANIMAL, userName);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendApproveRequest(String sender, String receiver){
        Command command = new Command(CommandTypes.APPROVE_REQUEST, sender, receiver);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFriendRequest(String sender, String receiver){
        Command command = new Command(CommandTypes.SEND_FRIEND_REQUEST, sender, receiver);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendItemsToMarket(Vector<Item> items){
        Command command = new Command(CommandTypes.SELL_TO_MARKET, items);
        try {
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
