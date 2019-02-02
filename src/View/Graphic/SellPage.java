package View.Graphic;

import Model.Constants;
import Model.Farm;
import Model.Items.Item;
import View.View;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.concurrent.ConcurrentHashMap;

public class SellPage
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Constants.WIDTH,Constants.HEIGHT);
    private int height = 0 , itemNumber = 0 , width = 0;

    public Scene getScene(Stage stage, View view, Farm farm, ConcurrentHashMap<String ,Image> items, ImageView rightTruck
            , ImageView fixedTruck, AnimationTimer aTimer)
    {
        height = 0;
        itemNumber = 0;
        width = 0;
        try
        {
            Image order = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\sell"
                    +farm.getHelicopter().getLevel()+".png"), Menu.WIDTH, Menu.HEIGHT, false, true);
            ImageView orderView = new ImageView(order);
            orderView.setX(0);
            orderView.setY(0);
            group.getChildren().addAll(orderView);
            insertBack(farm,stage,view,aTimer);
            insertOk(farm,stage,view,rightTruck,fixedTruck,aTimer);
            insertItems(farm,items);
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return scene;
    }

    private void insertBack(Farm farm , Stage stage , View view , AnimationTimer animationTimer)
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 80, 80, false, true);
            ImageView backView = new ImageView(back);
            backView.setX(Constants.WIDTH - 100);
            backView.setY(Constants.HEIGHT - 100);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    farm.clearTruckBeforeGo();
                    group.getChildren().clear();
                    animationTimer.start();
                    stage.setScene(view.getScene());
                }
            });
            group.getChildren().addAll(backView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void insertOk(Farm farm,Stage stage,View view,ImageView rightTruck, ImageView fixedTruck
            ,AnimationTimer animationTimer)
    {
        try
        {
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    150, 79, false, true);
            ImageView okView = new ImageView(ok);
            okView.setX(Constants.WIDTH - 350);
            okView.setY(Constants.HEIGHT - 100);
            okView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( farm.getTruck().getGainedMoney() > 0 )
                    {
                        farm.goTransportation(true);
                        farm.getTruck().setPrevMovingX(350);
                        farm.getTruck().setNextMovingX(350 + farm.getTruck().getMovingScale());
                        view.getGroup().getChildren().remove(fixedTruck);
                        view.getGroup().getChildren().add(rightTruck);
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
            height = 1;
            double scale = ( Constants.HEIGHT - 80 ) / 18;
            for( String item : Constants.ITEM_NAMES )
            {
                if( height > 17 )
                {
                    height = 1;
                    width = 1;
                }
                itemNumber = 0;
                ImageView itemView = new ImageView(items.get(item));
                itemView.setX(30 + width * ( 4 * scale + 100));
                itemView.setY(scale * ( height - 1 ) + 85);
                itemView.setFitHeight(scale);
                itemView.setFitWidth(scale);
                Label numberOfItems = new Label(Integer.toString(numberOfItem(farm,item)));
                numberOfItems.setTextFill(Color.rgb(54,16,0));
                numberOfItems.relocate(45 + scale + width * ( 4 * scale + 100) ,scale * ( height - 1 ) + 80);
                numberOfItems.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,14));
                ImageView plusView = new ImageView(
                        new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\plusButton.png")
                                , scale, scale, false, true));
                ImageView minusView = new ImageView(
                        new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\minusButton.png")
                                , scale, scale, false, true));
                plusView.setX(60 + width * ( 4 * scale + 100) + 2 * scale);
                plusView.setY(scale * ( height - 1 ) + 80);
                minusView.setX(3 * scale + 75 + width * ( 4 * scale + 100));
                minusView.setY(scale * ( height - 1 ) + 80);
                if( numberOfItem(farm,item) > 0 )
                {
                    height++;
                    group.getChildren().addAll(itemView,numberOfItems,plusView,minusView);
                }
                ImageView itemView1 = new ImageView(items.get(item));
                Label number = new Label("");
                itemView1.setX(550 + width * ( 2 * scale + 100));
                itemView1.setY(scale * ( height - 1 ) + 80);
                itemView1.setFitHeight(scale);
                itemView1.setFitWidth(scale);
                number.setTextFill(Color.rgb(54,16,0));
                number.relocate(550 + scale + width * ( 2 * scale + 100) ,scale * ( height - 1 ) + 80);
                number.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,14));
                plusView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        if( !farm.getTruck().contains(item) )
                            itemNumber = 0;
                        if( farm.getTruck().getCurrentVolume() > 0 )
                        {
                            group.getChildren().removeAll(itemView1,number,numberOfItems);
                            if( farm.addToTruck(item,1) == 1 )
                            {
                                itemNumber++;
                                number.setText(Integer.toString(itemNumber));
                                numberOfItems.setText(Integer.toString(numberOfItem(farm,item)));
                                group.getChildren().addAll(itemView1,number,numberOfItems);
                                if( numberOfItem(farm,item) == 0 )
                                    group.getChildren().removeAll(itemView,numberOfItems,plusView,minusView);
                            }
                            else if( itemNumber > 0 )
                                group.getChildren().addAll(itemView1,number,numberOfItems);
                        }
                    }
                });
                minusView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        group.getChildren().removeAll(itemView1,number,numberOfItems);
                        itemNumber--;
                        farm.clearOneItemFromTruck(item);
                        number.setText(Integer.toString(itemNumber));
                        numberOfItems.setText(Integer.toString(numberOfItem(farm,item)));
                        if( itemNumber > 1 )
                            group.getChildren().addAll(itemView1,number,numberOfItems);
                    }
                });
            }
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private int numberOfItem(Farm farm , String item )
    {
        int number = 0;
        for( Item i : farm.getWareHouse().getCollectedItems() )
            if( i.getKind().equals(item) )
                number++;
        return number;
    }
}
