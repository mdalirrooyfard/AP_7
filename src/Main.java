import Controller.Controller;
import Model.Player;
import com.gilecode.yagson.YaGson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import View.Graphic.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Main extends Application
{
    private static Stage stage;

    public static void main(String[] args)
    {
        launch(args);
        //Scanner scanner = new Scanner(System.in);
        //controller.getCommand(scanner);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        ArrayList<Player> players = Controller.loadPlayers();
        Player player = null;
        for( Player p : players )
            if( p.isLastPlayer() )
            {
                player = p;
                break;
            }
        Menu menu = new Menu(primaryStage,players,player);
        menu.setMenu(menu);
        menu.passMenuInstance(menu);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Farm Frenzy");
        primaryStage.show();
    }
    public static void setSceneOnStage(Scene scene)
    {
        stage.setScene(scene);
    }
}
