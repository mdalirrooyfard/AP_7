package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private int myPort;
    private ArrayList<Client> clients = new ArrayList<>();
    private ServerSender serverSender = new ServerSender();

    public Server(ServerSocket serverSocket , int myPort){
        this.serverSocket = serverSocket ;
        this.myPort = myPort;
    }

    public void start(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = serverSocket.accept();
                    Client client = new Client(socket);
                    clients.add(client);
                    serverSender.addClient(client);
                    Thread listener = new Thread(new ServerListener(client));
                    listener.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
