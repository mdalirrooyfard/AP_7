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
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);

    public Scene getScene()
    {
        return scene;
    }



    public void play(Stage stage)
    {
        Group root = new Group();
        Scene scene = new Scene(root , Menu.WIDTH,
                Menu.HEIGHT);
        stage.setScene(scene);
        try
        {
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\map.png"), Menu.WIDTH,
                    Menu.HEIGHT, false, true);
            ImageView imageView = new ImageView(background);
            root.getChildren().add(imageView);
            Circle circle = new Circle(50 , 65 , 30);
            circle.setFill(Color.rgb(255 , 255 ,125));
            root.getChildren().addAll(circle);
            Image henIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\icons\\henIcon.png"),
                    100 , 100 , false , true);
            ImageView henIconView = new ImageView(henIcon);
            root.getChildren().addAll(henIconView);
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
