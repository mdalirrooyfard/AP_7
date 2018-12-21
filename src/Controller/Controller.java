package Controller;

import Model.Farm;
import View.View;

public class Controller {
    View view = new View();
    Farm farm ;
    String[] command ;
    public void commandHandler()
    {

    }

    public void buyHandler(String animalName)
    {
        switch (animalName)
        {
            case "sheep":
                if (!farm.addSheep())
                    view.printError("Not Enough Money! :'( ");
                break;
            case "cow":
                if (!farm.addCow())
                    view.printError("Not Enough Money! :'( ");
                break;
            case "hen":
                if (!farm.addHen())
                    view.printError("Not Enough Money! :'( ");
                break;
            case "cat":
                if (!farm.addCat())
                    view.printError("Not Enough Money! :'( ");
                break;
            case "dog":
                if (!farm.addDog())
                    view.printError("Not Enough Money! :'( ");
                break;
        }
    }

    public void pickUp(double x , double y)
    {

    }

    public void cage(double x , double y){}

    public void plant(double x , double y)
    {
        if(!farm.plantGrass(x,y))
            view.printError("Not Enough Money! :'( ");
    }

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
