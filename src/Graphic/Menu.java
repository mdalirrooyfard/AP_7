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
    public static final int WIDTH = 1137;
    public static final int HEIGHT = 700;
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
        try
        {
            Image image = new Image(new FileInputStream("E:\\AP\\Project\\src\\Graphic\\menu.png")
                    , 1137, 700, false, true);
            ImageView imageView = new ImageView(image);
            imageView.setY(0);
            imageView.setX(0);
            group.getChildren().addAll(imageView);
        }
        catch ( IOException e ){}
        Text start = new Text( WIDTH - 300 , HEIGHT / 5 , "Start");
        start.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
        start.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                stage.setScene(start.getScene());
            }
        });
        Text choosePlayer = new Text(WIDTH - 350 , HEIGHT * 2 / 5 , "Choose Player");
        choosePlayer.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
        choosePlayer.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                stage.setScene(start.getScene());
            }
        });
        Text options = new Text(WIDTH - 320 , HEIGHT * 3 / 5 , "Options");
        options.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
        options.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                stage.setScene(start.getScene());
            }
        });
        Text exit = new Text(WIDTH - 290 , HEIGHT * 4 / 5 , "Exit");
        exit.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
        group.getChildren().addAll(start,choosePlayer,options,exit);
        exit.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("The game is closing...");
                alert.setContentText("Are you sure?");
                ButtonType b1 = new ButtonType("Yes");
                ButtonType b2 = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(b1, b2);
                alert.showAndWait().ifPresent(result -> {
                    if (result == b1)
                        stage.close();
                });
            }
        });
        stage.setScene(scene);
    }

    public void passMenuInstance(Menu menu)
    {
        choosePlayer = new ChoosePlayer();
        options = new Options();
        start = new Start();
    }

}

