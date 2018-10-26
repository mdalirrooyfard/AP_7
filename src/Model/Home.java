package Model;

import java.util.ArrayList;

public class Home extends Item
{
    private int numberOfFloors , numberOfUnit , blockId;
    private ArrayList<Floor> floors = new ArrayList<>();
    private final int scoreConst = 10;

    public Home(int numberOfFloors, int numberOfUnit)
    {
        this.numberOfFloors = numberOfFloors;
        this.numberOfUnit = numberOfUnit;
        for (int i = 0; i < numberOfFloors; i++)
            floors.add(new Floor(numberOfUnit));
    }

    public int getBlockId()
    {
        return blockId;
    }

    public int getNumberOfUnit()
    {
        return numberOfUnit;
    }

    public void getNumberOfFloor()
    {
        this.numberOfUnit = numberOfFloors;
    }

    public ArrayList<Floor> getFloors() {
        return floors;
    }

    public void increaseFloor(int x) {
        this.numberOfFloors += x;
        for (int i = 0; i < x; i++) {
            this.floors.add(new Floor(numberOfUnit));
        }
    }

    public void increaseUnit(int x) {
        this.numberOfUnit += x;
        for (Floor floor : floors) {
            floor.increaseUnit(x);
        }
    }

    @Override
    public void update()
    {
        floors.add(new Floor(numberOfUnit));
        numberOfFloors++;
    }

    @Override
    public void remove(int id)
    {

    }

    @Override
    public double calculateScore()
    {
        double personScore = calculatePersonScore();
        double unitScore = calculateUnitScore();
        double floorScore = calculateFloorScore();
        double homeScore = personScore * 3 + unitScore * 2 + floorScore + score;
        return homeScore + floorScore + unitScore + personScore;
    }

    private double calculatePersonScore(){
        double sum = 0;
        for (Floor floor: floors){
            for (Unit unit: floor.getUnits())
                sum += unit.getPersonList().size() * unit.getPersonList().get(0).getSatisfactory();
        }
        return sum;
    }

    private double calculateUnitScore() {
        double sum = 0;
        for (Floor floor: floors){
            for (Unit unit: floor.getUnits())
                sum += unit.calculateScore();
        }
        return sum;
    }

    private double calculateFloorScore(){
        double sum = 0;
        for (Floor floor: floors){
            sum += floor.calculateScore();
        }
        return sum;
    }

    public ArrayList<person> getPeople() {
        ArrayList<person> people = new ArrayList<>();
        for (Floor floor : floors)
            for (Unit unit : floor.getUnits())
                for (person person : unit.getPersonList())
                    people.add(person);
        return people;
    }
}
