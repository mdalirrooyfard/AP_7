package Network;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.FileInputStream;

public class ServerGui extends Application {
    private ScrollPane chatArea = new ScrollPane();
    private Pane area = new Pane();
    private ServerSender serverSender;
    private Scene scene;
    private boolean isOpen = false;
    private int y = 30;

    public boolean getOpen(){
        return isOpen;
    }

    public void setServerSender(ServerSender serverSender) {
        this.serverSender = serverSender;
        area.layout();
        chatArea.setVvalue(1D);
        chatArea.setContent(area);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        isOpen = true;
        primaryStage.setTitle("server in chatRoom");
        TextField textField = new TextField();
        Image send = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\sendButton.png"));
        ImageView sendView = new ImageView(send);
        sendView.setFitWidth(70);
        sendView.setFitHeight(40);
        chatArea.setPrefHeight(560);
        chatArea.setPrefWidth(800);
        HBox hBox = new HBox(20, chatArea);
        HBox hBox1 = new HBox(20, textField, sendView);
        VBox vBox = new VBox(20, hBox1, hBox);
        chatArea.setMinHeight(560);
        scene = new Scene(vBox, 800, 600);
        sendView.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Group tempGroup = new Group();
                scene.setRoot(tempGroup);
                isOpen = false;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public  void putInCharArea(String message) {
        Label messageView = new Label();
        Rectangle rectangle;
        messageView.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 15));
        TranslateTransition translateTransition , translateTransition1;
        messageView.setTranslateX(10);
        translateTransition = new TranslateTransition(Duration.millis(2000) , messageView);
        translateTransition.setToY(y);
        rectangle = new Rectangle((message.length()+1)*8,25);
        rectangle.setTranslateX(10);
        translateTransition1 = new TranslateTransition((Duration.millis(2000)) , rectangle);
        translateTransition1.setToY(y);
        messageView.setText(message);
        rectangle.setFill(Color.ROSYBROWN);
        rectangle.setOpacity(0.8);
        translateTransition1.play();
        translateTransition.play();
        area.getChildren().addAll(rectangle , messageView);
        y += 35;
    }

}
