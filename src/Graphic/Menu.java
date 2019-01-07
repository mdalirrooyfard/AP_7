package Graphic;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Menu
{
    Stage stage;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    private ChoosePlayer choosePlayer;
    private Options options;
    private Start start;
    private Group group = new Group();
    private Scene scene = new Scene(group, WIDTH, HEIGHT);

    public Scene getScene()
    {
        return scene;
    }

    public Menu(Stage stage)
    {
        this.stage = stage;
        Label player = new Label("New Player");
        player.relocate(20, 450);
        player.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
        Label start = new Label("Start");
        start.relocate(160, 450);
        start.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
        Label options = new Label("Options");
        options.relocate(20, 500);
        options.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
        Label exit = new Label("Exit");
        exit.relocate(160, 500);
        exit.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
        exit.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("The game is closing...");
                alert.setContentText("Are you sure???");
                ButtonType b1 = new ButtonType("Yes");
                ButtonType b2 = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(b1, b2);
                alert.showAndWait().ifPresent(result -> {
                    if (result == b1)
                        stage.close();
                });
            }
        });
        try
        {
            Image image = new Image(new FileInputStream("farm-cartoon-background.png")
                    , 1000, 960, false, true);
            ImageView imageView = new ImageView(image);
            imageView.setY(0);
            imageView.setX(0);
        }
        catch ( IOException e ){}
        stage.setScene(scene);

    }

    public void passMenuInstance(Menu menu)
    {
        choosePlayer = new ChoosePlayer();
        options = new Options();
        start = new Start();
    }
}

