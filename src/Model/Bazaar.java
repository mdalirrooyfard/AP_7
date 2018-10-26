package Model;

import Model.Item;
import Model.person;

import java.util.ArrayList;

public class Bazaar extends Item{
    private  long numberOfEmployee = 50L;
    private double increaseScoreAmount = 0 ;
    private int level = 1;
    private final int score = 5;
    private int daysOfBeing = 1;
    private int blockId;
    private ArrayList<person> employees= new ArrayList<>();
    public Bazaar(){
        setBlockId();
    }
    public ArrayList<person> getEmployees(){
        return employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIncreaseScoreAmount(){
        return increaseScoreAmount;
    }

    public void setIncreaseScoreAmount(double increaseScoreAmount){
        this.increaseScoreAmount = increaseScoreAmount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDaysOfBeing() {
        return daysOfBeing;
    }

    public void setDaysOfBeing(int daysOfBeing) {
        this.daysOfBeing = daysOfBeing;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void update() {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public double calculateScore() {
        return Math.pow(score, daysOfBeing);
    }



    public void setBlockId() {
        blockId = blockId;
    }


    public void sendCost(int cost){

    }

    public Long getNumberOfEmployee() {
        return numberOfEmployee;
    }

}
