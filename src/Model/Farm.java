package Model;

import Model.Animals.Animal;
import Model.Animals.Cat;
import Model.Animals.Dog;
import Model.Animals.Domestic.Cow;
import Model.Animals.Domestic.Domestic;
import Model.Animals.Domestic.Hen;
import Model.Animals.Domestic.Sheep;
import Model.Animals.Wild.Bear;
import Model.Animals.Wild.Lion;
import Model.Animals.Wild.Wild;
import Model.Items.Item;
import Model.Transportation.Helicopter;
import Model.Transportation.Truck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Farm {
    private Map map;
    private int mapLength;
    private int mapWidth;
    private ArrayList<Entity> stuffs = new ArrayList<>();
    private int time;
    private Helicopter helicopter = new Helicopter();
    private Truck truck = new Truck();
    private WareHouse wareHouse = new WareHouse();
    private Well well = new Well();
    private int money;
    private int shootWildAnimalTime = -1;
    private HashMap<String, Integer> goals = new HashMap<>();
    private HashMap<String, Integer> achievements = new HashMap<>();
    private boolean areCatsUpgraded = false;
    //TODO goals

    public Farm(int length, int width){
        mapLength = length;
        mapWidth = width;
        map = new Map(length, width);
        time = 0;
    }

    public int makeRandomXAndY(int dim){
        double temp = Math.random() * dim;
        return (int)temp;
    }

    public boolean addHen(){
        if (Constants.HEN_BUY_COST > money)
            return false;
        Hen hen = new Hen(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        decreaseMoney(hen.getBuyCost());
        stuffs.add(hen);
        updateAchievement("hen");
        return true;
    }

    public boolean addCow(){
        if (Constants.COW_BUY_COST > money)
            return false;
        Cow cow = new Cow(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        decreaseMoney(cow.getBuyCost());
        stuffs.add(cow);
        updateAchievement("cow");
        return true;
    }

    public boolean addSheep(){
        if (Constants.SHEEP_BUY_COST > money)
            return false;
        Sheep sheep = new Sheep(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        decreaseMoney(sheep.getBuyCost());
        stuffs.add(sheep);
        updateAchievement("sheep");
        return true;
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

    public boolean addDog(){
        if (Constants.DOG_BUY_COST > money)
            return false;
        Dog dog = new Dog(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        decreaseMoney(dog.getBuyCost());
        stuffs.add(dog);
        updateAchievement("dog");
        return true;
    }

    public boolean addCat(){
        if (Constants.COW_BUY_COST > money)
            return false;
        Cat cat = new Cat(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        decreaseMoney(cat.getBuyCost());
        stuffs.add(cat);
        updateAchievement("cat");
        return true;
    }

    public boolean upgrade(String entityName){
        switch (entityName){
            case "cat":{
                if (areCatsUpgraded)
                    return true;
                if (money < catCount() * Constants.CAT_BASE_UPGRADE_COST)
                    return false;
                for (Entity entity : stuffs)
                    if (entity instanceof Cat)
                        entity.upgrade();
                decreaseMoney(catCount() * Constants.CAT_BASE_UPGRADE_COST);
                areCatsUpgraded = true;
                break;
            }
            case "well":{
                if (money < well.getUpgradeCost())
                    return false;
                well.upgrade();
                decreaseMoney(well.getUpgradeCost());
                break;
            }
            case "truck":{
                if (money < truck.getUpgradeCost())
                    return false;
                truck.upgrade();
                decreaseMoney(truck.getUpgradeCost());
                break;
            }
            case "helicopter":{
                if (money < helicopter.getUpgradeCost())
                    return false;
                helicopter.upgrade();
                decreaseMoney(helicopter.getUpgradeCost());
                break;
            }
            case "warehouse":{
                if (money < wareHouse.getUpgradeCost())
                    return false;
                wareHouse.upgrade();
                decreaseMoney(wareHouse.getUpgradeCost());
                break;
            }
            default:{ //workshops
                //TODO upgrade workshops
            }
        }
        return true;
    }

    public boolean pickUp(double x, double y){
        int currentX = (int) Math.round(x);
        int currentY = (int) Math.round(y);
        ArrayList<Entity> cellItems = map.getCells()[currentY][currentX].getStuffs();
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
        map.getCells()[currentY][currentX].update(cellRemainItems);
        return isEveryThingPickedUp;
    }

    public boolean putCage(double x, double y){
        int currentX = (int) Math.round(x);
        int currentY = (int) Math.round(y);
        if (!map.getCells()[currentY][currentX].status()[0])
            return false;
        ArrayList<Entity> cellItems = map.getCells()[currentY][currentX].getStuffs();
        Iterator<Entity>  iterator = cellItems.iterator();
        int numberOfBears = 0;
        int numberOfLions = 0;
        while (iterator.hasNext()){
            Entity entity = iterator.next();
            if (entity instanceof Bear){
                iterator.remove();
                stuffs.remove(entity);
                numberOfBears ++;
            }
            else if (entity instanceof Lion){
                iterator.remove();
                stuffs.remove(entity);
                numberOfLions++;
            }
        }
        for (int i = 0; i < numberOfBears; i++){
            Item item = new Item(currentX, currentY, "cagedBear",
                    Constants.CAGED_BROWN_BEAR_SALE_COST, Constants.CAGED_BROWN_BEAR_BUY_COST);
            stuffs.add(item);
            cellItems.add(item);
        }
        for (int i = 0; i < numberOfLions; i++){
            Item item = new Item(currentX, currentY, "cagedLion",
                    Constants.CAGED_LION_SALE_COST, Constants.CAGED_LION_BUY_COST);
            stuffs.add(item);
            cellItems.add(item);
        }
        return true;
    }

    public boolean fullWell(){
        if (well.getBuyCost() <= money){
            money -= well.getBuyCost();
            well.full();
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

    public boolean turn(){
        time = time + 1;
        checkMoves();
        removeGrassAndItem();
        checkCollision();
        if (time % 10 == 0) {   //wild animals come
            shootWildAnimal();
            shootWildAnimalTime = time ;
        }
        if (shootWildAnimalTime != -1 && time - shootWildAnimalTime == 5) { //if there as any wild animal will leave
            stuffs.removeIf((Entity entity) -> entity instanceof Wild);
            shootWildAnimalTime = -1;
        }
        //TODO checkWorkshops
        //TODO check transportation
        map.clearCells();
        map.updateCells(stuffs);
        return isLevelFinished();
    }

    public void shootWildAnimal(){
        for (int i = 0; i < 2; i++){
            double random = Math.random();
            if ((int)(random * 100) % 2 == 0)
                stuffs.add(new Bear(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength)));
            else
                stuffs.add(new Lion(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength)));
        }
    }

    public boolean checkEatingGrass(double y, double x){
        int X = (int)Math.round(x);
        int Y = (int)Math.round(y);
        ArrayList<Entity> entities = map.getCells()[Y][X].getStuffs();
        for (Entity entity : entities)
            if (entity instanceof Grass && !((Grass) entity).isEaten()){
                ((Grass) entity).setEaten(true);
                return true;
            }
        return false;
    }

    public boolean catCollect(double y, double x){
        int X = (int)Math.round(x);
        int Y = (int)Math.round(y);
        boolean result = false;
        ArrayList<Entity> entities = map.getCells()[Y][X].getStuffs();
        for (Entity entity : entities)
            if (entity instanceof Item && entity.getVolume() <= wareHouse.getCurrentVolume() && !((Item) entity).isTakenByCat()){
                wareHouse.setCurrentVolume(wareHouse.getVolume() - entity.getVolume());
                ((Item) entity).setTakenByCat(true);
                wareHouse.add(entity);
                result = true;
            }
        return result;
    }

    public void checkMoves(){
        Iterator<Entity> iterator = stuffs.iterator();
        while (iterator.hasNext()){
            Entity entity = iterator.next();
            boolean doMove = true;
            if (entity instanceof Domestic){
                if (((Domestic) entity).getSatiety() == 0) {
                    iterator.remove();
                    continue;
                }
                else if (((Domestic) entity).getSatiety() < Constants.LEAST_DOMESTIC_SATIETY) {
                    doMove = !checkEatingGrass(entity.getY(), entity.getX());
                    if (!doMove) {
                        ((Domestic) entity).setSatiety(Constants.FULL_SATIETY);
                        Item item;
                        if (entity instanceof Hen) {
                            item = new Item(entity.getX(), entity.getY(), "egg",
                                    Constants.EGG_SALE_COST, Constants.EGG_BUY_COST);
                            stuffs.add(item);
                        }
                        else if (entity instanceof Cow) {
                            item = new Item(entity.getX(), entity.getY(), "milk",
                                    Constants.MILK_SALE_COST, Constants.MILK_BUY_COST);
                            stuffs.add(item);
                        }
                        else {
                            item = new Item(entity.getX(), entity.getY(), "wool",
                                    Constants.WOOL_SALE_COST, Constants.WOOL_BUY_COST);
                            stuffs.add(item);
                        }
                        updateAchievement(item.getKind());
                    }
                }
            }
            else if (entity instanceof Cat)
                doMove = !catCollect(entity.getY(), entity.getX());
            if (entity instanceof Animal && doMove){
                ((Animal) entity).move();
                if (entity instanceof Domestic)
                    ((Domestic) entity).decreaseSatiety(1);
            }
        }
    }

    public void removeGrassAndItem(){
        if (map.isThereGrass() && map.isThereItem()) {
            Iterator<Entity> iterator = stuffs.iterator();
            while (iterator.hasNext()) {
                Entity entity = iterator.next();
                if (entity instanceof Grass && ((Grass) entity).isEaten())
                    iterator.remove();
                else if (entity instanceof Item && ((Item) entity).isTakenByCat())
                    iterator.remove();
            }
        }
    }

    public void updateAchievement(String kind){
        if (achievements.containsKey(kind))
            achievements.replace(kind, achievements.get(kind) + 1);
    }

    public boolean isLevelFinished(){
        for (String s : goals.keySet())
            if (achievements.get(s) < goals.get(s))
                return false;
        return true;
    }

    public int catCount(){
        int count = 0;
        for (Entity entity : stuffs)
            if (entity instanceof Cat)
                count ++;
        return count;
    }

    public void clearFromHelicopter(){} //todo by Fereshteh :)

    public void clearFromTruck(){}//todo by Fereshteh :)

    public void addToTruck(Item item, int count){}//todo by Fereshteh :)

    public void addToHellicopter(Item item , int cout){}//todo by Fereshteh :)

    public void goTransportation(boolean vehicle){
        if(vehicle){
            truck.setCurrentTime(truck.getWorkingTime());
            truck.setMoving(true);
        }
        else {
            helicopter.setCurrentTime(helicopter.getWorkingTime());
            helicopter.setMoving(true);
        }

    }

    public String printLevel(){
        String string = goals.toString();
        string = string.replace("{","");
        string = string.replace("}","");
        string = string.replace(", ","\n");
        return string;
    }

}
