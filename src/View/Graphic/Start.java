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

    public Start(Stage stage, Menu menu)
    {
        insertBack(stage,menu);
        insertLevels();
    }

    private void insertLevels()
    {
        try
        {
            Image image = new Image(new FileInputStream("E:\\AP\\Project\\src\\Resources\\Graphic\\Level.png")
                    , Menu.WIDTH, Menu.HEIGHT, false, true);
            ImageView levels = new ImageView(image);
            levels.setY(0);
            levels.setX(0);
            group.getChildren().addAll(levels);
            Text[] level = new Text[10];
            for( int i = 0 ; i < 10 ; i++ )
            {
                level[i] = new Text( ( i % 2) * ( Menu.WIDTH - 200 ) + ( ( i + 1 ) % 2 ) * 200 , 200 + i / 2 * 50
                        , "Level "+Integer.toString(i + 1));
                level[i].setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
                group.getChildren().addAll(level[i]);
            }
        }
        catch ( IOException e ){}
    }

    private void insertBack( Stage stage , Menu menu )
    {
        try
        {
            Image image = new Image(new FileInputStream("E:\\AP\\Project\\src\\Resources\\Graphic\\back.png")
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
