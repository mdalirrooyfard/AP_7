package View.Graphic;

import Model.Player;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class ChoosePlayer
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);
    private Stage stage;
    private Menu menu;
    private Player player;

    public Group getGroup()
    {
        return group;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public void setMenu(Menu menu)
    {
        this.menu = menu;
    }

    public Scene getScene()
    {
        stage.setFullScreen(menu.isFullScreen());
        return scene;
    }

    public ChoosePlayer(Stage stage)
    {
        this.stage = stage;
        try
        {
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\background.png")
                    , Menu.WIDTH, Menu.HEIGHT, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setY(0);
            backgroundView.setX(0);

            Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image menuBackground = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\menuBackground.png")
                    , 550, Menu.HEIGHT, false, true);
            ImageView menuBackgroundView = new ImageView(menuBackground);
            menuBackgroundView.setY(0);
            menuBackgroundView.setX(Menu.WIDTH - 550);
            group.getChildren().addAll(backgroundView,rectangle,menuBackgroundView);

            insertBack();
        }
        catch ( Exception e ){}
    }

    private void insertBack()
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 84, 79, false, true);
            ImageView backView = new ImageView(back);
            backView.setY(Menu.HEIGHT - 150);
            backView.setX(Menu.WIDTH - 500);
            group.getChildren().addAll(backView);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    stage.setScene(menu.getScene());
                }
            });

        }
        catch ( Exception e ){}
    }


}
