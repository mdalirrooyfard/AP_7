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
import Model.Transportation.Transportations;
import Model.Transportation.Truck;
import Model.Workshops.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Farm {
    private Map map;
    private int mapLength;
    private int mapWidth;
    private ArrayList<Entity> stuffs = new ArrayList<>();
    private int time;
    private Helicopter helicopter;
    private Truck truck;
    private WareHouse wareHouse;
    private Well well;
    private int money;
    private int shootWildAnimalTime = -1;
    private HashMap<String, Integer> goals = new HashMap<>();
    private HashMap<String, Integer> achievements = new HashMap<>();
    private boolean areCatsUpgraded = false;
    private ArrayList<Workshop> workshops = new ArrayList<>();

    public HashMap<String, Integer> getGoals() {
        return goals;
    }

    public Farm(int length, int width){
        mapLength = length;
        mapWidth = width;
        map = new Map(length, width);
        time = 0;
        money = 0;
    }

    public void makeWell(int volume){
        well = new Well(volume);
    }

    public void makeWareHouse(int volume){
        wareHouse = new WareHouse(volume);
    }

    public void makeTruck(int volume){
        truck = new Truck(volume);
    }

    public void makeHelicopter(int volume){
        helicopter = new Helicopter(volume);
    }

    public int makeRandomXAndY(int dim){
        double temp = Math.random() * dim;
        return (int)temp;
    }

    public boolean addHen(boolean isBought){
        if( isBought )
        {
            if (Constants.HEN_BUY_COST > money)
                return false;
            decreaseMoney(Constants.HEN_BUY_COST);
        }
        Hen hen = new Hen(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(hen);
        updateAchievement("hen");
        return true;
    }

    public boolean addCow(boolean isBought){
        if( isBought )
        {
            if (Constants.COW_BUY_COST  > money)
                return false;
            decreaseMoney(Constants.COW_BUY_COST);
        }
        Cow cow = new Cow(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(cow);
        updateAchievement("cow");
        return true;
    }

    public boolean addSheep(boolean isBought){
        if( isBought )
        {
            if (Constants.SHEEP_BUY_COST  > money)
                return false;
            decreaseMoney(Constants.SHEEP_BUY_COST);
        }
        Sheep sheep = new Sheep(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
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

    public boolean addDog( boolean isBought ){
        if( isBought )
        {
            if (Constants.DOG_BUY_COST > money)
                return false;
            decreaseMoney(Constants.DOG_BUY_COST);
        }
        Dog dog = new Dog(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        decreaseMoney(dog.getBuyCost());
        stuffs.add(dog);
        updateAchievement("dog");
        return true;
    }

    public boolean addCat( boolean isBought ){
        if( isBought )
        {
            if (Constants.CAT_BUY_COST > money)
                return false;
            decreaseMoney(Constants.CAT_BUY_COST);
        }
        Cat cat = new Cat(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        decreaseMoney(cat.getBuyCost());
        stuffs.add(cat);
        updateAchievement("cat");
        return true;
    }

    public int upgrade(String entityName){
        switch (entityName){
            case "cat":{
                if (areCatsUpgraded)
                    return 2;
                if (money < catCount() * Constants.CAT_BASE_UPGRADE_COST)
                    return 1;
                decreaseMoney(catCount() * Constants.CAT_BASE_UPGRADE_COST);
                for (Entity entity : stuffs)
                    if (entity instanceof Cat)
                        entity.upgrade();
                areCatsUpgraded = true;
                return 0;
            }
            case "well":{
                return upgradeEntity(well);
            }
            case "truck":{
                return upgradeEntity(truck);
            }
            case "helicopter":{
                return upgradeEntity(helicopter);
            }
            case "warehouse":{
                return upgradeEntity(wareHouse);
            }
            default:{ //workshops
                Workshop workshop = null;
                for (Workshop w : workshops){
                    if (w.getWorkShopName().equals(entityName)) {
                        workshop = w;
                        break;
                    }
                }
                if (workshop != null) {
                    return upgradeEntity(workshop);
                }
                else
                    return 3;

            }
        }
    }

    public int upgradeEntity(Entity entity){
        if (entity.getLevel() == 5)
            return 2;
        if (entity.getUpgradeCost() > money)
            return 1;
        decreaseMoney(entity.getUpgradeCost());
        entity.upgrade();
        return 0;
    }

    public boolean pickUp(double x, double y){
        int currentX = (int) Math.round(x);
        int currentY = (int) Math.round(y);
        ArrayList<Entity> cellItems = map.getCells()[currentY][currentX].getStuffs();
        ArrayList<Entity> cellRemainItems = new ArrayList<>();
        boolean isEveryThingPickedUp = true;
        for (Entity entity : cellItems){
            if (entity instanceof Item)
                if (entity.getVolume() <= wareHouse.getCurrentVolume()){
                    wareHouse.decreaseCurrentVolume(entity.getVolume());
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
            Item item = new Item(currentX, currentY, "cagedBrownBear");
            stuffs.add(item);
            cellItems.add(item);
        }
        for (int i = 0; i < numberOfLions; i++){
            Item item = new Item(currentX, currentY, "cagedLion");
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
        checkTransportation();
        checkWorkShops();
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
                wareHouse.decreaseCurrentVolume(entity.getVolume());
                ((Item) entity).setTakenByCat(true);
                wareHouse.add((Item)entity);
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
                            item = new Item(entity.getX(), entity.getY(), "egg");
                            stuffs.add(item);
                        }
                        else if (entity instanceof Cow) {
                            item = new Item(entity.getX(), entity.getY(), "milk");
                            stuffs.add(item);
                        }
                        else {
                            item = new Item(entity.getX(), entity.getY(), "wool");
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

    //transportation methods
    public boolean clearFromHelicopter(){
        if (helicopter.isMoving())
            return false;
        helicopter.setMoving(false);
        Iterator<Item> iterator = helicopter.getItems().iterator();
        while(iterator.hasNext()){
            stuffs.add(iterator.next());
            iterator.remove();
        }
        return true;
    }

    public boolean clearFromTruck(){
        if (truck.isMoving())
            return false;
        truck.setMoving(false);
        truck.getItems().clear();
        increaseMoney(truck.getSpentMoney());
        truck.setSpentMoney(0);
        return true;
    }

    public int addToTruck(String itemName, int count){
        if (truck.isMoving())
            return -1;
        int result = 0;
        Iterator<Item> iterator = wareHouse.getCollectedItems().iterator();
        while (iterator.hasNext() && result < count){
            Item item = iterator.next();
            if (item.getKind().equals(itemName)){
                if (item.getVolume() <= truck.getCurrentVolume()){
                    truck.decreaseCurrentVolume(item.getVolume());
                    truck.increaseSpentMoney(item.getSellCost());
                    truck.add(item);
                    wareHouse.increaseCurrentVolume(item.getVolume());
                    iterator.remove();
                    result ++;
                }
                else
                    return result;
            }
        }
        return result;
    }

    public int addToHelicopter(String itemName , int count){
        if (helicopter.isMoving())
            return -1;
        int result = 0;
        for (int i = 0; i < count ; i++) {
            Item item = new Item(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), itemName);
            if (item.getVolume() <= helicopter.getCurrentVolume()
                    && item.getBuyCost() <= money){
                decreaseMoney(item.getBuyCost());
                helicopter.decreaseCurrentVolume(item.getVolume());
                helicopter.add(item);
                result ++;
            }
            else
                return result;
        }
        return result;
    }

    public boolean goTransportation(boolean vehicle){
        if(vehicle){
            if (truck.isMoving())
                return false;
            truck.setCurrentTime(truck.getWorkingTime());
            truck.setMoving(true);
        }
        else {
            if (helicopter.isMoving())
                return false;
            helicopter.setCurrentTime(helicopter.getWorkingTime());
            helicopter.setMoving(true);
        }
        return true;
    }

    public void checkTransportation(){
        if (truck.isMoving()){
            if (truck.getCurrentTime() > 0)
                truck.decreaseCurrentTime(1);
            else
                clearFromTruck();
        }
        if (helicopter.isMoving()){
            if (helicopter.getCurrentTime() > 0)
                helicopter.decreaseCurrentTime(1);
            else
                clearFromHelicopter();
        }
    }

    //print methods
    public String printLevel(){
        String string = goals.toString();
        string = string.replace("{","");
        string = string.replace("}","");
        string = string.replace(", ","\n");
        return string;
    }

    public String printInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("money : ").append(money).append("\n");
        stringBuilder.append("time : ").append(time);
        for (String s : goals.keySet()){
            stringBuilder.append("\n").append(s).append(" : ").append(achievements.get(s));
            stringBuilder.append(" of ").append(goals.get(s));
        }
        return stringBuilder.toString();
    }

    public String printWell(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("volume : ").append(well.getVolume()).append("\n");
        stringBuilder.append("current water : ").append(well.getCurrentVolume()).append("\n");
        stringBuilder.append("cost : ").append(well.getBuyCost()).append("\n");
        stringBuilder.append("upgrade cost : ").append(well.getUpgradeCost());
        return stringBuilder.toString();
    }

    public String printWareHouse(){
        String string = wareHouse.getCollectedItems().toString();
        string = string.replace("{","");
        string = string.replace("}","");
        string = string.replace(", ","\n");
        string += "\nvolume : " + wareHouse.getVolume() + "\n";
        string += "current volume : " + wareHouse.getCurrentVolume() + "\n";
        string += "upgrade cost : " + wareHouse.getUpgradeCost();
        return string;
    }

    public String printMap(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mapLength; i++) {
            for (int j = 0; j < mapWidth; j++){
                boolean[] status = map.cellStatus(j, i);
                for(boolean k : status)
                    if (k)
                        stringBuilder.append(1);
                    else
                        stringBuilder.append(0);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String printTransportation(boolean vehicle){
        Transportations transportations;
        if (vehicle)
            transportations = truck;
        else
            transportations = helicopter;
        StringBuilder stringBuilder = new StringBuilder();
        for (Item item : transportations.getItems())
            stringBuilder.append(item.getKind()).append(" ");
        return stringBuilder.toString();
    }

    public String printWorkshop(String workShopName){
        Workshop workshop = null;
        for(Workshop w : workshops)
            if (w.getWorkShopName().equals(workShopName)) {
                workshop = w;
                break;
            }
        if (workshop == null)
            return "This workShop is not included in this level!";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("inputs : ");
        for (String input : workshop.getInputs())
            stringBuilder.append(input).append(" ");
        stringBuilder.append("\noutput : ").append(workshop.getOutput());
        return stringBuilder.toString();
    }

    //workShops
    public void makeWorkShops(ArrayList<String> activeWorkShops){
        CakeBakery cakeBakery = new CakeBakery();
        if (activeWorkShops.contains(cakeBakery.getWorkShopName()))
            workshops.add(cakeBakery);

        CookieBakery cookieBakery = new CookieBakery();
        if (activeWorkShops.contains(cookieBakery.getWorkShopName()))
            workshops.add(cookieBakery);

        EggPowderPlant eggPowderPlant = new EggPowderPlant();
        if (activeWorkShops.contains(eggPowderPlant.getWorkShopName()))
            workshops.add(eggPowderPlant);

        SewingFactory sewingFactory = new SewingFactory();
        if (activeWorkShops.contains(sewingFactory.getWorkShopName()))
            workshops.add(sewingFactory);

        Spinnery spinnery = new Spinnery();
        if (activeWorkShops.contains(spinnery.getWorkShopName()))
            workshops.add(spinnery);

    }

    public void makeCustomWorkshop(String name, ArrayList<String> inputs, String output){
        CustomFactory customFactory = new CustomFactory(name, inputs, output);
        workshops.add(customFactory);
    }

    public int startWorkShop(String name){
        Workshop workshop = null;
        for (Workshop w : workshops)
            if (w.getWorkShopName().equals(name)){
                workshop = w;
                break;
            }
        if (workshop == null)
            return -1;
        if (workshop.isWorking())
            return -2;
        workshop.setCount(availableInputCount(workshop.getInputs(), workshop.getLevel()));
        if (workshop.getCount() > 0) {
            for (String s : workshop.getInputs()) {
                Iterator<Item> iterator = wareHouse.getCollectedItems().iterator();
                int count = 0;
                while (iterator.hasNext() && count < workshop.getCount()){
                    Item item = iterator.next();
                    if (item.getKind().equals(s)){
                        iterator.remove();
                        count ++ ;
                    }
                }
            }
            workshop.setCurrentTime(workshop.getWorkingTime());
            workshop.setWorking(true);
        }
        return workshop.getCount();
    }

    public int availableInputCount(ArrayList<String> inputs, int initial){
        int min = initial;
        for (String s : inputs){
            int count = 0;
            for (Item item : wareHouse.getCollectedItems())
                if (item.getKind().equals(s))
                    count++;
            if (count < min)
                min = count;
        }
        return min;
    }

    public void checkWorkShops(){
        for (Workshop w : workshops)
            if (w.isWorking())
                if (w.getCurrentTime() > 0)
                    w.currentTimeDecrease(1);
                else
                    endWorkShop(w);
    }

    public void endWorkShop(Workshop workshop){
        workshop.setWorking(false);
        for (int i = 0; i < workshop.getCount() ; i++) {
            Item item = new Item(workshop.getItem_x(), workshop.getItem_y(), workshop.getOutput());
            stuffs.add(item);
        }
    }

}
