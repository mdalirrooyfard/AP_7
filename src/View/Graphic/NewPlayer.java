package View.Graphic;

import Model.Player;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class NewPlayer {
    private Player player;

    public NewPlayer(Stage stage, int lastId){
        try {
            Group root = new Group();
            Scene scene = new Scene(root, 200, 300);
            String style = this.getClass().getResource("graphic.css").toExternalForm();
            scene.getStylesheets().add(style);
            Stage newPlayerStage = new Stage();
            newPlayerStage.setX(stage.getWidth() / 2);
            newPlayerStage.setY(stage.getHeight() / 2);
            Image image = new Image(new FileInputStream("src\\Resources\\Graphic\\Papirous.png"), 200, 300, false, true);
            ImageView imageView = new ImageView(image);
            root.getChildren().add(imageView);
            Label label = new Label("Enter your Name");
            label.setId("NewPlayer");
            label.relocate(50, 50);
            TextField nameField = new TextField();
            nameField.relocate(70, 70);
            nameField.setId("NameText");
            Button ok = new Button("ok");
            ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String name = nameField.getText();
                    if (!name.equals("")){
                        player = new Player(name, lastId + 1);
                        newPlayerStage.close();
                    }
                }
            });
            root.getChildren().addAll(label, nameField);
            newPlayerStage.setScene(scene);
            newPlayerStage.show();
        }
        catch (Exception e){

        }
    }

    public Player getPlayer(){
        return player;
    }
}
