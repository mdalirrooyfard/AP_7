package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private int myPort;
    private ArrayList<Socket> Sockets = new ArrayList<>();
    private ServerSender serverSender;
    private ServerGui serverGui = new ServerGui();

    public Server(ServerSocket serverSocket , int myPort){
        this.serverSocket = serverSocket ;
        this.myPort = myPort;
        this.serverSender = new ServerSender(serverGui);
    }

    public void start(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = serverSocket.accept();
                    Sockets.add(socket);
                    serverSender.addSocket(socket);
                    Thread listener = new Thread(new ServerListener(socket, serverSender));
                    listener.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
