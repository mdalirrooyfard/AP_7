package View.Graphic;

import Controller.Controller;
import Model.Player;
import View.View;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game
{
    private Controller controller = new Controller();
    private View view;
    private boolean loadNewGame;

    public void loadNewGame(boolean loadNewGame,int level)
    {
        if (loadNewGame)
        {
            try
            {
                String path2 = "src\\Resources\\Levels\\Level" + Integer.toString(level) + ".txt";
                controller.loadCustomHandler(path2);
            }
            catch (Exception e) {}
        }
        try
        {
            controller.runHandler();
        }
        catch ( Exception e ){}
    }

    public Game(Stage stage , Menu menu , int level , Player player , ArrayList<Player> players)
    {
        view = new View(stage);
        controller.setView(view);
        controller.setLevel(level);
        controller.setPlayer(player);
        controller.setPlayers(players);
    }

    public Scene getScene()
    {
        return view.getScene();
    }


}
