package Network;

import Model.Constants;
import Model.Player;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ClientGui extends Application
{
    private ClientSender clientSender;
    private Player player;
    private TextArea chatArea = new TextArea();
    private Group root = new Group();
    private Scene scene;

    public ClientGui(ClientSender clientSender, Player player){
        this.clientSender = clientSender;
        this.player = player;
    }


    public Player getPlayer(){
        return player;
    }

    public ClientSender getClientSender(){
        return clientSender;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(player.getName() + " in chatRoom");
        primaryStage.setResizable(false);
        TextField textField = new TextField();
        Button button = new Button("send");
        HBox hBox = new HBox(20, chatArea);
        HBox hBox1 = new HBox(20, textField, button);
        VBox vBox = new VBox(20, hBox1, hBox);
        chatArea.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
        chatArea.setWrapText(true);
        chatArea.setMinHeight(560);
        root.getChildren().add(vBox);
        scene = new Scene(root, 800, 600);
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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Group tempGroup = new Group();
                scene.setRoot(tempGroup);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public  void putInCharArea(String message) {
        chatArea.appendText(message + "\n");
    }

    public void playerJoinedMessage(String userName){
        if (!userName.equals(player.getUserName())){
            try {
                Rectangle rectangle = new Rectangle(0,0, 800, 600);
                rectangle.setFill(Color.rgb(54,16,0));
                rectangle.setOpacity(0.7);
                Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                        400, 150, false, true);
                ImageView messageView = new ImageView(message);
                messageView.setX(200);
                messageView.setY(200);
                Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                        100, 39, false, true);
                ImageView okView = new ImageView(ok);
                okView.setY(350);
                okView.setX(450);
                Image profile = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\viewProfileButton.png"),
                        100, 39, false , true);
                ImageView profileView = new ImageView(profile);
                profileView.setY(350);
                profileView.setX(250);
                Label label = new Label(userName+" had joined the chatRoom!");
                label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
                label.setTextFill(Color.rgb(54, 16, 0));
                label.relocate(235, 250);
                okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        root.getChildren().removeAll(rectangle, messageView, okView, profileView, label);
                    }
                });
                profileView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        clientSender.sendViewProfileRequest(userName);
                    }
                });

                root.getChildren().addAll(rectangle, messageView, okView, profileView, label);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public void showProfile(Player otherPlayer){
        try {
            Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                    400, 200, false, true);
            ImageView messageView = new ImageView(message);
            messageView.setX(200);
            messageView.setY(200);
            Label name = new Label("Name : " + otherPlayer.getName());
            Label userName = new Label("Username : " + otherPlayer.getUserName());
            Label level = new Label("Level : " + Integer.toString(otherPlayer.getLastLevel()));
            Label money = new Label("Money : " + Integer.toString(otherPlayer.getMoney()));
            name.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            userName.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            level.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            money.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            name.relocate(250, 230);
            userName.relocate(250, 270);
            level.relocate(250, 310 );
            money.relocate(250, 350);
            name.setTextFill(Color.rgb(54, 16, 0));
            userName.setTextFill(Color.rgb(54, 16, 0));
            level.setTextFill(Color.rgb(54, 16, 0));
            money.setTextFill(Color.rgb(54, 16, 0));
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 39, false, true);
            ImageView okView = new ImageView(ok);
            okView.setX(500);
            okView.setY(370);
            okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().removeAll(messageView, name, userName, level, money, okView);
                }
            });
            Image privateChat = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\privateChatButton.png"),
                    100, 39, false, true);
            ImageView privateChatView = new ImageView(privateChat);
            privateChatView.setX(350);
            privateChatView.setY(370);
            privateChatView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    clientSender.sendPrivateChatRequest(otherPlayer.getUserName());
                    root.getChildren().removeAll(messageView, name, userName, level, money, okView, privateChatView);
                }
            });
            root.getChildren().addAll(messageView, name, userName, level, money, okView, privateChatView);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
