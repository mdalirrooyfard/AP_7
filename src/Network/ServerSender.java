package Network;

import Model.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

public class ServerSender {
    private HashMap<Socket, Player> peopleAndSockets = new HashMap<>();
    private HashMap<Socket, ObjectOutputStream> outPutStreams = new HashMap<>();
    private HashMap<String, Player> usenames = new HashMap<>();
    private HashMap<String, Socket> userNameAndSocket = new HashMap<>();
    private ServerGui serverGui;

    public HashMap<String, Player> getUsenames() {
        return usenames;
    }

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
        userNameAndSocket.put(player.getUserName(), socket);
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

    public void sendIndividual(Socket socket, Command command){
        try{
            ObjectOutputStream sender = outPutStreams.get(socket);
            sender.writeObject(command);
            sender.flush();
            ObjectOutputStream receiver = outPutStreams.get(userNameAndSocket.get(command.getReceiver()));
            receiver.writeObject(command);
            receiver.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
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
        try {
            Vector<Player> players = new Vector<>();
            int numberOfPeople = 0;
            for (Socket s : peopleAndSockets.keySet()) {
                players.add(peopleAndSockets.get(s));
                numberOfPeople++;
            }
            Command command = new Command(CommandTypes.SEND_LEADER_BOARD, players, numberOfPeople);
            ObjectOutputStream objectOutputStream = outPutStreams.get(socket);
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void sendList(Socket socket){
        try {
            StringBuilder list = new StringBuilder();
            int numberOfLines = 0;
            for (Socket s : peopleAndSockets.keySet()) {
                if (!s.equals(socket)) {
                    list.append(peopleAndSockets.get(s).getUserName()).append("\n");
                    numberOfLines++;
                }
            }
            Command command = new Command(CommandTypes.SEND_LIST, list.toString(), numberOfLines);
            ObjectOutputStream objectOutputStream = outPutStreams.get(socket);
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openPrivateChat(Socket socket, String destUserName){
        try{
            ObjectOutputStream client1 = outPutStreams.get(socket);
            Command command1 = new Command(CommandTypes.PRIVATE_CHAT, destUserName);
            client1.writeObject(command1);
            client1.flush();
            ObjectOutputStream client2 = outPutStreams.get(userNameAndSocket.get(destUserName));
            Command command2 = new Command(CommandTypes.PRIVATE_CHAT, peopleAndSockets.get(socket).getUserName());
            client2.writeObject(command2);
            client2.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendWildAnimal(String userName){
        try{
            Command command = new Command(CommandTypes.SEND_WILD_ANIMAL);
            ObjectOutputStream receiver = outPutStreams.get(userNameAndSocket.get(userName));
            receiver.writeObject(command);
            receiver.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendFriendRequest(Command command, String receiver){
        try{
            ObjectOutputStream objectOutputStream = outPutStreams.get(userNameAndSocket.get(receiver));
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendApproveRequest(Command command){
        try{
            usenames.get(command.getSender()).addFriend(command.getReceiver());
            usenames.get(command.getReceiver()).addFriend(command.getSender());
            ObjectOutputStream objectOutputStream = outPutStreams.get(userNameAndSocket.get(command.getReceiver()));
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
