package Model;

import Model.Animals.Cat;
import Model.Animals.Dog;
import Model.Animals.Domestic.Cow;
import Model.Animals.Domestic.Domestic;
import Model.Animals.Domestic.Hen;
import Model.Animals.Domestic.Sheep;
import Model.Animals.Wild.Wild;
import Model.Items.Item;
import Model.Transportation.Helicopter;
import Model.Transportation.Truck;

import java.util.ArrayList;


public class Farm {
    private Map map;
    private int mapLength;
    private int mapWidth;
    private ArrayList<Entity> stuffs = new ArrayList<>();
    private double time;
    private Helicopter helicopter = new Helicopter();
    private Truck truck = new Truck();
    private WareHouse wareHouse = new WareHouse();
    private Well well = new Well();
    private int money;
    //TODO goals

    Farm(int length, int width){
        mapLength = length;
        mapWidth = width;
        map = new Map(length, width);
        time = 0;
    }

    public int makeRandomXAndY(int dim){
        double temp = Math.random() * dim;
        return (int)temp;
    }

    public void addHen(){
        Hen hen = new Hen(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(hen);
    }

    public void addCow(){
        Cow cow = new Cow(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(cow);
    }

    public void addSheep(){
        Sheep sheep = new Sheep(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(sheep);
    }

    public boolean plantGrass(double x, double y){
        if (well.getCurrentVolume() == 0)
            return false;
        well.setCurrentVolume(well.getCurrentVolume() - 1);
        int centerX = (int) Math.round(x);
        int centerY = (int) Math.round(y);
        stuffs.add(new Grass(centerX, centerY));
        if (centerX - 1 >= 0){
            stuffs.add(new Grass(centerX - 1, centerY));   //left
            if (centerY - 1 >= 0){
                stuffs.add(new Grass(centerX, centerY - 1)); //up
                stuffs.add(new Grass(centerX - 1, centerY - 1)); //up left
            }
            if (centerY + 1 <= mapLength - 1) {
                stuffs.add(new Grass(centerX, centerY + 1));   //down
                stuffs.add(new Grass(centerX - 1, centerY + 1)); //down left
            }
        }
        if (centerX + 1 <= mapWidth - 1){
            stuffs.add(new Grass(centerX + 1, centerY)); //right
            if (centerY - 1 >= 0)
                stuffs.add(new Grass(centerX + 1, centerY - 1)); //up right
            if (centerY + 1 <= mapLength - 1)
                stuffs.add(new Grass(centerX + 1, centerY + 1)); //down right
        }
        return true;
    }

    public void addDog(){
        Dog dog = new Dog(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(dog);
    }

    public void addCat(){
        Cat cat = new Cat(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(cat);
    }

    public void upgrade(String entityName){
        switch (entityName){
            case "cat":{
                for (Entity entity : stuffs)
                    if (entity instanceof Cat)
                        entity.upgrade();
                break;
            }
            case "well":{
                well.upgrade();
                break;
            }
            case "truck":{
                truck.upgrade();
                break;
            }
            case "helicopter":{
                helicopter.upgrade();
                break;
            }
            case "warehouse":{
                wareHouse.upgrade();
                break;
            }
            default:{ //workshops
                //TODO
            }
        }
    }

    public boolean pickUp(double x, double y){
        int currentX = (int) Math.round(x);
        int currentY = (int) Math.round(y);
        ArrayList<Entity> cellItems = map.getCells()[currentX][currentY].getStuffs();
        ArrayList<Entity> cellRemainItems = new ArrayList<>();
        boolean isEveryThingPickedUp = true;
        for (Entity entity : cellItems){
            if (entity instanceof Item || (entity instanceof Wild && ((Wild) entity).isInCage()))
                if (entity.getVolume() <= wareHouse.getVolume()){
                    wareHouse.setVolume(wareHouse.getVolume() - entity.getVolume());
                    stuffs.remove(entity);
                }
                else{
                    cellRemainItems.add(entity);
                    isEveryThingPickedUp = false;
                }
            else
                cellRemainItems.add(entity);
        }
        map.getCells()[currentX][currentY].update(cellRemainItems);
        return isEveryThingPickedUp;
    }

    public boolean putCage(double x, double y){
        int currentX = (int) Math.round(x);
        int currentY = (int) Math.round(y);
        if (!map.getCells()[currentX][currentY].status()[0])
            return false;
        ArrayList<Entity> cellItems = map.getCells()[currentX][currentY].getStuffs();
        for (Entity entity : cellItems)
            if (entity instanceof Wild)
                ((Wild) entity).setInCage(true);
        return true;
    }

    public boolean fullWell(){
        if (well.getCost() <= money){
            money -= well.getCost();
            well.setCurrentVolume(well.getVolume());
            return true;
        }
        return false;
    }

    public void increaseMoney(int amount){
        money += amount;
    }

    public void decreaseMoney(int amount){
        money -= amount;
    }

    public int getMoney(){
        return money;
    }

    public void checkCollision(){
        if (!map.isThereWild())
            return;
        for (int i = 0; i < mapLength; i++)
            for (int j = 0; j < mapWidth; j++){
                Cell cell = map.getCells()[i][j];
                if (cell.status()[0]) //has wild animal
                    if (cell.status()[4]) //has dog
                        killDogAndWild(i, j);
                    else if (cell.status()[1] || cell.status()[2])
                        killDomesticAndItems(i, j);
            }
    }

    public void killDogAndWild(int y, int x){
        ArrayList<Entity> entities = map.getCells()[y][x].getStuffs();
        for (Entity entity : entities)
            if (entity instanceof Dog || entity instanceof Wild)
                stuffs.remove(entity);
    }

    public void killDomesticAndItems(int y, int x){
        ArrayList<Entity> entities = map.getCells()[y][x].getStuffs();
        for (Entity entity : entities)
            if (entity instanceof Item || entity instanceof Domestic)
                stuffs.remove(entity);
    }


}
