package Network;

import Model.Items.Item;
import Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

public class ServerListener implements Runnable{
    private Socket socket;
    private ServerSender serverSender;
    private ObjectInputStream objectInputStream;

    public ServerListener(Socket socket, ServerSender serverSender){
        this.socket = socket;
        this.serverSender = serverSender;
        try {
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
                switch (command.getType()) {
                    case SEND_MASSAGE:
                        serverSender.sendGroup(command);
                        break;
                    case SEND_PLAYER:
                        Player player = (Player) command.getContent();
                        serverSender.setNewPlayer(socket, player);
                        Command sendNewPlayer = new Command(CommandTypes.PLAYER_JOINED, player.getUserName());
                        serverSender.sendGroup(sendNewPlayer);
                        break;
                    case SEND_LEVEL:
                        serverSender.sendGroup(command);
                        break;
                    case SEND_MONEY:
                        serverSender.sendGroup(command);
                        break;
                    case UPDATE_LEVEL:
                        serverSender.updateLevel(command.getSender(), (int)command.getContent());
                        break;
                    case UPDATE_MONEY:
                        serverSender.updateMoney(command.getSender(), (int)command.getContent());
                        break;
                    case VIEW_PROFILE:
                        serverSender.sendProfile(socket, (String)command.getContent());
                        break;
                    case PRIVATE_CHAT:
                        serverSender.openPrivateChat(socket, (String)command.getContent());
                        break;
                    case SEND_INDIVIDUAL_MESSAGE:
                        serverSender.sendIndividual(socket, command);
                        break;
                    case SEND_LIST:
                        serverSender.sendList(socket);
                        break;
                    case SELL_TO_MARKET:
                        serverSender.updateMarketAdd((Vector<Item>) command.getContent());
                        break;
                    case BUY_FROM_MARKET:
                        serverSender.updateMarketRemove((Vector<Item>) command.getContent());
                        break;
                    case SEND_MARKET:
                        serverSender.sendMarket(socket);
                        break;
                    case SEND_LEADER_BOARD:
                        serverSender.sendLeaderBoard(socket);
                        break;
                    case SEND_WILD_ANIMAL:
                        serverSender.sendWildAnimal((String) command.getContent());
                        break;
                    case SEND_FRIEND_REQUEST:
                        serverSender.sendFriendRequest(command, command.getReceiver());
                        break;
                    case APPROVE_REQUEST:
                        serverSender.sendApproveRequest(command);
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
