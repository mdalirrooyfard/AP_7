package View.Graphic;

import Controller.Controller;
import Model.Player;
import View.View;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Start
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);
    private Player player;
    private ArrayList<Player> players;
    private Game game;

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Scene getScene()
    {
        return scene;
    }

    public Start(Stage stage , Menu menu , Player player , ArrayList<Player> players)
    {
        this.player = player;
        this.players = players;
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
        }
        catch ( Exception e ){}
        insertBack(stage,menu);
        insertLevels(stage,menu);
    }

    private void insertLevels(Stage stage , Menu menu)
    {
        try
        {
            for( int i = 0 ; i < 10 ; i++ )
            {
                String levelName = "Level"+Integer.toString(i);
                Image openLevel = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\"+levelName+"Open.png")
                        , 200, 79, false, true);
                Image closeLevel = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\"+levelName+"Close.png")
                        , 200, 79, false, true);
                ImageView levelView;
                if( i + 1 <= player.getLastLevel() )
                    levelView = new ImageView(openLevel);
                else
                    levelView = new ImageView(closeLevel);
                levelView.setX((Menu.WIDTH - 500) * ( (i + 1) % 2 ) + (Menu.WIDTH - 250) * ( i % 2));
                levelView.setY(Menu.HEIGHT * ( i / 2  + 1) / 7);
                group.getChildren().addAll(levelView);
                levelView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        if( levelView.getImage() == openLevel )
                        {
                            int level = Integer.parseInt(Character.toString(levelName.toCharArray()[levelName.length()-1])+1);
                            game = new Game(stage,menu,level,player,players);
                            try
                            {
                                if( wasThisLevelPlayedBefore(level) )
                                {
                                    Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
                                    rectangle.setFill(Color.rgb(54,16,0));
                                    rectangle.setOpacity(0.7);

                                    Image exitMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\continueMessageBox.png")
                                            , 800, 300, false, true);
                                    ImageView exitMessageView = new ImageView(exitMessage);
                                    exitMessageView.setY(Menu.HEIGHT / 2 - 150);
                                    exitMessageView.setX(Menu.WIDTH / 2 - 400);

                                    Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                                            , 153, 145, false, true);
                                    ImageView yesView = new ImageView(yes);
                                    yesView.setY(Menu.HEIGHT / 2 + 150);
                                    yesView.setX(Menu.WIDTH / 2 - 200);

                                    Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                                            , 153, 146, false, true);
                                    ImageView noView = new ImageView(no);
                                    noView.setY(Menu.HEIGHT / 2 + 150 );
                                    noView.setX(Menu.WIDTH / 2 + 47);

                                    yesView.setOnMouseClicked(new EventHandler<MouseEvent>()
                                    {
                                        @Override
                                        public void handle(MouseEvent event)
                                        {
                                            game.loadNewGame(true,level);
                                            group.getChildren().removeAll(rectangle,exitMessageView,yesView,noView);
                                        }
                                    });

                                    noView.setOnMouseClicked(new EventHandler<MouseEvent>()
                                    {
                                        @Override
                                        public void handle(MouseEvent event)
                                        {
                                            game.loadNewGame(false,level);
                                            group.getChildren().removeAll(rectangle,exitMessageView,yesView,noView);
                                        }
                                    });
                                    group.getChildren().addAll(rectangle,exitMessageView,yesView,noView);
                                }
                                else
                                    game.loadNewGame(false,level);
                                game.getScene();
                            }
                            catch ( Exception e ){}
                        }
                        else
                        {
                            try
                            {
                                Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
                                rectangle.setFill(Color.rgb(54,16,0));
                                rectangle.setOpacity(0.7);

                                Image playerHasNotBeenChosenMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\levelErrorMessagebox.png")
                                        , 800, 300, false, true);
                                ImageView playerHasNotBeenChosenMessageView = new ImageView(playerHasNotBeenChosenMessage);
                                playerHasNotBeenChosenMessageView.setY(Menu.HEIGHT / 2 - 150);
                                playerHasNotBeenChosenMessageView.setX(Menu.WIDTH / 2 - 400);

                                Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png")
                                        , 200, 79, false, true);
                                ImageView okView = new ImageView(ok);
                                okView.setY(Menu.HEIGHT / 2 + 150);
                                okView.setX(Menu.WIDTH / 2 - 100);
                                okView.setOnMouseClicked(new EventHandler<MouseEvent>()
                                {
                                    @Override
                                    public void handle(MouseEvent event)
                                    {
                                        group.getChildren().removeAll(rectangle,playerHasNotBeenChosenMessageView,okView);
                                    }
                                });

                                group.getChildren().addAll(rectangle,playerHasNotBeenChosenMessageView,okView);
                            }
                            catch ( Exception e ){}
                        }
                    }
                });
            }
        }
        catch ( IOException e ){}
    }

    private void insertBack(Stage stage,Menu menu)
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 84, 79, false, true);
            ImageView backView = new ImageView(back);
            backView.setX(0);
            backView.setY(0);
            backView.setY(Menu.HEIGHT - 120);
            backView.setX(Menu.WIDTH - 450);
            group.getChildren().addAll(backView);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event){stage.setScene(menu.getScene());}
            });
        }
        catch ( Exception e ){}
    }

    private boolean wasThisLevelPlayedBefore(int level)
    {
        String path = "src\\SavedGames\\"+player.getName()+"-"+Integer.toString(player.getId())+"-"+Integer.toString(level);
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(path);
            return true;
        }
        catch ( Exception e )
        {
            return false;
        }
    }
}
