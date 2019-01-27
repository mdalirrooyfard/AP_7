package Controller;

import Model.Constants;
import Model.Farm;
import Model.Player;
import Model.Workshops.CustomFactory;
import Model.Workshops.Workshop;
import View.Graphic.Menu;
import com.gilecode.yagson.YaGson;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Loader
{
    private ConcurrentHashMap<String, Image> fixedWorkshops = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> movingWorkshops = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsLeft = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsRight = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsUp = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsDown = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsDeath = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> domesticEat = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsDownLeft = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsDownRight = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsUpLeft = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsUpRight = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> animalsFixed = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> wildCaged = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> items = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Image> wareHouseItems = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, ImageView> fixedWorkShopsImageViews = new ConcurrentHashMap<>();
    private ImageView movingCakeBakery , movingCookieBakery , movingEggPowderPlant , movingSewingFactory , movingSpinnery ,
            movingWeavingFactory , movingCustomFactory , fixedWell , movingWell ,fixedHelicopter , leftHelicopter ,
            rightHelicopter , fixedTruck , leftTruck , rightTruck , map , wareHouse;
    private ImageView[] wildAnimals = new ImageView[2];
    private Image cage;
    private Image upgradeButton;
    private Image[] grass = new Image[4];
    private ConcurrentHashMap<String, Integer[]> widthAndHeight = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer[]> colsAndRows = new ConcurrentHashMap<>();
    private Vector<ImageView> currentEntities = new Vector<>();

    public void setMovingCakeBakery(ImageView movingCakeBakery)
    {
        this.movingCakeBakery = movingCakeBakery;
    }

    public void setMovingCookieBakery(ImageView movingCookieBakery)
    {
        this.movingCookieBakery = movingCookieBakery;
    }

    public void setMovingEggPowderPlant(ImageView movingEggPowderPlant)
    {
        this.movingEggPowderPlant = movingEggPowderPlant;
    }

    public void setMovingSewingFactory(ImageView movingSewingFactory)
    {
        this.movingSewingFactory = movingSewingFactory;
    }

    public void setMovingSpinnery(ImageView movingSpinnery)
    {
        this.movingSpinnery = movingSpinnery;
    }

    public void setMovingWeavingFactory(ImageView movingWeavingFactory)
    {
        this.movingWeavingFactory = movingWeavingFactory;
    }

    public void setMovingCustomFactory(ImageView movingCustomFactory)
    {
        this.movingCustomFactory = movingCustomFactory;
    }

    public void setWildAnimal( int i , Image wildAnimals)
    {
        this.wildAnimals[i].setImage(wildAnimals);
    }

    public void setNullWildAnimals(int i, ImageView wildAnimals){
        this.wildAnimals[i] = wildAnimals;
    }

    public ConcurrentHashMap<String, Image> getFixedWorkshops()
    {
        return fixedWorkshops;
    }

    public ConcurrentHashMap<String, Image> getMovingWorkshops()
    {
        return movingWorkshops;
    }

    public ConcurrentHashMap<String, Image> getAnimalsLeft()
    {
        return animalsLeft;
    }

    public ConcurrentHashMap<String, Image> getAnimalsRight()
    {
        return animalsRight;
    }

    public ConcurrentHashMap<String, Image> getAnimalsUp()
    {
        return animalsUp;
    }

    public ConcurrentHashMap<String, Image> getAnimalsDown()
    {
        return animalsDown;
    }

    public ConcurrentHashMap<String, Image> getAnimalsDeath()
    {
        return animalsDeath;
    }

    public ConcurrentHashMap<String, Image> getDomesticEat()
    {
        return domesticEat;
    }

    public ConcurrentHashMap<String, Image> getAnimalsDownLeft()
    {
        return animalsDownLeft;
    }

    public ConcurrentHashMap<String, Image> getAnimalsDownRight()
    {
        return animalsDownRight;
    }

    public ConcurrentHashMap<String, Image> getAnimalsUpLeft()
    {
        return animalsUpLeft;
    }

    public ConcurrentHashMap<String, Image> getAnimalsUpRight()
    {
        return animalsUpRight;
    }

    public ConcurrentHashMap<String, Image> getAnimalsFixed()
    {
        return animalsFixed;
    }

    public ConcurrentHashMap<String, Image> getWildCaged()
    {
        return wildCaged;
    }

    public ConcurrentHashMap<String, Image> getItems()
    {
        return items;
    }

    public ConcurrentHashMap<String, Image> getWareHouseItems()
    {
        return wareHouseItems;
    }

    public ConcurrentHashMap<String, ImageView> getFixedWorkShopsImageViews()
    {
        return fixedWorkShopsImageViews;
    }

    public ImageView getMovingCakeBakery()
    {
        return movingCakeBakery;
    }

    public ImageView getMovingCookieBakery()
    {
        return movingCookieBakery;
    }

    public ImageView getMovingEggPowderPlant()
    {
        return movingEggPowderPlant;
    }

    public ImageView getMovingSewingFactory()
    {
        return movingSewingFactory;
    }

    public ImageView getMovingSpinnery()
    {
        return movingSpinnery;
    }

    public ImageView getMovingWeavingFactory()
    {
        return movingWeavingFactory;
    }

    public ImageView getMovingCustomFactory()
    {
        return movingCustomFactory;
    }

    public ImageView getFixedWell()
    {
        return fixedWell;
    }

    public ImageView getMovingWell()
    {
        return movingWell;
    }

    public ImageView getFixedHelicopter()
    {
        return fixedHelicopter;
    }

    public ImageView getLeftHelicopter()
    {
        return leftHelicopter;
    }

    public ImageView getRightHelicopter()
    {
        return rightHelicopter;
    }

    public ImageView getFixedTruck()
    {
        return fixedTruck;
    }

    public ImageView getLeftTruck()
    {
        return leftTruck;
    }

    public ImageView getRightTruck()
    {
        return rightTruck;
    }

    public ImageView getMap()
    {
        return map;
    }

    public ImageView getWareHouse()
    {
        return wareHouse;
    }

    public ImageView[] getWildAnimals()
    {
        return wildAnimals;
    }

    public Image getCage()
    {
        return cage;
    }

    public Image getUpgradeButton()
    {
        return upgradeButton;
    }

    public Image[] getGrass()
    {
        return grass;
    }

    public ConcurrentHashMap<String, Integer[]> getWidthAndHeight()
    {
        return widthAndHeight;
    }

    public ConcurrentHashMap<String, Integer[]> getColsAndRows()
    {
        return colsAndRows;
    }

    public Vector<ImageView> getCurrentEntities()
    {
        return currentEntities;
    }

    public void loadImages(Farm farm)
    {
        loadImageOfMap();
        loadImagesOfItems();
        loadImagesOfWorkshops(farm.getWorkshops());
        loadImageOfServices(farm);
        loadImagesOfGrass();
        loadImagesOfAnimals();
    }

    private void loadImageOfMap()
    {
        try
        {
            map = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\map.png"), Menu.WIDTH, Menu.HEIGHT,
                    false, true));
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }

    private void loadImagesOfAnimals()
    {
        try
        {
            Image image;
            for (String s : Constants.ANIMAL)
            {
                if (s.equals("hen") || s.equals("cow") || s.equals("sheep"))
                {
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\" + s + "\\" +
                            "death" + ".png"));
                    animalsDeath.put(s.toLowerCase(), image);
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Animals\\"+s+"\\"+
                            "eat"+".png"));
                    domesticEat.put(s.toLowerCase(), image);
                }
                if (s.equals("bear") || s.equals("lion"))
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
        catch ( Exception e ){ e.printStackTrace(); }
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
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void loadImagesOfItems()
    {
        try
        {
            Image image;
            for (String item : Constants.ITEM_NAMES)
            {
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\Products\\"+item+".png"),
                        Constants.ITEM_SIZE, Constants.ITEM_SIZE, false, true);
                items.put(item, image);
                image = new Image(new FileInputStream("src\\Resources\\Graphic\\wareHouseItems\\"
                        +item+".png"));
                wareHouseItems.put(item, image);
            }
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void loadImagesOfWorkshops( Workshop[] workshops )
    {
        try
        {
            Image image;
            for (Workshop w : workshops)
            {
                if (w != null)
                {
                    String name = w instanceof CustomFactory ? "customFactory" : w.getWorkShopName();
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Workshops\\" + name + "\\" + "fixed"
                            + Integer.toString(w.getLevel()) + ".png"),
                            200, 200, false, true);
                    fixedWorkshops.put(w.getWorkShopName(), image);
                    image = new Image(new FileInputStream("src\\Resources\\Graphic\\Workshops\\" + name + "\\" + "moving"
                            + Integer.toString(w.getLevel()) + ".png"),
                            800, 800, false, true);
                    movingWorkshops.put(w.getWorkShopName(), image);
                }
            }
            loadImageViewsOfMovingWorkShops();
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    private void loadImageViewsOfMovingWorkShops()
    {
        movingCakeBakery = new ImageView(movingWorkshops.get("cakeBakery"));
        movingCookieBakery = new ImageView(movingWorkshops.get("cookieBakery"));
        movingEggPowderPlant = new ImageView(movingWorkshops.get("eggPowderPlant"));
        movingSewingFactory = new ImageView(movingWorkshops.get("sewingFactory"));
        movingSpinnery = new ImageView(movingWorkshops.get("spinnery"));
        movingWeavingFactory = new ImageView(movingWorkshops.get("weavingFactory"));
        //todo custom
    }

    private void loadImageOfServices(Farm farm)
    {
        try
        {
            fixedTruck = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"fixed"
                    +farm.getTruck().getLevel() +".png"), 200, 200, false, true));
            leftTruck = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"left"
                    +farm.getTruck().getLevel() +".png"), 50, 50, false, true));
            rightTruck = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Truck\\"+"right"
                    +farm.getTruck().getLevel() +".png"), 50, 50, false, true));
            fixedHelicopter = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"fixed"
                    +farm.getTruck().getLevel() +".png"), 220, 220, false, true));
            leftHelicopter = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"left"
                    +farm.getTruck().getLevel() +".png"), 144, 96, false, true));
            rightHelicopter = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Helicopter\\"+"right"
                    +farm.getTruck().getLevel() +".png"), 144, 96, false, true));
            fixedWell = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\" + "fixed"
                    + farm.getWell().getLevel() + ".png"), 200, 200, false, true));
            movingWell = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Well\\" + "moving"
                    + farm.getWell().getLevel() + ".png"), 800, 800, false, true));
            cage = new Image(new FileInputStream("src\\Resources\\Graphic\\Cages\\cage.png"),
                    50, 50, false, true);
            wareHouse = new ImageView(new Image(new FileInputStream("src\\Resources\\Graphic\\Service\\Depot\\" +
                    farm.getWareHouse().getLevel() +".png") , 250 , 150 , false , true));
            upgradeButton = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\upgradeButton.png"));
        }
        catch ( Exception e ){ e.printStackTrace(); }
    }

    public void loadSize()
    {
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

    public Vector<Player> loadPlayers()
    {
        Vector<Player> players = null;
        try( InputStream inputStream = new FileInputStream("src\\Resources\\Players.txt") )
        {
            Scanner scanner = new Scanner(inputStream);
            YaGson yaGson = new YaGson();
            if(scanner.hasNext())
            {
                String savedPlayers = scanner.nextLine();
                players = yaGson.fromJson(savedPlayers,Vector.class);
            }
        }
        catch ( Exception e ){ e.printStackTrace(); }
        return players;
    }
}
