package Model;

public class Grass extends Entity{
    private int isEaten = 8;
    @Override
    public double getShowX() {
        return getX()*Constants.ANIMAL_SHOW_SCALE + Constants.ANIMAL_DISPLACEMENT_X;
    }
    @Override
    public double getShowY() {
        return getY()*Constants.ANIMAL_SHOW_SCALE + Constants.ANIMAL_DISPLACEMENT_Y;
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
}
