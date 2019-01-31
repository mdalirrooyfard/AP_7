package View.Graphic;

import Model.Constants;
import Model.Player;
import Network.ClientGui;
import Network.Server;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

public class HostMenu
{
    private Group group = new Group();
    private Scene scene = new Scene(group, 800, 600);
    private Server server;
    private VBox leaderBoardBox;

    public HostMenu(Server server)
    {
        try {
            this.server = server;
            Image backGround = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\hostBackground.png"));
            ImageView backGroundView = new ImageView(backGround);
            backGroundView.setX(0);
            backGroundView.setY(0);
            backGroundView.setFitWidth(800);
            backGroundView.setFitHeight(600);
            Image leaderBoard = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\leaderBoardButton.png"));
            ImageView leaderBoardView = new ImageView(leaderBoard);
            leaderBoardView.setFitWidth(100);
            leaderBoardView.setFitHeight(100);
            Image chatRoom = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\privateChatButton.png"));
            ImageView chatRoomView = new ImageView(chatRoom);
            chatRoomView.setFitHeight(100);
            chatRoomView.setFitWidth(200);
            group.getChildren().addAll(backGroundView, leaderBoardView, chatRoomView);
            leaderBoardView.relocate(100, 250);
            chatRoomView.relocate(430, 250);
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
            leaderBoardView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Vector<Player> players = new Vector<>();
                    HashMap<String, Player> users = server.getServerSender().getUsenames();
                    int numberOfLines = 0;
                    for (String s : users.keySet()) {
                        players.add(users.get(s));
                        numberOfLines++;
                    }
                    showLeaderBoard(players, numberOfLines);
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showLeaderBoard(Vector<Player> players, int numberOfLines){
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
}
