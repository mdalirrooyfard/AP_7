package Controller;

import Model.Farm;
import View.View;

public class Controller {
    View view = new View();
    Farm farm ;
    String[] command ;
    public void commandHandler()
    {
        this.command = view.getCommand().toLowerCase().split(" ");
        switch (this.command[0])
        {
            case "buy":
                if( command.length == 2 )
                    buyHandler(command[1]);
                else
                    view.printError("Format of command is wrong!");
                break;
            case "pickup":
                if( command.length == 3 )
                    pickUpHandler(Double.parseDouble(command[1]),Double.parseDouble(command[2]));
                else
                    view.printError("Format of command is wrong!");
                break;
            case "cage":
                if( command.length == 3 )
                    cageHandler(Double.parseDouble(command[1]),Double.parseDouble(command[2]));
                else
                    view.printError("Format of command is wrong!");
                break;
            case "plant":
                if( command.length == 3 )
                    plantHandler(Double.parseDouble(command[1]),Double.parseDouble(command[2]));
                else
                    view.printError("Format of command is wrong!");
                break;
            case "well":
                if( command.length == 1 )
                    wellHandler();
                else
                    view.printError("Format of command is wrong!");
                break;
            case "start":
                if( command.length == 2 )
                    startWorkShopHandler(command[1]);
                else
                    view.printError("Format of command is wrong!");
                break;
            case "upgrade":
                if( command.length == 2 )
                    upgradeHandler(command[1]);
                else
                    view.printError("Format of command is wrong!");
                break;
            case "load":
                if( command.length == 3 )
                {
                    if( command[1].equals("custom") )
                        loadHandler(command[2]);
                    else if( command[1].equals("game") )
                        loadGameHandler(command[2]);
                    else
                        view.printError("Format of command is wrong!");
                }
                else
                    view.printError("Format of command is wrong!");
                break;
            case "run":
                if( command.length == 2 )
                    runHandler(command[1]);
                else
                    view.printError("Format of command is wrong!");
                break;
            case "save":
                if( command.length == 3 )
                {
                    if( command[1].equals("game") )
                        saveGameHandler(command[2]);
                    else
                        view.printError("Format of command is wrong!");
                }
                else
                    view.printError("Format of command is wrong!");
                break;
            case "print":
                if( command.length == 2 )
                    printHandler(command[1]);
                else
                    view.printError("Format of command is wrong!");
                break;
            case "turn":
                if( command.length == 2 )
                    turnHandler(Integer.parseInt(command[1]));
                else
                    view.printError("Format of command is wrong!");
                break;
            case "truck":
            case "helicopter":
                if( command.length > 1 )
                {
                    switch (command[1])
                    {
                        case "add":
                            if( command.length == 4 )
                                addToTransportationHandler(command[0],command[2],Integer.parseInt(command[3]));
                            else
                                view.printError("Format of command is wrong!");
                            break;
                        case "clear":
                            if( command.length == 2 )
                                clearFromTransportationHandler(command[0]);
                            else
                                view.printError("Format of command is wrong!");
                            break;
                        case "go":
                            if( command.length == 2 )
                                goHandler(command[0]);
                            else
                                view.printError("Format of command is wrong!");
                            break;
                        default:
                            view.printError("Format of command is wrong!");
                    }
                }
                else
                    view.printError("Format of command is wrong!");
                break;
            default:
                view.printError("Format of command is wrong!");
        }
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

    public void printHandler(String what)
    {
        switch (what)
        {
            case "info":view.printInfo(farm.printInfo());break;
            case "map"://TODO view.printInfo(farm.printMap());
                break;
            case "levels":view.printInfo(farm.printLevel());break;
            case "warehouse":view.printInfo(farm.printWareHouse());break;
            case "well":view.printInfo(farm.printWell());break;
            case "workshops"://TODO view.printInfo(farm.printWorkshops());
                break;
            case "helicopter"://TODO view.printInfo(farm.printHelicopter());
                break;
            case "truck"://TODO view.printInfo(farm.printTruck());
                break;
        }
    }

    public void addToTransportationHandler(String  vehicle , String name , int count)
    {
        if (vehicle.equals("helicopter"))
        {
            int c = farm.addToHellicopter(name,count);
            if( c < count )
                view.printError("More than helicopter capacity! "+Integer.toString(c)+" is added.");
        }
        else if(vehicle.equals("truck"))
        {
            int c = farm.addToTruck(name,count);
            if( c < count )
                view.printError("More than helicopter capacity! "+Integer.toString(c)+" is added.");
        }
    }

    public void clearFromTransportationHandler(String vehicle)
    {
        if (vehicle.equals("helicopter"))
            farm.clearFromHelicopter();
        else if(vehicle.equals("truck"))
            farm.clearFromTruck();
    }

    public void goHandler(String vehicle)
    {
        farm.goTransportation(vehicle.equals("truck"));
    }

    public void startWorkShopHandler(String name)
    {
        farm.startWorkShop(name);
    }

}
