package View;

import View.Graphic.Menu;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class View
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);
    private Stage stage;
    public Scene getScene()
    {
        return scene;
    }

    public View(Stage stage)
    {
        this.stage = stage;
    }


    public void play()
    {
        stage.setScene(scene);
        try
        {
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\map.png"), Menu.WIDTH,
                    Menu.HEIGHT, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setX(0);
            backgroundView.setY(0);
            group.getChildren().addAll(backgroundView);
            Circle henCircle = new Circle(35 , 42 , 30);
            henCircle.setFill(Color.rgb(250 , 0 ,30));
            group.getChildren().addAll(henCircle);
            Image henIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\chicken.png"),
                    60 , 60 , false , true);
            ImageView henIconView = new ImageView(henIcon);
            henIconView.setX(5);
            henIconView.setY(8);
            group.getChildren().addAll(henIconView);
            Circle cowCircle = new Circle(100 , 42 , 30);
            cowCircle.setFill(Color.rgb(96 , 96 ,96));
            group.getChildren().addAll(cowCircle);
            Image cowIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\cow.png"),
                    50 , 50 , false , true);
            ImageView cowIconView = new ImageView(cowIcon);
            cowIconView.setX(70);
            cowIconView.setY(13);
            group.getChildren().addAll(cowIconView);
            Circle sheepCircle = new Circle(175 , 42 , 30);
            sheepCircle.setFill(Color.rgb(225 , 153 ,224));
            group.getChildren().addAll(sheepCircle);
            Image sheepIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\sheep.png"),
                    52, 52 , false , true);
            ImageView sheepIconView = new ImageView(sheepIcon);
            sheepIconView.setX(146);
            sheepIconView.setY(10);
            group.getChildren().addAll(sheepIconView);

        }
        catch (FileNotFoundException e) {}
    }

    /*String command;
    public String getCommand()
    {
        return command;
    }
    public void printError(String error)
    {
        System.out.println(error);
    }
    public void levelIsFinished()
    {
        System.out.println("Level is finished.  Congratulations! :) ");
    }
    public void printInfo(String info)
    {
        System.out.println(info);
    }
    public void setCommand( Scanner scanner )
    {
        command = scanner.nextLine();
    }*/
}
