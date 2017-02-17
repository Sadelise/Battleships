package battleships.logic;

/**
 * Class contains the location and size information of a ship and records when
 * players manage to guess the ships location correctly.
 */
public class Ship {

    private final int[] x;
    private final int[] y;
    private final int size;
    private int hits;

    /**
     * Method constructs an implementation of the class and initialises
     * variables.
     *
     * @param size The amount of slots in the array the ship must occupy
     * @param x The x coordinates of the slots in the array the ship occupies.
     * @param y The y coordinates of the slots in the array the ship occupies.
     */
    public Ship(int size, int[] x, int[] y) {
        this.size = size;
        this.hits = 0;
        this.x = x;
        this.y = y;
    }

    /**
     * Method increases the hit counter by one.
     */
    public void shot() {
        hits++;
    }

    /**
     * Method checks if there are more or equal amount of hits on the ship than
     * there are pieces of the ship.
     *
     * @return True if there are more or equal amount of hits than the are
     * pieces, otherwise false
     */
    public Boolean didItSink() {
        return size <= hits;
    }

    /**
     * Method returns the size of the Ship.
     *
     * @return Size
     */
    public int getSize() {
        return size;
    }

    /**
     * Method returns the array containing all the x coordinates for the ship
     * sections.
     *
     * @return Array of integers, marking the x coordinates
     */
    public int[] getXcoordinates() {
        return x;
    }

    /**
     * Method returns the array containing all the y coordinates for the ship
     * sections.
     *
     * @return Array of integers, marking the y coordinates
     */
    public int[] getYcoordinates() {
        return y;
    }
}
