package View.Graphic;

import Controller.Controller;
import Model.Player;
import View.View;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private Player player;
    //todo set players name
    class levelHandler implements EventHandler<MouseEvent>
    {
        private final int number;
        private Stage stage;
        private boolean newGame = false;
        levelHandler(int n, Stage stage)
        {
            this.number = n;
            this.stage = stage;
        }
        @Override
        public void handle(MouseEvent event) {
            if (player.getLastLevel() < number){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("This level is not opened for you!");
                alert.setContentText("Choose a lower level");
                alert.showAndWait();
                return;
            }

            Controller controller = new Controller();
            try{
                controller.setPlayer(player);
                controller.setLevel(number);
                String path = "src\\SavedGames\\"+player.getName()+"-"+Integer.toString(player.getId())+"-"+Integer.toString(number);
                controller.canGameBeContinued(path);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Would you like to continue?");
                alert.setHeaderText("You have started this level before");
                ButtonType b1 = new ButtonType("Yes");
                ButtonType b2 = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(b1, b2);
                alert.showAndWait().ifPresent(result -> {
                    if (result == b1) {
                        try {
                            controller.loadGameHandler(path);
                        }
                        catch (Exception e){
                        }
                        //todo make the game from controller
                    }
                    if (result == b2)
                        newGame = true;
                });
            }
            catch (Exception e){
                newGame = true;
            }
            finally {
                if (newGame){
                    //todo ask if player wanted custom workshop
                    try{
                        String path2 = "src\\Resources\\Levels\\Level"+Integer.toString(number)+".txt";
                        controller.loadCustomHandler(path2);
                    }
                    catch (Exception e) {
                    }
                    finally {
                        try {
                            controller.runHandler();
                        } catch (Exception e) {

                        }
                    }
                }
            }

        }
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Scene getScene()
    {
        return scene;
    }

    public Start(Stage stage , Menu menu , Player player)
    {
        this.player = player;
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
        }
        catch ( Exception e ){}
        insertBack(stage,menu);
        insertLevels(stage);
    }

    private void insertLevels(Stage stage)
    {
        try
        {
            for( int i = 0 ; i < 10 ; i++ )
            {
                String levelName = "Level"+Integer.toString(i + 1);
                if( i + 1 <= player.getLastLevel() )
                    levelName += "Open.png";
                else
                    levelName += "Close.png";
                Image level = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\"+levelName)
                        , 200, 79, false, true);
                ImageView levelView = new ImageView(level);
                levelView.setX((Menu.WIDTH - 500) * ( (i + 1) % 2 ) + (Menu.WIDTH - 250) * ( i % 2));
                levelView.setY(Menu.HEIGHT * ( i / 2  + 1) / 7);
                group.getChildren().addAll(levelView);
                levelView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {

                    }
                });
            }
        }
        catch ( IOException e ){}
    }

    private void insertBack(Stage stage,Menu menu)
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 84, 79, false, true);
            ImageView backView = new ImageView(back);
            backView.setX(0);
            backView.setY(0);
            backView.setY(Menu.HEIGHT - 120);
            backView.setX(Menu.WIDTH - 450);
            group.getChildren().addAll(backView);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event){stage.setScene(menu.getScene());}
            });
        }
        catch ( Exception e ){}
    }
}
