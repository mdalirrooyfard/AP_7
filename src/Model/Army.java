package Model;

public class Army extends Item
{
    int level , numberOfEmployee , blockId , unitId;
    double powerLevel;
    boolean existance;
    final int UPGRADE_COST = -20000;
    final int ADD_COST = -15000;
    final int REMOVE_COST = 10000;

    public int getBlockId()
    {
        return blockId;
    }
    public int getUnitId()
    {
        return unitId;
    }
    public int getUPGRADE_COST()
    {
        return UPGRADE_COST;
    }
    public int getADD_COST()
    {
        return ADD_COST;
    }
    public int REMOVE_COST()
    {
        return REMOVE_COST;
    }
    public Army add( int blockId , int unitId )
    {
        if( !existance )
        {
            existance = true;
            level = 1;
            numberOfEmployee = 100;
            this.blockId = blockId;
            this.unitId = unitId;
            powerLevel = 0.2;
        }
        return this;
    }
}
