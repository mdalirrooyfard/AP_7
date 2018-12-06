package Model;

public class Grass extends Entity{
    private boolean isEaten = false ;

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    public Grass (double x, double y){
        this.x = x ;
        this.y = y ;
    }
}
