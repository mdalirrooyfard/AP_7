package Model;

public class Defense
{
    int level , numberOfEmployee , blockId , unitId;
    double powerLevel;
    final int UPGRADE_COST = -10000;
    final int ADD_COST = -5000;
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
    public Defense( int blockId , int unitId )
    {
        level = 1;
        numberOfEmployee = 30;
        powerLevel = 0.2;
        this.blockId = blockId;
        this.unitId = unitId;
    }
    public Defense powerLevelUpgrade()
    {
        if( level < 5 )
        {
            numberOfEmployee += 10;
            powerLevel += 0.2;
            level += 1;
        }
        return this;
    }
}
