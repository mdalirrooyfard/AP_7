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

    public void loadNewGame(boolean loadNewGame,int level,Player player)
    {
        if (loadNewGame)
        {
            try
            {
                String path = "src\\Resources\\Levels\\Level" + Integer.toString(level) + ".txt";
                controller.loadCustomHandler(path);
            }
            catch (Exception e)
            {
                if( !e.getMessage().equals("No such directory exists!") )
                {
                    System.out.println("loadNewGame Exception");
                    e.printStackTrace();
                }
            }
        }
        else
        {
            try
            {
                String path = "src\\SavedGames\\"+player.getName()+"-"+Integer.toString(player.getId())+"-"+Integer.toString(level);
                controller.loadCustomHandler(path);
            }
            catch (Exception e)
            {
                System.out.println("loadGame Exception");
                e.printStackTrace();
            }
        }
        try
        {
            controller.runHandler();
            System.out.println("done");
        }
        catch ( Exception e )
        {
            System.out.println("Run Handler Exception");
            e.printStackTrace();
        }
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
