package Model;

import Model.Animals.Cat;
import Model.Animals.Dog;
import Model.Animals.Domestic.Cow;
import Model.Animals.Domestic.Hen;
import Model.Animals.Domestic.Sheep;
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

    public Farm(int length, int width){
        mapLength = length;
        mapWidth = width;
        //TODO new map
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

    public void plantGrass(double x, double y){
        int centerX = (int) x;
        int centerY = (int) y;
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
    }

    public void addDog(){
        Dog dog = new Dog(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(dog);
    }

    public void addCat(){
        Cat cat = new Cat(makeRandomXAndY(mapWidth), makeRandomXAndY(mapLength), map);
        stuffs.add(cat);
    }





}
