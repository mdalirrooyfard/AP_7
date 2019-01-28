package Network;

import Model.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ClientGui extends Application
{
    private ClientSender clientSender;
    private Player player;
    private TextArea chatArea = new TextArea();

    public ClientGui(ClientSender clientSender, Player player){
        this.clientSender = clientSender;
        this.player = player;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(player.getName() + " in chatRoom");
        TextField textField = new TextField();
        Button button = new Button("send");
        HBox hBox = new HBox(20, chatArea);
        HBox hBox1 = new HBox(20, textField, button);
        VBox vBox = new VBox(20, hBox1, hBox);
        Scene scene = new Scene(vBox);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String data = textField.getText();
                if (!data.equals("")){
                    clientSender.sendMessage(player.getName()+": "+data);
                }
                textField.setText("");
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public  void putInCharArea(String message) {
        chatArea.appendText(message + "\n");
    }

}
