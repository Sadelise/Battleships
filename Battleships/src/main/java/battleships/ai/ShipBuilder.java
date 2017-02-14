package battleships.ai;

import battleships.logic.Player;
import battleships.logic.Ship;
import java.util.Random;

/**
 * Class generates viable Ship Objects.
 */
public class ShipBuilder {

    private Random guesser;
    private int[] x;
    private int[] y;

    /**
     * Method constructs an implementation of the class and initialises
     * variables.
     */
    public ShipBuilder() {
        this.guesser = new Random();
    }

    /**
     * Method initialises the production of several Ship Objects which are
     * eventually placed into the location map of a player.
     *
     * @param player The player that will receive the ships
     * @param amount The amount of ships that will be produced
     * @param biggest The size of the biggest ship in the fleet
     *
     */
    public void buildAllShips(Player player, int amount, int biggest) {
        int size = biggest;
        for (int i = 0; i < amount; i++) {
            randomizeStartingCoordinate(size, player);
            if (i != 2) {
                size--;
            }
        }
    }

    private void randomizeStartingCoordinate(int size, Player player) {
        while (true) {
            int cx = guesser.nextInt(10);
            int cy = guesser.nextInt(10);
            int orientation = guesser.nextInt(2);
            Ship ship = buildShip(cx, cy, orientation, size);
            if (player.addShip(ship)) {
                break;
            }
        }
    }

    /**
     * Method creates a single ship according to parameters and returns it.
     *
     * @param cx The x coordinate of the Ships first piece
     * @param cy The y coordinate of the Ships first piece
     * @param orientation The orientation of the Ship
     * @param size The size of the Ship
     *
     * @return The created Ship
     */
    public Ship buildShip(int cx, int cy, int orientation, int size) {
        x = new int[size];
        y = new int[size];
        for (int i = 0; i < size; i++) {
            x[i] = cx;
            y[i] = cy;
            if (orientation == 0) {
                cx++;
            }
            if (orientation == 1) {
                cy++;
            }
        }
        return new Ship(size, x, y);
    }

}
