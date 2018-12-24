package Controller;

import Model.Farm;
import View.View;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Controller {
    private View view = new View();
    private Farm farm ;
    private String[] command ;
    private String paths;
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
                    runHandler();
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
                if (!farm.addSheep(true))
                    view.printError("Not Enough Money! :'( ");
                break;
            case "cow":
                if (!farm.addCow(true))
                    view.printError("Not Enough Money! :'( ");
                break;
            case "hen":
                if (!farm.addHen(true))
                    view.printError("Not Enough Money! :'( ");
                break;
            case "cat":
                if (!farm.addCat(true))
                    view.printError("Not Enough Money! :'( ");
                break;
            case "dog":
                if (!farm.addDog(true))
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

    public void loadHandler(String path){
        paths = path;
    }

    public void runHandler(){
        mapHandler();
        goalsHandler();
        //workShopHandler();
    }

    public void mapHandler(){
        try {
            InputStream inputStream = new FileInputStream(paths + "\\map.txt");
            Scanner scanner = new Scanner(inputStream);
            int length = 0;
            while(scanner.hasNext()){
                String string = scanner.next();
                switch (string){
                    case "length":{
                        length = scanner.nextInt();
                        break;
                    }
                    case "width":{
                        farm = new Farm(length, scanner.nextInt());
                        break;
                    }
                    case "money":{
                        farm.increaseMoney(scanner.nextInt());
                        break;
                    }
                    case "hen":{
                        int count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addHen(false);
                        break;
                    }
                    case "cow":{
                        int count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addCow(false);
                        break;
                    }
                    case "sheep":{
                        int count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addSheep(false);
                        break;
                    }
                    case "cat":{
                        int count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addCat(false);
                        break;
                    }
                    case "dog":{
                        int count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addDog(false);
                        break;
                    }
                    default:{
                    }
                }

            }
        }
        catch ( IOException e )
        {
            view.printError("No directory is loaded");
        }
    }

    public void goalsHandler(){
        try {
            InputStream inputStream = new FileInputStream(paths+"\\goals.txt");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()){
                String name = scanner.next();
                int count = scanner.nextInt();
                farm.getGoals().put(name, count);
            }
        } catch (FileNotFoundException e) {
            view.printError("No directory is loaded");
        }
    }



    public void saveHandler(String path){}

    public void loadGameHandler(String path){}

    public void saveGameHandler(String path){}

    public void printHandler(String what)
    {
        switch (what)
        {
            case "info":view.printInfo(farm.printInfo());break;
            case "map":view.printInfo(farm.printMap());break;
            case "levels":view.printInfo(farm.printLevel());break;
            case "warehouse":view.printInfo(farm.printWareHouse());break;
            case "well":view.printInfo(farm.printWell());break;
            case "workshops":view.printInfo(farm.printWorkshop(what));
                break;
            case "helicopter":view.printInfo(farm.printTransportation(false));
                break;
            case "truck":view.printInfo(farm.printTransportation(true));
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
