package Graphic;

import javafx.scene.Group;
import javafx.scene.Scene;

public class Start
{
    private static final int HEIGHT = 400;
    private static final int WIDTH = 400;
    private Group group = new Group();
    private Scene scene = new Scene(group, WIDTH, HEIGHT);

    public Scene getScene()
    {
        return scene;
    }
}
