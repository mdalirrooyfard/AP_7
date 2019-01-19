package Controller;

import Model.Farm;
import Model.Player;
import View.Graphic.Menu;
import View.Graphic.Start;
import View.View;
import com.gilecode.yagson.YaGson;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Controller
{
    private View view;
    private Farm farm = new Farm();
    //private String command ;
    private String path = null;
    private boolean isLevelFinished = false;
    private Player player;
    private int level;
    private ArrayList<Player> players;
    private Menu menu;
    private Stage stage;
    private Start start;
    private ArrayList<ImageView> levels = new ArrayList<>();

    public Controller(Stage stage)
    {
        this.stage = stage;
        loadPlayers();
        this.start = new Start(stage);
        loadLevels();
        this.menu = new Menu(stage,players,start);
        this.start.setMenu(menu);
        view = new View(stage,menu);
        view.loadImages();
        menu.setMenu(menu);
        menu.passMenuInstance(menu);
    }

    public void setView(View view )
    {
        this.view = view;
    }

    public Farm getFarm(){
        return farm;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }

    /*public void getCommand(Scanner scanner)
    {
        while(!isLevelFinished)
        {
            view.setCommand(scanner);
            command = view.getCommand();
            try
            {
                commandHandler();
            }
            catch ( Exception e )
            {
                if( !( command.startsWith("load custom") && e instanceof NullPointerException ) )
                    view.printError(e.getMessage());
            }
        }
    }*/

    private void commandHandler(String command) throws Exception
    {
        if( command.startsWith("buy") )
        {
            if( command.substring(4).matches("sheep|cow|hen|cat|dog") )
                buyHandler(command.substring(4));
        }
        else if( command.matches("pickup [0-9]+ [0-9]+"))
            pickUpHandler(Double.parseDouble(command.substring(7,8)),Double.parseDouble(command.substring(9)));
        else if ( command.matches("cage [0-9]+ [0-9]+"))
            cageHandler(Double.parseDouble(command.substring(5,6)),Double.parseDouble(command.substring(7)));
        else if( command.matches("plant [0-9]+ [0-9]+") )
            plantHandler(Double.parseDouble(command.substring(6,7)),Double.parseDouble(command.substring(8)));
        else if( command.matches("well") )
            wellHandler();
        else if( command.startsWith("start ") )
        {
            if( command.substring(6).matches("eggPowderPlant|cakeBakery|cookieBakery|customFactory|sewingFactory|" +
                    "spinnery|weavingFactory"))
                startWorkShopHandler(command.substring(6));
        }
        else if( command.startsWith("upgrade "))
        {
            if( command.substring(8).matches("eggPowderPlant|cakeBakery|cookieBakery|customFactory|sewingFactory|" +
                    "spinnery|weavingFactory|cat|well|truck|helicopter|warehouse"))
                upgradeHandler(command.substring(8));
        }
        else if( command.startsWith("load game "))
            loadGameHandler(command.substring(10));
        else if( command.startsWith("save game "))
            saveGameHandler(command.substring(10));
        else if( command.startsWith("run ") )
            runHandler();
        else if( command.matches("turn [0-9]+") )
            turnHandler(Integer.parseInt(command.substring(5)));
        else if( command.matches("truck add [{a-z, }]+ [0-9]+") )
            addToTransportationHandler(true,command.substring(command.indexOf("add")+4));
        else if( command.matches("helicopter add [{a-z, }]+ [0-9]+") )
            addToTransportationHandler(false,command.substring(command.indexOf("add")+4));
        else if( command.endsWith(" clear"))
        {
            if( command.startsWith("truck") )
                clearFromTransportationHandler(true);
            else if( command.startsWith("helicopter") )
                clearFromTransportationHandler(false);
        }
        else if( command.endsWith(" go"))
        {
            if( command.startsWith("truck") )
                goHandler(true);
            else if( command.startsWith("helicopter") )
                goHandler(false);
        }
        else if( command.startsWith("print") )
        {
            if( command.substring(6).matches("info|map|levels|warehouse|well|eggPowderPlant|cakeBakery|" +
                    "cookieBakery|customFactory|sewingFactory|spinnery|weavingFactory|truck|helicopter") )
                printHandler(command.substring(6));
        }
        else if( command.startsWith("load custom ") )
            loadCustomHandler(command.substring(12));
        else
            throw new Exception("Wrong Command Format");
    }

    private void buyHandler(String animalName) throws Exception
    {
        boolean isBought = false;
        switch (animalName)
        {
            case "sheep":isBought = farm.addSheep(true);break;
            case "cow": isBought = farm.addCow(true);break;
            case "hen": isBought = farm.addHen(true);break;
            case "cat": isBought = farm.addCat(true);break;
            case "dog": isBought = farm.addDog(true);break;
        }
        if( !isBought )
            throw new Exception("Not Enough Money! :'( ");
    }

    private void pickUpHandler(double x , double y) throws Exception
    {
        if(!farm.pickUp(x,y))
            throw new Exception("Warehouse is full! :'(");
    }

    private void cageHandler(double x , double y) throws Exception
    {
        if(!farm.putCage(x,y))
            throw new Exception("No wild animal is here!");
    }

    private void plantHandler(double x , double y) throws Exception
    {
        if(!farm.plantGrass(x,y))
            throw new Exception("Well is empty! :'( ");
    }

    private void wellHandler() throws Exception
    {
        if(!farm.fullWell())
            throw new Exception("Not Enough Money! :'( ");
    }

    private void upgradeHandler(String entityName) throws Exception
    {
        int result = farm.upgrade(entityName);
        if( result == 1 )
            throw new Exception("Not Enough Money! :'( ");
        else if( result == 2 )
            throw new Exception(entityName+" is at maximum level!");
        else if( result == 3 )
            throw new Exception(entityName+" doesn't exits");
    }

    private void turnHandler(int n)
    {
        for (int i = 0 ; i < n ; i++)
            if(farm.turn())
            {
                //view.levelIsFinished();
                break;
            }
    }

    public void loadCustomHandler(String path) throws Exception
    {
        InputStream inputStream = null;
        try
        {
            this.path = path;
            inputStream = new FileInputStream(path+"\\custom.txt");
            Scanner scanner = new Scanner(inputStream);
            String name = scanner.next() , input = scanner.nextLine().substring(8) , output = scanner.next();
            ArrayList<String> inputs = new ArrayList<>();
            for( String s : input.split(" ") )
                inputs.add(s);
            farm.makeCustomWorkshop(name,inputs,output);
        }
        catch ( Exception e )
        {
            throw new Exception("No such directory exists!");
        }
        finally
        {
            if (inputStream != null)
                inputStream.close();
        }
    }

    public void runHandler() throws Exception
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(path);
            Scanner scanner = new Scanner(inputStream);
            String string = scanner.next();
            while(!string.equals("endOfMap"))
            {
                switch (string)
                {
                    case "well":farm.makeWell(scanner.nextInt());break;
                    case "wareHouse":farm.makeWareHouse(scanner.nextInt());break;
                    case "truck":farm.makeTruck(scanner.nextInt());break;
                    case "helicopter":farm.makeHelicopter(scanner.nextInt());break;
                    case "money":farm.increaseMoney(scanner.nextInt());break;
                    case "hen":
                        int count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addHen(false);
                        break;
                    case "cow":
                        count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addCow(false);
                        break;
                    case "sheep":
                        count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addSheep(false);
                        break;
                    case "cat":
                        count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addCat(false);
                        break;
                    case "dog":
                        count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addDog(false);
                        break;
                    default:
                }
                string = scanner.next();
            }
            farm.updateMap();
            string = scanner.nextLine();
            string = scanner.nextLine();
            while(true)
            {
                String name = scanner.next();
                if( name.equals("endOfGoals") )
                    break;
                int count = scanner.nextInt();
                farm.getGoals().put(name, count);
                farm.makeAchievements();
            }
            farm.makeWorkShops();
            view.play(farm);
            turnHandler();
        }
        catch ( FileNotFoundException e )
        {
            throw new Exception("No such directory exists!");
        }
        finally
        {
            if (inputStream != null)
                inputStream.close();
        }
    }

    public void turnHandler(){
        while(true){
            try {
                boolean finish = farm.turn();
                Thread.sleep(2000);
                view.showMap();
                view.showMovingAnimals();
                if (finish) {
                    System.out.println("wiiiiiiin");
                    //todo win method in view
                    break;
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    public void saveGameHandler(String path) throws Exception
    {
        try
        {
            OutputStream outputStream = new FileOutputStream(path + "\\"+ player.getName()+"-"+Integer.toString(player.getId())+"-"+Integer.toString(level)+".txt");
            Formatter formatter = new Formatter(outputStream);
            YaGson yaGson = new YaGson();
            String savedFarm = yaGson.toJson(farm);
            formatter.format(savedFarm);
            formatter.flush();
            formatter.close();
        }
        catch ( IOException e )
        {
            throw new Exception("No such directory exists!");
        }

    }

    public void canGameBeContinued(String path) throws Exception
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(path);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (inputStream != null)
                inputStream.close();
        }
    }

    public void loadGameHandler(String path) throws Exception
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(path);
            Scanner scanner = new Scanner(inputStream);
            YaGson yaGson = new YaGson();
            String savedFarm = scanner.nextLine();
            farm = yaGson.fromJson(savedFarm,Farm.class);
        }
        catch ( IOException e )
        {
            throw new Exception("No such directory exsits!");
        }
        finally
        {
            if( inputStream != null )
                inputStream.close();
        }
    }

    private void printHandler(String what)
    {
        switch (what)
        {
            /*case "info":view.printInfo(farm.printInfo());break;
            case "map":view.printInfo(farm.printMap());break;
            case "levels":view.printInfo(farm.printLevel());break;
            case "warehouse":view.printInfo(farm.printWareHouse());break;
            case "well":view.printInfo(farm.printWell());break;
            case "helicopter":view.printInfo(farm.printTransportation(false));break;
            case "truck":view.printInfo(farm.printTransportation(true));break;
            default:view.printInfo(farm.printWorkshop(what));*/
        }
    }

    private void addToTransportationHandler(boolean  vehicle , String name_count) throws Exception
    {
        String[] tmp = name_count.split(" ");
        int count = Integer.parseInt(tmp[tmp.length - 1]);
        String name = "";
        for( int  i = 0 ; i < tmp.length - 2 ; i++ )
            name = name.concat(tmp[i] + " ");
        name = name.concat(tmp[tmp.length - 2]);
        if (vehicle)
        {
            int c = farm.addToTruck(name,count);
            if( c == -1 )
                throw new Exception("Truck is moving right now!");
            else if( c < count )
                throw new Exception("More than truck capacity!      "+Integer.toString(c)+" is added.");
        }
        else
        {
            int c = farm.addToHelicopter(name,count);
            if( c == -1 )
                throw new Exception("Helicopter is moving right now!");
            else if( c < count )
                throw new Exception("More than helicopter capacity!      "+Integer.toString(c)+" is added.");
        }
    }

    private void clearFromTransportationHandler(boolean vehicle) throws Exception
    {
        if (vehicle)
        {
            if( !farm.clearTruckBeforeGo() )
                throw new Exception("Truck is moving right now!");
        }
        else
            if( !farm.clearHelicopterBeforeGo() )
                throw new Exception("Helicopter is moving right now!");
    }

    private void goHandler(boolean vehicle) throws Exception
    {
        if( !farm.goTransportation(vehicle) )
        {
            if( vehicle )
                throw new Exception("Truck is moving right now!");
            else
                throw new Exception("Helicopter is moving right now!");
        }
    }

    private void startWorkShopHandler(String name) throws Exception
    {
        int result = farm.startWorkShop(name);
        if( result == -2 )
            throw new Exception("This workshop is working now!");
        else if( result == -1 )
            throw new Exception("This workshop doesn't exist!");
        else if( result == 0 )
            throw new Exception("Inputs of this workshop don't exist in warehouse");
    }

    private void loadPlayers()
    {
        InputStream inputStream;
        try
        {
            inputStream = new FileInputStream("src\\Resources\\Players.txt");
            Scanner scanner = new Scanner(inputStream);
            YaGson yaGson = new YaGson();
            if(scanner.hasNext())
            {
                String savedPlayers = scanner.nextLine();
                players = yaGson.fromJson(savedPlayers,ArrayList.class);
            }
            for( Player p : players )
                if( p.isLastPlayer() )
                    player = p;
            inputStream.close();
        }
        catch ( Exception e ){e.printStackTrace();}
    }

    public static void savePlayers( ArrayList<Player> players )
    {
        OutputStream outputStream;
        try
        {
            outputStream = new FileOutputStream("src\\Resources\\Players.txt");
            Formatter formatter = new Formatter(outputStream);
            YaGson yaGson = new YaGson();
            String savedPlayers = yaGson.toJson(players);
            formatter.format(savedPlayers);
            formatter.flush();
            outputStream.close();
        }
        catch ( IOException e ){}
    }

    public void loadLevels()
    {
        try
        {
            for( int i = 0 ; i < 10 ; i++ )
            {
                String levelName = "Level"+Integer.toString(i);
                Image openLevel = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\"+levelName+"Open.png")
                        , 200, 79, false, true);
                Image closeLevel = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\"+levelName+"Close.png")
                        , 200, 79, false, true);
                ImageView levelView;
                if( i + 1 <= player.getLastLevel() )
                    levelView = new ImageView(openLevel);
                else
                    levelView = new ImageView(closeLevel);
                levels.add(levelView);
                levelView.setX((Menu.WIDTH - 500) * ( (i + 1) % 2 ) + (Menu.WIDTH - 250) * ( i % 2));
                levelView.setY(Menu.HEIGHT * ( i / 2  + 1) / 7);
                start.getGroup().getChildren().addAll(levels.get(i));
                levelView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        if( levelView.getImage() == openLevel )
                        {
                            int level = Integer.parseInt(Character.toString(levelName.toCharArray()[levelName.length()-1])+1);
                            try
                            {
                                if( wasThisLevelPlayedBefore(level) )
                                {
                                    Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
                                    rectangle.setFill(Color.rgb(54,16,0));
                                    rectangle.setOpacity(0.7);

                                    Image exitMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\continueMessageBox.png")
                                            , 800, 300, false, true);
                                    ImageView exitMessageView = new ImageView(exitMessage);
                                    exitMessageView.setY(Menu.HEIGHT / 2 - 150);
                                    exitMessageView.setX(Menu.WIDTH / 2 - 400);

                                    Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                                            , 153, 145, false, true);
                                    ImageView yesView = new ImageView(yes);
                                    yesView.setY(Menu.HEIGHT / 2 + 150);
                                    yesView.setX(Menu.WIDTH / 2 - 200);

                                    Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                                            , 153, 146, false, true);
                                    ImageView noView = new ImageView(no);
                                    noView.setY(Menu.HEIGHT / 2 + 150 );
                                    noView.setX(Menu.WIDTH / 2 + 47);

                                    yesView.setOnMouseClicked(new EventHandler<MouseEvent>()
                                    {
                                        @Override
                                        public void handle(MouseEvent event)
                                        {
                                            loadGame(false,level,player);
                                            start.getGroup().getChildren().removeAll(rectangle,exitMessageView,yesView,noView);
                                        }
                                    });

                                    noView.setOnMouseClicked(new EventHandler<MouseEvent>()
                                    {
                                        @Override
                                        public void handle(MouseEvent event)
                                        {
                                            loadGame(true,level,player);
                                            start.getGroup().getChildren().removeAll(rectangle,exitMessageView,yesView,noView);
                                        }
                                    });
                                    start.getGroup().getChildren().addAll(rectangle,exitMessageView,yesView,noView);
                                }
                                else
                                    loadGame(true,level,player);
                            }
                            catch ( Exception e ){}
                        }
                        else
                        {
                            try
                            {
                                Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
                                rectangle.setFill(Color.rgb(54,16,0));
                                rectangle.setOpacity(0.7);

                                Image playerHasNotBeenChosenMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\levelErrorMessagebox.png")
                                        , 800, 300, false, true);
                                ImageView playerHasNotBeenChosenMessageView = new ImageView(playerHasNotBeenChosenMessage);
                                playerHasNotBeenChosenMessageView.setY(Menu.HEIGHT / 2 - 150);
                                playerHasNotBeenChosenMessageView.setX(Menu.WIDTH / 2 - 400);

                                Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png")
                                        , 200, 79, false, true);
                                ImageView okView = new ImageView(ok);
                                okView.setY(Menu.HEIGHT / 2 + 150);
                                okView.setX(Menu.WIDTH / 2 - 100);
                                okView.setOnMouseClicked(new EventHandler<MouseEvent>()
                                {
                                    @Override
                                    public void handle(MouseEvent event)
                                    {
                                        start.getGroup().getChildren().removeAll(rectangle,playerHasNotBeenChosenMessageView,okView);
                                    }
                                });

                                start.getGroup().getChildren().addAll(rectangle,playerHasNotBeenChosenMessageView,okView);
                            }
                            catch ( Exception e ){}
                        }
                    }
                });
            }
        }
        catch ( IOException e ){}
    }

    private boolean wasThisLevelPlayedBefore(int level)
    {
        String path = "src\\SavedGames\\"+player.getName()+"-"+Integer.toString(player.getId())+"-"+Integer.toString(level);
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(path);
            return true;
        }
        catch ( Exception e )
        {
            return false;
        }
    }

    private void loadGame( boolean newGame , int level , Player player )
    {
        if (newGame)
        {
            try
            {
                path = "src\\Resources\\Levels\\Level" + Integer.toString(level) + ".txt";
            }
            catch (Exception e) {}
        }
        else
        {
            try
            {
                path = "src\\SavedGames\\"+player.getName()+"-"+Integer.toString(player.getId())+"-"+Integer.toString(level);
            }
            catch (Exception e) {}
        }
        try
        {
            runHandler();
        }
        catch ( Exception e ){}
    }
}
