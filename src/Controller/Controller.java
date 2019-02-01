package Controller;

import Model.Animals.Animal;
import Model.Animals.Domestic.Domestic;
import Model.Animals.Wild.Wild;
import Model.*;
import Model.Items.Item;
import Model.Workshops.*;
import Network.*;
import View.Graphic.*;
import View.ImageViewSprite;
import View.MoveTransition;
import View.View;
import com.gilecode.yagson.YaGson;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import static Model.Constants.HEIGHT;
import static Model.Constants.WIDTH;

public class Controller
{
    private View view;
    private Farm farm = new Farm();
    private String path = null;
    private Player player = new Player("",0);
    private Vector<Player> players;
    private Menu menu;
    private Stage stage;
    private Start start;
    private Market market = new Market();
    private ChoosePlayer choosePlayer;
    private Multiplayer multiplayer = new Multiplayer();
    private AnimationTimer animationTimer;
    private OrderPage orderPage = new OrderPage();
    private SellPage sellPage = new SellPage();
    private HostMenu hostMenu;
    private Label moneyLabel = new Label();
    private Vector<String[]> goals = new Vector<>();
    private ConcurrentHashMap<String, Label> achievements = new ConcurrentHashMap<>();
    private Loader loader = new Loader();
    private Vector<ImageView> levels = new Vector<>();
    private boolean isMultiPlayer = false;
    private ClientSender clientSender;
    private Thread clientListener;
    private ClientGui clientGui;
    private  int flagWell = 0 , flagWareHouse = 0;
    private Image moneyIcon ,  arrow;
    private RecipeSetter recipeSetter = new RecipeSetter();
    private Socket socket;
    {
        try
        {
            moneyIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\money.png"));
            arrow = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\arrow.png"));
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
    }
    private ImageView moneyView = new ImageView(moneyIcon);
    private ImageView arrowViewWell = new ImageView(arrow);
    private ImageView arrowViewWareHouse = new ImageView(arrow);
    private ConcurrentHashMap<Integer, Image> animalSatiety = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Image> wellFullness = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Image> workShopWorking = new ConcurrentHashMap<>();


    public Controller(Stage stage)
    {
        makeMultiplayerScene();
        loader.loadSize();
        players = loader.loadPlayers();
        findLastPlayer();
        this.stage = stage;
        this.start = new Start(stage);
        this.choosePlayer = new ChoosePlayer(stage);
        loadLevels();
        writePlayers();
        insertNewPlayer();
        this.menu = new Menu(stage,players,choosePlayer,start,multiplayer,player);
        this.start.setMenu(menu);
        this.choosePlayer.setMenu(menu);
        view = new View();
        menu.setMenu(menu);
        menu.passMenuInstance(menu,player);
    }

    private void loadSatietyImages(){
        try {
            Image image;
            for (int i = 1; i < 9 ; i++) {
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Progress\\progress" + i + ".png"));
                animalSatiety.put(i-1 , image);
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    private void loadWellFullnessImages(){
        try {
            Image image;
            for (int i = 0 ; i < 6 ; i++) {
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Progress\\full" + i + ".png"));
                wellFullness.put(i , image);
                workShopWorking.put(i , image);
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    private void findLastPlayer()
    {
        if( players.size() > 0 )
            for( Player p : players )
                if( p.isLastPlayer() )
                    player = p;
    }

    private void zoom()
    {
        Zoom zoom = new Zoom();
        loader.getMap().setOnScroll(new EventHandler<ScrollEvent>()
        {
            @Override
            public void handle(ScrollEvent event) {
                zoom.zoomStarter(view.getGroup());
            }
        });
    }

    private void runHandler() throws Exception
    {
        try(InputStream inputStream = new FileInputStream(path))
        {
            Scanner scanner = new Scanner(inputStream);
            farm = new Farm();
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
            view.getGroup().getChildren().clear();
            stage.setScene(view.getScene());
            loader.loadImages(farm);
            makeScene();
            if (isMultiPlayer && player.isClient())
                clientGui.setFarm(farm);
            turnHandler();
        }
        catch ( FileNotFoundException e )
        {
            throw new Exception("No such directory exists!");
        }
    }

    private void turnHandler()
    {
        zoom();
        animationTimer = new AnimationTimer()
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
                    time++;
                    farm.increaseTimer();
                    lastTime = now;
                }
                if (time % 3 == 0)
                {
                    finish = farm.turn();
                    synchronized (farm.getStuffs())
                    {
                        showMap();
                        showMovingAnimals();
                        showMovingWildAnimals();
                        checkWell();
                        checkWareHouse();
                        checkHelicopter();
                        checkTruck();
                        checkWorkShops();
                        updateMoney();
                        updateAchievement();
                    }
                    time = 31;
                    lastTime = 0;
                }
               if (finish)
               {
                   winHandler();
                   player.increaseLevel();
                   player.increaseMoney(farm.getMoney());
                   savePlayers(players);
                   if (isMultiPlayer && player.isClient()){
                       clientSender.sendMoney(player.getMoney(), player.getUserName());
                       clientSender.sendLevel(player.getLastLevel(), player.getUserName());
                   }
                   start.getGroup().getChildren().removeAll(levels);
                   levels.clear();
                   loadLevels();
                   this.stop();
               }
            }
        };
        animationTimer.start();
    }

    private void loadGoals()
    {
        ConcurrentHashMap<String, Integer> farmGoals = farm.getGoals();
        int i = 0;
        for (String s : farmGoals.keySet())
        {
            goals.add(new String[]{s, Integer.toString(farm.getGoals().get(s))});
            Image image;
            if (s.equals("hen") || s.equals("sheep") || s.equals("cow"))
                image = loader.getAnimalsFixed().get(s);
            else
                image = loader.getItems().get(s);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            imageView.setY(HEIGHT - 100);
            imageView.setX(WIDTH - farm.getGoals().keySet().size() * 50 + i * 50 - 20);
            Label fixed = new Label(Integer.toString(farm.getGoals().get(s))) ;
            Label variable = new Label(Integer.toString(farm.getAchievements().get(s)));
            Label of = new Label("of");
            achievements.put(s, variable);
            fixed.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,11));
            fixed.relocate(WIDTH - farm.getGoals().keySet().size() * 50 + i * 50, HEIGHT - 50);
            of.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,11));
            of.relocate(WIDTH - farm.getGoals().keySet().size() * 50 + i * 50, HEIGHT - 60);
            variable.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,11));
            variable.relocate(WIDTH - farm.getGoals().keySet().size() * 50 + i * 50, HEIGHT - 70);
            view.getGroup().getChildren().addAll(imageView, fixed, variable, of);
            i++;
        }
    }

    private void updateAchievement()
    {
        ConcurrentHashMap<String, Integer> hashMap = farm.getAchievements();
        for (String s : hashMap.keySet())
            achievements.get(s).setText(Integer.toString(farm.getAchievements().get(s)));
    }

    private void showGoals()
    {
        try
        {
            Image box = new Image (new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"));
            ImageView boxView = new ImageView(box);
            boxView.setFitWidth(farm.getGoals().keySet().size() * 50);
            boxView.setFitHeight(80);
            boxView.setX(WIDTH - farm.getGoals().keySet().size() * 50 - 20);
            boxView.setY(HEIGHT - 100);
            view.getGroup().getChildren().add(boxView);

        } catch (Exception e) { e.printStackTrace(); }
    }

    private void winHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image winningMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\winningMessage.png")
                    , 800, 300, false, true);
            ImageView winningMessageView = new ImageView(winningMessage);
            winningMessageView.setY(Menu.HEIGHT / 2 - 150);
            winningMessageView.setX(Menu.WIDTH / 2 - 400);

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

            view.getGroup().getChildren().addAll(rectangle,winningMessageView,okView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void saveGameHandler() throws Exception
    {
        if( !Files.exists(Paths.get("src\\Resources\\Saved Games\\"+player.getUserName())) )
            Files.createDirectory(Paths.get("src\\Resources\\Saved Games\\"+player.getUserName()));
        try(OutputStream outputStream = new FileOutputStream(
                "src\\Resources\\Saved Games\\"+player.getUserName()+"\\"+Integer.toString(player.getLevelThatPlaysNow())+".txt"))
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
            farm = new Farm();
            YaGson yaGson = new YaGson();
            String savedFarm = scanner.nextLine();
            farm = yaGson.fromJson(savedFarm,Farm.class);
            view.getGroup().getChildren().clear();
            stage.setScene(view.getScene());
            loader.loadImages(farm);
            makeScene();
            turnHandler();
        }
        catch ( IOException e )
        {
            throw new Exception("No such directory exsits!");
        }
    }

    public static void savePlayers( Vector<Player> players )
    {
        try( OutputStream outputStream = new FileOutputStream("src\\Resources\\Players.txt"))
        {
            Formatter formatter = new Formatter(outputStream);
            YaGson yaGson = new YaGson();
            String savedPlayers = yaGson.toJson(players);
            formatter.format(savedPlayers);
            formatter.flush();
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
        return Files.exists(Paths.get("src\\Resources\\Saved Games\\"+player.getUserName()+"\\"+Integer.toString(level)+".txt"));
    }

    private void loadGame( boolean newGame , int level , Player player )
    {
        try
        {
            if (newGame)
            {
                path = "src\\Resources\\Levels\\Level" + Integer.toString(level) + ".txt";
                runHandler();
            }
            else
            {
                path = "src\\Resources\\Saved Games\\"+player.getUserName()+"\\"+Integer.toString(level)+".txt";
                loadGameHandler();
            }
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void chooseOpenLevelHandler( String levelName )
    {
        int level = Integer.parseInt(Character.toString(levelName.toCharArray()[levelName.length()-1]))+1;
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
                        start.getGroup().getChildren().removeAll(rectangle,continueMessageView,yesView,noView);
                        player.setLevelThatPlaysNow(level);
                        loadGame(false,level,player);
                    }
                });
                noView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        start.getGroup().getChildren().removeAll(rectangle,continueMessageView,yesView,noView);
                        player.setLevelThatPlaysNow(level);
                        loadGame(true,level,player);
                    }
                });
                start.getGroup().getChildren().addAll(rectangle,continueMessageView,yesView,noView);
            }
            else
            {
                player.setLevelThatPlaysNow(level);
                loadGame(true, level, player);
            }
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
                    120, 60, false, true);
            ImageView timerView = new ImageView(timer);
            timerView.setX(90);
            timerView.setY(HEIGHT - 95);
            Label timeLabel = new Label("");
            timeLabel.relocate(135, HEIGHT - 80);
            timeLabel.setTextFill(Color.rgb(54,16,0));
            timeLabel.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,12.5));
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

    private void danceTheMoney()
    {
        KeyValue kvHieght = new KeyValue(moneyView.fitHeightProperty() , moneyView.getFitHeight() - 10);
        KeyValue kvWidth = new KeyValue(moneyView.fitWidthProperty() , moneyView.getFitWidth() - 25);
        KeyValue kvX = new KeyValue(moneyView.xProperty() , moneyView.getX() + 12.5);
        KeyValue kvY = new KeyValue(moneyView.yProperty() , moneyView.getY() +5);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), kvHieght , kvWidth , kvX , kvY);
        Timeline timeline = new Timeline(keyFrame);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(6);
        timeline.play();
    }

    private void buyIcons()
    {
        try
        {
            Image henIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\buyGuineaHenButton.png"),
                    60, 60, false, true);
            ImageView henIconView = new ImageView(henIcon);
            henIconView.setX(15);
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
            if (!farm.isAreCatsUpgraded())
            {
                ImageView upgradeCat = new ImageView(loader.getUpgradeButton());
                upgradeCat.setFitWidth(50);
                upgradeCat.setFitHeight(30);
                upgradeCat.setX(280);
                upgradeCat.setY(75);
                Label label = new Label(Integer.toString(Constants.CAT_BASE_UPGRADE_COST));
                label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,10));
                label.relocate(300, 80);
                view.getGroup().getChildren().addAll(upgradeCat, label);
                upgradeCat.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                         int result = farm.upgrade("cat");
                         if(result == 1)
                             danceTheMoney();
                         if (result == 0)
                             view.getGroup().getChildren().removeAll(upgradeCat, label);
                    }
                });
            }
            cowIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try
                    {
                        if(!farm.addCow(true))
                        {
                            danceTheMoney();
                        }
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
                        if(!farm.addHen(true))
                        {
                            danceTheMoney();
                        }
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
                        if(!farm.addCat(true))
                        {
                            danceTheMoney();
                        }
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
                        if(!farm.addSheep(true))
                        {
                            danceTheMoney();
                        }
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
                        if(!farm.addDog(true))
                        {
                            danceTheMoney();
                        }
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            });
            view.getGroup().getChildren().addAll(henIconView,cowIconView,sheepIconView,dogIconView,catIconView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void menuIcon()
    {
        try
        {
            Image menuIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\menuButton.png"),
                    77, 73, false, true);
            ImageView menuView = new ImageView(menuIcon);
            menuView.setX(5);
            menuView.setY(HEIGHT - 100);
            menuView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    animationTimer.stop();
                    showMenu();
                }
            });
            view.getGroup().getChildren().addAll(menuView);
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void truckIconHandler()
    {
        loader.getFixedTruck().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                animationTimer.stop();
                stage.setScene(sellPage.getScene(stage,view,farm,loader.getItems(),loader.getRightTruck()
                        ,loader.getFixedTruck(), animationTimer));
            }
        });
    }

    private void helicopterIconHandler()
    {
        loader.getFixedHelicopter().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (isMultiPlayer && player.isClient()){
                    clientGui.setMarket(null);
                    clientSender.sendMarketRequest();
                    market = clientGui.getMarket();
                    while (market == null){
                        market = clientGui.getMarket();
                    }
                }
                animationTimer.stop();
                stage.setScene(orderPage.getScene(stage,view,farm,isMultiPlayer,market.getItems(),loader.getItems(),
                        loader.getLeftHelicopter(),loader.getFixedHelicopter(), animationTimer, clientSender));
            }
        });
    }

    private void checkWorkShops()
    {
        for (Workshop w : farm.getWorkshops())
            if (w != null && w.isWorking())
            {
                ImageView workshopWorkingView = new ImageView(workShopWorking.get((w.getWorkingTime() - w.getCurrentTime())
                        *5/w.getWorkingTime()));
                workshopWorkingView.setX(w.getShowX() + 180);
                workshopWorkingView.setY(w.getShowY() + 80);
                workshopWorkingView.setFitWidth(20);
                workshopWorkingView.setFitHeight(80);
                view.getGroup().getChildren().add(workshopWorkingView);
                loader.getCurrentEntities().add(workshopWorkingView);
                if (w.getCurrentTime() > 0)
                    w.currentTimeDecrease(1);
                else
                {
                    view.getGroup().getChildren().remove(workshopWorkingView);
                    farm.endWorkShop(w);
                    if (w instanceof CakeBakery)
                        view.getGroup().getChildren().remove(loader.getMovingCakeBakery());
                    else if (w instanceof CookieBakery)
                        view.getGroup().getChildren().remove(loader.getMovingCookieBakery());
                    else if (w instanceof CustomFactory)
                        view.getGroup().getChildren().remove(loader.getMovingCustomFactory());
                    else if (w instanceof EggPowderPlant)
                        view.getGroup().getChildren().remove(loader.getMovingEggPowderPlant());
                    else if(w instanceof SewingFactory)
                        view.getGroup().getChildren().remove(loader.getMovingSewingFactory());
                    else if (w instanceof Spinnery)
                        view.getGroup().getChildren().remove(loader.getMovingSpinnery());
                    else if (w instanceof WeavingFactory)
                        view.getGroup().getChildren().remove(loader.getMovingWeavingFactory());
                    view.getGroup().getChildren().add(loader.getFixedWorkShopsImageViews().get(w.getWorkShopName()));
                }
            }
    }

    private void checkWareHouse()
    {
        int i = 0;
        double firstShow_X = WIDTH / 2 - 70;
        double firstShow_y = HEIGHT - 50;
        double disPlaceMent= 18;
        for ( Item item : farm.getWareHouse().getCollectedItems() )
        {
            ImageView imageView = new ImageView( loader.getWareHouseItems().get( item.getKind() ) );
            imageView.setX( firstShow_X + i * disPlaceMent );
            imageView.setLayoutY( firstShow_y );
            i++;
            if ( i == 9 )
            {
                i = 0;
                firstShow_y -= disPlaceMent;
            }
            view.getGroup().getChildren().add(imageView);
            loader.getCurrentEntities().add(imageView);
        }
    }

    private void wellIcon()
    {
        ImageView upgrade = new ImageView(loader.getUpgradeButton());
        upgrade.setFitHeight(39);
        upgrade.setFitWidth(100);
        Label label = new Label(Integer.toString(farm.getWell().getUpgradeCost()));
        label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
        label.relocate(farm.getWell().getShowX() - 55, farm.getWell().getShowY() + 110);
        upgrade.setX(farm.getWell().getShowX() - 90);
        upgrade.setY(farm.getWell().getShowY() + 110);
        view.getGroup().getChildren().addAll(upgrade, label);
        loader.getFixedWell().setX(0);
        loader.getFixedWell().setY(10);
        loader.getFixedWell().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                int result = farm.fullWell();
                if (result == -1)
                    danceTheMoney();
                if (result == 1)
                {
                    flagWell = 0;
                    view.getGroup().getChildren().remove(arrowViewWell);
                    view.getGroup().getChildren().remove(loader.getFixedWell());
                    loader.getMovingWell().setX(farm.getWell().getShowX());
                    loader.getMovingWell().setY(farm.getWell().getShowY());
                    view.getGroup().getChildren().add(loader.getMovingWell());
                    flagWell = 0;
                    view.getGroup().getChildren().remove(arrowViewWell);
                    AnimationTimer imageViewSprite = new ImageViewSprite(loader.getMovingWell(),
                            20,false ,4, 4, 16, 200, 200, 16);
                    imageViewSprite.start();
                }
            }
        });
        upgrade.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                int result = farm.upgrade("well");
                if (result == 1)
                    danceTheMoney();
                if (result == 0)
                {
                    try
                    {
                        flagWell = 0;
                        view.getGroup().getChildren().remove(arrowViewWell);
                        label.setText(Integer.toString(farm.getWell().getUpgradeCost()));
                        loader.getFixedWell().setImage(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\" + "fixed"
                                + farm.getWell().getLevel() + ".png"), 200, 200, false, true));
                        loader.getMovingWell().setImage(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\" + "moving"
                                + farm.getWell().getLevel() + ".png"), 800, 800, false, true));
                        if (farm.getWell().getLevel() == 4)
                            view.getGroup().getChildren().removeAll(label, upgrade);
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            }
        });
    }

    private void checkWell()
    {
        ImageView wellFullnessView = new ImageView(wellFullness.get(farm.getWell().getCurrentVolume()*5/farm.getWell().getVolume()));
        wellFullnessView.setX(farm.getWell().getShowX() + 180);
        wellFullnessView.setY(farm.getWell().getShowY() + 80);
        wellFullnessView.setFitWidth(20);
        wellFullnessView.setFitHeight(80);
        view.getGroup().getChildren().add(wellFullnessView);
        loader.getCurrentEntities().add(wellFullnessView);
        if (farm.getWell().isWorking())
        {
            if (farm.getWell().getCurrentVolume() < farm.getWell().getVolume()) {
                farm.getWell().increase(1);
                view.getGroup().getChildren().remove(arrowViewWell);
            }
            else
            {
                farm.getWell().setWorking(false);
                view.getGroup().getChildren().remove(loader.getMovingWell());
                view.getGroup().getChildren().add(loader.getFixedWell());
            }
        }
    }

    private void showBackground()
    {
        loader.getMap().setX(0);
        loader.getMap().setY(0);
        view.getGroup().getChildren().addAll(loader.getMap());
        loader.getMap().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                double x = event.getX() - Constants.GRASS_DISPLACEMENT_X;
                double y = event.getY() - Constants.GRASS_DISPLACEMENT_Y;
                if (x >= 0 && x <= farm.getMapWidth()*Constants.ANIMAL_SHOW_SCALE &&
                    y >= 0 && y <= farm.getMapLength()*Constants.ANIMAL_SHOW_SCALE)
                {
                    boolean result = farm.plantGrass(x/Constants.ANIMAL_SHOW_SCALE, y/Constants.ANIMAL_SHOW_SCALE);
                    if(!result){
                        arrowTo(farm.getWell().getShowX() + loader.getFixedWell().getFitWidth() + 150 ,
                                farm.getWell().getShowY() + loader.getFixedWell().getFitHeight() + 20 ,
                                arrowViewWell , true);
                    }
                }
            }
        });
    }

    private void arrowTo(double x , double y , ImageView arrowView , boolean kind)
    {
        arrowView.setX(x);
        arrowView.setY(y);
        if(!view.getGroup().getChildren().contains(arrowView))
            view.getGroup().getChildren().add(arrowView);
        ImageViewSprite imageViewSprite = new ImageViewSprite(arrowView , 10 , false , 4 , 2 , 8 ,
                52 , 52 , 8);
        if(kind)
        {
            if(flagWell == 0 && !farm.getWell().isWorking())
            {
                flagWell = 1;
                imageViewSprite.start();
            }
        }
        else
        {
            if(flagWareHouse == 0)
            {
                flagWareHouse = 1;
                imageViewSprite.start();
            }
        }
    }

    private void makeScene()
    {
        showBackground();
        buyIcons();
        menuIcon();
        wellIcon();
        showChatRoomIcon();
        loadSatietyImages();
        loadWellFullnessImages();
        showMoneyIcon();
        workshopsIcons();
        servicesIcons();
        helicopterIconHandler();
        truckIconHandler();
        showGoals();
        loadGoals();
    }

    private void showChatRoomIcon()
    {
        if (isMultiPlayer)
        {
            try
            {
                Image image  = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\chatButton.png"),
                        77, 73, false, true);
                ImageView chatView = new ImageView(image);
                chatView.setX(5);
                chatView.setY(Constants.HEIGHT - 200);
                view.getGroup().getChildren().add(chatView);
                chatView.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        try
                        {
                            if (!clientGui.getOpen()) {
                                Stage chatStage = new Stage();
                                clientGui.start(chatStage);
                            }
                        }
                        catch (Exception e) { e.printStackTrace(); }
                    }
                });
            }
            catch (FileNotFoundException e) { e.printStackTrace(); }
        }
    }

    private void showUpgradeTruck()
    {
        ImageView upgradeTruck = new ImageView(loader.getUpgradeButton());
        upgradeTruck.setX(farm.getTruck().getShowX() - 10);
        upgradeTruck.setY(farm.getTruck().getShowY() + 140);
        upgradeTruck.setFitHeight(30);
        upgradeTruck.setFitWidth(90);
        Label label = new Label(Integer.toString(farm.getTruck().getUpgradeCost()));
        label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,15));
        label.relocate(farm.getTruck().getShowX() + 25, farm.getTruck().getShowY() + 140);
        view.getGroup().getChildren().addAll(upgradeTruck, label);
        upgradeTruck.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                int result = farm.upgrade("truck");
                if (result == 1)
                    danceTheMoney();
                if (result == 0)
                {
                    try
                    {
                        label.setText(Integer.toString(farm.getTruck().getUpgradeCost()));
                        loader.getFixedTruck().setImage(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\" + "fixed"
                                + farm.getTruck().getLevel() + ".png"), 200, 200, false, true));
                        if (farm.getTruck().getLevel() == 4)
                            view.getGroup().getChildren().removeAll(label, upgradeTruck);
                    }
                    catch (IOException e){ e.printStackTrace(); }
                }
            }
        });
    }

    private void showUpgradeHelicopter()
    {
        ImageView upgradeHelicopter = new ImageView(loader.getUpgradeButton());
        upgradeHelicopter.setX(farm.getHelicopter().getShowX() - 30);
        upgradeHelicopter.setY(farm.getHelicopter().getShowY() + 160);
        upgradeHelicopter.setFitHeight(30);
        upgradeHelicopter.setFitWidth(90);
        Label label = new Label(Integer.toString(farm.getHelicopter().getUpgradeCost()));
        label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,15));
        label.relocate(farm.getHelicopter().getShowX() + 5, farm.getHelicopter().getShowY() + 160);
        view.getGroup().getChildren().addAll(upgradeHelicopter, label);
        upgradeHelicopter.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                int result = farm.upgrade("helicopter");
                if (result == 1)
                    danceTheMoney();
                if (result == 0)
                {
                    try
                    {
                        label.setText(Integer.toString(farm.getHelicopter().getUpgradeCost()));
                        loader.getFixedHelicopter().setImage(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\" + "fixed"
                                + farm.getHelicopter().getLevel() + ".png"), 200, 200, false, true));
                        loader.getLeftHelicopter().setImage(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\" + "left"
                                + farm.getHelicopter().getLevel() + ".png"), 144, 96, false, true));
                        loader.getRightHelicopter().setImage(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\" + "right"
                                + farm.getHelicopter().getLevel() + ".png"), 144, 96, false, true));
                        if (farm.getHelicopter().getLevel() == 4)
                            view.getGroup().getChildren().removeAll(label, upgradeHelicopter);
                    }
                    catch (IOException e) { e.printStackTrace(); }
                }
            }
        });

    }

    private void showMoneyIcon()
    {
        moneyView.setFitWidth(150);
        moneyView.setFitHeight(50);
        moneyView.setX(WIDTH - 150);
        moneyView.setY(10);
        moneyLabel.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
        moneyLabel.relocate(WIDTH - 95, 15);
        view.getGroup().getChildren().addAll(moneyView , moneyLabel);
    }

    private void updateMoney()
    {
        moneyLabel.setText(Integer.toString(farm.getMoney()));
    }

    private void workshopsIcons()
    {
        for (Workshop w : farm.getWorkshops())
        {
            ImageView imageView = new ImageView(loader.getFixedWorkshops().get(w.getWorkShopName()));
            loader.getFixedWorkShopsImageViews().put(w.getWorkShopName(), imageView);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( event.getButton().equals(MouseButton.PRIMARY) )
                    {
                        if (w instanceof CustomFactory)
                        {
                            animationTimer.stop();
                            recipeSetter.makeScene(view, farm, loader.getItems(), animationTimer, loader);
                        }
                        else
                        {
                            int result = farm.startWorkShop(w.getWorkShopName());
                            if (result > 0)
                            {
                                view.getGroup().getChildren().remove(imageView);
                                flagWareHouse = 0;
                                view.getGroup().getChildren().remove(arrowViewWareHouse);
                                ImageView imageView1;
                                if (w instanceof CakeBakery)
                                    imageView1 = loader.getMovingCakeBakery();
                                else if (w instanceof CookieBakery)
                                    imageView1 = loader.getMovingCookieBakery();
                                else if (w instanceof EggPowderPlant)
                                    imageView1 = loader.getMovingEggPowderPlant();
                                else if (w instanceof SewingFactory)
                                    imageView1 = loader.getMovingSewingFactory();
                                else if (w instanceof Spinnery)
                                    imageView1 = loader.getMovingSpinnery();
                                else
                                    imageView1 = loader.getMovingWeavingFactory();
                                imageView1.setX(w.getShowX());
                                imageView1.setY(w.getShowY());
                                view.getGroup().getChildren().add(imageView1);
                                AnimationTimer imageViewSprite = new ImageViewSprite(imageView1, 1, false,
                                        4, 4, 16, 200, 200, 16);
                                flyingItems(w.getInputs(), result, w);
                                imageViewSprite.start();
                            }
                        }
                    }
                    else
                        workshopInfo(w);
                }
            });
            ImageView upgrade = new ImageView(loader.getUpgradeButton());
            upgrade.setFitHeight(39);
            upgrade.setFitWidth(100);
            Label label = new Label(Integer.toString(w.getUpgradeCost()));
            label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
            show(imageView, w);
            if (w instanceof EggPowderPlant || w instanceof CookieBakery || w instanceof CakeBakery)
            {
                label.relocate(w.getShowX() + 5, w.getShowY() + 40);
                upgrade.setX(w.getShowX() - 30);
                upgrade.setY(w.getShowY() + 40);
            }
            else
            {
                label.relocate(w.getShowX() + 235, w.getShowY() + 70);
                upgrade.setX(w.getShowX() + 200);
                upgrade.setY(w.getShowY() + 70);
            }
            upgrade.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    int result = farm.upgrade(w.getWorkShopName());
                    if (result == 1)
                        danceTheMoney();
                    if (result == 0)
                    {
                        try
                        {
                            label.setText(Integer.toString(w.getUpgradeCost()));
                            Image image = new Image(new FileInputStream("src\\Resources\\Graphic\\Workshops\\" + w.getWorkShopName() + "\\" + "fixed"
                                    + Integer.toString(w.getLevel()) + ".png"),
                                    200, 200, false, true);
                            loader.getFixedWorkshops().replace(w.getWorkShopName(), image);
                            imageView.setImage(loader.getFixedWorkshops().get(w.getWorkShopName()));
                            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Workshops\\" + w.getWorkShopName() + "\\" + "moving"
                                    + Integer.toString(w.getLevel()) + ".png"),
                                    800, 800, false, true);
                            loader.getMovingWorkshops().replace(w.getWorkShopName(), image);
                            boolean isUpgradeFinished = false;
                            if (w instanceof CakeBakery)
                            {
                                loader.setMovingCakeBakery(new ImageView(loader.getMovingWorkshops().get("cakeBakery")));
                                if (farm.getWorkshops()[0].getLevel() == 4)
                                    isUpgradeFinished = true;
                            }
                            else if (w instanceof CookieBakery)
                            {
                                loader.setMovingCookieBakery(new ImageView(loader.getMovingWorkshops().get("cookieBakery")));
                                if (farm.getWorkshops()[1].getLevel() == 4)
                                    isUpgradeFinished = true;
                            }
                            else if (w instanceof EggPowderPlant)
                            {
                                loader.setMovingEggPowderPlant(new ImageView(loader.getMovingWorkshops().get("eggPowderPlant")));
                                if (farm.getWorkshops()[2].getLevel() == 4)
                                    isUpgradeFinished = true;
                            }
                            else if (w instanceof SewingFactory)
                            {
                                loader.setMovingSewingFactory(new ImageView(loader.getMovingWorkshops().get("sewingFactory")));
                                if (farm.getWorkshops()[3].getLevel() == 4)
                                    isUpgradeFinished = true;
                            } else if (w instanceof Spinnery) {
                                loader.setMovingSpinnery(new ImageView(loader.getMovingWorkshops().get("spinnery")));
                                if (farm.getWorkshops()[5].getLevel() == 4)
                                    isUpgradeFinished = true;
                            }
                            else if (w instanceof WeavingFactory)
                            {
                                loader.setMovingWeavingFactory(new ImageView(loader.getMovingWorkshops().get("weavingFactory")));
                                if (farm.getWorkshops()[4].getLevel() == 4)
                                    isUpgradeFinished = true;
                            }
                            else if (w instanceof CustomFactory)
                            {
                                loader.setMovingCustomFactory(new ImageView(loader.getMovingWorkshops().get("customFactory")));
                                if (farm.getWorkshops()[6].getLevel() == 4)
                                    isUpgradeFinished = true;
                            }
                            if (isUpgradeFinished)
                                view.getGroup().getChildren().removeAll(label, upgrade);
                        }
                        catch (Exception e) { e.printStackTrace(); }
                    }
                }
            });
            view.getGroup().getChildren().addAll(upgrade, label);
        }
    }

    private void workshopInfo( Workshop workshop )
    {
        if( workshop.getInputs().size() > 0 )
        {
            try
            {
                animationTimer.stop();
                Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
                rectangle.setFill(Color.rgb(54,16,0));
                rectangle.setOpacity(0.7);

                Image infoBack = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png")
                        , 500, 506, false, true);
                ImageView infoBackView = new ImageView(infoBack);
                infoBackView.setY(Constants.HEIGHT / 2 - 253);
                infoBackView.setX(Constants.WIDTH / 2 - 250);

                Label label1 = new Label("Inputs:");
                label1.setTextFill(Color.rgb(54,16,0));
                label1.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
                label1.setLayoutX(Menu.WIDTH / 2 - 200);
                label1.setLayoutY(Menu.HEIGHT / 2 - 230);

                Label label2 = new Label("Output:");
                label2.setTextFill(Color.rgb(54,16,0));
                label2.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
                label2.setLayoutX(Menu.WIDTH / 2 - 200);
                label2.setLayoutY(Menu.HEIGHT / 2);

                ImageView[] inputs = new ImageView[workshop.getInputs().size()];
                ImageView output = new ImageView(loader.getItems().get(workshop.getOutput()));
                output.setFitHeight(56);
                output.setFitWidth(56);
                output.setY(Constants.HEIGHT / 2 + 60);
                output.setX(Constants.WIDTH / 2 - 180);
                for( int i = 0 ; i < workshop.getInputs().size() ; i++ )
                {
                    inputs[i] = new ImageView(loader.getItems().get(workshop.getInputs().get(i)));
                    inputs[i].setFitHeight(56);
                    inputs[i].setFitWidth(56);
                    inputs[i].setY(Constants.HEIGHT / 2 - 170 + ( i / 5 ) * 76);
                    inputs[i].setX(Constants.WIDTH / 2 - 190 + ( i % 5 ) * 76);
                }
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        view.getGroup().getChildren().removeAll(rectangle,infoBackView,label1,label2,output);
                        view.getGroup().getChildren().removeAll(inputs);
                        animationTimer.start();
                    }
                });

                view.getGroup().getChildren().addAll(rectangle,infoBackView,label1,label2,output);
                view.getGroup().getChildren().addAll(inputs);
            }
            catch ( Exception e ) { e.printStackTrace(); }
        }
    }

    private void showUpgradeWareHouse()
    {
        ImageView button =  new ImageView(loader.getUpgradeButton());
        button.setFitWidth(90);
        button.setFitHeight(30);
        button.setX(farm.getWareHouse().getShowX() - 70);
        button.setY(farm.getWareHouse().getShowY() + 95);
        Label label = new Label(Integer.toString(farm.getWareHouse().getUpgradeCost()));
        label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,15));
        label.relocate(farm.getWareHouse().getShowX() - 40, farm.getWareHouse().getShowY() + 95);
        view.getGroup().getChildren().addAll(button , label);
        button.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                int result = farm.upgrade("wareHouse");
                if(result == 0)
                {
                    try
                    {
                        label.setText(Integer.toString(farm.getWareHouse().getUpgradeCost()));
                        loader.getWareHouse().setImage(
                                new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Depot\\" +
                                farm.getWareHouse().getLevel() + ".png"), 250 , 150 ,
                                        false , true));
                        if(farm.getWareHouse().getLevel() == 4)
                            view.getGroup().getChildren().removeAll(button , label);
                    }
                    catch (Exception e) { e.getStackTrace(); }
                }
                if(result == 1)
                {
                    danceTheMoney();
                }
            }

        });
    }

    private void flyingItems(Vector<String> items , int count , Workshop workshop)
    {
        Vector<ImageView> movingItems = new Vector<>();
        int counter = 0;
        double startX = WIDTH/2 - 100;
        double startY = HEIGHT - 50;
        double displacement = 18;
        for (String s : items)
        {
            for (int i = 0; i < count ; i++)
            {
                ImageView imageView = new ImageView(loader.getWareHouseItems().get(s));
                imageView.setX(startX);
                imageView.setY(startY + counter * displacement);
                movingItems.add(imageView);
                counter ++;
                if(counter == 4)
                {
                    counter = 0;
                    startX -= displacement;
                }
                view.getGroup().getChildren().add(imageView);
            }
        }
        for (ImageView m : movingItems)
        {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(3000) , m);
            translateTransition.setToX(- m.getX() + workshop.getShowX() + 100);
            translateTransition.setToY(- m.getY() + workshop.getShowY() + 100);
            translateTransition.play();
            translateTransition.setOnFinished(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    view.getGroup().getChildren().remove(m);
                }
            });
        }
    }

    private void servicesIcons()
    {
        show(loader.getFixedTruck() , farm.getTruck());
        show(loader.getFixedHelicopter() , farm.getHelicopter());
        show(loader.getFixedWell() , farm.getWell());
        show(loader.getWareHouse() , farm.getWareHouse());
        showUpgradeTruck();
        showUpgradeHelicopter();
        showUpgradeWareHouse();
    }

    private void showMap()
    {
        view.getGroup().getChildren().removeAll(loader.getCurrentEntities());
        loader.getCurrentEntities().clear();
        for(int j = 0; j < farm.getMapLength(); j++)
            for (int i = 0; i < farm.getMapWidth(); i++)
            {
                int numberOfGrass = 0;
                Vector<Entity> stuffs = farm.getMap().getCells()[j][i].getStuffs();
                for (Entity e : stuffs)
                {
                    ImageView imageView = null;
                    if(e instanceof Item && !e.isDead()) {
                        imageView = new ImageView(loader.getItems().get(((Item) e).getKind()));
                        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                double x = e.getX();
                                double y = e.getY();
                                boolean result = farm.pickUp(x, y);
                                if (!result){
                                    arrowTo(farm.getWareHouse().getShowX() + 180
                                            , farm.getWareHouse().getShowY() - 50 ,
                                            arrowViewWareHouse , false);
                                }else{
                                    flagWareHouse = 0;
                                    view.getGroup().getChildren().remove(arrowViewWareHouse);
                                }
                            }
                        });
                    }
                    else if(e instanceof Grass && !e.isDead())
                    {
                        numberOfGrass ++;
                        if (numberOfGrass <= 3)
                            imageView = new ImageView(loader.getGrass()[numberOfGrass - 1]);
                        else
                            imageView = new ImageView(loader.getGrass()[3]);
                    }
                    if (imageView != null) {
                        loader.getCurrentEntities().add(imageView);
                        showFixed(imageView, e);
                    }
                }
            }
    }

    private void showFixed(ImageView iView, Entity e)
    {
        iView.setX(e.getShowX());
        iView.setY(e.getShowY());
        view.getGroup().getChildren().add(iView);
    }

    private void showMovingAnimals()
    {
        ImageView animalView = null;
        for(Entity e : farm.getStuffs())
        {
            if(e instanceof Animal && !(e instanceof Wild) && !e.isDead())
            {
                switch (((Animal) e).getDirection())
                {
                    case UP: animalView = new ImageView(loader.getAnimalsUp().get(((Animal) e).getName())); break;
                    case RIGHT: animalView = new ImageView(loader.getAnimalsRight().get(((Animal) e).getName())); break;
                    case LEFT: animalView = new ImageView(loader.getAnimalsLeft().get(((Animal) e).getName())); break;
                    case DOWN: animalView = new ImageView(loader.getAnimalsDown().get(((Animal) e).getName())); break;
                    case UP_LEFT: animalView = new ImageView(loader.getAnimalsUpLeft().get(((Animal) e).getName())); break;
                    case DOWN_LEFT: animalView = new ImageView(loader.getAnimalsDownLeft().get(((Animal) e).getName())); break;
                    case DOWN_RIGHT: animalView = new ImageView(loader.getAnimalsDownRight().get(((Animal) e).getName())); break;
                    case UP_RIGHT: animalView = new ImageView(loader.getAnimalsUpRight().get(((Animal) e).getName())); break;
                    case NONE: animalView = new ImageView(loader.getDomesticEat().get(((Animal) e).getName())); break;
                }
                animalView.setFitWidth(Constants.ANIMAL_SIZE);
                animalView.setFitHeight(Constants.ANIMAL_SIZE);
                int col = loader.getColsAndRows().get(((Animal) e).getName())[0];
                int row = loader.getColsAndRows().get(((Animal) e).getName())[1];
                int width = loader.getWidthAndHeight().get(((Animal) e).getName())[0];
                int height = loader.getWidthAndHeight().get(((Animal) e).getName())[1];
                if (((Animal) e).getDirection() != DIRECTION.NONE)
                {
                    animalView.setX(((Animal) e).getPreviousX());
                    animalView.setY(((Animal) e).getPreviousY());
                    view.getGroup().getChildren().add(animalView);
                    loader.getCurrentEntities().add(animalView);
                    AnimationTimer animationTimer = new ImageViewSprite(
                            animalView,1,false, col, row, col * row,
                            width / col,
                            height / row, row * col
                    );
                    animationTimer.start();
                    MoveTransition pathTransition = new MoveTransition(animalView, ((Animal) e).getPreviousX(),
                            ((Animal) e).getPreviousY(), e.getShowX(), e.getShowY(), 3000);
                    pathTransition.setAutoReverse(false);
                    pathTransition.setCycleCount(1);
                    pathTransition.play();
                }
                else
                {
                    animalView.setX(e.getShowX());
                    animalView.setY(e.getShowY());
                    view.getGroup().getChildren().add(animalView);
                    loader.getCurrentEntities().add(animalView);
                    AnimationTimer animationTimer = new ImageViewSprite(
                            animalView,1,false, col, row, col * row,
                            width / col,
                            height / row, row * col
                    );
                    animationTimer.start();
                }
                if (e instanceof Domestic){
                    ImageView satietyView = new ImageView(animalSatiety.get((int)(Math.floor((((Domestic) e).getSatiety())+1)/2)));
                    satietyView.setX(e.getShowX());
                    satietyView.setY(e.getShowY());
                    satietyView.setFitWidth(((Domestic) e).getSatiety()*5);
                    satietyView.setFitHeight(5);
                    if(((Domestic) e).getDirection() != DIRECTION.NONE) {
                        MoveTransition satietyTransition = new MoveTransition(satietyView, ((Animal) e).getPreviousX(),
                                ((Animal) e).getPreviousY(), e.getShowX(), e.getShowY(), 3000);
                        satietyTransition.setAutoReverse(false);
                        satietyTransition.setCycleCount(1);
                        satietyTransition.play();
                    }
                    view.getGroup().getChildren().add(satietyView);
                    loader.getCurrentEntities().add(satietyView);
                }
            }
        }
    }

    private void showMovingWildAnimals()
    {
        int i = 0;
        for(Entity e : farm.getStuffs())
        {
            if(e instanceof Wild && !e.isDead())
            {
                Image image;
                switch (((Wild) e).getDirection())
                {
                    case UP:
                        image = loader.getAnimalsUp().get(((Wild) e).getName()); break;
                    case RIGHT:
                        image = loader.getAnimalsRight().get(((Wild) e).getName()); break;
                    case LEFT:
                        image = loader.getAnimalsLeft().get(((Wild) e).getName()); break;
                    case DOWN:
                        image = loader.getAnimalsDown().get(((Wild) e).getName()); break;
                    case UP_LEFT:
                        image = loader.getAnimalsUpLeft().get(((Wild) e).getName()); break;
                    case DOWN_LEFT:
                        image = loader.getAnimalsDownLeft().get(((Wild) e).getName()); break;
                    case DOWN_RIGHT:
                        image = loader.getAnimalsDownRight().get(((Wild) e).getName()); break;
                    default: //up_right
                        image = loader.getAnimalsUpRight().get(((Wild) e).getName()); break;
                }
                if (loader.getWildAnimals()[i] == null){
                    loader.getWildAnimals()[i] = new ImageView(image);
                }
                else{
                    loader.getWildAnimals()[i].setImage(image);
                }

                loader.getWildAnimals()[i].setFitWidth(Constants.ANIMAL_SIZE);
                loader.getWildAnimals()[i].setFitHeight(Constants.ANIMAL_SIZE);
                int col = loader.getColsAndRows().get(((Wild) e).getName())[0];
                int row = loader.getColsAndRows().get(((Wild) e).getName())[1];
                int width = loader.getWidthAndHeight().get(((Wild) e).getName())[0];
                int height = loader.getWidthAndHeight().get(((Wild) e).getName())[1];
                loader.getWildAnimals()[i].setX(((Wild) e).getPreviousX());
                loader.getWildAnimals()[i].setY(((Wild) e).getPreviousY());
                view.getGroup().getChildren().add(loader.getWildAnimals()[i]);
                loader.getCurrentEntities().add(loader.getWildAnimals()[i]);
                AnimationTimer animationTimer = new ImageViewSprite(
                        loader.getWildAnimals()[i],1,false, col, row, col * row,
                        width / col, height / row, row * col);
                animationTimer.start();
                MoveTransition pathTransition = new MoveTransition(loader.getWildAnimals()[i], ((Wild) e).getPreviousX(),
                        ((Wild) e).getPreviousY(), e.getShowX(), e.getShowY(), 3000);
                pathTransition.setAutoReverse(false);
                pathTransition.setCycleCount(1);
                pathTransition.play();
                loader.getWildAnimals()[i].setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        boolean result = farm.putCage(e.getX(), e.getY());
                    }
                });
            i++;
            }

        }
    }

    private void show(ImageView iView, Entity e)
    {
        iView.setTranslateX(e.getShowX());
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(e.getShowY()*3) , iView);
        translateTransition.setToY(e.getShowY());
        view.getGroup().getChildren().add(iView);
        translateTransition.play();
    }

    private void checkHelicopter()
    {
        if (farm.getHelicopter().isMoving())
        {
            if( farm.getHelicopter().getCurrentTime() > 0 )
            {
                if ( farm.getHelicopter().getCurrentTime() > farm.getHelicopter().getWorkingTime() / 1.8 )
                {
                    loader.getLeftHelicopter().setX(farm.getHelicopter().getPrevMovingX() + 550);
                    loader.getLeftHelicopter().setY(loader.getFixedHelicopter().getY());
                    AnimationTimer animationTimer = new ImageViewSprite(loader.getLeftHelicopter(), 1,
                            false, 3, 2, 6, 48, 48, 6);
                    animationTimer.start();
                    MoveTransition pathTransition = new MoveTransition(loader.getLeftHelicopter(),
                            farm.getHelicopter().getPrevMovingX()+550, loader.getFixedHelicopter().getY(),
                            farm.getHelicopter().getNextMovingX()+550, loader.getFixedHelicopter().getY(), 2000);
                    pathTransition.setAutoReverse(false);
                    pathTransition.setCycleCount(1);
                    pathTransition.play();
                    farm.getHelicopter().setPrevMovingX(farm.getHelicopter().getNextMovingX());
                    farm.getHelicopter().setNextMovingX(farm.getHelicopter().getNextMovingX() - Constants.movingScale);
                }
                else
                {
                    if( farm.getHelicopter().getCurrentTime() == farm.getHelicopter().getWorkingTime() / 2 )
                    {
                        view.getGroup().getChildren().remove(loader.getLeftHelicopter());
                        farm.getHelicopter().setNextMovingX(farm.getHelicopter().getNextMovingX() + Constants.movingScale);
                    }
                    loader.getRightHelicopter().setX(farm.getHelicopter().getPrevMovingX() + 550);
                    loader.getRightHelicopter().setY(loader.getFixedHelicopter().getY());
                    if (!view.getGroup().getChildren().contains(loader.getRightHelicopter()))
                        view.getGroup().getChildren().add(loader.getRightHelicopter());
                    AnimationTimer animationTimer = new ImageViewSprite(loader.getRightHelicopter(),2,
                            false, 3, 2, 6, 48, 48, 6);
                    animationTimer.start();
                    MoveTransition pathTransition = new MoveTransition(loader.getRightHelicopter(),
                            farm.getHelicopter().getPrevMovingX()+550, loader.getFixedHelicopter().getY(),
                            farm.getHelicopter().getNextMovingX()+550, loader.getFixedHelicopter().getY(), 2000);
                    pathTransition.setAutoReverse(false);
                    pathTransition.setCycleCount(1);
                    pathTransition.play();
                    farm.getHelicopter().setPrevMovingX(farm.getHelicopter().getNextMovingX());
                    farm.getHelicopter().setNextMovingX(farm.getHelicopter().getNextMovingX() + Constants.movingScale);
                }
                farm.getHelicopter().decreaseCurrentTime(1);
            }
            else
            {
                farm.getHelicopter().setMoving(false);
                loader.getFixedHelicopter().setFitHeight(220);
                loader.getFixedHelicopter().setFitWidth(220);
                loader.getFixedHelicopter().setY(0);
                loader.getFixedHelicopter().setX(0);
                view.getGroup().getChildren().removeAll(loader.getLeftHelicopter(), loader.getRightHelicopter());
                view.getGroup().getChildren().add(loader.getFixedHelicopter());
                farm.clearFromHelicopter();
            }
        }
    }

    private void checkTruck()
    {
        if (farm.getTruck().isMoving())
        {
            if( farm.getTruck().getCurrentTime() > 0 )
            {
                if ( farm.getTruck().getCurrentTime() > farm.getTruck().getWorkingTime() / 1.8 )
                {
                    loader.getRightTruck().setX(farm.getTruck().getPrevMovingX());
                    loader.getRightTruck().setY(loader.getFixedTruck().getY() + 50);
                    loader.getRightTruck().setFitWidth(48);
                    loader.getRightTruck().setFitHeight(48);
                    AnimationTimer animationTimer = new ImageViewSprite(loader.getRightTruck(), 2,
                            false, 2, 1, 2, 48, 48, 2);
                    animationTimer.start();
                    MoveTransition pathTransition = new MoveTransition(loader.getRightTruck(),
                            farm.getTruck().getPrevMovingX(), loader.getFixedTruck().getY()+50,
                            farm.getTruck().getNextMovingX(), loader.getFixedTruck().getY()+50, 2000);
                    pathTransition.setAutoReverse(false);
                    pathTransition.setCycleCount(1);
                    pathTransition.play();
                    farm.getTruck().setPrevMovingX(farm.getTruck().getNextMovingX());
                    farm.getTruck().setNextMovingX(farm.getTruck().getNextMovingX() + Constants.movingScale);
                }
                else
                {
                    if( farm.getTruck().getCurrentTime() == farm.getTruck().getWorkingTime() / 2 )
                    {
                        view.getGroup().getChildren().remove(loader.getRightTruck());
                        farm.getTruck().setNextMovingX(farm.getTruck().getNextMovingX() + Constants.movingScale);
                    }
                    loader.getLeftTruck().setX(farm.getTruck().getPrevMovingX());
                    loader.getLeftTruck().setY(loader.getFixedTruck().getY() + 50);
                    loader.getLeftTruck().setFitWidth(48);
                    loader.getLeftTruck().setFitHeight(48);
                    if (!view.getGroup().getChildren().contains(loader.getLeftTruck()))
                        view.getGroup().getChildren().add(loader.getLeftTruck());
                    AnimationTimer animationTimer = new ImageViewSprite(loader.getLeftTruck(),1,
                            false, 2, 1, 2, 48, 48, 2);
                    animationTimer.start();
                    MoveTransition pathTransition = new MoveTransition(loader.getLeftTruck(),
                            farm.getTruck().getPrevMovingX(), loader.getFixedTruck().getY()+50,
                            farm.getTruck().getNextMovingX(), loader.getFixedTruck().getY()+50, 2000);
                    pathTransition.setAutoReverse(false);
                    pathTransition.setCycleCount(1);
                    pathTransition.play();
                    farm.getTruck().setPrevMovingX(farm.getTruck().getNextMovingX());
                    farm.getTruck().setNextMovingX(farm.getTruck().getNextMovingX() - Constants.movingScale);
                }
                farm.getTruck().decreaseCurrentTime(1);
            }
            else
            {
                farm.getTruck().setMoving(false);
                loader.getFixedTruck().setFitHeight(200);
                loader.getFixedTruck().setFitWidth(200);
                loader.getFixedTruck().setY(0);
                loader.getFixedTruck().setX(0);
                view.getGroup().getChildren().removeAll(loader.getLeftTruck(), loader.getRightTruck());
                view.getGroup().getChildren().add(loader.getFixedTruck());
                if (isMultiPlayer && player.isClient()){
                    clientSender.sendItemsToMarket(farm.getTruck().getItems());
                }
                farm.clearFromTruck();
            }
        }
    }

    private void showMenu()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0, WIDTH, HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);
            Image menuBackground = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\gameMenuBackground.png"),
                    300, 480, false, true);
            ImageView menuBackgroundView = new ImageView(menuBackground);
            menuBackgroundView.setX(WIDTH / 2 - 150);
            menuBackgroundView.setY(HEIGHT / 2 - 240);
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
                    animationTimer.start();
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
            continueView.setX(WIDTH / 2 - 75);
            continueView.setY(HEIGHT / 2 - 210);
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
            menuView.setX(WIDTH / 2 - 75);
            menuView.setY(HEIGHT / 2 - 120);
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
            restartView.setX(WIDTH / 2 - 75);
            restartView.setY(HEIGHT / 2 - 30);
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
            levelsView.setX(WIDTH / 2 - 75);
            levelsView.setY(HEIGHT / 2 + 60);
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
            optionsView.setX(WIDTH / 2 - 75);
            optionsView.setY(HEIGHT / 2 + 150);
            return optionsView;
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return null;
    }

    private void mainMenuHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0, WIDTH, HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);
            Image quitMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\quitMessageBox.png")
                    , 800, 300, false, true);
            ImageView quitMessageView = new ImageView(quitMessage);
            quitMessageView.setY(HEIGHT / 2 - 150);
            quitMessageView.setX(WIDTH / 2 - 400);
            Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                    , 153, 145, false, true);
            ImageView yesView = new ImageView(yes);
            yesView.setY(HEIGHT / 2 + 150);
            yesView.setX(WIDTH / 2 - 200);
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
            noView.setY(HEIGHT / 2 + 150 );
            noView.setX(WIDTH / 2 + 47);
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
            Rectangle rectangle = new Rectangle(0,0, WIDTH, HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);
            Image restartMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\restartMessageBox.png")
                    , 800, 300, false, true);
            ImageView restartMessageView = new ImageView(restartMessage);
            restartMessageView.setY(HEIGHT / 2 - 150);
            restartMessageView.setX(WIDTH / 2 - 400);
            Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                    , 153, 145, false, true);
            ImageView yesView = new ImageView(yes);
            yesView.setY(HEIGHT / 2 + 150);
            yesView.setX(WIDTH / 2 - 200);
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
                        path = "src\\Resources\\Levels\\Level" + Integer.toString(player.getLevelThatPlaysNow()) + ".txt";
                        runHandler();
                    }
                    catch ( Exception e ) { e.printStackTrace(); }
                }
            });
            Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                    , 153, 146, false, true);
            ImageView noView = new ImageView(no);
            noView.setY(HEIGHT / 2 + 150 );
            noView.setX(WIDTH / 2 + 47);
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
            Rectangle rectangle = new Rectangle(0,0, WIDTH, HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);
            Image quitMessage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\goToLevelsMessageBox.png")
                    , 800, 300, false, true);
            ImageView quitMessageView = new ImageView(quitMessage);
            quitMessageView.setY(HEIGHT / 2 - 150);
            quitMessageView.setX(WIDTH / 2 - 400);
            Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png")
                    , 153, 145, false, true);
            ImageView yesView = new ImageView(yes);
            yesView.setY(HEIGHT / 2 + 150);
            yesView.setX(WIDTH / 2 - 200);
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
                    catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            });
            Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                    , 153, 146, false, true);
            ImageView noView = new ImageView(no);
            noView.setY(HEIGHT / 2 + 150 );
            noView.setX(WIDTH / 2 + 47);
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
            Rectangle rectangle = new Rectangle(0,0, WIDTH, HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\gameMenuBackground.png")
                    , 500, 600, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setY(HEIGHT / 2 - 300);
            backgroundView.setX(WIDTH / 2 - 250);
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

    private void makeMultiplayerScene()
    {
        try
        {
            Image host = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\hostButton.png")
                    , 300, 118, false, true);
            ImageView hostView = new ImageView(host);
            hostView.setY(HEIGHT / 2);
            hostView.setX(WIDTH / 2 - 150);
            hostView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    hostHandler();
                }
            });

            Image client = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\clientButton.png")
                    , 300, 118, false, true);
            ImageView clientView = new ImageView(client);
            clientView.setY(HEIGHT / 2 + 126.5 );
            clientView.setX(WIDTH / 2 - 150);
            clientView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    clientHandler();
                }
            });

            multiplayer.getGroup().getChildren().addAll(hostView,clientView);
        }
        catch ( Exception e ){}
    }

    private void hostHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image hostInfo = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png")
                    , 300, 300, false, true);
            ImageView hostInfoView = new ImageView(hostInfo);
            hostInfoView.setY(Constants.HEIGHT / 2 - 150);
            hostInfoView.setX(Constants.WIDTH / 2 - 150);

            Image exit = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                    , 50, 50, false, true);
            ImageView exitView = new ImageView(exit);
            exitView.setY(Constants.HEIGHT / 2 - 150);
            exitView.setX(Constants.WIDTH / 2 + 100);

            Label message = new Label("Port: ");
            message.setTextFill(Color.rgb(54,16,0));
            message.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            message.setLayoutX(Constants.WIDTH / 2 - 110);
            message.setLayoutY(Constants.HEIGHT / 2 - 110);

            Label ipAdress = new Label("IP : "+ Inet4Address.getLocalHost().getHostAddress());
            ipAdress.setTextFill(Color.rgb(54,16,0));
            ipAdress.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            ipAdress.setLayoutX(Constants.WIDTH / 2 - 110);
            ipAdress.setLayoutY(Constants.HEIGHT / 2 - 50);

            TextField port = new TextField();
            port.setText("8050");
            port.setStyle("-fx-text-fill : gray");
            port.setAlignment(Pos.CENTER);
            port.setLayoutX(Constants.WIDTH / 2 - 40);
            port.setLayoutY(Constants.HEIGHT / 2 - 110);
            port.setPrefSize(150,40);
            port.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));

            port.textProperty().addListener(new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                {
                    port.setStyle("-fx-text-fill : black");
                }
            });

            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png")
                    , 150, 59, false, true);
            ImageView okView = new ImageView(ok);
            okView.setY(Constants.HEIGHT / 2 + 150);
            okView.setX(Constants.WIDTH / 2 - 75);

            okView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try {
                        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port.getText()));
                        Server server = new Server(serverSocket, Integer.parseInt(port.getText()));
                        server.start();
                        hostMenu = new HostMenu(server);
                        stage.setScene(hostMenu.getScene());
                        player.setClient(false);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });

            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    multiplayer.getGroup().getChildren().removeAll(rectangle,hostInfoView,exitView,message,port,ipAdress,okView);
                    makeMultiplayerScene();
                }
            });

            exitView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    multiplayer.getGroup().getChildren().removeAll(rectangle,hostInfoView,message,port,ipAdress,okView);
                    makeMultiplayerScene();
                }
            });

            multiplayer.getGroup().getChildren().addAll(rectangle,hostInfoView,message,port,ipAdress,okView , exitView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void clientHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,Constants.WIDTH,Constants.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image clientInfo = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png")
                    , 500, 500, false, true);
            ImageView clientInfoView = new ImageView(clientInfo);
            clientInfoView.setY(Constants.HEIGHT / 2 - 250);
            clientInfoView.setX(Constants.WIDTH / 2 - 250);

            Image exit = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png")
                    , 50, 50, false, true);
            ImageView exitView = new ImageView(exit);
            exitView.setY(Constants.HEIGHT / 2 - 225);
            exitView.setX(Constants.WIDTH / 2 + 215);

            Label clientPortLabel = new Label("Client Port: ");
            clientPortLabel.setTextFill(Color.rgb(54,16,0));
            clientPortLabel.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            clientPortLabel.setLayoutX(Constants.WIDTH / 2 - 210);
            clientPortLabel.setLayoutY(Constants.HEIGHT / 2 - 220);

            TextField clientPort = new TextField();
            clientPort.setText("8060");
            clientPort.setStyle("-fx-text-fill : gray");
            clientPort.setAlignment(Pos.CENTER);
            clientPort.setLayoutX(Constants.WIDTH / 2 - 50);
            clientPort.setLayoutY(Constants.HEIGHT / 2 - 220);
            clientPort.setPrefSize(150,40);
            clientPort.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));

            Label clientIPAddress = new Label("Client IP :      "+Inet4Address.getLocalHost().getHostAddress());
            clientIPAddress.setTextFill(Color.rgb(54,16,0));
            clientIPAddress.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            clientIPAddress.setLayoutX(Constants.WIDTH / 2 - 200);
            clientIPAddress.setLayoutY(Constants.HEIGHT / 2 - 140);

            Label serverPortLabel = new Label("Server Port: ");
            serverPortLabel.setTextFill(Color.rgb(54,16,0));
            serverPortLabel.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            serverPortLabel.setLayoutX(Constants.WIDTH / 2 - 200);
            serverPortLabel.setLayoutY(Constants.HEIGHT / 2 - 60);

            TextField serverPort = new TextField();
            serverPort.setAlignment(Pos.CENTER);
            serverPort.setLayoutX(Constants.WIDTH / 2 - 50);
            serverPort.setLayoutY(Constants.HEIGHT / 2 - 60);
            serverPort.setPrefSize(150,40);
            serverPort.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));

            Label serverIPAddress = new Label("Server IP : ");
            serverIPAddress.setTextFill(Color.rgb(54,16,0));
            serverIPAddress.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
            serverIPAddress.setLayoutX(Constants.WIDTH / 2 - 190);
            serverIPAddress.setLayoutY(Constants.HEIGHT / 2);

            TextField serverIP = new TextField();
            serverIP.setAlignment(Pos.CENTER);
            serverIP.setLayoutX(Constants.WIDTH / 2 - 50);
            serverIP.setLayoutY(Constants.HEIGHT / 2);
            serverIP.setPrefSize(150,40);
            serverIP.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));

            clientPort.textProperty().addListener(new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                {
                    clientPort.setStyle("-fx-text-fill : black");
                }
            });

            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png")
                    , 150, 59, false, true);
            ImageView okView = new ImageView(ok);
            okView.setY(Constants.HEIGHT / 2 + 250);
            okView.setX(Constants.WIDTH / 2 - 75);

            okView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    try {
                        if (!serverIP.getText().equals("") && !serverPort.getText().equals("")) {
                            socket = new Socket(serverIP.getText(), Integer.parseInt(serverPort.getText()));
                            clientSender = new ClientSender(socket);
                            clientGui = new ClientGui(clientSender, player);
                            clientListener = new Thread(new ClientListener(socket, clientGui));
                            clientListener.start();
                            clientSender.sendPlayer(player);
                            isMultiPlayer = true;
                            stage.setScene(menu.getScene());
                            player.setClient(true);
                        } else {
                            //todo error haye zahra!:D
                        }
                        //if( clientPort.getText().equals("") )
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    multiplayer.getGroup().getChildren().removeAll(rectangle,clientInfoView,exitView,clientPortLabel,
                            clientPort,clientIPAddress,serverIPAddress,serverPort,okView,serverIP,serverPortLabel);
                    makeMultiplayerScene();
                }
            });

            exitView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    multiplayer.getGroup().getChildren().removeAll(rectangle,clientInfoView,exitView,clientPortLabel,
                            clientPort,clientIPAddress,serverIPAddress,serverPort,okView,serverIP,serverPortLabel);
                    makeMultiplayerScene();
                }
            });

            multiplayer.getGroup().getChildren().addAll(rectangle,clientInfoView,exitView,clientPortLabel,clientPort,
                    clientIPAddress,serverIPAddress,serverPort,okView,serverIP,serverPortLabel);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void writePlayers()
    {
        try
        {
            Image uncheckedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxFalse.png")
                    , 25, 25, false, true);
            Image checkedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxTrue.png")
                    , 25, 25, false, true);
            for(Node node:choosePlayer.getGroup().getChildren())
            {
                if( node instanceof Text)
                    ((Text) node).setText("");
                if( node instanceof ImageView && ( ((ImageView) node).getImage() == uncheckedCheckBox
                        || ((ImageView) node).getImage() == checkedCheckBox)  )
                    ((ImageView) node).setImage(null);
            }
            Text text;
            int i = 0;
            for (Player p : players)
            {
                i++;
                text = new Text( Menu.WIDTH - 450, ( Menu.HEIGHT - 150 ) * ( i + 1 ) / (players.size() + 2),p.getName());
                text.setFill(Color.rgb(54,16,0));
                text.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));
                choosePlayer.getGroup().getChildren().addAll(text);
                text.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        for( Node node : choosePlayer.getGroup().getChildren() )
                            if( node instanceof Label )
                                ((Label) node).setText("");
                        player = p;
                        player.setLastPlayer(true);
                        for( Player p1 : players )
                            if( p1.isLastPlayer() && p1 != p )
                                p1.setLastPlayer(false);
                        savePlayers(players);
                        start.getGroup().getChildren().removeAll(levels);
                        levels.clear();
                        loadLevels();
                        insertPlayer();
                        choosePlayer.setPlayer(player);
                    }
                });
            }
        }
        catch ( Exception e ){}
    }

    private void insertPlayer()
    {
        Label playerName = new Label("");
        playerName.setTextFill(Color.rgb(54,16,0));
        playerName.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,30));
        playerName.setLayoutX(Menu.WIDTH - 400);
        playerName.setLayoutY((Menu.HEIGHT - 150 ) / (players.size() + 2));
        if( player != null )
            playerName.setText("Player : "+player.getName());
        choosePlayer.getGroup().getChildren().addAll(playerName);
    }

    private void insertNewPlayer()
    {
        try
        {
            Image newPlayer = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\newPlayerButton.png")
                    , 200, 78, false, true);
            ImageView newPlayerView = new ImageView(newPlayer);
            newPlayerView.setY(Menu.HEIGHT - 150);
            newPlayerView.setX(Menu.WIDTH - 250);
            choosePlayer.getGroup().getChildren().addAll(newPlayerView);
            newPlayerView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
                    rectangle.setFill(Color.rgb(54,16,0));
                    rectangle.setOpacity(0.7);
                    choosePlayer.getGroup().getChildren().addAll(rectangle);
                    try
                    {
                        Image newPlayer = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png")
                                , 500, 506, false, true);
                        ImageView newPlayerView = new ImageView(newPlayer);
                        newPlayerView.setY(Menu.HEIGHT / 2 - 253);
                        newPlayerView.setX(Menu.WIDTH / 2 - 250);

                        Label message = new Label("Player Name:");
                        message.setTextFill(Color.rgb(54,16,0));
                        message.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,40));
                        message.setLayoutX(Menu.WIDTH / 2 - 150);
                        message.setLayoutY(Menu.HEIGHT / 2 - 200);

                        TextField playerName = new TextField();
                        playerName.setAlignment(Pos.CENTER);
                        playerName.setLayoutX(Menu.WIDTH / 2 - 150);
                        playerName.setLayoutY(Menu.HEIGHT / 2 - 100);
                        playerName.setPrefSize(300,100);
                        playerName.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,20));

                        Image add = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\addButton.png")
                                , 150, 59, false, true);
                        ImageView addView = new ImageView(add);
                        addView.setY(Menu.HEIGHT / 2 + 130);
                        addView.setX(Menu.WIDTH / 2 - 175);

                        Image cancel = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\cancelButton.png")
                                , 150, 59, false, true);
                        ImageView cancelView = new ImageView(cancel);
                        cancelView.setY(Menu.HEIGHT / 2 + 130);
                        cancelView.setX(Menu.WIDTH / 2 + 25);

                        addView.setOnMouseClicked(new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                player = new Player(playerName.getText(),players.size() + 1);
                                players.add(player);
                                player.setLastPlayer(true);
                                for( Player p1 : players )
                                    if( p1.isLastPlayer() && p1 != player )
                                        p1.setLastPlayer(false);
                                savePlayers(players);
                                start.getGroup().getChildren().removeAll(levels);
                                levels.clear();
                                loadLevels();
                                choosePlayer.getGroup().getChildren().removeAll(newPlayerView,message,playerName,cancelView,addView,rectangle);
                                writePlayers();
                                for( Node node : choosePlayer.getGroup().getChildren() )
                                    if( node instanceof Label )
                                        ((Label) node).setText("");
                                choosePlayer.setPlayer(player);
                            }
                        });

                        cancelView.setOnMouseClicked(new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                choosePlayer.getGroup().getChildren().removeAll(newPlayerView,message,playerName,cancelView,addView,rectangle);
                            }
                        });

                        choosePlayer.getGroup().getChildren().addAll(newPlayerView,message,playerName,addView,cancelView);
                    }
                    catch ( Exception e ){}

                }
            });
        }
        catch ( Exception e ){}
    }
}
