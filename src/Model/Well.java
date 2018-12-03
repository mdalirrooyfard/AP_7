package Model;

import static Model.Constants.WELL_CAPACITY;

public class Well extends Entity{
    private int currentVolume;

    public Well(){
        this.setVolume(WELL_CAPACITY);
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public void decrease (int amount){
        currentVolume -= amount ;
    }

    public void full (){
        currentVolume = this.getVolume() ;
    }
    @Override
    public void upgrade(){
        super.upgrade();
        //TODO complete
    }
}
