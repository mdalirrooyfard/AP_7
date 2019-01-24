package Model;

public class Grass extends Entity{
    private int isEaten ;
    private boolean eatenAlready = false;
    @Override
    public double getShowX() {
        return getX()*Constants.ANIMAL_SHOW_SCALE + Constants.GRASS_DISPLACEMENT_X;
    }
    @Override
    public double getShowY() {
        return getY()*Constants.ANIMAL_SHOW_SCALE + Constants.GRASS_DISPLACEMENT_Y;
    }
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

    public boolean isEatenAlready() {
        return eatenAlready;
    }

    public void setEatenAlready(boolean eatenAlready) {
        this.eatenAlready = eatenAlready;
    }
}
