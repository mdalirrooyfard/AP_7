package View.Graphic;

import Model.Constants;
import Model.Transportation.Truck;
import View.View;
import javafx.scene.Group;
import javafx.scene.Scene;

public class SellPage
{
    Group group = new Group();
    Scene scene = new Scene(group, Constants.WIDTH,Constants.HEIGHT);

    public Scene getScene()
    {
        return scene;
    }

    public SellPage(View view, Truck truck)
    {

    }
}
