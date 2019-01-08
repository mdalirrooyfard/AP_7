package View.Graphic;

import Model.Player;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;


public class Menu
{
    Stage stage;
    public static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private ChoosePlayer choosePlayer;
    private Options options;
    private Start start;
    private Group group = new Group();
    private Scene scene = new Scene(group, WIDTH, HEIGHT);
    private boolean doesChoosePlayer = false;
    private Player player;

    public Scene getScene()
    {
        return scene;
    }

    public Menu(Stage stage)
    {
        this.stage = stage;
        try
        {
            Image image = new Image(new FileInputStream("E:\\AP\\Project\\src\\View\\Graphic\\menu.png")
                    , WIDTH, HEIGHT, false, true);
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
                if( doesChoosePlayer )
                    stage.setScene(start.getScene());
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Player hasn't been chosen!");
                    alert.setContentText("Choose Player First");
                    ButtonType b1 = new ButtonType("OK");
                    alert.getButtonTypes().setAll(b1);
                    alert.showAndWait();
                }
            }
        });
        Text choosePlayer = new Text(WIDTH - 350 , HEIGHT * 2 / 5 , "Choose Player");
        choosePlayer.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
        choosePlayer.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                doesChoosePlayer = true;
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
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setScene(scene);
    }

    public void passMenuInstance(Menu menu)
    {
        choosePlayer = new ChoosePlayer();
        options = new Options();
        start = new Start(stage,menu,player);
    }

}

