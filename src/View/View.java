package View;

import java.util.Scanner;

public class View
{
    String command;
    Scanner scanner;
    public String getCommand()
    {
        return command;
    }
    public void printError(String error)
    {
        System.out.println(error);
    }
    public void levelIsFinished()
    {
        System.out.println("Level is finished.  Congratulations! :) ");
    }
    public void printInfo(String info)
    {
        System.out.println(info);
    }
    public void setCommand(Scanner scanner)
    {
        this.scanner = scanner;
        command = scanner.nextLine();
    }
}
