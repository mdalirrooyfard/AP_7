package Model;

public class Grass extends Entity{
    private int isEaten = 8;

    public int isEaten() {
        return isEaten;
    }

    public void setEaten(int eaten) {
        isEaten = eaten;
    }

    public void decreaseEaten(){
        isEaten -= 1;
    }
    public Grass (double x, double y){
        this.x = x ;
        this.y = y ;
    }
}
