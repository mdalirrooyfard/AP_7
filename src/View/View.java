package View;

import View.Graphic.Menu;
import javafx.scene.Group;
import javafx.scene.Scene;


public class View
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);

    public Scene getScene()
    {
        return scene;
    }

    public Group getGroup()
    {
        return group;
    }

}
