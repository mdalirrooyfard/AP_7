package View.Graphic;

import Model.Constants;
import Model.Farm;
import View.View;
import javafx.animation.AnimationTimer;
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
import java.util.concurrent.ConcurrentHashMap;

public class RecipeSetter
{
    Group group = new Group();
    Scene scene = new Scene(group, Constants.WIDTH,Constants.HEIGHT);
    int height = 0 , itemNumber = 0 , width = 0;

    public Scene getScene(Stage stage, View view, Farm farm, ConcurrentHashMap<String ,Image> items,  AnimationTimer aTimer)
    {
        height = 0;
        itemNumber = 0;
        width = 0;
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image recipeBack = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\recipeBack.png")
                    , 600, 500, false, true);
            ImageView recipeBackView = new ImageView(recipeBack);
            recipeBackView.setX(Constants.WIDTH - 300);
            recipeBackView.setY(Constants.HEIGHT - 250);
            group.getChildren().addAll(recipeBackView);
            insertBack(farm,stage,view);
            insertOk(farm,stage,view,aTimer);
            insertItems(farm,items);
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return scene;
    }

    private void insertBack(Farm farm , Stage stage , View view)
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 60, 60, false, true);
            ImageView backView = new ImageView(back);
            backView.setX(Constants.WIDTH / 2 + 320);
            backView.setY(Constants.HEIGHT / 2 + 220);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    farm.getWorkshops()[6].getInputs().clear();
                    group.getChildren().clear();
                    stage.setScene(view.getScene());
                }
            });
            group.getChildren().addAll(backView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void insertOk(Farm farm,Stage stage,View view,AnimationTimer animationTimer)
    {
        try
        {
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 53, false, true);
            ImageView okView = new ImageView(ok);
            okView.setX(Constants.WIDTH / 2 + 50);
            okView.setY(Constants.HEIGHT / 2 + 220);
            okView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( farm.getWorkshops()[6].getInputs().size() > 0 && farm.getWorkshops()[6].getOutput().equals("") )
                    {
                        farm.startWorkShop("customWorkshop");
                        animationTimer.start();
                        stage.setScene(view.getScene());
                    }
                }
            });
            group.getChildren().addAll(okView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void insertItems(Farm farm,ConcurrentHashMap<String,Image> items)
    {
        try
        {
            height = 0;
            for( String item : Constants.ITEM_NAMES )
            {
                height++;
                if( height > 7 )
                {
                    height = 1;
                    width++;
                }

                ImageView itemView = new ImageView(items.get(item));
                itemView.setX(Constants.WIDTH - 330 + width * ( 50 + 100));
                itemView.setY(30 * ( height - 1 ) + Constants.HEIGHT - 215);
                itemView.setFitHeight(50);
                itemView.setFitWidth(50);

                /*plusView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        if( !farm.getWorkshops()[6].getInputs().contains(item) )
                            farm.getWorkshops()[6].getInputs().add(item);
                    }
                });
                minusView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        if( farm.getWorkshops()[6].getInputs().contains(item) )
                            farm.getWorkshops()[6].getInputs().remove(item);
                    }
                });*/
            }
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }
}
