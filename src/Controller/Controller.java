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

    public void pickUpHandler(double x , double y)
    {
        if(!farm.pickUp(x,y))
            view.printError("Warehouse is full! :'(");
    }

    public void cageHandler(double x , double y)
    {
        if(!farm.putCage(x,y))
            view.printError("No wild animal is here!");
    }

    public void plantHandler(double x , double y)
    {
        if(!farm.plantGrass(x,y))
            view.printError("Not Enough Money! :'( ");
    }

    public void wellHandler()
    {
        if(!farm.fullWell())
            view.printError("Not Enough Money! :'( ");
    }

    public void upgradeHandler(String entityName)
    {
        if( !farm.upgrade(entityName) )
            view.printError("Not Enough Money! :'( ");
    }

    public void turnHandler(int n)
    {
        boolean isLevelFinished = false;
        for (int i = 0 ; i < n && !isLevelFinished ; i++)
            if(farm.turn())
                isLevelFinished = true;
        if (isLevelFinished)
            view.levelIsFinished();
    }

    public void loadHandler(String path){}

    public void runHandler(String mapName){}

    public void saveHandler(String path){}

    public void loadGameHandler(String path){}

    public void saveGameHandler(String path){}

    public void printHandler(String what){}

    public void addToTransportationHandler(String  vehicle , String name , int count)
    {
        if (vehicle.equals("helicopter"))
            farm.addToHellicopter(name,count);
        else if(vehicle.equals("truck"))
            farm.addToTruck(name,count);
    }

    public void clearFromTransportationHandler(boolean vehicle){}

    public void goHandler(boolean vehicle){}

    public void startWorkShopHandler(String name){}

}
