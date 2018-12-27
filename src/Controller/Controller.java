package Controller;

import Model.Farm;
import View.View;
import com.gilecode.yagson.YaGson;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;

public class Controller {
    private View view = new View();
    private Farm farm ;
    private String command ;
    private String paths;
    private boolean isLevelFinished = false;

    public void getCommand()
    {
        while(!isLevelFinished)
        {
            view.setCommand();
            commandHandler();
        }
    }

    public void commandHandler()
    {
        if( command.matches("buy sheep|cow|hen") )
            buyHandler(command.substring(4));
        else if( command.matches("pickup [0-9]+ [0-9]+"))
            pickUpHandler(Double.parseDouble(command.substring(7,8)),Double.parseDouble(command.substring(9)));
        else if ( command.matches("cage [0-9]+ [0-9]+"))
            cageHandler(Double.parseDouble(command.substring(5,6)),Double.parseDouble(command.substring(7)));
        else if( command.matches("plant [0-9]+ [0-9]+") )
            plantHandler(Double.parseDouble(command.substring(6,7)),Double.parseDouble(command.substring(8)));
        else if( command.matches("well") )
            wellHandler();
        else if( command.matches("start " +
                "eggpowderplant|cakebaker|cookiebakery|customfactory|sweingfactory|spinnery|weavingfactory") )
            startWorkShopHandler(command.substring(6));
        else if( command.matches("upgrade " +
                "eggpowderplant|cakebaker|cookiebakery|customfactory|sweingfactory|spinnery|weavingfactory|" +
                "cat|well|truck|helicopter|warehouse"))
            upgradeHandler(command.substring(7));
        else if( command.startsWith("load game "))
            loadGameHandler(command.substring(10));
        else if( command.startsWith("save game "))
            saveGameHandler(command.substring(10));
        else if( command.startsWith("run ") )
            runHandler(command.substring(4));
        else if( command.matches("turn [0-9]+") )
            turnHandler(Integer.parseInt(command.substring(5)));
        else if( command.matches("truck|helicopter add [item_name] [count]") )

        switch (this.command[0])
        {
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
            case "print":
                if( command.length == 2 )
                    printHandler(command[1]);
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
        for (int i = 0 ; i < n && !isLevelFinished ; i++)
            if(farm.turn())
                isLevelFinished = true;
        if (isLevelFinished)
            view.levelIsFinished();
    }

    public void loadHandler(String path)
    {
        paths = path;
    }

    public void runHandler( String mapName )
    {
        mapHandler();
        goalsHandler();
        workShopHandler();
    }

    public void mapHandler()
    {
        try
        {
            InputStream inputStream = new FileInputStream(paths + "\\map.txt");
            Scanner scanner = new Scanner(inputStream);
            int length = 0;
            while(scanner.hasNext())
            {
                String string = scanner.next();
                switch (string)
                {
                    case "length":
                        length = scanner.nextInt();
                        break;
                    case "width":
                        farm = new Farm(length, scanner.nextInt());
                        break;
                    case "money":
                        farm.increaseMoney(scanner.nextInt());
                        break;
                    case "hen":
                        int count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addHen(false);
                        break;
                    case "cow":
                        count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addCow(false);
                        break;
                    case "sheep":
                        count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addSheep(false);
                        break;
                    case "cat":
                        count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addCat(false);
                        break;
                    case "dog":
                        count = scanner.nextInt();
                        for (int i = 0; i < count; i++)
                            farm.addDog(false);
                        break;
                    default:
                }
            }
        }
        catch ( IOException e )
        {
            view.printError("No directory is loaded");
        }
    }

    public void goalsHandler()
    {
        try
        {
            InputStream inputStream = new FileInputStream(paths+"\\goals.txt");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext())
            {
                String name = scanner.next();
                int count = scanner.nextInt();
                farm.getGoals().put(name, count);
            }
        }
        catch (FileNotFoundException e)
        {
            view.printError("No directory is loaded");
        }
    }

    public void workShopHandler()
    {
        try
        {
            InputStream inputStream = new FileInputStream(paths+"\\workShops.txt");
            Scanner scanner = new Scanner(inputStream);
            ArrayList<String> workShops = new ArrayList<>();
            while(scanner.hasNext())
            {
                workShops.add(scanner.nextLine());
            }
            farm.makeWorkShops(workShops);
        }
        catch (FileNotFoundException e)
        {
            view.printError("No directory is loaded");
        }

    }

    public void saveGameHandler(String path)
    {
        Date date = new Date();
        try
        {
            OutputStream outputStream = new FileOutputStream(path + "\\game" + Long.toString(date.getTime()) + ".txt");
            Formatter formatter = new Formatter(outputStream);
            YaGson yaGson = new YaGson();
            String savedFarm = yaGson.toJson(farm);
            formatter.format(savedFarm);
            formatter.flush();
            formatter.close();
        }
        catch ( IOException e )
        {
            view.printError("No directory is loaded!");
        }

    }

    public void loadGameHandler(String path)
    {
        try
        {
            InputStream inputStream = new FileInputStream(path);
            Scanner scanner = new Scanner(inputStream);
            YaGson yaGson = new YaGson();
            String savedFarm = scanner.nextLine();
            farm = yaGson.fromJson(savedFarm,Farm.class);
        }
        catch ( IOException e )
        {
            view.printError("No directory is loaded!");
        }
    }

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
