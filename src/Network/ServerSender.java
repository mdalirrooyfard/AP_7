package Network;

import Model.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerSender {
    private HashMap<Socket, Player> people = new HashMap<>();
    private HashMap<Socket, ObjectOutputStream> outPutStreams = new HashMap<>();
    private ServerGui serverGui;

    public ServerSender(ServerGui serverGui){
        this.serverGui = serverGui;
    }

    public void addSocket(Socket socket){
        people.put(socket, null);
    }

    public void addOutPutStream(Socket socket){
        try {
            outPutStreams.put(socket, new ObjectOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPlayer(Player player){
        people.replace(player.getSocket(), player);
    }

    public synchronized void sendGroup(Command command){
        for (Socket s : outPutStreams.keySet()){
            try {
                ObjectOutputStream objectOutputStream = outPutStreams.get(s);
                objectOutputStream.writeObject(command);
                objectOutputStream.flush();
                if (command.getType().equals(CommandTypes.SEND_MASSAGE))
                    serverGui.putInCharArea((String) command.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendIndividual(Socket socket){
        //todo get the destination client and sent the message to him
    }

    public void sendLeaderBoard(Socket socket){

    }

    public void sendList(Socket socket){

    }
}
