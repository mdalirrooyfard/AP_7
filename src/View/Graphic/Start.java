package View.Graphic;

import Model.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class Start
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);


    public Scene getScene()
    {
        return scene;
    }

    public Start(Stage stage, Menu menu, Player player)
    {
        insertBack(stage,menu);
    }

    private void insertBack( Stage stage , Menu menu )
    {
        try
        {
            Image image = new Image(new FileInputStream("E:\\AP\\Project\\src\\View\\Graphic\\back.png")
                    , 100, 70, false, true);
            ImageView back = new ImageView(image);
            back.setY(Menu.HEIGHT - 200);
            back.setX(Menu.WIDTH - 400);
            group.getChildren().addAll(back);
            back.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    stage.setScene(menu.getScene());
                }
            });
        }
        catch ( IOException e ){}

    }
}
