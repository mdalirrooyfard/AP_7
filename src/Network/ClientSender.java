package Network;

import java.net.Socket;

public class ClientSender {
    private Socket socket;

    public ClientSender(Socket socket){
        this.socket = socket;
    }
}
