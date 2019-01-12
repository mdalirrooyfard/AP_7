package View;

import Controller.Controller;
import Model.Farm;
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
    private Farm farm;
    private Controller controller;
    public View(Controller controller, Stage stage){
        this.controller = controller;
        this.farm = controller.getFarm();
        play(stage);
    }

    public void play(Stage stage){
        Group root = new Group();
        Scene scene = new Scene(root , Menu.WIDTH,
                Menu.HEIGHT);
        stage.setScene(scene);
        try {
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\map.png"), Menu.WIDTH,
                    Menu.HEIGHT, false, true);
            ImageView imageView = new ImageView(background);
            root.getChildren().add(imageView);
            Circle henCircle = new Circle(35 , 42 , 30);
            henCircle.setFill(Color.rgb(250 , 0 ,30));
            root.getChildren().addAll(henCircle);
            Image henIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\chicken.png"),
                    60 , 60 , false , true);
            ImageView henIconView = new ImageView(henIcon);
            henIconView.setX(5);
            henIconView.setY(8);
            root.getChildren().addAll(henIconView);
            Circle cowCircle = new Circle(100 , 42 , 30);
            cowCircle.setFill(Color.rgb(96 , 96 ,96));
            root.getChildren().addAll(cowCircle);
            Image cowIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\cow.png"),
                    50 , 50 , false , true);
            ImageView cowIconView = new ImageView(cowIcon);
            cowIconView.setX(70);
            cowIconView.setY(13);
            root.getChildren().addAll(cowIconView);
            Circle sheepCircle = new Circle(175 , 42 , 30);
            sheepCircle.setFill(Color.rgb(225 , 153 ,224));
            root.getChildren().addAll(sheepCircle);
            Image sheepIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\sheep.png"),
                    52, 52 , false , true);
            ImageView sheepIconView = new ImageView(sheepIcon);
            sheepIconView.setX(146);
            sheepIconView.setY(10);
            root.getChildren().addAll(sheepIconView);
        } catch (FileNotFoundException e) {

        }
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
