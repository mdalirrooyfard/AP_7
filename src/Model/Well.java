package Model;

public class Well extends Entity{
    private int currentVolume;
    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }
    @Override
    public void upgrade(){
        super.upgrade();
        //TODO complete
    }
}
