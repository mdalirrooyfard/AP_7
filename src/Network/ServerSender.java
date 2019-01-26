package Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSender {
    private ArrayList<Socket> sockets = new ArrayList<>();
    private ServerGui serverGui;
    //todo change this hashmap

    public ServerSender(ServerGui serverGui){
        this.serverGui = serverGui;
    }
    public void addSocket(Socket socket){
        sockets.add(socket);
    }

    public void sendGroup(Command command){
        for (Socket s : sockets){
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
                objectOutputStream.writeObject(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendIndividual(String message){
        //todo get the destination client and sent the message to him
    }
}
