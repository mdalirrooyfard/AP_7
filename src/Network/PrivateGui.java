package Network;

import Model.Player;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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


public class PrivateGui extends Application {
    private Player sender;
    private String receiver;
    private ClientSender clientSender;
    private ScrollPane chatArea = new ScrollPane();
    private Pane area = new Pane();
    private Group root = new Group();
    private Scene scene;
    private boolean isOpen;
    private int y = 30;

    public PrivateGui(Player sender, String receiver, ClientSender clientSender){
        this.sender = sender;
        this.receiver = receiver;
        this.clientSender = clientSender;
        this.isOpen = false;
        area.layout();
        chatArea.setVvalue(1D);
        chatArea.setContent(area);
    }

    public boolean getOpen(){
        return isOpen;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        isOpen = true;
        primaryStage.setTitle(sender.getUserName()+ " to " + receiver);
        primaryStage.setResizable(false);
        javafx.scene.control.TextField textField = new javafx.scene.control.TextField();
        Image send = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\sendButton.png"));
        ImageView sendView = new ImageView(send);
        sendView.setFitWidth(70);
        sendView.setFitHeight(40);
        chatArea.setPrefHeight(560);
        chatArea.setPrefWidth(800);
        HBox hBox = new HBox(20, chatArea);
        HBox hBox1 = new HBox(20, textField, sendView);
        VBox vBox = new VBox(20, hBox1, hBox);
        root.getChildren().add(vBox);
        scene = new Scene(root, 800, 600);
        sendView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String data = textField.getText();
                if (!data.equals("")){
                    clientSender.sendPrivateMessage(sender.getName()+ ": "+ data, sender.getUserName(), receiver);
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
        if(message.startsWith(sender.getName())){
            String[] strings = message.split(":" , 2);
            message = strings[1].trim();
            rectangle = new Rectangle((message.length()+1)*8,25);
            messageView.setTranslateX(800 - (message.length()+1)*8);
            translateTransition = new TranslateTransition(Duration.millis(2000) , messageView);
            translateTransition.setToY(y);
            rectangle.setTranslateX(800 - (message.length()+1)*8);
            translateTransition1 = new TranslateTransition((Duration.millis(2000)) , rectangle);
            translateTransition1.setToY(y);
            messageView.setText(message);
        }
        else{
            messageView.setTranslateX(10);
            translateTransition = new TranslateTransition(Duration.millis(2000) , messageView);
            translateTransition.setToY(y);
            rectangle = new Rectangle((message.length()+1)*8,25);
            rectangle.setTranslateX(10);
            translateTransition1 = new TranslateTransition((Duration.millis(2000)) , rectangle);
            translateTransition1.setToY(y);
            messageView.setText(message);
        }
        rectangle.setFill(Color.SADDLEBROWN);
        rectangle.setOpacity(0.8);
        translateTransition1.play();
        translateTransition.play();
        area.getChildren().addAll(rectangle , messageView);
        y += 35;
    }

}
