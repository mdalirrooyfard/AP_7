package Model;

import java.util.ArrayList;

public class Block extends Item{
    private String itemKind;
    private int numberOfItems = 0;
    private int numberOfPersons = 0;
    private int level = 1;
    private  int blockId = 0;

    public Block(String kind){
        blockId++;
        itemKind = kind;
    }

    public  int removeCost(){
        int cost = (int)Math.pow(500 , level);
        return cost;
    }

    public int upgrade(){
        this.level++;
        int cost = -500;
        return cost;
    }

}
