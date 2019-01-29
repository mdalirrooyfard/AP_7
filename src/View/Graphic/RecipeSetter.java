package View.Graphic;

import Model.Constants;
import Model.Farm;
import View.View;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class RecipeSetter
{
    private int height = 0 , width = 0;
    private Rectangle rectangle;
    private ImageView recipeBackView , okView , backView;
    private ArrayList<ImageView> items = new ArrayList<>();
    private ArrayList<ImageView> inputs = new ArrayList<>();

    public void makeScene( View view , Farm farm , ConcurrentHashMap<String,Image> items , AnimationTimer aTimer)
    {
        height = 0;
        width = 0;
        try
        {
            rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            recipeBackView = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\recipeBack.png")
                    , 800, 600, false, true));
            recipeBackView.setX(Constants.WIDTH / 2 - 400);
            recipeBackView.setY(Constants.HEIGHT / 2 - 300);

            view.getGroup().getChildren().addAll(rectangle,recipeBackView);
            insertBack(farm,view,aTimer);
            insertOk(farm,view,aTimer,items);
            inputsSetter(view,farm,items);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void insertBack( Farm farm ,  View view , AnimationTimer animationTimer )
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 60, 60, false, true);
            backView = new ImageView(back);
            backView.setX(Constants.WIDTH / 2 + 320);
            backView.setY(Constants.HEIGHT / 2 + 300);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    farm.getWorkshops()[6].getInputs().clear();
                    view.getGroup().getChildren().removeAll(rectangle,recipeBackView,okView,backView);
                    view.getGroup().getChildren().removeAll(items);
                    animationTimer.start();
                }
            });
            view.getGroup().getChildren().addAll(backView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void insertOk( Farm farm , View view , AnimationTimer animationTimer , ConcurrentHashMap<String,Image> itemsImages )
    {
        try
        {
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 53, false, true);
            okView = new ImageView(ok);
            okView.setX(Constants.WIDTH / 2 + 20);
            okView.setY(Constants.HEIGHT / 2 + 300);
            okView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( farm.getWorkshops()[6].getInputs().size() > 0 )
                    {
                        view.getGroup().getChildren().removeAll(recipeBackView,okView,backView);
                        view.getGroup().getChildren().removeAll(items);
                        outputSetter(view, farm, itemsImages, animationTimer);
                    }
                    else
                        noInputHandler();
                }
            });
            view.getGroup().getChildren().addAll(okView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void inputsSetter(View view,Farm farm,ConcurrentHashMap<String,Image> itemsImages)
    {
        try
        {
            width = 0;
            for( String item : Constants.ITEM_NAMES )
            {
                width++;
                if( width > 5 )
                {
                    width = 1;
                    height++;
                }

                ImageView itemView = new ImageView(itemsImages.get(item));
                items.add(itemView);
                itemView.setX(Constants.WIDTH / 2 - 450 + width * 75);
                itemView.setY(Constants.HEIGHT / 2 - 225 + height * 75);
                itemView.setFitHeight(50);
                itemView.setFitWidth(50);
                view.getGroup().getChildren().add(itemView);
                itemView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        if( !farm.getWorkshops()[6].getInputs().contains(item) )
                        {
                            itemView.setX(itemView.getX()+400);
                            farm.getWorkshops()[6].getInputs().add(item);
                        }
                        else
                        {
                            itemView.setX(itemView.getX()-400);
                            farm.getWorkshops()[6].getInputs().remove(item);
                        }
                    }
                });
            }
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void outputSetter(View view,Farm farm,ConcurrentHashMap<String,Image> itemsImages,AnimationTimer animationTimer)
    {
        try
        {
            width = 0;
            for( String item : Constants.ITEM_NAMES )
            {
                width++;
                if( width > 5 )
                {
                    width = 1;
                    height++;
                }

                ImageView itemView = new ImageView(itemsImages.get(item));
                items.add(itemView);
                itemView.setX(Constants.WIDTH / 2 - 450 + width * 75);
                itemView.setY(Constants.HEIGHT / 2 - 225 + height * 75);
                itemView.setFitHeight(50);
                itemView.setFitWidth(50);
                view.getGroup().getChildren().add(itemView);
                itemView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        if( !farm.getWorkshops()[6].getInputs().contains(item) )
                        {
                            itemView.setX(itemView.getX()+400);
                            farm.getWorkshops()[6].getInputs().add(item);
                        }
                    }
                });
                itemView.setOnDragDropped(new EventHandler<DragEvent>()
                {
                    @Override
                    public void handle(DragEvent event)
                    {
                        if( !farm.getWorkshops()[6].getInputs().contains(item) )
                        {
                            itemView.setX(itemView.getX()+400);
                            farm.getWorkshops()[6].getInputs().add(item);
                        }
                        else
                        {
                            itemView.setX(itemView.getX()-400);
                            farm.getWorkshops()[6].getInputs().remove(item);
                        }
                    }
                });
            }
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void noInputHandler()
    {

    }
}
