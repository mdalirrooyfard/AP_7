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
        serverGui.setServerSender(serverSender);
    }

    public ServerSender getServerSender(){
        return serverSender;
    }

    public ServerGui getServerGui() {
        return serverGui;
    }

    public void start(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Socket socket = serverSocket.accept();
                        Sockets.add(socket);
                        serverSender.addSocket(socket);
                        serverSender.addOutPutStream(socket);
                        Thread listener = new Thread(new ServerListener(socket, serverSender));
                        listener.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
