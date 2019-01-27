package Network;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ServerGui extends Application {
    private TextArea chatArea = new TextArea();
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("server in chatRoom");
        chatArea.setFont(new Font(25));
        chatArea.setWrapText(true);
        VBox vBox = new VBox(20, chatArea);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public  void putInCharArea(String message) {
        chatArea.appendText(message + "\n");
    }

}
