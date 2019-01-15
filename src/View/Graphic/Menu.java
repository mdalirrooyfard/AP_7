package View.Graphic;

import Controller.Controller;
import Model.Player;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Menu
{
    Stage stage;
    public static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private Options optionsScene;
    private Start startScene;
    private ChoosePlayer choosePlayerScene;
    private Group group = new Group();
    private Scene scene = new Scene(group, WIDTH, HEIGHT);
    private Player player = new Player("",0);
    private ArrayList<Player> players;
    private boolean muteMusic = false , muteSound = false , fullScreen = true;
    private MediaPlayer mediaPlayer;
    private Menu menu;

    public Player getPlayer()
    {
        return player;
    }

    public void setMenu(Menu menu)
    {
        this.menu = menu;
    }

    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

    public boolean isMusicMuted()
    {
        return muteMusic;
    }

    public void setMuteMusic(boolean muteMusic)
    {
        this.muteMusic = muteMusic;
    }

    public boolean isSoundMuted()
    {
        return muteSound;
    }

    public void setMuteSound(boolean muteSound)
    {
        this.muteSound = muteSound;
    }

    public boolean isFullScreen()
    {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen)
    {
        this.fullScreen = fullScreen;
    }

    public Scene getScene()
    {
        player = choosePlayerScene.getPlayer();
        player = menu.getPlayer();
        for( Node node : group.getChildren() )
            if( node instanceof Label )
                ((Label) node).setText("");
        for( Player p : players )
            if( p.isLastPlayer() && p != player )
                p.setLastPlayer(false);
        Label playerName = insertPlayer();
        group.getChildren().addAll(playerName);
        return scene;
    }

    public Menu(Stage stage , ArrayList<Player> players )
    {
        String style = this.getClass().getResource("graphic.css").toExternalForm();
        scene.getStylesheets().add(style);
        this.stage = stage;
        this.players = players;
        for( Player p : players )
            if( p.isLastPlayer() )
            {
                player = p;
                break;
            }
        try
        {
            music();
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\background.png")
                    , WIDTH, HEIGHT, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setY(0);
            backgroundView.setX(0);
            Image menuButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\mainMenuButton.png")
                    , 220, 79, false, true);
            ImageView menuButtonView = new ImageView(menuButton);
            menuButtonView.setY(HEIGHT - 100);
            menuButtonView.setX(20);
            group.getChildren().addAll(backgroundView,menuButtonView);
            menuButtonView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        Rectangle rectangle = new Rectangle(0,0,WIDTH,HEIGHT);
                        rectangle.setFill(Color.rgb(54,16,0));
                        rectangle.setOpacity(0.7);

                        Image menuBackground = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\menuBackground.png")
                                , 550, HEIGHT, false, true);
                        ImageView menuBackgroundView = new ImageView(menuBackground);
                        menuBackgroundView.setY(0);
                        menuBackgroundView.setX(WIDTH - 550);

                        ImageView startView = insertStart();
                        startView.setOnMouseClicked(new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                if( player != null && player.getId() > 0 )
                                {
                                    makeStart();
                                    stage.setScene(startScene.getScene());
                                }
                                else
                                {
                                    try
                                    {
                                        Rectangle rectangle = new Rectangle(0,0,WIDTH,HEIGHT);
                                        rectangle.setFill(Color.rgb(54,16,0));
                                        rectangle.setOpacity(0.7);

                                        Image playerHasNotBeenChosenMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\playerHasNotBeenChosenMessageBox.png")
                                                , 800, 300, false, true);
                                        ImageView playerHasNotBeenChosenMessageView = new ImageView(playerHasNotBeenChosenMessage);
                                        playerHasNotBeenChosenMessageView.setY(HEIGHT / 2 - 150);
                                        playerHasNotBeenChosenMessageView.setX(WIDTH / 2 - 400);

                                        Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png")
                                                , 200, 79, false, true);
                                        ImageView okView = new ImageView(ok);
                                        okView.setY(HEIGHT / 2 + 150);
                                        okView.setX(WIDTH / 2 - 100);
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

                        ImageView choosePlayerView = insertChoosePlayer();
                        choosePlayerView.setOnMouseClicked(new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                choosePlayerScene.setPlayer(player);
                                stage.setScene(choosePlayerScene.getScene());
                            }
                        });

                        ImageView optionsView = insertOptions();
                        optionsView.setOnMouseClicked(new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                stage.setScene(optionsScene.getScene());
                            }
                        });

                        ImageView exitView = insertExit();
                        exitView.setOnMouseClicked(new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                exitHandler();
                            }
                        });
                        Label playerName = insertPlayer();
                        group.getChildren().addAll(rectangle,menuBackgroundView,startView,choosePlayerView,optionsView,exitView,playerName);
                    }
                    catch ( Exception e ){}
                }
            });
        }
        catch ( IOException e ){}
        stage.setFullScreen(fullScreen);
        stage.setResizable(false);
        stage.setScene(scene);
    }

    private void music()
    {
        Media media = new Media(new File("src\\Resources\\Music\\oldmacdonald.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void passMenuInstance(Menu menu)
    {
        optionsScene = new Options(stage,menu);
        choosePlayerScene = new ChoosePlayer(stage,menu,players);
    }

    private Label insertPlayer()
    {
        Label playerName = new Label("Player hasn't been chosen!");
        playerName.setTextFill(Color.rgb(54,16,0));
        playerName.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
        playerName.setLayoutX(Menu.WIDTH - 470);
        playerName.setLayoutY(Menu.HEIGHT / 6);
        if( player != null && player.getId() > 0 )
            playerName.setText("Player : "+player.getName());
        else
            playerName.setText("Player hasn't been chosen!");
        return playerName;
    }

    private ImageView insertStart()
    {
        try
        {
            Image start = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\startButton.png")
                    , 250,81, false, true);
            ImageView startView = new ImageView(start);
            startView.setY(HEIGHT / 3);
            startView.setX(WIDTH - 400);
            return startView;
        }
        catch ( Exception e ){}
        return null;
    }

    private ImageView insertChoosePlayer()
    {
        try
        {
            Image choosePlayer = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\choosePlayerButton.png")
                    , 250, 81, false, true);
            ImageView choosePlayerView = new ImageView(choosePlayer);
            choosePlayerView.setY(HEIGHT / 2);
            choosePlayerView.setX(WIDTH - 400);
            return choosePlayerView;
        }
        catch ( Exception e ){}
        return null;
    }

    private ImageView insertOptions()
    {
        try
        {
            Image options = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\optionsButton.png")
                    , 250, 81, false, true);
            ImageView optionsView = new ImageView(options);
            optionsView.setY(HEIGHT * 2 / 3);
            optionsView.setX(WIDTH - 400);
            return optionsView;
        }
        catch ( Exception e ){}
        return null;
    }

    private ImageView insertExit()
    {
        try
        {
            Image exit = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\exitButton.png")
                    , 250, 81, false, true);
            ImageView exitView = new ImageView(exit);
            exitView.setY(HEIGHT * 5 / 6);
            exitView.setX(WIDTH - 400);
            return exitView;
        }
        catch ( Exception e ){}
        return null;
    }

    private void exitHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,WIDTH,HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image exitMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\exitMessageBox.png")
                    , 800, 300, false, true);
            ImageView exitMessageView = new ImageView(exitMessage);
            exitMessageView.setY(HEIGHT / 2 - 150);
            exitMessageView.setX(WIDTH / 2 - 400);

            Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                    , 153, 145, false, true);
            ImageView yesView = new ImageView(yes);
            yesView.setY(HEIGHT / 2 + 150);
            yesView.setX(WIDTH / 2 - 200);
            yesView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( player != null )
                        player.setLastPlayer(true);
                    for( Player p : players )
                        if( p.isLastPlayer() && p != player )
                            p.setLastPlayer(false);
                    Controller.savePlayers(players);
                    stage.close();
                }
            });

            Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                    , 153, 146, false, true);
            ImageView noView = new ImageView(no);
            noView.setY(HEIGHT / 2 + 150 );
            noView.setX(WIDTH / 2 + 47);
            noView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    group.getChildren().removeAll(rectangle,exitMessageView,yesView,noView);
                }
            });
            group.getChildren().addAll(rectangle,exitMessageView,yesView,noView);
        }
        catch ( Exception e ){}
    }

    private void makeStart()
    {
        startScene = new Start(stage,menu,player,players);
    }
}

