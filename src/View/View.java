package View;

import Controller.Controller;
import Model.*;
import Model.Animals.Animal;
import Model.Items.Item;
import Model.Workshops.CustomFactory;
import Model.Workshops.Workshop;
import View.Graphic.Menu;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class View
{
    private final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);
    private Stage stage;
    private HashMap<String, Image> fixedWorkshops = new HashMap<>();
    private HashMap<String, Image> movingWorkshops = new HashMap<>();
    private HashMap<String, Image> animalsLeft = new HashMap<>();
    private HashMap<String, Image> animalsRight = new HashMap<>();
    private HashMap<String, Image> animalsUp = new HashMap<>();
    private HashMap<String, Image> animalsDown = new HashMap<>();
    private HashMap<String, Image> animalsDeath = new HashMap<>();
    private HashMap<String, Image> domesticEat = new HashMap<>();
    private HashMap<String, Image> animalsDownLeft = new HashMap<>();
    private HashMap<String, Image> animalsDownRight = new HashMap<>();
    private HashMap<String, Image> animalsUpLeft = new HashMap<>();
    private HashMap<String, Image> animalsUpRight = new HashMap<>();
    private HashMap<String, Image> animalsFixed = new HashMap<>();
    private HashMap<String, Image> wildCaged = new HashMap<>();
    private HashMap<String, Image> items = new HashMap<>();
    private Image movingWell;
    private Image fixedWell;
    private Image fixedHelicopter;
    private Image leftHelicopter;
    private Image rightHelicopter;
    private Image fixedTruck;
    private Image leftTruck;
    private Image rightTruck;
    private Image cage;
    private Image[] grass = new Image[4];
    private Farm farm;
    private long gameTime = 0 , lastTime = 0 , second = 1000000000;
    private Label timerLabel;
    private Menu menu;

    public Scene getScene()
    {
        return scene;
    }

    public View(Stage stage , Menu menu)
    {
        this.menu = menu;
        this.stage = stage;
    }

    public void play(Farm farm)
    {
        this.farm = farm;
        stage.setScene(scene);
        loadImages();
        showBackground();
        showIcons();
        showWorkshops();
        showServices();
        showMap();
        showTimer();
    }

    private void showIcons()
    {
        try
        {
            Circle henCircle = new Circle(35 , 42 , 30);
            henCircle.setFill(Color.rgb(250 , 0 ,30));
            Image henIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\chicken.png"),
                    60 , 60 , false , true);
            ImageView henIconView = new ImageView(henIcon);
            henIconView.setX(5);
            henIconView.setY(8);

            Circle cowCircle = new Circle(100 , 42 , 30);
            cowCircle.setFill(Color.rgb(96 , 96 ,96));
            group.getChildren().addAll();
            Image cowIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\cow.png"),
                    50 , 50 , false , true);
            ImageView cowIconView = new ImageView(cowIcon);
            cowIconView.setX(70);
            cowIconView.setY(13);

            Circle sheepCircle = new Circle(175 , 42 , 30);
            sheepCircle.setFill(Color.rgb(225 , 153 ,224));
            Image sheepIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\sheep.png"),
                    52, 52 , false , true);
            ImageView sheepIconView = new ImageView(sheepIcon);
            sheepIconView.setX(146);
            sheepIconView.setY(10);

            Image menuIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\menuButton.png"),
                    77, 73, false, true);
            ImageView menuView = new ImageView(menuIcon);
            menuView.setX(30);
            menuView.setY(HEIGHT - 100);
            menuView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    showMenu();
                }
            });

            group.getChildren().addAll(henCircle,henIconView,cowCircle,cowIconView,sheepCircle,sheepIconView,menuView);

        }
        catch ( Exception e ){}
    }

    private void showBackground()
    {
        try
        {
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\map.png"), Menu.WIDTH,
                    Menu.HEIGHT, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setX(0);
            backgroundView.setY(0);
            group.getChildren().addAll(backgroundView);
        }
        catch ( Exception e ){}
    }

    private void showWorkshops()
    {
        for(Workshop w : farm.getWorkshops())
            if (w != null)
            {
                ImageView imageView = new ImageView(fixedWorkshops.get(w.getWorkShopName()));
                show(imageView, w);
            }
    }

    private void showServices()
    {
        ImageView imageView = new ImageView(fixedWell);
        show(imageView , farm.getWell());
        imageView = new ImageView(fixedTruck);
        show(imageView , farm.getTruck());
        imageView = new ImageView(fixedHelicopter);
        show(imageView , farm.getHelicopter());
        //todo show(wareHouse)
    }

    private void showMap()
    {
        ImageView imageView = null;
        for(int j = 0; j < farm.getMapLength(); j++)
            for (int i = 0; i < farm.getMapWidth(); i++)
            {
                int numberOfGrass = 0;
                ArrayList<Entity> stuffs = farm.getMap().getCells()[j][i].getStuffs();
                for (Entity e : stuffs)
                {
                    if(e instanceof Animal)
                        imageView = new ImageView(animalsFixed.get(((Animal) e).getName()));
                    else if(e instanceof Item)
                        imageView = new ImageView(items.get(((Item) e).getKind()));
                    else if(e instanceof Grass)
                    {
                        numberOfGrass ++;
                        if (numberOfGrass <= 3)
                            imageView = new ImageView(grass[numberOfGrass - 1]);
                        else
                            imageView = new ImageView(grass[3]);
                    }
                    show(imageView, e);
                }
            }
    }

    private void show(ImageView iView, Entity e)
    {
        iView.setTranslateX(e.getX());
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(e.getY()*3) , iView);
        translateTransition.setToY(e.getY());
        group.getChildren().add(iView);
        translateTransition.play();
    }

    private void showTimer()
    {
        timerLabel = new Label();
        timerLabel.relocate(WIDTH - 100, HEIGHT - 80);
        timerLabel.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,16));
        try
        {
            Image timerImage = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\timer.png"),
                    150, 79, false, true);
            ImageView timerView = new ImageView(timerImage);
            timerView.setX(WIDTH - 150);
            timerView.setY(HEIGHT - 100);
            group.getChildren().addAll(timerView,timerLabel);
            new AnimationTimer()
            {
                @Override
                public void handle(long now)
                {
                    if (lastTime == 0)
                        lastTime = now;
                    if (now > lastTime + second )
                    {
                        lastTime = now;
                        gameTime += 1;
                        String time = "";
                        if( gameTime / 3600 < 10 )
                            time += "0";
                        time += Long.toString(gameTime / 3600) + ":";
                        if( ( gameTime % 3600 ) / 60  < 10 )
                            time += "0";
                        time += Long.toString(( gameTime % 3600 ) / 60) + ":";
                        if( gameTime % 60 < 10 )
                            time += "0";
                        time += Long.toString(gameTime % 60 );
                        timerLabel.setText(time);
                    }
                }
            }.start();
        }
        catch ( Exception e )
        {

        }

    }

    private void showMenu()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,WIDTH,HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image menuBackground = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\gameMenuBackground.png")
                    , 300, 480, false, true);
            ImageView menuBackgroundView = new ImageView(menuBackground);
            menuBackgroundView.setY(HEIGHT / 2 - 240);
            menuBackgroundView.setX(WIDTH / 2 - 150);

            ImageView continueView = insertContinue();
            ImageView mainMenuView = insertMainMenu();
            ImageView restartView = insertRestart();
            ImageView levelsView = insertBackToLevels();
            ImageView optionsView = insertOptions();

            continueView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    group.getChildren().removeAll(rectangle,menuBackgroundView,continueView,mainMenuView,restartView,levelsView,optionsView);
                }
            });

            mainMenuView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    goToMainMenuHandler();
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
                    changeLevelHandler();
                }
            });

            optionsView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    //TODO show Options
                }
            });

            group.getChildren().addAll(rectangle,menuBackgroundView,continueView,mainMenuView,restartView,levelsView,optionsView);
        }
        catch ( IOException e ){}
    }

    private void loadImages()
    {
        try
        {
            loadImagesOfItems();
            loadImagesOfWorkshops();
            loadImageOfServices();
            loadImagesOfGrass();
            loadImagesOfAnimals();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    private void loadImagesOfAnimals()
    {
        try
        {
            Image image;
            for (String s : Constants.ANIMAL)
            {
                if (s.equals("Hen") || s.equals("Cow") || s.equals("Sheep"))
                {
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\" + s + "\\" +
                            "death" + ".png"),
                            50, 50, false, true);
                    animalsDeath.put(s.toLowerCase(), image);
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                            "eat"+".png"),
                            50, 50, false, true);
                    domesticEat.put(s.toLowerCase(), image);
                }
                if (s.equals("Bear") || s.equals("Lion"))
                {
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\" + s + "\\" +
                            "caged" + ".png"),
                            50, 50, false, true);
                    wildCaged.put(s.toLowerCase(), image);
                }
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "fixed"+".png"),
                        50, 50, false, true);
                animalsFixed.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "down"+".png"),
                        50, 50, false, true);
                animalsDown.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "down_left"+".png"),
                        50, 50, false, true);
                animalsDownLeft.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "down_right"+".png"),
                        50, 50, false, true);
                animalsDownRight.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "right"+".png"),
                        50, 50, false, true);
                animalsRight.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "left"+".png"),
                        50, 50, false, true);
                animalsLeft.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "up"+".png"),
                        50, 50, false, true);
                animalsUp.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "up_right"+".png"),
                        50, 50, false, true);
                animalsUpRight.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "up_left"+".png"),
                        50, 50, false, true);
                animalsUpLeft.put(s.toLowerCase(),image);
            }
        }
        catch ( Exception e ){}
    }

    private void loadImagesOfGrass()
    {
        try
        {
            for (int i = 0; i < 4; i++)
                grass[i] = new Image(new FileInputStream("src\\Resources\\Graphic\\Grass\\grass"+
                        Integer.toString(i+1)+".png"),
                        50, 50, false, true);
        }
        catch ( Exception e ){}
    }

    private void loadImagesOfItems()
    {
        try
        {
            Image image;
            for (String item : Constants.ITEM_NAMES)
            {
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Products\\"+item+".png"),
                        50, 50, false, true);
                items.put(item, image);
            }
        }
        catch ( Exception e ){}
    }

    private void loadImagesOfWorkshops()
    {
        try
        {
            Image image;
            for (Workshop w : farm.getWorkshops())
            {
                if (w != null)
                {
                    String name = w instanceof CustomFactory? "customFactory" : w.getWorkShopName();
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Workshops\\" + name + "\\" + "fixed"
                            + Integer.toString(w.getLevel()) + ".png"),
                            200, 200, false, true);
                    fixedWorkshops.put(w.getWorkShopName(), image);
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Workshops\\" + name + "\\" + "moving"
                            + Integer.toString(w.getLevel()) + ".png"),
                            200, 200, false, true);
                    movingWorkshops.put(w.getWorkShopName(), image);
                }
            }
        }
        catch ( Exception e ){}
    }

    private void loadImageOfServices()
    {
        try
        {
            fixedTruck = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"fixed"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            leftTruck = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"left"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            rightTruck = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"right"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            fixedHelicopter = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"fixed"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            leftHelicopter = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"left"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            rightHelicopter = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"right"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            fixedWell = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\"+"fixed"
                    +Integer.toString(farm.getWell().getLevel()) +".png"),
                    50, 50, false, true);
            movingWell = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\"+"moving"
                    +Integer.toString(farm.getWell().getLevel()) +".png"),
                    50, 50, false, true);
            cage = new Image(new FileInputStream("src\\Resources\\Graphic\\Cages\\cage.png"),
                    50, 50, false, true);
        }
        catch ( Exception e ){}
    }

    private ImageView insertContinue()
    {
        try
        {
            Image continueButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\continueButton.png")
                    , 150, 60, false, true);
            ImageView continueView = new ImageView(continueButton);
            continueView.setY(HEIGHT / 2 - 210);
            continueView.setX(WIDTH / 2 - 75);
            return continueView;
        }
        catch ( Exception e ){}
        return null;
    }

    private ImageView insertMainMenu()
    {
        try
        {
            Image menuButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\gameMainMenuButton.png")
                    , 150, 60, false, true);
            ImageView menuView = new ImageView(menuButton);
            menuView.setY(HEIGHT / 2 - 120);
            menuView.setX(WIDTH / 2 - 75);
            return menuView;
        }
        catch ( Exception e ){}
        return null;
    }

    private ImageView insertRestart()
    {
        try
        {
            Image restartButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\restartButton.png")
                    , 150, 60, false, true);
            ImageView restartView = new ImageView(restartButton);
            restartView.setY(HEIGHT / 2 - 30);
            restartView.setX(WIDTH / 2 - 75);
            return restartView;
        }
        catch ( Exception e ){}
        return null;
    }

    private ImageView insertBackToLevels()
    {
        try
        {
            Image levelsButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\levelsButton.png")
                    , 150, 60, false, true);
            ImageView levelView = new ImageView(levelsButton);
            levelView.setY(HEIGHT / 2 + 60);
            levelView.setX(WIDTH / 2 - 75);
            return levelView;
        }
        catch ( Exception e ){}
        return null;
    }

    private ImageView insertOptions()
    {
        try
        {
            Image options = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\optionsButton.png")
                    , 150, 60, false, true);
            ImageView optionsView = new ImageView(options);
            optionsView.setY(HEIGHT / 2 + 150);
            optionsView.setX(WIDTH / 2 - 75);
            return optionsView;
        }
        catch ( Exception e ){}
        return null;
    }

    private void goToMainMenuHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,WIDTH,HEIGHT);
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
                    //TODO save game!
                    stage.setScene(menu.getScene());
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
                    group.getChildren().removeAll(rectangle,quitMessageView,yesView,noView);
                }
            });

            group.getChildren().addAll(rectangle,quitMessageView,yesView,noView);
        }
        catch ( Exception e ){}
    }

    private void restartHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,WIDTH,HEIGHT);
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
                    //TODO Restart Game
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
                    group.getChildren().removeAll(rectangle,restartMessageView,yesView,noView);
                }
            });

            group.getChildren().addAll(rectangle,restartMessageView,yesView,noView);
        }
        catch ( Exception e ){}
    }

    private void changeLevelHandler()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0,WIDTH,HEIGHT);
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
                    //TODO Show Levels & ...
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
                    group.getChildren().removeAll(rectangle,quitMessageView,yesView,noView);
                }
            });

            group.getChildren().addAll(rectangle,quitMessageView,yesView,noView);
        }
        catch ( Exception e ){}
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
