package Network;

import Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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
                //todo fill the switch
                switch (command.getType()) {
                    case BUY_FROM_MARKET:
                        break;
                    case SEND_MASSAGE:
                        serverSender.sendGroup(command);
                        break;
                    case SEND_PLAYER:
                        Player player = (Player) command.getContent();
                        serverSender.setPlayer(socket, player);
                        Command sendNewPlayer = new Command(CommandTypes.PLAYER_JOINED, player.getUserName());
                        serverSender.sendGroup(sendNewPlayer);
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
                    case SELL_TO_MARKET:
                        break;
                    case SEND_LIST:
                        serverSender.sendList(socket);
                        break;
                    case UPDATE_MARKET:
                        break;
                    case SEND_LEADER_BOARD:
                        serverSender.sendLeaderBoard(socket);
                        break;
                    case SEND_WILD_ANIMAL:
                        serverSender.sendWildAnimal((String) command.getContent());
                        break;
                    case SEND_FRIEND_REQUEST:
                        serverSender.sendFriendConnection(command, command.getReceiver());
                        break;
                    case APPROVE_REQUEST:
                        serverSender.sendFriendConnection(command, command.getReceiver());
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
