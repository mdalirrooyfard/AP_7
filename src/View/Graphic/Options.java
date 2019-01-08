package View.Graphic;

import javafx.scene.Group;
import javafx.scene.Scene;

public class Options
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);

    public Scene getScene()
    {
        return scene;
    }
}
