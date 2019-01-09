package View;

import Controller.Controller;
import Model.Farm;
import View.Graphic.Menu;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
