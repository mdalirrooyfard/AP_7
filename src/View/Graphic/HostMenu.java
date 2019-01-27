package View.Graphic;

import Model.Constants;
import Network.Server;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HostMenu
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Constants.WIDTH,Constants.HEIGHT);
    private Server server;

    public HostMenu(Server server)
    {
        this.server = server;
        Button leaderBoard = new Button("LeaderBoard");
        Button chatRoom = new Button("Chat room");
        group.getChildren().addAll(leaderBoard,chatRoom);
        leaderBoard.relocate(200,200);
        chatRoom.relocate(300,300);
        chatRoom.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = new Stage();
                try {
                    server.getServerGui().start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
