package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientListener{
    private Socket socket;
    private ClientGui clientGui;
    private ObjectInputStream objectInputStream;

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

    public void start(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Command command = (Command) objectInputStream.readObject();
                    //todo fill this switch
                    switch (command.getType()){
                        case SEND_MASSAGE:
                            clientGui.putInCharArea((String) command.getContent());
                            break;
                        case SEND_LIST:
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
                }catch (IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
