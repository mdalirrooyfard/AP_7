package View.Graphic;

import Model.Constants;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class HostMenu
{
    Group group = new Group();
    Scene scene = new Scene(group, Constants.WIDTH,Constants.HEIGHT);

    public HostMenu()
    {
        Button button1 = new Button("LeaderBoard");
        Button button2 = new Button("Chat room");
        group.getChildren().addAll(button1,button2);
        button1.relocate(200,200);
        button2.relocate(300,300);
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
