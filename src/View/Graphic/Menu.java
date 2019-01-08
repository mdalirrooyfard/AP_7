package View.Graphic;

import Model.Animals.Wild.Wild;
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
            Image image = new Image(new FileInputStream("C:\\Users\\mahsa\\Desktop\\farm_frenzi_project\\src\\Resources\\Graphic\\menu.jpg")
                    , WIDTH, HEIGHT, false, true);
            ImageView imageView = new ImageView(image);
            imageView.setY(0);
            imageView.setX(0);
            group.getChildren().addAll(imageView);
        }
        catch ( IOException e ){}
        Button newPlayer = new Button("New Player");
        newPlayer.relocate(WIDTH / 2 - 90, 3.70 * HEIGHT / 5);
        newPlayer.setId("button");

        String style = getClass().getResource("graphic.css").toString();
        group.getStylesheets().add(style);

        Button start = new Button("start");
        start.relocate(WIDTH/2 + 90, 4 * HEIGHT / 5);
        start.setId("button");
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
        Button choosePlayer = new Button("choose player");
        choosePlayer.relocate(WIDTH/2 , 3.80 * HEIGHT / 5);
        choosePlayer.setId("button");
        choosePlayer.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                doesChoosePlayer = true;
                stage.setScene(start.getScene());
            }
        });
        Button options = new Button("options");
        options.relocate(WIDTH/2 + 130, 4.25 * HEIGHT / 5);
        options.setId("button");
        options.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                stage.setScene(start.getScene());
            }
        });
        Button exit = new Button("exit");
        exit.relocate(WIDTH / 2 + 135, 4.5 * HEIGHT / 5);
        exit.setId("button");
        group.getChildren().addAll(newPlayer,start,choosePlayer,options,exit);
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

