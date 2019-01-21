package Controller;

import Model.Constants;
import Model.Farm;
import Model.Player;
import View.Graphic.Menu;
import View.Graphic.Start;
import View.View;
import com.gilecode.yagson.YaGson;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import View.ImageViewSprite;

public class Controller
{
    private View view;
    private Farm farm = new Farm();
    private String path = null;
    private boolean isLevelFinished = false;
    private Player player;
    private int level;
    private ArrayList<Player> players;
    private Menu menu;
    private Stage stage;
    private Start start;
    private ArrayList<ImageView> levels = new ArrayList<>();
    private AnimationTimer aTimer;



    private Image movingWell;
    private Image fixedWell;


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
        //loadImage();
        menu.setMenu(menu);
        menu.passMenuInstance(menu);
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

    private void loadCustomHandler(String path) throws Exception
    {
        try(InputStream inputStream = new FileInputStream(path+"\\custom.txt"))
        {
            this.path = path;
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
    }

    private void runHandler() throws Exception
    {
        try(InputStream inputStream = new FileInputStream(path))
        {
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
            scanner.nextLine();
            scanner.nextLine();
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
            iconsHandler();
            loadWell();
            turnHandler();
        }
        catch ( FileNotFoundException e )
        {
            throw new Exception("No such directory exists!");
        }
    }

    private void turnHandler()
    {
        aTimer = new AnimationTimer()
        {
            private long time = 0;
            private long lastTime = 0;
            private long second = 1000000000;
            private boolean finish = false;

            @Override
            public void handle(long now)
            {
                timer();
                if (lastTime == 0)
                    lastTime = now;
                if (now > lastTime + second )
                {
                    time += 1;
                    farm.increaseTimer();
                    lastTime = now;
                }
                if (time % 3 == 0)
                {
                    finish = farm.turn();
                    view.showMap();
                    view.showMovingAnimals();
                    time = 31;
                    lastTime = 0;
                }
               if (finish)
               {
                   this.stop();
                   try
                   {
                       Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
                       rectangle.setFill(Color.rgb(54,16,0));
                       rectangle.setOpacity(0.7);

                       Image playerHasNotBeenChosenMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\winningMessage.png")
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
                               stage.setScene(start.getScene());
                           }
                       });

                       start.getGroup().getChildren().addAll(rectangle,playerHasNotBeenChosenMessageView,okView);
                   }
                   catch ( Exception e ) { e.printStackTrace(); }
               }
            }
        };
        aTimer.start();
    }

    private void saveGameHandler() throws Exception
    {
        if( !Files.exists(Paths.get("src\\Resources\\Saved Games\\"+player.getName()+"\\"+Integer.toString(level)+".txt")) )
            Files.createDirectory(Paths.get("src\\Resources\\Saved Games\\"+player.getName()));
        try(OutputStream outputStream = new FileOutputStream(
                "src\\Resources\\Saved Games\\"+player.getName()+"\\"+Integer.toString(level)+".txt"))
        {
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

    private void loadGameHandler() throws Exception
    {
        try(InputStream inputStream = new FileInputStream(path))
        {
            Scanner scanner = new Scanner(inputStream);
            YaGson yaGson = new YaGson();
            String savedFarm = scanner.nextLine();
            farm = yaGson.fromJson(savedFarm,Farm.class);
            view.play(farm);
            iconsHandler();
            turnHandler();
        }
        catch ( IOException e )
        {
            throw new Exception("No such directory exsits!");
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
        catch ( Exception e ){ e.printStackTrace(); }
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
        catch ( IOException e ){ e.printStackTrace(); }
    }

    private void loadLevels()
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
                        if( levels.indexOf(levelView) < player.getLastLevel() )
                            chooseOpenLevelHandler( levelName );
                        else
                            chooseCloseLevelHandler();
                    }
                });
            }
        }
        catch ( IOException e ){ e.printStackTrace(); }
    }

    private boolean wasThisLevelPlayedBefore(int level)
    {
        return Files.exists(Paths.get("src\\Resources\\Saved Games\\"+player.getName()+"\\"+Integer.toString(level)+".txt"));
    }

    private void loadGame( boolean newGame , int level , Player player )
    {
        this.level = level;
        try
        {
            if (newGame)
            {
                path = "src\\Resources\\Levels\\Level" + Integer.toString(level) + ".txt";
                runHandler();
            }
            else
            {
                path = "src\\Resources\\Saved Games\\"+player.getName()+"\\"+Integer.toString(level)+".txt";
                loadGameHandler();
            }
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void chooseOpenLevelHandler( String levelName )
    {
        int level = Integer.parseInt(Character.toString(levelName.toCharArray()[levelName.length()-1])+1);
        try
        {
            if( wasThisLevelPlayedBefore(level) )
            {
                Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
                rectangle.setFill(Color.rgb(54,16,0));
                rectangle.setOpacity(0.7);

                Image continueMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\continueMessageBox.png")
                        , 800, 300, false, true);
                ImageView continueMessageView = new ImageView(continueMessage);
                continueMessageView.setY(Menu.HEIGHT / 2 - 150);
                continueMessageView.setX(Menu.WIDTH / 2 - 400);

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
                        start.getGroup().getChildren().removeAll(rectangle,continueMessageView,yesView,noView);
                    }
                });

                noView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        loadGame(true,level,player);
                        start.getGroup().getChildren().removeAll(rectangle,continueMessageView,yesView,noView);
                    }
                });
                start.getGroup().getChildren().addAll(rectangle,continueMessageView,yesView,noView);
            }
            else
                loadGame(true,level,player);
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void chooseCloseLevelHandler()
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
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void timer()
    {
        try
        {
            Image timer = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\timer.png"),
                    150, 79, false, true);
            ImageView timerView = new ImageView(timer);
            timerView.setX(Constants.WIDTH - 200);
            timerView.setY(Constants.HEIGHT - 100);

            Label timeLabel = new Label("");
            timeLabel.relocate(Constants.WIDTH - 160,Constants.HEIGHT - 80);
            timeLabel.setTextFill(Color.rgb(54,16,0));
            timeLabel.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,14));
            if( farm.getTimer() / 3600 < 10 )
                timeLabel.setText(timeLabel.getText()+"0");
            timeLabel.setText(timeLabel.getText()+Long.toString(farm.getTimer() / 3600)+":");
            if( farm.getTimer() % 3600 / 60 < 10 )
                timeLabel.setText(timeLabel.getText()+"0");
            timeLabel.setText(timeLabel.getText()+Long.toString(farm.getTimer() % 3600 / 60)+":");
            if( farm.getTimer() % 60 < 10 )
                timeLabel.setText(timeLabel.getText()+"0");
            timeLabel.setText(timeLabel.getText()+Long.toString(farm.getTimer() % 60));

            view.getGroup().getChildren().addAll(timerView,timeLabel);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void iconsHandler()
    {
        buyIconHandler();
        menuIconHandler();
    }

    private void buyIconHandler()
    {
        try
        {
            Image henIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\buyGuineaHenButton.png"),
                    60, 60, false, true);
            ImageView henIconView = new ImageView(henIcon);
            henIconView.setX(5);
            henIconView.setY(10);

            Image cowIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\buyCowButton.png"),
                    60, 60, false, true);
            ImageView cowIconView = new ImageView(cowIcon);
            cowIconView.setX(80);
            cowIconView.setY(10);

            Image sheepIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\buySheepButton.png"),
                    60, 60, false, true);
            ImageView sheepIconView = new ImageView(sheepIcon);
            sheepIconView.setX(145);
            sheepIconView.setY(10);

            Image dogIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\buyDogButton.png"),
                    60, 60, false, true);
            ImageView dogIconView = new ImageView(dogIcon);
            dogIconView.setX(210);
            dogIconView.setY(10);

            Image catIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\buyCatButton.png"),
                    60, 60, false, true);
            ImageView catIconView = new ImageView(catIcon);
            catIconView.setX(275);
            catIconView.setY(10);

            cowIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        buyHandler("cow");
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            });

            henIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        buyHandler("hen");
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            });

            catIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        buyHandler("cat");
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            });

            sheepIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        buyHandler("sheep");
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            });

            dogIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        buyHandler("dog");
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            });

            view.getGroup().getChildren().addAll(henIconView,cowIconView,sheepIconView,dogIconView,catIconView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void menuIconHandler()
    {
        try
        {
            Image menuIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\menuButton.png"),
                    77, 73, false, true);
            ImageView menuView = new ImageView(menuIcon);
            menuView.setX(5);
            menuView.setY(Constants.HEIGHT - 100);
            menuView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    aTimer.stop();
                    showMenu();
                }
            });
            view.getGroup().getChildren().addAll(menuView);
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void showMenu()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image menuBackground = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\gameMenuBackground.png"),
                    300, 480, false, true);
            ImageView menuBackgroundView = new ImageView(menuBackground);
            menuBackgroundView.setX(Constants.WIDTH / 2 - 150);
            menuBackgroundView.setY(Constants.HEIGHT / 2 - 240);

            ImageView continueView = insertContinue();
            ImageView menuView = insertMainMenu();
            ImageView restartView = insertRestart();
            ImageView levelsView = insertLevels();
            ImageView optionsView = insertOptions();

            continueView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    view.getGroup().getChildren().removeAll(rectangle,menuBackgroundView,continueView,menuView,
                            restartView,levelsView,optionsView);
                    aTimer.start();
                }
            });

            menuView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    mainMenuHandler();
                }
            });

            restartView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    restartHandler();
                }
            });

            levelsView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    levelsHandler();
                }
            });

            optionsView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    optionsHandler();
                }
            });

            view.getGroup().getChildren().addAll(rectangle,menuBackgroundView,continueView,menuView,restartView,
                    levelsView,optionsView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private ImageView insertContinue()
    {
        try
        {
            Image continueButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\continueButton.png"),
                    150, 60, false, true);
            ImageView continueView = new ImageView(continueButton);
            continueView.setX(Constants.WIDTH / 2 - 75);
            continueView.setY(Constants.HEIGHT / 2 - 210);
            return continueView;
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return null;
    }

    private ImageView insertMainMenu()
    {
        try
        {
            Image menuButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\gameMainMenuButton.png"),
                    150, 60, false, true);
            ImageView menuView = new ImageView(menuButton);
            menuView.setX(Constants.WIDTH / 2 - 75);
            menuView.setY(Constants.HEIGHT / 2 - 120);
            return menuView;
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return null;
    }

    private ImageView insertRestart()
    {
        try
        {
            Image restartButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\restartButton.png"),
                    150, 60, false, true);
            ImageView restartView = new ImageView(restartButton);
            restartView.setX(Constants.WIDTH / 2 - 75);
            restartView.setY(Constants.HEIGHT / 2 - 30);
            return restartView;
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return null;
    }

    private ImageView insertLevels()
    {
        try
        {
            Image levelsButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\levelsButton.png"),
                    150, 60, false, true);
            ImageView levelsView = new ImageView(levelsButton);
            levelsView.setX(Constants.WIDTH / 2 - 75);
            levelsView.setY(Constants.HEIGHT / 2 + 60);
            return levelsView;
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return null;
    }

    private ImageView insertOptions()
    {
        try
        {
            Image optionsButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\optionsButton.png"),
                    150, 60, false, true);
            ImageView optionsView = new ImageView(optionsButton);
            optionsView.setX(Constants.WIDTH / 2 - 75);
            optionsView.setY(Constants.HEIGHT / 2 + 150);
            return optionsView;
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return null;
    }

    private void mainMenuHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image quitMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\quitMessageBox.png")
                    , 800, 300, false, true);
            ImageView quitMessageView = new ImageView(quitMessage);
            quitMessageView.setY(Constants.HEIGHT / 2 - 150);
            quitMessageView.setX(Constants.WIDTH / 2 - 400);

            Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                    , 153, 145, false, true);
            ImageView yesView = new ImageView(yes);
            yesView.setY(Constants.HEIGHT / 2 + 150);
            yesView.setX(Constants.WIDTH / 2 - 200);
            yesView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        saveGameHandler();

                        stage.setScene(menu.getScene());
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            });

            Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                    , 153, 146, false, true);
            ImageView noView = new ImageView(no);
            noView.setY(Constants.HEIGHT / 2 + 150 );
            noView.setX(Constants.WIDTH / 2 + 47);
            noView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    view.getGroup().getChildren().removeAll(rectangle,quitMessageView,yesView,noView);
                }
            });
            view.getGroup().getChildren().addAll(rectangle,quitMessageView,yesView,noView);
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void restartHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image restartMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\restartMessageBox.png")
                    , 800, 300, false, true);
            ImageView restartMessageView = new ImageView(restartMessage);
            restartMessageView.setY(Constants.HEIGHT / 2 - 150);
            restartMessageView.setX(Constants.WIDTH / 2 - 400);

            Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                    , 153, 145, false, true);
            ImageView yesView = new ImageView(yes);
            yesView.setY(Constants.HEIGHT / 2 + 150);
            yesView.setX(Constants.WIDTH / 2 - 200);
            yesView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        view.getGroup().getChildren().removeAll();
                        Thread.sleep(700);
                        farm.setTimer(0);
                        path = "src\\Resources\\Levels\\Level" + Integer.toString(level) + ".txt";
                        runHandler();
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            });

            Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                    , 153, 146, false, true);
            ImageView noView = new ImageView(no);
            noView.setY(Constants.HEIGHT / 2 + 150 );
            noView.setX(Constants.WIDTH / 2 + 47);
            noView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    view.getGroup().getChildren().removeAll(rectangle,restartMessageView,yesView,noView);
                }
            });
            view.getGroup().getChildren().addAll(rectangle,restartMessageView,yesView,noView);
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void levelsHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image quitMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\goToLevelsMessageBox.png")
                    , 800, 300, false, true);
            ImageView quitMessageView = new ImageView(quitMessage);
            quitMessageView.setY(Constants.HEIGHT / 2 - 150);
            quitMessageView.setX(Constants.WIDTH / 2 - 400);

            Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                    , 153, 145, false, true);
            ImageView yesView = new ImageView(yes);
            yesView.setY(Constants.HEIGHT / 2 + 150);
            yesView.setX(Constants.WIDTH / 2 - 200);
            yesView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        saveGameHandler();
                        stage.setScene(start.getScene());
                    }
                    catch ( Exception e ) {}
                }
            });

            Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                    , 153, 146, false, true);
            ImageView noView = new ImageView(no);
            noView.setY(Constants.HEIGHT / 2 + 150 );
            noView.setX(Constants.WIDTH / 2 + 47);
            noView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    view.getGroup().getChildren().removeAll(rectangle,quitMessageView,yesView,noView);
                }
            });
            view.getGroup().getChildren().addAll(rectangle,quitMessageView,yesView,noView);
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void optionsHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\gameMenuBackground.png")
                    , 500, 600, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setY(Constants.HEIGHT / 2 - 300);
            backgroundView.setX(Constants.WIDTH / 2 - 250);

            Label sound = new Label("Sound On/Off : ");
            sound.setLayoutY(Menu.HEIGHT / 2 - 150);
            sound.setLayoutX(Menu.WIDTH / 2 - 200);
            sound.setTextFill(Color.rgb(54,16,0));
            sound.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            Image soundIconMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\muteSoundButton.png")
                    , 80, 76, false, true);
            Image soundIconUnMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\soundButton.png")
                    , 80, 76, false, true);
            ImageView soundIconView;
            if( menu.isSoundMuted() )
                soundIconView = new ImageView(soundIconMute);
            else
                soundIconView = new ImageView(soundIconUnMute);
            soundIconView.setY(Menu.HEIGHT / 2 - 175);
            soundIconView.setX(Menu.WIDTH / 2 - 30);
            soundIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    //TODO really mute sound!
                    if( menu.isSoundMuted() )
                    {
                        menu.setMuteSound(false);
                        soundIconView.setImage(soundIconUnMute);
                        System.out.println();
                    }
                    else
                    {
                        menu.setMuteSound(true);
                        soundIconView.setImage(soundIconMute);
                    }
                }
            });

            Label music = new Label("Music On/Off : ");
            music.setLayoutY(Menu.HEIGHT / 2);
            music.setLayoutX(Menu.WIDTH / 2 - 200);
            music.setTextFill(Color.rgb(54,16,0));
            music.setFont(javafx.scene.text.Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            Image musicIconMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\musicMuteButton.png")
                    , 80, 76, false, true);
            Image musicIconUnMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\musicButton.png")
                    , 80, 76, false, true);
            ImageView musicIconView;
            if( menu.isMusicMuted() )
                musicIconView = new ImageView(musicIconMute);
            else
                musicIconView = new ImageView(musicIconUnMute);
            musicIconView.setY(Menu.HEIGHT / 2 - 25);
            musicIconView.setX(Menu.WIDTH / 2 - 30);
            musicIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( menu.isMusicMuted() )
                    {
                        musicIconView.setImage(musicIconUnMute);
                        menu.getMediaPlayer().play();
                        menu.setMuteMusic(false);
                        System.out.println();
                    }
                    else
                    {
                        musicIconView.setImage(musicIconMute);
                        menu.getMediaPlayer().stop();
                        menu.setMuteMusic(true);
                    }
                }
            });
            //TODO fullScreen still has problem!
            Label fullScreen = new Label("Full Screen On/Off : ");
            fullScreen.setLayoutY(Menu.HEIGHT / 2 + 150);
            fullScreen.setLayoutX(Menu.WIDTH / 2 - 200);
            fullScreen.setTextFill(Color.rgb(54,16,0));
            fullScreen.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            Image uncheckedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxFalse.png")
                    , 25, 25, false, true);
            Image checkedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxTrue.png")
                    , 25, 25, false, true);
            ImageView fullScreenView;
            if( menu.isFullScreen() )
                fullScreenView = new ImageView(checkedCheckBox);
            else
                fullScreenView = new ImageView(uncheckedCheckBox);
            fullScreenView.setY(Menu.HEIGHT / 2 + 150);
            fullScreenView.setX(Menu.WIDTH / 2 + 10);
            fullScreenView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( menu.isFullScreen() )
                    {
                        fullScreenView.setImage(uncheckedCheckBox);
                        stage.setFullScreen(false);
                        menu.setFullScreen(false);
                        System.out.println();
                    }
                    else
                    {
                        fullScreenView.setImage(checkedCheckBox);
                        stage.setFullScreen(true);
                        menu.setFullScreen(true);
                    }
                }
            });

            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 80, 76, false, true);
            ImageView backView = new ImageView(back);
            backView.setY(0);
            backView.setY(Menu.HEIGHT / 2 + 200);
            backView.setX(Menu.WIDTH / 2 - 200);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    view.getGroup().getChildren().removeAll(rectangle,backgroundView,sound,soundIconView,music,musicIconView,
                            fullScreen,fullScreenView,backView);
                }
            });

            view.getGroup().getChildren().addAll(rectangle,backgroundView,sound,soundIconView,music,musicIconView,
                    fullScreen,fullScreenView,backView);
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void truckIconHandler()
    {

    }


    private void helicopterIconHandler()
    {

    }

    private void loadWell(){
        try {
            fixedWell = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\" + "fixed"
                    + Integer.toString(farm.getWell().getLevel()) + ".png"),
                    200, 200, false, true);
            movingWell = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\" + "moving"
                    + Integer.toString(farm.getWell().getLevel()) + ".png"),
                    800, 800, false, true);
            ImageView fixedWellView = new ImageView(fixedWell);
            fixedWellView.setX(farm.getWell().getShowX());
            fixedWellView.setY(farm.getWell().getShowY());
            view.getGroup().getChildren().add(fixedWellView);
            fixedWellView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int result = farm.fullWell();
                    //todo if result == -1 dance the money
                    if (result == 1){
                        view.getGroup().getChildren().remove(fixedWellView);
                        ImageView movingWellView = new ImageView(movingWell);
                        movingWellView.setX(farm.getWell().getShowX());
                        movingWellView.setY(farm.getWell().getShowY());
                        view.getGroup().getChildren().add(movingWellView);
                        AnimationTimer imageViewSprite = new ImageViewSprite(movingWellView,
                                40,true,4, 4, 16, 200, 200, 16);
                        imageViewSprite.start();
                    }
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadImage(){
        loadWell();
    }
}
