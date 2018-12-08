package Controller;

import Model.Farm;
import View.View;

public class Controller {
    View view = new View();
    Farm farm ;
    String command ;
    public void commandHandler(){}

    public void buyHandler(String animalName){}

    public void pickUp(double x , double y){}

    public void cage(double x , double y){}

    public void plant(double x , double y){}

    public void well(){}

    public void upgrade(String entityName){}

    public void turnHandler(int n){}

    public void loadHandler(String path){}

    public void runHandler(String mapName){}

    public void saveHandler(String path){}

    public void loadGameHandler(String path){}

    public void saveGameHandler(String path){}

    public void printHandler(String what){}

    public void addToTransportationHandler(boolean vehicle , String name , int count){}

    public void clearToTransportationHandler(boolean vehicle){}

    public void goHandler(boolean vehicle){}

    public void startWorkShopHandler(String name){}

}
