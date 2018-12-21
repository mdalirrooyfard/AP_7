package View;

public class View
{
    String command;
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
}
