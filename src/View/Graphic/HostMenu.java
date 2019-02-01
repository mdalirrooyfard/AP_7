package View.Graphic;

import Controller.Controller;
import Model.Constants;
import Model.Player;
import Network.ClientGui;
import Network.Server;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

public class HostMenu
{
    Stage stage;
    private Group group = new Group();
    private Scene scene = new Scene(group, Constants.WIDTH, Constants.HEIGHT);
    private Player player;
    private Vector<Player> players;
    private boolean muteMusic , muteSound , isFullScreen;
    private Server server;
    private VBox leaderBoardBox;

    public HostMenu( Stage stage , Player player , Menu menu , Vector<Player> players , Server server)
    {
        this.stage = stage;
        this.player = player;
        muteMusic = menu.isMusicMuted();
        isFullScreen = menu.isFullScreen();
        muteSound = menu.isSoundMuted();
        this.players = players;
        try
        {
            this.server = server;
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\background.png")
                    , Constants.WIDTH, Constants.HEIGHT, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setY(0);
            backgroundView.setX(0);

            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image menuBackground = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\menuBackground.png")
                    , 550, Constants.HEIGHT, false, true);
            ImageView menuBackgroundView = new ImageView(menuBackground);
            menuBackgroundView.setY(0);
            menuBackgroundView.setX(Constants.WIDTH - 550);

            ImageView leaderBoardView = insertLeaderBoard();
            ImageView chatRoomView = insertChatroom();
            ImageView optionsView = insertOptions();
            ImageView exitView = insertExit();

            leaderBoardView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    Vector<Player> players = new Vector<>();
                    HashMap<String, Player> users = server.getServerSender().getUsernames();
                    int numberOfLines = 0;
                    for (String s : users.keySet())
                    {
                        players.add(users.get(s));
                        numberOfLines++;
                    }
                    showLeaderBoard(players, numberOfLines);
                }
            });

            chatRoomView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        if (!server.getServerGui().getOpen())
                        {
                            Stage stage = new Stage();
                            server.getServerGui().start(stage);
                        }
                    }
                    catch (Exception e) { e.printStackTrace(); }
                }
            });

            optionsView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    optionsHandler(menu);
                }
            });

            exitView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    exitHandler();
                }
            });
            Label playerName = insertPlayer();

            group.getChildren().addAll(backgroundView, rectangle , menuBackgroundView, leaderBoardView, chatRoomView ,
                    optionsView,exitView,playerName);


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showLeaderBoard(Vector<Player> players, int numberOfLines){
        try{
            Rectangle rectangle = new Rectangle(0, 0, 800, 600);
            rectangle.setFill(Color.rgb(54, 16, 0));
            rectangle.setOpacity(0.7);
            Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                    500, (numberOfLines + 1) * 50, false, true);
            ImageView messageView = new ImageView(message);
            messageView.setX(150);
            messageView.setY(Constants.HEIGHT / 2 - numberOfLines * 45);
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 39, false, true);
            ImageView okView = new ImageView(ok);
            okView.setY(messageView.getY() + numberOfLines * 50 + 50);
            okView.setX(450);
            leaderBoardBox = ClientGui.makeLeaderBoard(players, numberOfLines);
            leaderBoardBox.relocate( 220,Constants.HEIGHT / 2 - numberOfLines * 45);
            Image levelSort = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\levelSortButton.png"),
                    100, 39, false, true);
            ImageView levelSortView = new ImageView(levelSort);
            levelSortView.relocate(320,messageView.getY() + numberOfLines * 50 + 50);

            Image moneySort = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\moneySortButton.png"),
                    100, 39, false, true);
            ImageView moneySortView = new ImageView(moneySort);
            moneySortView.relocate( 210,messageView.getY() + numberOfLines * 50 + 50);

            okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    group.getChildren().removeAll(rectangle, messageView, okView, leaderBoardBox, levelSortView, moneySortView);
                }
            });

            levelSortView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    group.getChildren().remove(leaderBoardBox);
                    Collections.sort(players);
                    leaderBoardBox = ClientGui.makeLeaderBoard(players, numberOfLines);
                    leaderBoardBox.relocate( 220,Constants.HEIGHT / 2 - numberOfLines * 45);
                    group.getChildren().add(leaderBoardBox);
                }
            });

            moneySortView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    group.getChildren().remove(leaderBoardBox);
                    players.sort(new Comparator<Player>() {
                        @Override
                        public int compare(Player o1, Player o2) {
                            return o1.getMoney() - o2.getMoney();
                        }
                    });
                    leaderBoardBox = ClientGui.makeLeaderBoard(players, numberOfLines);
                    leaderBoardBox.relocate( 220,Constants.HEIGHT / 2 - numberOfLines * 45);
                    group.getChildren().add(leaderBoardBox);
                }
            });
            group.getChildren().addAll(rectangle, messageView, okView, leaderBoardBox, levelSortView, moneySortView);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Group getGroup()
    {
        return group;
    }

    public Scene getScene()
    {
        return scene;
    }

    private ImageView insertLeaderBoard()
    {
        try
        {
            Image leaderBoard = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\leaderBoardButton.png")
                    ,250,81,false,true);
            ImageView leaderBoardView = new ImageView(leaderBoard);
            leaderBoardView.setY(Constants.HEIGHT / 3);
            leaderBoardView.setX(Constants.WIDTH - 400);
            return leaderBoardView;
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return null;
    }

    private ImageView insertChatroom()
    {
        try
        {
            Image chatRoom = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\chatRoomButton.png")
                    ,250,81,false,true);
            ImageView chatRoomView = new ImageView(chatRoom);
            chatRoomView.setY(Constants.HEIGHT / 2);
            chatRoomView.setX(Constants.WIDTH - 400);
            return chatRoomView;
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return null;
    }

    private ImageView insertOptions()
    {
        try
        {
            Image options = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\optionsButton.png")
                    , 250, 81, false, true);
            ImageView optionsView = new ImageView(options);
            optionsView.setY(Constants.HEIGHT * 2 / 3);
            optionsView.setX(Constants.WIDTH - 400);
            return optionsView;
        }
        catch ( Exception e ){ e.printStackTrace(); }
        return null;
    }

    private ImageView insertExit()
    {
        try
        {
            Image exit = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\exitButton.png")
                    , 250, 81, false, true);
            ImageView exitView = new ImageView(exit);
            exitView.setY(Constants.HEIGHT * 5 / 6);
            exitView.setX(Constants.WIDTH - 400);
            return exitView;
        }
        catch ( Exception e ){ e.printStackTrace(); }
        return null;
    }

    private void optionsHandler(Menu menu)
    {
        try
        {
            Image menuBackground = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\menuBackground.png")
                    , 550, Menu.HEIGHT, false, true);
            ImageView menuBackgroundView = new ImageView(menuBackground);
            menuBackgroundView.setY(0);
            menuBackgroundView.setX(Menu.WIDTH - 550);

            Image soundIconMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\muteSoundButton.png")
                    , 102, 98, false, true);
            Image soundIconUnMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\soundButton.png")
                    , 102, 98, false, true);

            Image musicIconMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\musicMuteButton.png")
                    , 102, 98, false, true);
            Image musicIconUnMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\musicButton.png")
                    , 102, 98, false, true);

            Image uncheckedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxFalse.png")
                    , 25, 25, false, true);
            Image checkedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxTrue.png")
                    , 25, 25, false, true);

            Label sound = new Label("Sound On/Off : ");
            sound.setLayoutY(Menu.HEIGHT / 4);
            sound.setLayoutX(Menu.WIDTH - 400);
            sound.setTextFill(Color.rgb(54,16,0));
            sound.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,25));

            Label music = new Label("Music On/Off : ");
            music.setLayoutY(Menu.HEIGHT / 2);
            music.setLayoutX(Menu.WIDTH - 400);
            music.setTextFill(Color.rgb(54,16,0));
            music.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,25));

            Label fullScreen = new Label("Full Screen On/Off : ");
            fullScreen.setLayoutY(Menu.HEIGHT * 3 / 4);
            fullScreen.setLayoutX(Menu.WIDTH - 400);
            fullScreen.setTextFill(Color.rgb(54,16,0));
            fullScreen.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,25));

            ImageView soundView = insertSoundOption();
            ImageView musicView = insertMusicOption();
            ImageView fullScreenView = insertFullScreenOption();
            ImageView backView = insertBack();

            soundView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    //TODO really mute sound!
                    if( muteSound )
                    {
                        muteSound = false;
                        soundView.setImage(soundIconUnMute);
                    }
                    else
                    {
                        muteSound = true;
                        soundView.setImage(soundIconMute);
                    }
                }
            });

            musicView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( muteMusic )
                    {
                        musicView.setImage(musicIconUnMute);
                        menu.getMediaPlayer().play();
                        muteMusic = false;
                    }
                    else
                    {
                        musicView.setImage(musicIconMute);
                        menu.getMediaPlayer().stop();
                        muteMusic = true;
                    }
                }
            });

            fullScreenView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( isFullScreen )
                    {
                        fullScreenView.setImage(uncheckedCheckBox);
                        stage.setFullScreen(false);
                        isFullScreen = false;
                    }
                    else
                    {
                        fullScreenView.setImage(checkedCheckBox);
                        stage.setFullScreen(true);
                        isFullScreen = true;
                    }
                }
            });

            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    group.getChildren().removeAll(soundView,sound,fullScreen,music,menuBackgroundView,musicView,fullScreenView,backView);
                }
            });
            group.getChildren().addAll(menuBackgroundView,soundView,sound,fullScreen,music,musicView,fullScreenView,backView);
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private ImageView insertBack()
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 84, 79, false, true);
            ImageView backView = new ImageView(back);
            backView.setY(0);
            backView.setY(Menu.HEIGHT - 150);
            backView.setX(Menu.WIDTH - 500);
            return backView;
        }
        catch ( Exception e ){ e.printStackTrace(); }
        return null;
    }

    private ImageView insertSoundOption()
    {
        try
        {
            Image soundIconMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\muteSoundButton.png")
                    , 102, 98, false, true);
            Image soundIconUnMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\soundButton.png")
                    , 102, 98, false, true);
            ImageView soundIconView;
            if( muteSound )
                soundIconView = new ImageView(soundIconMute);
            else
                soundIconView = new ImageView(soundIconUnMute);
            soundIconView.setY(Menu.HEIGHT / 4);
            soundIconView.setX(Menu.WIDTH - 198);
            return soundIconView;
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return null;
    }

    private ImageView insertMusicOption()
    {
        try
        {
            Image musicIconMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\musicMuteButton.png")
                    , 102, 98, false, true);
            Image musicIconUnMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\musicButton.png")
                    , 102, 98, false, true);
            ImageView musicIconView;
            if( muteMusic )
                musicIconView = new ImageView(musicIconMute);
            else
                musicIconView = new ImageView(musicIconUnMute);
            musicIconView.setY(Menu.HEIGHT / 2);
            musicIconView.setX(Menu.WIDTH - 198);
            return  musicIconView;
        }
        catch ( Exception e ){ e.printStackTrace(); }
        return null;
    }

    private ImageView insertFullScreenOption()
    {
        //TODO fullScreen still has problem!
        try
        {
            Image uncheckedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxFalse.png")
                    , 25, 25, false, true);
            Image checkedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxTrue.png")
                    , 25, 25, false, true);
            ImageView fullScreenView;
            if( isFullScreen )
                fullScreenView = new ImageView(checkedCheckBox);
            else
                fullScreenView = new ImageView(uncheckedCheckBox);
            fullScreenView.setY(Menu.HEIGHT * 3 / 4 + 10);
            fullScreenView.setX(Menu.WIDTH - 135);
            return fullScreenView;
        }
        catch ( Exception e ){ e.printStackTrace(); }
        return null;
    }

    private void exitHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image exitMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\exitMessageBox.png")
                    , 800, 300, false, true);
            ImageView exitMessageView = new ImageView(exitMessage);
            exitMessageView.setY(Constants.HEIGHT / 2 - 150);
            exitMessageView.setX(Constants.WIDTH / 2 - 400);

            Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                    , 153, 145, false, true);
            ImageView yesView = new ImageView(yes);
            yesView.setY(Constants.HEIGHT / 2 + 150);
            yesView.setX(Constants.WIDTH / 2 - 200);
            yesView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
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
            noView.setY(Constants.HEIGHT / 2 + 150 );
            noView.setX(Constants.WIDTH / 2 + 47);
            noView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    group.getChildren().removeAll(rectangle,exitMessageView,yesView,noView);
                }
            });
            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    group.getChildren().removeAll(rectangle,exitMessageView,yesView,noView);
                }
            });
            group.getChildren().addAll(rectangle,exitMessageView,yesView,noView);
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private Label insertPlayer()
    {
        Label playerName = new Label("Player: " + player.getName());
        playerName.setTextFill(Color.rgb(54,16,0));
        playerName.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
        playerName.setLayoutX(Menu.WIDTH - 470);
        playerName.setLayoutY(Menu.HEIGHT / 6);
        return playerName;
    }
}
