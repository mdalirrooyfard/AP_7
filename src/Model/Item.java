package Model;

public abstract class Item {
    protected long cost ;
    protected int id ;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int level;

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public abstract void update();
    public abstract void remove(int id);
    public abstract double calculateScore();



}
