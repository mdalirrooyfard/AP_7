package View.Graphic;

import Model.Player;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ChoosePlayer
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);
    private Player player;

    public Player getPlayer()
    {
        return player;
    }

    public Scene getScene()
    {
        return scene;
    }

    public ChoosePlayer(Stage stage , Menu menu , ArrayList<Player> players)
    {
        stage.setFullScreen(menu.isFullScreen());
        try
        {
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\background.png")
                    , Menu.WIDTH, Menu.HEIGHT, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setY(0);
            backgroundView.setX(0);

            Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image menuBackground = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\menuBackground.png")
                    , 550, Menu.HEIGHT, false, true);
            ImageView menuBackgroundView = new ImageView(menuBackground);
            menuBackgroundView.setY(0);
            menuBackgroundView.setX(Menu.WIDTH - 550);
            group.getChildren().addAll(backgroundView,rectangle,menuBackgroundView);

            writePlayers(players);
            insertNewPlayer(players);
            insertDelete();
            insertBack(stage,menu);
        }
        catch ( Exception e ){}
    }

    private void writePlayers(ArrayList<Player> players)
    {
        //TODO choose player!
        for(Node node:group.getChildren())
            if( node instanceof Text )
                ((Text) node).setText("");
        Text text;
        int i = 0;
        for (Player p : players)
        {
            i++;
            text = new Text( Menu.WIDTH - 450, ( Menu.HEIGHT - 150 ) * i / (players.size() + 1),p.getName());
            text.setFill(Color.rgb(54,16,0));
            text.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            group.getChildren().add(text);
        }
    }

    private void insertNewPlayer(ArrayList<Player> players)
    {
        try
        {
            Image newPlayer = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\newPlayerButton.png")
                    , 200, 78, false, true);
            ImageView newPlayerView = new ImageView(newPlayer);
            newPlayerView.setY(Menu.HEIGHT - 150);
            newPlayerView.setX(Menu.WIDTH - 250);
            group.getChildren().addAll(newPlayerView);
            newPlayerView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
                    rectangle.setFill(Color.rgb(54,16,0));
                    rectangle.setOpacity(0.7);
                    group.getChildren().addAll(rectangle);
                    try
                    {
                        Image newPlayer = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png")
                                , 500, 506, false, true);
                        ImageView newPlayerView = new ImageView(newPlayer);
                        newPlayerView.setY(Menu.HEIGHT / 2 - 253);
                        newPlayerView.setX(Menu.WIDTH / 2 - 250);

                        Label message = new Label("Player Name:");
                        message.setTextFill(Color.rgb(54,16,0));
                        message.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,40));
                        message.setLayoutX(Menu.WIDTH / 2 - 150);
                        message.setLayoutY(Menu.HEIGHT / 2 - 200);

                        TextField playerName = new TextField();
                        playerName.setAlignment(Pos.CENTER);
                        playerName.setLayoutX(Menu.WIDTH / 2 - 150);
                        playerName.setLayoutY(Menu.HEIGHT / 2 - 100);
                        playerName.setPrefSize(300,100);
                        playerName.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));

                        Image add = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\addButton.png")
                                , 150, 59, false, true);
                        ImageView addView = new ImageView(add);
                        addView.setY(Menu.HEIGHT / 2 + 130);
                        addView.setX(Menu.WIDTH / 2 - 175);

                        Image cancel = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\cancelButton.png")
                                , 150, 59, false, true);
                        ImageView cancelView = new ImageView(cancel);
                        cancelView.setY(Menu.HEIGHT / 2 + 130);
                        cancelView.setX(Menu.WIDTH / 2 + 25);

                        addView.setOnMouseClicked(new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                player = new Player(playerName.getText(),players.size());
                                players.add(player);
                                group.getChildren().removeAll(newPlayerView,message,playerName,cancelView,addView,rectangle);
                                writePlayers(players);
                            }
                        });

                        cancelView.setOnMouseClicked(new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                group.getChildren().removeAll(newPlayerView,message,playerName,cancelView,addView,rectangle);
                            }
                        });

                        group.getChildren().addAll(newPlayerView,message,playerName,addView,cancelView);
                    }
                    catch ( Exception e ){}

                }
            });
        }
        catch ( Exception e ){}
    }

    private void insertBack(Stage stage , Menu menu)
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 84, 79, false, true);
            ImageView backView = new ImageView(back);
            backView.setY(Menu.HEIGHT - 150);
            backView.setX(Menu.WIDTH - 500);
            group.getChildren().addAll(backView);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    stage.setScene(menu.getScene());
                }
            });

        }
        catch ( Exception e ){}
    }

    private void insertDelete()
    {

    }

}
