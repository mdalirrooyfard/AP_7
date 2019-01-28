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
import Model.Workshops.*;
import javafx.stage.Screen;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;


public class Farm {
    private Map map;
    private final int mapLength = (int) (Screen.getPrimary().getVisualBounds().getHeight() / 44);
    private final int mapWidth = (int) (Screen.getPrimary().getVisualBounds().getWidth() / 48);
    private Vector<Entity> stuffs = new Vector<>();
    private Vector<Item> addedItems = new Vector<>();
    private int time;
    private long timer = 0;
    private Helicopter helicopter;
    private Truck truck;
    private WareHouse wareHouse;
    private Well well;
    private int money;
    private int shootWildAnimalTime = -1;
    private ConcurrentHashMap<String, Integer> goals = new ConcurrentHashMap<>();
    private Vector<String> achievementsNames = new Vector<>();
    private Vector<Integer> achievementsCounts = new Vector<>();
    private boolean areCatsUpgraded = false;
    private Workshop[] workshops = new Workshop[7];

    public boolean isAreCatsUpgraded() {
        return areCatsUpgraded;
    }

    public Vector<Entity> getStuffs() {
        return stuffs;
    }

    public int getMapLength() {
        return mapLength;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public Map getMap() {
        return map;
    }

    public long getTimer()
    {
        return timer;
    }

    public void increaseTimer()
    {
        timer++;
    }

    public void setTimer(long timer)
    {
        this.timer = timer;
    }

    public Well getWell(){
        return well;
    }

    public Truck getTruck(){
        return truck;
    }

    public Helicopter getHelicopter(){
        return helicopter;
    }

    public void makeAchievements()
    {
        for( String s : goals.keySet() )
        {
            achievementsNames.add(s);
            achievementsCounts.add(0);
        }
    }

    public ConcurrentHashMap<String, Integer> getAchievements() {
        ConcurrentHashMap<String, Integer> achievements = new ConcurrentHashMap<>();
        for( String s : achievementsNames )
        {
            achievements.put(s,achievementsCounts.get(achievementsNames.indexOf(s)));
        }
        return achievements;
    }

    public Workshop[] getWorkshops() {
        return workshops;
    }

    public ConcurrentHashMap<String, Integer> getGoals() {
        return goals;
    }

    public Farm()
    {
        map = new Map(mapLength, mapWidth);
        time = 0;
        money = 0;
    }

    public void makeWell(int volume) {
        well = new Well(volume);
    }

    public void makeWareHouse(int volume) {
        wareHouse = new WareHouse(volume);
    }

    public void makeTruck(int volume) {
        truck = new Truck(volume);
    }

    public void makeHelicopter(int volume) {
        helicopter = new Helicopter(volume);
    }

    public int makeRandomXAndY(int dim) {
        double temp = Math.random() * dim;
        return (int) temp;
    }

    public boolean addHen(boolean isBought) {
        if (isBought) {
            if (Constants.HEN_BUY_COST > money)
                return false;
            decreaseMoney(Constants.HEN_BUY_COST);
        }
        Hen hen = new Hen(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(hen);
        updateAchievement("hen");
        map.getCells()[(int) hen.getY()][(int) hen.getX()].add(hen);
        return true;
    }

    public boolean addCow(boolean isBought) {
        if (isBought) {
            if (Constants.COW_BUY_COST > money)
                return false;
            decreaseMoney(Constants.COW_BUY_COST);
        }
        Cow cow = new Cow(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(cow);
        updateAchievement("cow");
        map.getCells()[(int) cow.getY()][(int) cow.getX()].add(cow);
        return true;
    }

    public boolean addSheep(boolean isBought) {
        if (isBought) {
            if (Constants.SHEEP_BUY_COST > money)
                return false;
            decreaseMoney(Constants.SHEEP_BUY_COST);
        }
        Sheep sheep = new Sheep(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(sheep);
        updateAchievement("sheep");
        map.getCells()[(int) sheep.getY()][(int) sheep.getX()].add(sheep);
        return true;
    }

    public boolean plantGrass(double x, double y) {
        if (well.getCurrentVolume() == 0 || well.isWorking())
            return false;
        well.setCurrentVolume(well.getCurrentVolume() - 1);
        int centerX = (int) x;
        int centerY = (int) y;
        stuffs.add(new Grass(centerX, centerY));
        if (centerY - 1 >= 0)
            stuffs.add(new Grass(centerX, centerY - 1)); //up
        if (centerY + 1 <= mapLength - 1)
            stuffs.add(new Grass(centerX, centerY + 1));   //down
        if (centerX - 1 >= 0) {
            stuffs.add(new Grass(centerX - 1, centerY));   //left
            if (centerY - 1 >= 0)
                stuffs.add(new Grass(centerX - 1, centerY - 1)); //up left
            if (centerY + 1 <= mapLength - 1)
                stuffs.add(new Grass(centerX - 1, centerY + 1)); //down left
        }
        if (centerX + 1 <= mapWidth - 1) {
            stuffs.add(new Grass(centerX + 1, centerY)); //right
            if (centerY - 1 >= 0)
                stuffs.add(new Grass(centerX + 1, centerY - 1)); //up right
            if (centerY + 1 <= mapLength - 1)
                stuffs.add(new Grass(centerX + 1, centerY + 1)); //down right

        }
        updateMap();
        return true;
    }

    public boolean addDog(boolean isBought) {
        if (isBought) {
            if (Constants.DOG_BUY_COST > money)
                return false;
            decreaseMoney(Constants.DOG_BUY_COST);
        }
        Dog dog = new Dog(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(dog);
        updateAchievement("dog");
        map.getCells()[(int) dog.getY()][(int) dog.getX()].add(dog);
        return true;
    }

    public boolean addCat(boolean isBought) {
        if (isBought) {
            int cost;
            if (areCatsUpgraded)
                cost = Constants.CAT_BUY_COST + Constants.CAT_BASE_UPGRADE_COST;
            else
                cost = Constants.CAT_BUY_COST;
            if (cost > money)
                return false;
            decreaseMoney(cost);
        }
        Cat cat = new Cat(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        if (areCatsUpgraded)
            cat.setLevel(2);
        stuffs.add(cat);
        updateAchievement("cat");
        map.getCells()[(int) cat.getY()][(int) cat.getX()].add(cat);
        return true;
    }

    public int upgrade(String entityName) {
        switch (entityName) {
            case "cat": {
                if (catCount() == 0)
                    return 3;
                if (areCatsUpgraded)
                    return 2;
                if (money < catCount() * Constants.CAT_BASE_UPGRADE_COST)
                    return 1;
                decreaseMoney(catCount() * Constants.CAT_BASE_UPGRADE_COST);
                for (Entity entity:stuffs)
                    if (entity instanceof Cat)
                        entity.upgrade();
                areCatsUpgraded = true;
                return 0;
            }
            case "well": {
                return upgradeEntity(well);
            }
            case "truck": {
                return upgradeEntity(truck);
            }
            case "helicopter": {
                return upgradeEntity(helicopter);
            }
            case "wareHouse": {
                return upgradeEntity(wareHouse);
            }
            default: { //workshops
                Workshop workshop = null;
                for (Workshop w : workshops)
                {
                    if( w.getWorkShopName().equals(entityName) )
                    {
                        workshop =  w;
                        break;
                    }
                }
                if (workshop != null) {
                    return upgradeEntity(workshop);
                } else
                    return 3;

            }
        }
    }

    public int upgradeEntity(Entity entity) {
        if (entity.getLevel() == 4)
            return 2;
        if (entity.getUpgradeCost() > money)
            return 1;
        decreaseMoney(entity.getUpgradeCost());
        entity.upgrade();
        return 0;
    }

    public boolean pickUp(double x, double y) {
        int currentX = (int)x;
        int currentY = (int)y;
        Vector<Entity> cellItems = map.getCells()[currentY][currentX].getStuffs();
        Vector<Entity> cellRemainItems = new Vector<>();
        boolean isEveryThingPickedUp = true;
        for (Entity entity : cellItems) {
            if (entity instanceof Item)
                if (entity.getVolume() <= wareHouse.getCurrentVolume()) {
                    wareHouse.decreaseCurrentVolume(entity.getVolume());
                    wareHouse.add((Item)entity);
                    entity.setDead(true);
                } else {
                    cellRemainItems.add(entity);
                    isEveryThingPickedUp = false;
                }
            else
                cellRemainItems.add(entity);
        }
        map.getCells()[currentY][currentX].update(cellRemainItems);
        return isEveryThingPickedUp;
    }

    public boolean putCage(double x, double y) {
        int currentX = (int) x;
        int currentY = (int) y;
        if (!map.getCells()[currentY][currentX].status()[0])
            return false;
        Vector<Entity> cellItems = map.getCells()[currentY][currentX].getStuffs();
        int numberOfBears = 0;
        int numberOfLions = 0;
        for (Entity entity : cellItems) {
            if (entity instanceof Bear) {
                entity.setDead(true);
                numberOfBears++;
            } else if (entity instanceof Lion) {
                entity.setDead(true);
                numberOfLions++;
            }
        }
        for (int i = 0; i < numberOfBears; i++) {
            Item item = new Item(currentX, currentY, "cagedGrizzly");
            stuffs.add(item);
            cellItems.add(item);
        }
        for (int i = 0; i < numberOfLions; i++) {
            Item item = new Item(currentX, currentY, "cagedLion");
            stuffs.add(item);
            cellItems.add(item);
        }
        return true;
    }

    public int fullWell() {
        if (well.getCurrentVolume() != 0)
            return 0;
        if (well.getBuyCost() <= money) {
            money -= well.getBuyCost();
            well.setWorking(true);
            well.increase(1);
            return 1;
        }
        return -1;
    }

    public void increaseMoney(int amount) {
        money += amount;
    }

    public void decreaseMoney(int amount) {
        money -= amount;
    }

    public int getMoney() {
        return money;
    }

    public void checkCollision() {
        if (!map.isThereWild())
            return;
        for (int i = 0; i < mapLength; i++)
            for (int j = 0; j < mapWidth; j++) {
                Vector<Integer[]> places = new Vector<>();
                places.add(new Integer[]{i,j});
                if (i - 1 >= 0)
                    places.add(new Integer[]{j, i - 1}); //up
                if (i + 1 <= mapLength - 1)
                    places.add(new Integer[]{j, i + 1});   //down
                if (j - 1 >= 0) {
                    places.add(new Integer[]{j - 1, i});   //left
                    if (i - 1 >= 0)
                        places.add(new Integer[]{j - 1, i - 1}); //up left
                    if (i + 1 <= mapLength - 1)
                        places.add(new Integer[]{j - 1, i + 1}); //down left
                }
                if (j + 1 <= mapWidth - 1) {
                    places.add(new Integer[]{j + 1, i}); //right
                    if (i - 1 >= 0)
                        places.add(new Integer[]{j + 1, i - 1}); //up right
                    if (i + 1 <= mapLength - 1)
                        places.add(new Integer[]{j + 1, i + 1}); //down right
                }
                Cell cell = map.getCells()[i][j];
                if (cell.status()[0]) {
                    if (cell.status()[4]) {
                        killDogAndWild(places);
                    }
                    else if (cell.status()[1] || cell.status()[2]) {
                        killDomesticAndItems(places);
                    }
                }
            }
    }

    public void killDogAndWild(Vector<Integer[]> places) {
        for (Entity entity : stuffs){
            if ( !entity.isDead() && (entity instanceof Wild || entity instanceof Dog)){
                Integer[] animalPlace = new Integer[]{(int)entity.getY(), (int)entity.getX()};
                if (vectorContains(places, animalPlace)) {
                    entity.setDead(true);
                    System.out.println("mord as dog");
                }
            }
        }
    }

    public void killDomesticAndItems(Vector<Integer[]> places) {
        for (Entity entity : stuffs){
            if ( !entity.isDead() && (entity instanceof Domestic || entity instanceof Item)){
                Integer[] animalPlace = new Integer[]{(int)entity.getY(), (int)entity.getX()};
                if (vectorContains(places, animalPlace)) {
                    entity.setDead(true);
                    System.out.println("mord as wild");
                }
            }
        }
    }

    public boolean vectorContains(Vector<Integer[]> places, Integer[] place){
        for (Integer[] i : places){
            if (i[0].equals(place[0]) && i[1].equals(place[1]))
                return true;
        }
        return false;
    }

    public boolean turn()
    {
        time ++;
        checkMoves();
        stuffs.addAll(addedItems);
        addedItems.clear();
        updateMap();
        removeGrassAndItem();
        updateMap();
        checkCollision();
        updateMap();
        if (time % 20 == 0)
        {   //wild animals come
            shootWildAnimal();
            updateMap();
            shootWildAnimalTime = time;
        }
        if (shootWildAnimalTime != -1 && time - shootWildAnimalTime == 10)
        { //if there as any wild animal will leave
            for (Entity entity : stuffs)
            {
                if (entity instanceof Wild)
                    entity.setDead(true);
            }
            shootWildAnimalTime = -1;
            updateMap();
        }
        return isLevelFinished();
    }

    public void shootWildAnimal() {
        for (int i = 0; i < 2; i++) {
            double random = Math.random();
            if ((int) (random * 100) % 2 == 0)
                stuffs.add(new Bear(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map));
            else
                stuffs.add(new Lion(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map));
        }
    }

    public WareHouse getWareHouse(){
        return wareHouse;
    }

    public boolean checkEatingGrass(double y, double x, int max) {
        int X = (int) x;
        int Y = (int) y;
        Vector<Entity> entities = map.getCells()[Y][X].getStuffs();
        for (Entity entity : entities)
            if (entity instanceof Grass && !((Grass) entity).isEatenAlready()) {
                ((Grass) entity).setEaten(max);
                ((Grass) entity).setEatenAlready(true);
                return true;
            }
        return false;
    }

    public boolean catCollect(double y, double x) {
        int X = (int) x;
        int Y = (int) y;
        boolean result = false;
        Vector<Entity> entities = map.getCells()[Y][X].getStuffs();
        for (Entity entity : entities)
            if (entity instanceof Item && entity.getVolume() <= wareHouse.getCurrentVolume() && !((Item) entity).isTakenByCat()) {
                wareHouse.decreaseCurrentVolume(entity.getVolume());
                ((Item) entity).setTakenByCat(true);
                wareHouse.add((Item) entity);
                result = true;
            }
        return result;
    }

    public void checkMoves()
    {
        for (Entity entity : stuffs)
        {
            if (!entity.isDead())
            {
                boolean doMove = true;
                if (entity instanceof Domestic)
                {
                    int isEating = ((Domestic) entity).isEating();
                    if (((Domestic) entity).getSatiety() == 0)
                    {
                        entity.setDead(true);
                        continue;
                    }
                    else if (isEating == Constants.MAX_DOMESTIC_SATIETY)
                    {
                        ((Domestic) entity).setEating(0);
                        produceItem((Domestic) entity);
                    }
                    else if (isEating != 0 && isEating < Constants.MAX_DOMESTIC_SATIETY)
                    {
                        ((Domestic) entity).increaseSatiety(1);
                        ((Domestic) entity).setEating(((Domestic) entity).getSatiety());
                        doMove = false;
                    }
                    else if (((Domestic) entity).getSatiety() < Constants.LEAST_DOMESTIC_SATIETY)
                    {
                        System.out.println(entity.getX()+ " "+ entity.getY());
                        doMove = !checkEatingGrass(entity.getY(), entity.getX(),
                                Constants.MAX_DOMESTIC_SATIETY - 1 - ((Domestic) entity).getSatiety());
                        if (!doMove)
                        {
                            ((Domestic) entity).increaseSatiety(1);
                            ((Domestic) entity).setEating(((Domestic) entity).getSatiety());
                        }
                    }
                }
                else if (entity instanceof Cat)
                    catCollect(entity.getY(), entity.getX());
                if (entity instanceof Animal && doMove)
                {
                    ((Animal) entity).move();
                    if (entity instanceof Domestic)
                        ((Domestic) entity).decreaseSatiety(1);
                }
                else if (entity instanceof Animal)
                    ((Animal) entity).setDirection(DIRECTION.NONE);

            }
        }
    }

    public void produceItem(Domestic entity)
    {
        Item item;
        if (entity instanceof Hen)
            item = new Item(entity.getX(), entity.getY(), "egg");
        else if (entity instanceof Cow)
            item = new Item(entity.getX(), entity.getY(), "milk");
        else
            item = new Item(entity.getX(), entity.getY(), "wool");
        updateAchievement(item.getKind());
        addedItems.add(item);
        updateMap();
    }

    public void removeGrassAndItem() {
        if (map.isThereGrass() || map.isThereItem()) {
            for (Entity entity : stuffs) {
                if (entity instanceof Grass && ((Grass) entity).isEatenAlready() && ((Grass) entity).isEaten() == 0) {
                    entity.setDead(true);
                }
                else if (entity instanceof Grass && ((Grass) entity).isEatenAlready())
                    ((Grass) entity).decreaseEaten();
                else if (entity instanceof Item && ((Item) entity).isTakenByCat())
                    entity.setDead(true);
            }
        }
        updateMap();
    }

    public void updateAchievement(String kind) {
        if (achievementsNames.contains(kind))
            achievementsCounts.set(achievementsNames.indexOf(kind), achievementsCounts.get(achievementsNames.indexOf(kind)) + 1);
    }

    public void updateMap() {
        map.updateCells(stuffs);
    }

    public boolean isLevelFinished() {
        for (String s : goals.keySet())
            if (achievementsCounts.get(achievementsNames.indexOf(s)) < goals.get(s))
                return false;
        return true;
    }

    public int catCount() {
        int count = 0;
        for (Entity entity : stuffs)
            if (entity instanceof Cat)
                count++;
        return count;
    }

    //transportation methods
    public boolean clearFromHelicopter() {
        if (helicopter.isMoving())
            return false;
        helicopter.setMoving(false);
        stuffs.addAll(helicopter.getItems());
        helicopter.getItems().clear();
        updateMap();
        helicopter.setSpentMoney(0);
        helicopter.setCurrentVolume(helicopter.getVolume());
        return true;
    }

    public boolean clearFromTruck() {
        if (truck.isMoving())
            return false;
        truck.setMoving(false);
        truck.getItems().clear();
        truck.getItems().clear();
        increaseMoney(truck.getSpentMoney());
        truck.setSpentMoney(0);
        truck.setCurrentVolume(truck.getVolume());
        return true;
    }

    public boolean clearTruckBeforeGo(){
        if (truck.isMoving())
            return false;
        wareHouse.getCollectedItems().addAll(truck.getItems());
        wareHouse.decreaseCurrentVolume(truck.getVolume() - truck.getCurrentVolume());
        truck.setSpentMoney(0);
        truck.setCurrentVolume(truck.getVolume());
        return true;
    }

    public void clearOneItemFromTruck(String kind){
        Vector<Item> items = truck.getItems();
        for (Item item : items){
            if (item.getKind().equals(kind)){
                truck.decreaseSpentMoney(item.getBuyCost());
                truck.increaseCurrentVolume(item.getVolume());
                items.remove(item);
                return;
            }
        }
    }

    public void clearOneItemFromHelicopter(String kind){
        Vector<Item> items = helicopter.getItems();
        for (Item item : items){
            if (item.getKind().equals(kind)){
                increaseMoney(item.getBuyCost());
                helicopter.decreaseSpentMoney(item.getBuyCost());
                helicopter.increaseCurrentVolume(item.getVolume());
                items.remove(item);
                return;
            }
        }
    }

    public boolean clearHelicopterBeforeGo(){
        if (helicopter.isMoving())
            return false;
        helicopter.getItems().clear();
        increaseMoney(helicopter.getSpentMoney());
        helicopter.setSpentMoney(0);
        helicopter.setCurrentVolume(helicopter.getVolume());
        return true;
    }

    public int addToTruck(String itemName, int count) {
        if (truck.isMoving())
            return -1;
        int result = 0;
        Vector<Item> collectedItems = wareHouse.getCollectedItems();
        for (int j = 0; j < collectedItems.size() && result < count; j++) {
            Item item = collectedItems.get(j);
            if (item.getKind().equals(itemName)) {
                if (item.getVolume() <= truck.getCurrentVolume()) {
                    truck.decreaseCurrentVolume(item.getVolume());
                    truck.increaseSpentMoney(item.getSellCost());
                    truck.add(item);
                    wareHouse.increaseCurrentVolume(item.getVolume());
                    collectedItems.remove(item);
                    result++;
                } else
                    return result;
            }
        }
        return result;
    }

    public int addToHelicopter(String itemName, int count) {
        if (helicopter.isMoving())
            return -1;
        int result = 0;
        for (int i = 0; i < count; i++) {
            Item item = new Item(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), itemName);
            if (item.getVolume() <= helicopter.getCurrentVolume()
                    && item.getBuyCost() <= money) {
                decreaseMoney(item.getBuyCost());
                helicopter.increaseSpentMoney(item.getBuyCost());
                helicopter.decreaseCurrentVolume(item.getVolume());
                helicopter.add(item);
                result++;
            } else
                return result;
        }
        return result;
    }

    public boolean goTransportation(boolean vehicle) {
        if (vehicle) {
            if (truck.isMoving())
                return false;
            truck.setCurrentTime(truck.getWorkingTime());
            truck.setMoving(true);
        } else {
            if (helicopter.isMoving())
                return false;
            helicopter.setCurrentTime(helicopter.getWorkingTime());
            helicopter.setMoving(true);
        }
        return true;
    }

    //workShops
    public void makeWorkShops()
    {
        workshops[0] = new CakeBakery(0, mapLength - 1);
        workshops[1] = new CookieBakery(0, mapLength / 2);
        workshops[2] = new EggPowderPlant(0, 0);
        workshops[3] = new SewingFactory(mapWidth - 1, mapLength - 1);
        workshops[4] = new WeavingFactory(mapWidth - 1, (int)mapLength/2);
        workshops[5] = new Spinnery(mapWidth - 1, 0);
        workshops[6] = new CustomFactory(0,mapWidth / 2);
    }

    public int startWorkShop(String name)
    {
        Workshop workshop = null;
        for (Workshop w : workshops)
            if (w.getWorkShopName().equals(name))
            {
                workshop = w;
                break;
            }
        if (workshop == null)
            return -1;
        if (workshop.isWorking())
            return -2;
        workshop.setCount(availableInputCount(workshop.getInputs(), workshop.getLevel()));
        if (workshop.getCount() > 0)
        {
            for (String s : workshop.getInputs())
            {
                Iterator<Item> iterator = wareHouse.getCollectedItems().iterator();
                int count = 0;
                while (iterator.hasNext() && count < workshop.getCount())
                {
                    Item item = iterator.next();
                    if (item.getKind().equals(s))
                    {
                        iterator.remove();
                        count++;
                    }
                }
            }
            workshop.setWorkingTime(10);
            workshop.setCurrentTime(workshop.getWorkingTime());
            workshop.setWorking(true);
        }
        return workshop.getCount();
    }

    public int availableInputCount(Vector<String> inputs, int initial)
    {
        int min = initial;
        for (String s : inputs)
        {
            int count = 0;
            for (Item item : wareHouse.getCollectedItems())
                if (item.getKind().equals(s))
                    count++;
            if (count < min)
                min = count;
        }
        return min;
    }

    public void endWorkShop(Workshop workshop) {
        workshop.setWorking(false);
        for (int i = 0; i < workshop.getCount(); i++) {
            Item item = new Item(workshop.getItem_x(), workshop.getItem_y(), workshop.getOutput());
            stuffs.add(item);
            updateAchievement(item.getKind());
        }
    }
}
