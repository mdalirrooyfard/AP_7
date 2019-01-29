package Network;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ServerGui extends Application {
    private TextArea chatArea = new TextArea();
    private ServerSender serverSender;

    public void setServerSender(ServerSender serverSender) {
        this.serverSender = serverSender;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("server in chatRoom");
        TextField textField = new TextField();
        Button button = new Button("send");
        chatArea.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
        chatArea.setWrapText(true);
        HBox hBox = new HBox(20, chatArea);
        HBox hBox1 = new HBox(20, textField, button);
        VBox vBox = new VBox(20, hBox1, hBox);
        chatArea.setMinHeight(560);
        Scene scene = new Scene(vBox, 800, 600);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String data = textField.getText();
                if (!data.equals("")){
                    Command command = new Command(CommandTypes.SEND_MASSAGE, "server: " + data);
                    serverSender.sendGroup(command);
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
