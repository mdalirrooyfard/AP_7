package Network;

import Model.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    private VBox vBox = new VBox();
    private ScrollPane chatArea = new ScrollPane();
    private Pane pane = new Pane();
    private int y = 10;

    public ClientGui(ClientSender clientSender, Player player){
        this.clientSender = clientSender;
        this.player = player;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(player.getName() + " in chatRoom");
        TextField textField = new TextField();
        Button button = new Button("send");
        chatArea.setContent(pane);
        HBox hBox = new HBox(textField, button);
        vBox.getChildren().addAll(chatArea, hBox);
        Scene scene = new Scene(vBox, 800, 600);
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
        y += 30;
        Label label = new Label();
        label.setText(message);
        label.setFont(new Font(20));
        if (message.startsWith(player.getName()))
            label.relocate(400, y);
        else
            label.relocate(10, y);
        pane.getChildren().add(label);
    }

}
