package Model;

public  class Item {
    private  int unitId = 0 ;
    private int blockId ;
    protected int bazaarLevel = 0 ;
    private String kind ;
        public  int getUnitId(){
        return unitId;
    }

    public Item(){}

    public Item(int id , String kind){
            blockId = id ;
            this.kind = kind ;
    }


    public int getBlockId(){
        return blockId ;
    }

    public int getFinalCost(int cost){
            return cost ;
    }

    public int getBazaarLevel(){
          return bazaarLevel;
    }




}
