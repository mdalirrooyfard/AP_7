package Model;

import java.util.ArrayList;
import Model.Item;
import Controller.itemcounter;

public class Block extends Item{
    private int capacity = 15;
    private itemcounter counter = new itemcounter();
    private ArrayList<Item> items = new ArrayList<>();

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public double calculateScore() {
        double score = 0;
        for(Item item : items){
            score += item.calculateScore();
        }
        return score;
    }

    @Override
    public void update() {

    }

    @Override
    public void remove(int id) {

    }

    public Item getItemById(int id){
        for (Item item: items){
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }

    public itemcounter getitemCounter() {
        return counter;
    }
}
