package View;

import View.Graphic.Menu;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;


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

    public void goHelicopter(ImageView movingHelicopter,ImageView fixedHelicopter,boolean back)
    {
        /*AnimationTimer animationTimer = new ImageViewSprite(movingHelicopter,1,false, 3, 2,
                6, 48, 48, 6);
        animationTimer.start();**/
        double x1 = 500,x2 = 600;
        if( back )
        {
            x1 = 600;
            x2 = 500;
        }
        MoveTransition pathTransition = new MoveTransition(fixedHelicopter, x1, 10, x2, 10, 30000);
        pathTransition.setAutoReverse(false);
        pathTransition.setCycleCount(1);
        pathTransition.play();
    }
}
