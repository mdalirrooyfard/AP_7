package View.Graphic;

import Model.Constants;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;


public class Multiplayer
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Constants.WIDTH,Constants.HEIGHT);

    public Group getGroup()
    {
        try
        {
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\background.png")
                    , Constants.WIDTH, Constants.HEIGHT, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setY(0);
            backgroundView.setX(0);

            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image multiplayerBack = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png")
                    , 500, 506, false, true);
            ImageView multiplayerView = new ImageView(multiplayerBack);
            multiplayerView.setY(Constants.HEIGHT / 2 - 253);
            multiplayerView.setX(Constants.WIDTH / 2 - 250);

            Label message = new Label("Host or Client?");
            message.setTextFill(Color.rgb(54,16,0));
            message.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,40));
            message.setLayoutX(Menu.WIDTH / 2 - 126.5);
            message.setLayoutY(Menu.HEIGHT / 2 - 150);

            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    group.getChildren().clear();
                }
            });
            group.getChildren().addAll(backgroundView,rectangle,multiplayerView,message);
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return group;
    }

    public Scene getScene()
    {
        return scene;
    }
}
