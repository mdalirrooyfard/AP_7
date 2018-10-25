package Model;

public class Bazaar extends Item{
    private  int numberOfEmployee ;
    int blockId , unitId ;
    public Bazaar(){
        setBlockId();
        setUnitId();
        bazaarLevel = 1 ;
        numberOfEmployee = 50 ;
    }
    public Bazaar upgrade(){
        bazaarLevel ++ ;
        numberOfEmployee += 20 ;
        return this ;
    }

    public void setBlockId() {
        blockId = getBlockId();
    }

    public void setUnitId(){
        unitId = getUnitId() ;
    }

    public void sendCost(int cost){
        cost = -(bazaarLevel*5000);
        getFinalCost(cost);
    }

    public int getNumberOfEmployee() {
        return numberOfEmployee;
    }



}
