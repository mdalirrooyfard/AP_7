package Network;

import Model.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ServerSender {
    private HashMap<Socket, Player> peopleAndSockets = new HashMap<>();
    private HashMap<Socket, ObjectOutputStream> outPutStreams = new HashMap<>();
    private HashMap<String, Player> usenames = new HashMap<>();
    private ServerGui serverGui;

    public ServerSender(ServerGui serverGui){
        this.serverGui = serverGui;
    }

    public void addSocket(Socket socket){
        peopleAndSockets.put(socket, null);
    }

    public void addOutPutStream(Socket socket){
        try {
            outPutStreams.put(socket, new ObjectOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPlayer(Socket socket, Player player){
        peopleAndSockets.replace(socket, player);
        usenames.put(player.getUserName(), player);
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

    public void sendProfile(Socket socket, String username){
        try{
            ObjectOutputStream objectOutputStream = outPutStreams.get(socket);
            Player player = usenames.get(username);
            Command command = new Command(CommandTypes.VIEW_PROFILE, player);
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendLeaderBoard(Socket socket){

    }

    public void sendList(Socket socket){

    }
}
