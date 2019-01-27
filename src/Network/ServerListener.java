package Network;

import Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerListener implements Runnable{
    private Socket socket;
    private ServerSender serverSender;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ServerListener(Socket socket, ServerSender serverSender){
        this.socket = socket;
        this.serverSender = serverSender;
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
                        serverSender.sendGroup(command);
                        break;
                    case SEND_PLAYER:
                        Player player = (Player) command.getContent();
                        serverSender.setPlayer(player);
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
