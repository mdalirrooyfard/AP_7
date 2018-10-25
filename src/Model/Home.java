package Model;

public class Home extends Item
{
    private boolean[][] units = new boolean[6][4];
    private int numberOfFloors , numberOfUnitsPerFloor , blockId , unitId;

    public int getBlockId()
    {
        return blockId;
    }
    public int getUnitId()
    {
        return unitId;
    }
    public int getNumberOfFloors()
    {
        return numberOfFloors;
    }
    public int getNumberOfUnitsPerFloor()
    {
        return numberOfUnitsPerFloor;
    }
    public void setBlockId( int blockId )
    {
        this.blockId = blockId;
    }
    public void setUnitId( int unitId )
    {
        this.unitId = unitId;
    }
    public Home upgrade()
    {
        numberOfUnitsPerFloor++;
        numberOfFloors++;
        for( int i = 0 ; i < numberOfFloors ; i++ )
            for( int j = 0 ;  j < numberOfUnitsPerFloor ; j++ )
                units[i][j] = true;
        return this;
    }
    public int upgradeCost()
    {
        return  -( numberOfUnitsPerFloor * numberOfFloors - ( numberOfUnitsPerFloor - 1 ) * ( numberOfFloors - 1 ) ) * 50 - 300;
    }
    public Home add( int numberOfFloors , int numberOfUnitsPerFloor , int blockId , int unitId )
    {
        this.numberOfFloors = numberOfFloors;
        this.numberOfUnitsPerFloor = numberOfUnitsPerFloor;
        this.unitId = unitId;
        this.blockId = blockId;
        for( int i = 0 ; i < numberOfFloors ; i++ )
            for( int j = 0 ; j < numberOfUnitsPerFloor ; j++ )
                units[i][j] = true;
        return this;
    }
    public int addCost( int numberOfFloors , int numberOfUnitsPerFloor )
    {
        return -( numberOfFloors * 100 + numberOfFloors * 300 + 700 );
    }
    public double scoreOfHome( int level )
    {
        double person = 1;
        if( level != 0 )
            person = 1 + 0.2 * level;
         return ( 10 + 3 * numberOfFloors + 5 * numberOfUnitsPerFloor * numberOfFloors + 30 * person * numberOfFloors * numberOfUnitsPerFloor );
    }
}
