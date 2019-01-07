import Controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import Graphic.*;

import java.io.FileInputStream;
import java.util.Scanner;

public class Main extends Application
{
    private Controller controller = new Controller();
    private Stage stage;
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
        Menu menu = new Menu(primaryStage);
        primaryStage.setTitle("Farm Frenzy");
        primaryStage.show();
    }
}
