package battleships.logic;

public class Ship {

    private int[] x;
    private int[] y;
    private int size;
    private int hits;

    public Ship(int size, int[] x, int[] y) {
        this.size = size;
        this.hits = 0;
        this.x = x;
        this.y = y;
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

    public int[] getXcoordinates() {
        return x;
    }

    public int[] getYcoordinates() {
        return y;
    }
}
