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
import javafx.stage.Stage;
import Model.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static Model.Constants.HEIGHT;
import static Model.Constants.WIDTH;

public class ClientGui extends Application
{
    private ClientSender clientSender;
    private Player player;
    private TextArea chatArea = new TextArea();
    private Group root = new Group();

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
        root.getChildren().add(vBox);
        Scene scene = new Scene(root);
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

    public void playerJoinedMessage(String userName){
        if (!userName.equals(player.getUserName())){
            try {
                Rectangle rectangle = new Rectangle(0,0, WIDTH, HEIGHT);
                rectangle.setFill(Color.rgb(54,16,0));
                rectangle.setOpacity(0.7);
                Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                        800, 300, false, true);
                ImageView messageView = new ImageView(message);
                messageView.setX(WIDTH / 2 - 400);
                messageView.setY(HEIGHT / 2 - 150);
                Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                        100, 39, false, true);
                ImageView okView = new ImageView(ok);
                okView.setY(HEIGHT / 2 + 150 );
                okView.setX(WIDTH / 2 + 47);
                Image profile = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\viewProfileButton.png"),
                        100, 39, false , true);
                ImageView profileView = new ImageView(profile);
                profileView.setY(HEIGHT / 2 + 150);
                profileView.setX(WIDTH / 2 - 200);
                okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        root.getChildren().removeAll(rectangle, messageView, okView, profileView);
                    }
                });
                profileView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        clientSender.sendViewProfileRequest(userName);
                    }
                });

                root.getChildren().addAll(rectangle, messageView, okView, profileView);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public void showProfile(Scene scene){
        Stage stage  = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
