package View.Graphic;

import Controller.Loader;
import Model.Constants;
import Model.Farm;
import Model.Workshops.Workshop;
import View.ImageViewSprite;
import View.View;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import static Model.Constants.HEIGHT;
import static Model.Constants.WIDTH;

public class RecipeSetter
{
    private int height = 0 , width = 0;
    private Rectangle rectangle , choose , choose2;
    private ImageView recipeBackView , okView , backView , outputBackView;
    private ArrayList<ImageView> items = new ArrayList<>();
    private ArrayList<ImageView> inputs = new ArrayList<>();

    public void makeScene( View view , Farm farm , ConcurrentHashMap<String,Image> items , AnimationTimer aTimer , Loader loader)
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
            insertOk(farm,view,aTimer,items,loader);
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

    private void insertOk( Farm farm , View view , AnimationTimer animationTimer , ConcurrentHashMap<String,Image> itemsImages , Loader loader )
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
                        outputSetter(view, farm, itemsImages, animationTimer , loader);
                    }
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
            height = 0;
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

    private void outputSetter(View view,Farm farm,ConcurrentHashMap<String,Image> itemsImages,AnimationTimer animationTimer , Loader loader)
    {
        try
        {
            width = 0;
            height = 0;
            outputBackView = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\outputBack.png")
                    , 400, 600, false, true));
            outputBackView.setX(Constants.WIDTH / 2 - 200);
            outputBackView.setY(Constants.HEIGHT / 2 - 300);
            showOk( farm , view , animationTimer,loader);
            choose = new Rectangle();
            choose.setFill(Color.rgb(54,16,0));
            choose2 = new Rectangle();
            choose2.setFill(Color.rgb(234,205,164));
            view.getGroup().getChildren().addAll(outputBackView);
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
                itemView.setX(Constants.WIDTH / 2 - 250 + width * 75);
                itemView.setY(Constants.HEIGHT / 2 - 225 + height * 75);
                itemView.setFitHeight(50);
                itemView.setFitWidth(50);
                view.getGroup().getChildren().add(itemView);
                itemView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        view.getGroup().getChildren().removeAll(itemView,choose,choose2);
                        choose.setX(itemView.getX() - 5);
                        choose.setY(itemView.getY() - 5);
                        choose.setWidth(60);
                        choose.setHeight(60);
                        choose2.setX(itemView.getX());
                        choose2.setY(itemView.getY());
                        choose2.setWidth(50);
                        choose2.setHeight(50);
                        view.getGroup().getChildren().addAll(choose,choose2,itemView);
                        farm.getWorkshops()[6].setOutput(item);
                    }
                });
            }
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void showOk(Farm farm , View view , AnimationTimer animationTimer , Loader loader )
    {
        try
        {
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 53, false, true);
            okView = new ImageView(ok);
            okView.setX(Constants.WIDTH / 2 - 50);
            okView.setY(Constants.HEIGHT / 2 + 300);
            okView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( farm.getWorkshops()[6].getInputs().size() > 0)
                    {
                        view.getGroup().getChildren().removeAll(rectangle,outputBackView,okView,choose,choose2);
                        view.getGroup().getChildren().removeAll(items);
                        if(farm.getWareHouse().getCollectedItems().contains(inputs)) {
                            view.getGroup().getChildren().remove(loader.getFixedWorkShopsImageViews().get("customFactory"));
                            int result = farm.startWorkShop("customFactory");
                            animationTimer.start();
                            ImageView imageView = loader.getMovingCustomFactory();
                            imageView.setX(farm.getWorkshops()[6].getShowX());
                            imageView.setY(farm.getWorkshops()[6].getShowY());
                            view.getGroup().getChildren().add(imageView);
                            AnimationTimer imageViewSprite = new ImageViewSprite(imageView, 1, false,
                                    4, 4, 16, 200, 200, 16);
                            flyingItems(farm.getWorkshops()[6].getInputs(), result, farm.getWorkshops()[6], loader, view);
                            imageViewSprite.start();
                        }
                    }
                }
            });
            view.getGroup().getChildren().addAll(okView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void flyingItems(Vector<String> items , int count , Workshop workshop , Loader loader , View view)
    {
        Vector<ImageView> movingItems = new Vector<>();
        int counter = 0;
        System.out.println("");
        double startX = WIDTH/2 - 100;
        double startY = HEIGHT - 50;
        double displacement = 18;
        for (String s : items)
        {
            for (int i = 0; i < count ; i++)
            {
                ImageView imageView = new ImageView(loader.getWareHouseItems().get(s));
                imageView.setX(startX);
                imageView.setY(startY + counter * displacement);
                movingItems.add(imageView);
                counter ++;
                if(counter == 4)
                {
                    counter = 0;
                    startX -= displacement;
                }
                view.getGroup().getChildren().add(imageView);
            }
        }
        for (ImageView m : movingItems)
        {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(3000) , m);
            translateTransition.setToX(- m.getX() + workshop.getShowX() + 100);
            translateTransition.setToY(- m.getY() + workshop.getShowY() + 100);
            translateTransition.play();
            translateTransition.setOnFinished(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    view.getGroup().getChildren().remove(m);
                }
            });
        }
    }
}
