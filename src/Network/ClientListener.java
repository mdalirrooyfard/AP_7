package Network;

import Model.Player;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientListener implements Runnable{
    private Socket socket;
    private ClientGui clientGui;
    private ObjectInputStream objectInputStream;
    private HashMap<String, PrivateGui> privateReceivers = new HashMap<>();

    public ClientListener(Socket socket, ClientGui clientGui){
        this.socket = socket;
        this.clientGui = clientGui;
        try {
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Command command = (Command) objectInputStream.readObject();
                //todo fill this switch
                switch (command.getType()) {
                    case SEND_MASSAGE:
                        clientGui.putInCharArea((String) command.getContent());
                        break;
                    case PLAYER_JOINED:
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                clientGui.playerJoinedMessage((String) command.getContent());
                            }
                        });
                        break;
                    case VIEW_PROFILE:
                        Player player = (Player)command.getContent();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                clientGui.showProfile(player);
                            }
                        });
                        break;
                    case PRIVATE_CHAT:
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    PrivateGui privateGui;
                                    if (!privateReceivers.containsKey((String) command.getContent())) {
                                        privateGui = new PrivateGui(clientGui.getPlayer(), (String) command.getContent(), clientGui.getClientSender());
                                        privateReceivers.put((String) command.getContent(), privateGui);
                                    } else
                                        privateGui = privateReceivers.get((String) command.getContent());
                                    Stage stage = new Stage();
                                    privateGui.start(stage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case SEND_INDIVIDUAL_MESSAGE:
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                PrivateGui privateGui;
                                if (privateReceivers.containsKey(command.getReceiver())){
                                    privateGui = privateReceivers.get(command.getReceiver());
                                }
                                else
                                    privateGui = privateReceivers.get(command.getSender());
                                privateGui.putInCharArea((String)command.getContent());
                            }
                        });
                        break;
                    case SEND_LIST:
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                clientGui.showList((String)command.getContent(), command.getNumberOfLines());
                            }
                        });
                        break;
                    case SEND_LEADER_BOARD:
                        break;
                    case UPDATE_MARKET:
                        break;
                    case SELL_TO_MARKET:
                        break;
                    case BUY_FROM_MARKET:
                        break;
                    default:
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
