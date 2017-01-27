package battleships.logic;

public class Ship {

    private int size;
    private int hits;

    public Ship(int size) {
        this.size = size;
        this.hits = 0;
    }

    public void shoot() {
        hits++;
    }

    public Boolean didItSink() {
        return size <= hits;
    }

    public int getSize() {
        return size;
    }
}
