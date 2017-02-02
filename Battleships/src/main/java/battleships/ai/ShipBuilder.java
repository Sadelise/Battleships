package battleships.ai;

import battleships.logic.Player;
import battleships.logic.Ship;
import java.util.Random;

class ShipBuilder {

    private Player ai;
    private Random guesser;

    public ShipBuilder(Player ai, Random guesser) {
        this.ai = ai;
        this.guesser = guesser;
    }

    public void buildShips(int amount, int biggest) {
        int size = biggest;
        for (int i = 0; i < amount; i++) {
            randomizeStartingCoordinate(size);
            if (i != 4) {
                size--;
            }
        }
    }

    private void randomizeStartingCoordinate(int size) {
        while (true) {
            int x = guesser.nextInt(10);
            int y = guesser.nextInt(10);
            int direction = guesser.nextInt(2);
            if (buildShip(x, y, direction, size)) {
                break;
            }
        }
    }

    private Boolean buildShip(int x, int y, int direction, int size) {
        int[] cx = new int[size];
        int[] cy = new int[size];
        for (int i = 0; i < size; i++) {
            cx[i] = x;
            cy[i] = y;
            if (direction == 0) {
                x++;
            }
            if (direction == 1) {
                y++;
            }
        }
        return ai.addShip(new Ship(size, cx, cy), cx, cy);
    }

}
