import Controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Controller controller = new Controller(primaryStage);
       // primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Farm Frenzy");
        primaryStage.show();
    }
}
