package View;

import Model.Animals.Animal;
import Model.*;
import Model.Items.Item;
import Model.Workshops.CustomFactory;
import Model.Workshops.Workshop;
import View.Graphic.Menu;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
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
    private ArrayList<ImageView> currentEntities = new ArrayList<>();

    private HashMap<String, Integer[]> widthAndHeight = new HashMap<>();
    private HashMap<String, Integer[]> colsAndRows = new HashMap<>();

    public Scene getScene()
    {
        return scene;
    }

    public Group getGroup()
    {
        return group;
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
        loadSize();
        loadImages();
        showWorkshops();
        showServices();
        showMap();
        showFixedAnimal();
        //showTimer();
    }

    private void loadSize(){
        widthAndHeight.put("bear", new Integer[]{480, 648});
        colsAndRows.put("bear", new Integer[]{4, 6});
        widthAndHeight.put("cat", new Integer[]{288, 480});
        colsAndRows.put("cat", new Integer[]{4, 6});
        widthAndHeight.put("cow", new Integer[]{536, 864});
        colsAndRows.put("cow", new Integer[]{3, 8});
        widthAndHeight.put("dog", new Integer[]{648, 334});
        colsAndRows.put("dog", new Integer[]{6, 4});
        widthAndHeight.put("hen", new Integer[]{480, 336});
        colsAndRows.put("hen", new Integer[]{6, 4});
        widthAndHeight.put("lion", new Integer[]{824, 540});
        colsAndRows.put("lion", new Integer[]{6, 4});
        widthAndHeight.put("sheep", new Integer[]{780, 384});
        colsAndRows.put("sheep", new Integer[]{6, 4});
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
        ImageView imageView;
        imageView = new ImageView(fixedTruck);
        show(imageView , farm.getTruck());
        imageView = new ImageView(fixedHelicopter);
        show(imageView , farm.getHelicopter());
        //todo show(wareHouse)
    }

    public void showMap()
    {
        group.getChildren().removeAll(currentEntities);
        currentEntities.clear();
        for(int j = 0; j < farm.getMapLength(); j++)
            for (int i = 0; i < farm.getMapWidth(); i++)
            {
                int numberOfGrass = 0;
                ArrayList<Entity> stuffs = farm.getMap().getCells()[j][i].getStuffs();
                for (Entity e : stuffs)
                {
                    ImageView imageView = null;
                    if(e instanceof Item)
                        imageView = new ImageView(items.get(((Item) e).getKind()));
                    else if(e instanceof Grass)
                    {
                        numberOfGrass ++;
                        if (numberOfGrass <= 3)
                            imageView = new ImageView(grass[numberOfGrass - 1]);
                        else
                            imageView = new ImageView(grass[3]);
                    }
                    if (imageView != null) {
                        currentEntities.add(imageView);
                        showFixed(imageView, e);
                    }
                }
            }
    }

    public void showFixed(ImageView iView, Entity e){
        iView.setX(e.getShowX());
        iView.setY(e.getShowY());
        group.getChildren().add(iView);
    }

    public void showFixedAnimal(){
        ImageView imageView;
        for (Entity e : farm.getStuffs()) {
            if(e instanceof Animal) {
                imageView = new ImageView(animalsFixed.get(((Animal) e).getName()));
                currentEntities.add(imageView);
                show(imageView, e);
            }
        }
    }

    public void showMovingAnimals(){
        ImageView imageView = null;
        ArrayList<Entity> stuffs = farm.getStuffs();
        for(Entity e : stuffs){
            if(e instanceof Animal){
                DIRECTION direction = ((Animal) e).getDirection();
                switch (direction){
                    case UP:
                        imageView = new ImageView(animalsUp.get(((Animal) e).getName()));
                        break;
                    case RIGHT:
                        imageView = new ImageView(animalsRight.get(((Animal) e).getName()));
                        break;
                    case LEFT:
                        imageView = new ImageView(animalsLeft.get(((Animal) e).getName()));
                        break;
                    case DOWN:
                        imageView = new ImageView(animalsDown.get(((Animal) e).getName()));
                        break;
                    case UP_LEFT:
                        imageView = new ImageView(animalsUpLeft.get(((Animal) e).getName()));
                        break;
                    case DOWN_LEFT:
                        imageView = new ImageView(animalsDownLeft.get(((Animal) e).getName()));
                        break;
                    case DOWN_RIGHT:
                        imageView = new ImageView(animalsDownRight.get(((Animal) e).getName()));
                        break;
                    case UP_RIGHT:
                        imageView = new ImageView(animalsUpRight.get(((Animal) e).getName()));
                        break;
                    case NONE:
                        imageView = new ImageView(domesticEat.get(((Animal) e).getName()));
                        break;
                }
                imageView.setFitWidth(Constants.ANIMAL_SIZE);
                imageView.setFitHeight(Constants.ANIMAL_SIZE);
                if (direction != DIRECTION.NONE) {
                    imageView.setX(((Animal) e).getPreviousX());
                    imageView.setY(((Animal) e).getPreviousY());
                    imageView.setFitWidth(Constants.ANIMAL_SIZE);
                    imageView.setFitHeight(Constants.ANIMAL_SIZE);
                    group.getChildren().add(imageView);
                    currentEntities.add(imageView);
                    AnimationTimer animationTimer = new ImageViewSprite(
                            imageView,1,false, 6, 4, 24,
                            (int)imageView.getImage().getWidth() / 6,
                            (int)imageView.getImage().getHeight() / 4, 24
                    );
                    animationTimer.start();
                    MoveTransition pathTransition = new MoveTransition(imageView, ((Animal) e).getPreviousX(),
                            ((Animal) e).getPreviousY(), e.getShowX(), e.getShowY(), 3000);
                    pathTransition.setAutoReverse(false);
                    pathTransition.setCycleCount(1);
                    pathTransition.play();

                }
                else{
                    imageView.setX(e.getShowX());
                    imageView.setY(e.getShowY());
                    group.getChildren().add(imageView);
                    currentEntities.add(imageView);
                    final AnimationTimer animationTimer = new ImageViewSprite(
                            imageView,1 ,false,4, 6, 24, 120, 108, 24
                    );
                    animationTimer.start();
                }
            }
        }
    }

    private void show(ImageView iView, Entity e)
    {
        iView.setTranslateX(e.getShowX());
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(e.getShowY()*3) , iView);
        translateTransition.setToY(e.getShowY());
        group.getChildren().add(iView);
        translateTransition.play();
    }

    public void loadImages()
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
                            "death" + ".png"));
                    animalsDeath.put(s.toLowerCase(), image);
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                            "eat"+".png"));
                    domesticEat.put(s.toLowerCase(), image);
                }
                if (s.equals("Bear") || s.equals("Lion"))
                {
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\" + s + "\\" +
                            "caged" + ".png"));
                    wildCaged.put(s.toLowerCase(), image);
                }
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "fixed"+".png"));
                animalsFixed.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "down"+".png"));
                animalsDown.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "down_left"+".png"));
                animalsDownLeft.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "down_right"+".png"));
                animalsDownRight.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "right"+".png"));
                animalsRight.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "left"+".png"));
                animalsLeft.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "up"+".png"));
                animalsUp.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "up_right"+".png"));
                animalsUpRight.put(s.toLowerCase(), image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "up_left"+".png"));
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
                    200, 200, false, true);
            leftTruck = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"left"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            rightTruck = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"right"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            fixedHelicopter = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"fixed"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    220, 220, false, true);
            leftHelicopter = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"left"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            rightHelicopter = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"right"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            cage = new Image(new FileInputStream("src\\Resources\\Graphic\\Cages\\cage.png"),
                    50, 50, false, true);
        }
        catch ( Exception e ){}
    }

}
