package battleships.logic;

public class Laiva {

    private int size;
    private int hits;

    public Laiva(int size) {
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
