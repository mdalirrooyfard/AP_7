package View;

import Model.*;
import Model.Animals.Animal;
import Model.Items.Item;
import Model.Workshops.CustomFactory;
import Model.Workshops.Workshop;
import View.Graphic.Menu;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class View
{
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

    public Scene getScene()
    {
        return scene;
    }

    public View(Stage stage)
    {
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
    }

    private void showIcons()
    {
        try
        {
            Circle henCircle = new Circle(35 , 42 , 30);
            henCircle.setFill(Color.rgb(250 , 0 ,30));
            group.getChildren().addAll(henCircle);
            Image henIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\chicken.png"),
                    60 , 60 , false , true);
            ImageView henIconView = new ImageView(henIcon);
            henIconView.setX(5);
            henIconView.setY(8);
            group.getChildren().addAll(henIconView);
            Circle cowCircle = new Circle(100 , 42 , 30);
            cowCircle.setFill(Color.rgb(96 , 96 ,96));
            group.getChildren().addAll(cowCircle);
            Image cowIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\cow.png"),
                    50 , 50 , false , true);
            ImageView cowIconView = new ImageView(cowIcon);
            cowIconView.setX(70);
            cowIconView.setY(13);
            group.getChildren().addAll(cowIconView);
            Circle sheepCircle = new Circle(175 , 42 , 30);
            sheepCircle.setFill(Color.rgb(225 , 153 ,224));
            group.getChildren().addAll(sheepCircle);
            Image sheepIcon = new Image(new FileInputStream("src\\Resources\\Graphic\\UI\\Icons\\Products\\sheep.png"),
                    52, 52 , false , true);
            ImageView sheepIconView = new ImageView(sheepIcon);
            sheepIconView.setX(146);
            sheepIconView.setY(10);
            group.getChildren().addAll(sheepIconView);
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
