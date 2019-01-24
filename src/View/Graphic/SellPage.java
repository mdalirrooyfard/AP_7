package View.Graphic;

import Model.Constants;
import Model.Farm;
import Model.Items.Item;
import View.View;
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
import java.util.HashMap;

public class SellPage
{
    Group group = new Group();
    Scene scene = new Scene(group, Constants.WIDTH,Constants.HEIGHT);
    int height = 0 , itemNumber = 0 , width = 1;

    public Scene getScene()
    {
        return scene;
    }

    public SellPage(Stage stage , View view, Farm farm, HashMap<String , Image> items)
    {
        try
        {
            Image order = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\sell"
                    +farm.getHelicopter().getLevel()+".png"), Menu.WIDTH, Menu.HEIGHT, false, true);
            ImageView orderView = new ImageView(order);
            orderView.setX(0);
            orderView.setY(0);
            group.getChildren().addAll(orderView);
            insertBack(farm,stage,view);
            insertOk(farm,stage,view);
            insertItems(farm,items);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void insertBack(Farm farm , Stage stage , View view)
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 80, 80, false, true);
            ImageView backView = new ImageView(back);
            backView.setX(Constants.WIDTH - 200);
            backView.setY(Constants.HEIGHT - 100);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    farm.clearHelicopterBeforeGo();
                    stage.setScene(view.getScene());
                }
            });
            group.getChildren().addAll(backView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void insertOk(Farm farm , Stage stage , View view)
    {
        try
        {
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    150, 79, false, true);
            ImageView okView = new ImageView(ok);
            okView.setX(Constants.WIDTH - 400);
            okView.setY(Constants.HEIGHT - 100);
            okView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    farm.goTransportation(false);
                    stage.setScene(view.getScene());
                }
            });
            group.getChildren().addAll(okView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void insertItems(Farm farm, HashMap<String,Image> items)
    {
        try
        {
            double scale = ( Constants.HEIGHT - 10 ) / 33;
            for( String item : Constants.ITEM_NAMES )
            {
                if( height > 16 )
                    width = 2;
                itemNumber = 0;
                ImageView itemView = new ImageView(items.get(item));
                itemView.setX(30);
                itemView.setY(scale * height + 10);
                itemView.setFitHeight(scale);
                itemView.setFitWidth(scale);
                ImageView plusView = new ImageView(
                        new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\plusButton.png")
                                , scale - 10, scale - 10, false, true));
                ImageView minusView = new ImageView(
                        new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\minusButton.png")
                                , scale - 10, scale - 10, false, true));
                plusView.setX(100);
                plusView.setY(scale * height + 10);
                minusView.setX(190);
                minusView.setY(scale * height + 10);
                group.getChildren().addAll(itemView,plusView,minusView);
                ImageView itemView1 = new ImageView(items.get(item));
                Label number = new Label("");
                itemView1.setX(550);
                itemView1.setY(scale * height + 10);
                itemView1.setFitHeight(scale);
                itemView1.setFitWidth(scale);
                number.setTextFill(Color.rgb(54,16,0));
                number.relocate(650,scale * height);
                number.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,14));
                group.getChildren().addAll(itemView1,number);
                plusView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        if( farm.getHelicopter().getCurrentVolume() < farm.getHelicopter().getVolume() )
                        {
                            itemNumber++;
                            //farm.addToHelicopter(item,1);
                            number.setText(Integer.toString(itemNumber));
                        }
                    }
                });
                minusView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        if( itemNumber > 1 )
                        {
                            itemNumber--;
                            farm.clearOneItemFromHelicopter(item);
                            number.setText(Integer.toString(itemNumber));
                        }
                        else if( itemNumber == 1 )
                        {
                            group.getChildren().removeAll(number,itemView1);
                        }
                    }
                });
                height++;
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
