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

public class OrderPage
{
    Group group = new Group();
    Scene scene = new Scene(group, Constants.WIDTH,Constants.HEIGHT);
    int height = 1 , itemNumber = 0;

    public Scene getScene()
    {
        return scene;
    }

    public OrderPage(Stage stage , View view, Farm farm, HashMap<String , Image> items)
    {
        try
        {
            Image order = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\order"
                    +farm.getHelicopter().getLevel()+".png"), Menu.WIDTH, Menu.HEIGHT, false, true);
            ImageView orderView = new ImageView(order);
            orderView.setX(0);
            orderView.setY(0);
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 80, 80, false, true);
            ImageView backView = new ImageView(back);
            backView.setX(Constants.WIDTH - 200);
            backView.setY(Constants.HEIGHT - 100);
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    150, 79, false, true);
            ImageView okView = new ImageView(ok);
            okView.setX(Constants.WIDTH - 400);
            okView.setY(Constants.HEIGHT - 100);
            group.getChildren().addAll(orderView,backView,okView);
            for( String item : Constants.ITEM_NAMES )
            {
                height = 1;
                itemNumber = 0;
                if( numberOfItem(farm,item) > 0 )
                {
                    ImageView itemView = new ImageView(items.get(item));
                    itemView.setX(30);
                    itemView.setY(50 * height + 100);
                    itemView.setFitHeight(50);
                    itemView.setFitWidth(50);
                    ImageView plusView = new ImageView(
                            new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\plusButton.png")
                                    , 70, 70, false, true));
                    ImageView minusView = new ImageView(
                            new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\minusButton.png")
                                    , 70, 70, false, true));
                    plusView.setX(100);
                    plusView.setY(50 * height + 90);
                    minusView.setX(190);
                    minusView.setY(50 * height + 90);
                    group.getChildren().addAll(itemView,plusView,minusView);
                    plusView.setOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent event)
                        {
                            if( farm.getHelicopter().getCurrentVolume() < farm.getHelicopter().getVolume() )
                            {
                                itemNumber++;
                                farm.addToHelicopter(item,1);
                                ImageView itemView = new ImageView(items.get(item));
                                Label number = new Label(Integer.toString(itemNumber));
                                itemView.setX(450);
                                itemView.setY(50 * height + 100);
                                itemView.setFitHeight(50);
                                itemView.setFitWidth(50);
                                number.setTextFill(Color.rgb(54,16,0));
                                number.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,14));
                                group.getChildren().addAll(itemView,number);
                            }
                        }
                    });
                    minusView.setOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent event)
                        {
                            if( itemNumber > 0 )
                            {
                                itemNumber--;
                                farm.clearOneItemFromHelicopter(item);
                                Label number = new Label(Integer.toString(itemNumber));
                                number.setTextFill(Color.rgb(54,16,0));
                                number.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,14));
                                group.getChildren().addAll(number);
                            }
                        }
                    });
                    height++;
                }
            }
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    farm.clearHelicopterBeforeGo();
                    stage.setScene(view.getScene());
                }
            });
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private int numberOfItem( Farm farm , String item )
    {
        int number = 0;
        for( Item i : farm.getWareHouse().getCollectedItems() )
            if( i.getKind().equals(item) )
                number++;
        return number;
    }
}
