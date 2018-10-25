package Model;

public class Bazaar extends Item{
    private  int level ;
    private  int numberOfEmployee ;
    int blockId , unitId ;
    public Bazaar(){
        setBlockId();
        setUnitId();
        level = 1 ;
        numberOfEmployee = 50 ;
    }
    public Bazaar upgrade(){
        level ++ ;
        numberOfEmployee += 20 ;
        return this ;
    }

    public void setBlockId() {
        blockId = getBlockId();
    }

    public void setUnitId(){
        unitId = getUnitId() ;
    }

    public void getCost(int cost){
        cost = -(level*5000);
        getFinalCost(cost);
    }

    public int getNumberOfEmployee() {
        return numberOfEmployee;
    }

}
