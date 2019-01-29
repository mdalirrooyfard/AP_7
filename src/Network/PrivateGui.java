package Network;

import Model.Player;
import javafx.application.Application;
import javafx.stage.Stage;

public class PrivateGui extends Application {
    private Player sender;
    private String receiver;
    private ClientSender clientSender;

    public PrivateGui(Player sender, String receiver, ClientSender clientSender){
        this.sender = sender;
        this.receiver = receiver;
        this.clientSender = clientSender;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
    }
}
