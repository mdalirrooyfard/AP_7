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
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
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
    public static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private Options optionsScene;
    private Group group = new Group();
    private Scene scene = new Scene(group, WIDTH, HEIGHT);
    private Player player = new Player("",0);
    private Vector<Player> players;
    private boolean muteMusic = false , muteSound = false , fullScreen = true;
    private MediaPlayer mediaPlayer;
    private Menu menu;
    private Server server;
    private VBox leaderBoardBox;

    public HostMenu(Server server)
    {
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

            leaderBoardView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Vector<Player> players = new Vector<>();
                    HashMap<String, Player> users = server.getServerSender().getUsernames();
                    int numberOfLines = 0;
                    for (String s : users.keySet()) {
                        players.add(users.get(s));
                        numberOfLines++;
                    }
                    showLeaderBoard(players, numberOfLines);
                }
            });

            chatRoomView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        if (!server.getServerGui().getOpen()) {
                            Stage stage = new Stage();
                            server.getServerGui().start(stage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            optionsView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    stage.setScene(optionsScene.getScene());
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
            leaderBoardView.setY(HEIGHT / 3);
            leaderBoardView.setX(WIDTH - 400);
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
            chatRoomView.setY(HEIGHT / 2);
            chatRoomView.setX(WIDTH - 400);
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
        catch ( Exception e ){}
    }

    private Label insertPlayer()
    {
        Label playerName = new Label(player.getName());
        playerName.setTextFill(Color.rgb(54,16,0));
        playerName.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
        playerName.setLayoutX(Menu.WIDTH - 470);
        playerName.setLayoutY(Menu.HEIGHT / 6);
        return playerName;
    }
}
