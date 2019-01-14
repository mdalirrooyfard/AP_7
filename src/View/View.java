package View;

import Model.Constants;
import Model.Farm;
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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class View
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);
    private Stage stage;
    private HashMap<String, ImageView> fixedWorkshops = new HashMap<>();
    private HashMap<String, ImageView> movingWorkshops = new HashMap<>();
    private HashMap<String, ImageView> animalsLeft = new HashMap<>();
    private HashMap<String, ImageView> animalsRight = new HashMap<>();
    private HashMap<String, ImageView> animalsUp = new HashMap<>();
    private HashMap<String, ImageView> animalsDown = new HashMap<>();
    private HashMap<String, ImageView> animalsDeath = new HashMap<>();
    private HashMap<String, ImageView> domesticEat = new HashMap<>();
    private HashMap<String, ImageView> animalsDownLeft = new HashMap<>();
    private HashMap<String, ImageView> animalsDownRight = new HashMap<>();
    private HashMap<String, ImageView> animalsUpLeft = new HashMap<>();
    private HashMap<String, ImageView> animalsUpRight = new HashMap<>();
    private HashMap<String, ImageView> wildCaged = new HashMap<>();
    private HashMap<String, ImageView> items = new HashMap<>();
    private ImageView movingWell;
    private ImageView fixedWell;
    private ImageView fixedHelicopter;
    private ImageView leftHelicopter;
    private ImageView rightHelicopter;
    private ImageView fixedTruck;
    private ImageView leftTruck;
    private ImageView rightTruck;
    private ImageView cage;
    private Farm farm;
    private ImageView imageView;



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
        try
        {
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\map.png"), Menu.WIDTH,
                    Menu.HEIGHT, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setX(0);
            backgroundView.setY(0);
            group.getChildren().addAll(backgroundView);
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

            for(Workshop w : farm.getWorkshops()){
                if(w != null){
                    Path path = new Path(new MoveTo(0 , 0) , new LineTo(0 , w.getY()*100));
                    path.setVisible(false);
                    group.getChildren().addAll(path);
                    imageView = fixedWorkshops.get(w.getWorkShopName());
                    imageView.setTranslateX(w.getX());
                    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(w.getY()*3) , imageView);
                    translateTransition.setToY(w.getY());
                    group.getChildren().add(imageView);
                    translateTransition.play();
                }
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadImages(){
        try{
            Image image;
            //items
            for (String item : Constants.ITEM_NAMES){
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Products\\"+item+".png"),
                        50, 50, false, true);
                items.put(item, new ImageView(image));
            }

            //workshops
            for (Workshop w : farm.getWorkshops()){
                if (w != null) {
                    String name = w instanceof CustomFactory? "customFactory" : w.getWorkShopName();
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Workshops\\" + name + "\\" + "fixed"
                            + Integer.toString(w.getLevel()) + ".png"),
                            200, 200, false, true);
                    fixedWorkshops.put(w.getWorkShopName(), new ImageView(image));
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Workshops\\" + name + "\\" + "moving"
                            + Integer.toString(w.getLevel()) + ".png"),
                            200, 200, false, true);
                    movingWorkshops.put(w.getWorkShopName(), new ImageView(image));
                }
            }

            //well
            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\"+"fixed"
                    +Integer.toString(farm.getWell().getLevel()) +".png"),
                    50, 50, false, true);
            fixedWell = new ImageView(image);
            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\"+"moving"
                    +Integer.toString(farm.getWell().getLevel()) +".png"),
                    50, 50, false, true);
            movingWell =new ImageView(image);

            //truck
            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"fixed"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            fixedTruck = new ImageView(image);
            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"left"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            leftTruck = new ImageView(image);
            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"right"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            rightTruck = new ImageView(image);

            //helicopter
            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"fixed"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            fixedHelicopter = new ImageView(image);
            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"left"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            leftHelicopter = new ImageView(image);
            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"right"
                    +Integer.toString(farm.getTruck().getLevel()) +".png"),
                    50, 50, false, true);
            rightHelicopter = new ImageView(image);

            //animals
            for (String s : Constants.ANIMAL){
                if (s.equals("Hen") || s.equals("Cow") || s.equals("Sheep")) {
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\" + s + "\\" +
                            "death" + ".png"),
                            50, 50, false, true);
                    animalsDeath.put(s.toLowerCase(), new ImageView(image));
                }
                if (s.equals("Bear") || s.equals("Lion")){
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\" + s + "\\" +
                            "caged" + ".png"),
                            50, 50, false, true);
                    wildCaged.put(s.toLowerCase(), new ImageView(image));
                }
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "down"+".png"),
                        50, 50, false, true);
                animalsDown.put(s.toLowerCase(), new ImageView(image));
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "down_left"+".png"),
                        50, 50, false, true);
                animalsDownLeft.put(s.toLowerCase(), new ImageView(image));
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "down_right"+".png"),
                        50, 50, false, true);
                animalsDownRight.put(s.toLowerCase(), new ImageView(image));
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "right"+".png"),
                        50, 50, false, true);
                animalsRight.put(s.toLowerCase(), new ImageView(image));
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "left"+".png"),
                        50, 50, false, true);
                animalsLeft.put(s.toLowerCase(), new ImageView(image));
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "up"+".png"),
                        50, 50, false, true);
                animalsUp.put(s.toLowerCase(), new ImageView(image));
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "up_right"+".png"),
                        50, 50, false, true);
                animalsUpRight.put(s.toLowerCase(), new ImageView(image));
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "up_left"+".png"),
                        50, 50, false, true);
                animalsUpLeft.put(s.toLowerCase(), new ImageView(image));
            }
            //domestic eat
            for (String s: Constants.DOMESTIC){
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                        "eat"+".png"),
                        50, 50, false, true);
                domesticEat.put(s.toLowerCase(), new ImageView(image));
            }
            image = new Image(new FileInputStream("src\\Resources\\Graphic\\Cages\\cage.png"),
                    50, 50, false, true);
            cage = new ImageView(image);


        }catch (Exception e){
            e.printStackTrace();
        }
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
