package Model;
import java.util.ArrayList;

 public class User {
    private ArrayList<Block> blocks = new ArrayList();
    private int numberOfBlocks = 0;
    private String kind;
    private int fund = 30000;
    private double score = 0;

    public User(String kind){
       this.kind = kind;
    }
    public double getScore(){
       return score;
    }

    public int getFund(){
       return fund;
    }
    public void addBlock(String kind){
       Block block = new Block(kind);
       blocks.add(block);
       numberOfBlocks ++;
       fund -= 1000;
    }
    public void removeBlock(int index){
       if(index == numberOfBlocks)
       fund += 500;
    }

 }
