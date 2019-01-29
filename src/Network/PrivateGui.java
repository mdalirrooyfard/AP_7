package Network;

import Model.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class PrivateGui extends Application {
    private Player sender;
    private String receiver;
    private ClientSender clientSender;
    private TextArea chatArea = new TextArea();
    private Group root = new Group();

    public PrivateGui(Player sender, String receiver, ClientSender clientSender){
        this.sender = sender;
        this.receiver = receiver;
        this.clientSender = clientSender;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(sender.getUserName()+ " to " + receiver);
        primaryStage.setResizable(false);
        javafx.scene.control.TextField textField = new javafx.scene.control.TextField();
        javafx.scene.control.Button button = new javafx.scene.control.Button("send");
        HBox hBox = new HBox(20, chatArea);
        HBox hBox1 = new HBox(20, textField, button);
        VBox vBox = new VBox(20, hBox1, hBox);
        chatArea.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
        chatArea.setWrapText(true);
        chatArea.setMinHeight(560);
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 800, 600);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String data = textField.getText();
                if (!data.equals("")){
                    clientSender.sendPrivateMessage(sender.getName()+ ": "+ data, sender.getUserName(), receiver);
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
