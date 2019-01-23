package Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServerSender {
    private ArrayList<Client> clients = new ArrayList<>();
    //todo change this hashmap

    public void addClient (Client client){
        clients.add(client);
    }

    public void sendGroup(Command command){
        for (Client c : clients){
            ObjectOutputStream objectOutputStream = c.getClientListener().getObjectOutputStream();
            try {
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
