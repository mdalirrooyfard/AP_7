package Model;

import java.util.ArrayList;

public class Block {
    private ArrayList<Item> items = new ArrayList();
    private int numberOfItems = 0;
    private int numberOfPersons = 0;
    private int level = 3;
    private  int blockId = 0;

    public Block(){
        blockId++;
    }

    public int getBlockId(){
        return blockId;
    }

    public  void remove(){

    }

    public void upgrade(){

    }

    public void addPerson(int number){
        numberOfPersons += number;
    }
}
