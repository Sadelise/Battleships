package battleships.ai;

import battleships.logic.Player;
import battleships.logic.Ship;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class generates the coordinates the AI opponent in Battleships will play.
 */
public class Ai extends Player {

    private Boolean shipWasHit;
    private List<Integer> destroyedX;
    private List<Integer> destroyedY;
    private List<Integer> acceptableX;
    private List<Integer> acceptableY;
    private Random guesser;

    /**
     * Method constructs an implementation of the class and initialises
     * variables.
     *
     * @param shipLimit The amount of ships the Ai will place on its map.
     */
    public Ai(int shipLimit) {
        guesser = new Random();
        ShipBuilder sb = new ShipBuilder();
        sb.buildAllShips(this, shipLimit, 5);
        shipWasHit = false;
        destroyedX = new ArrayList<>();
        destroyedY = new ArrayList<>();
    }

    /**
     * Method randomises the coordinates AI will play from a list of acceptable
     * coordinates.
     *
     *
     * @return array of two integers - x and y coordinates
     */
    public int[] getShootingCoordinates() {
        acceptableX = new ArrayList();
        acceptableY = new ArrayList();
        int[] coordinates = {-1, -1};
        acceptableCoordinates();
        if (acceptableX.size() > 0) {
            int randomIndex = guesser.nextInt(acceptableX.size());
            coordinates[0] = acceptableX.get(randomIndex);
            coordinates[1] = acceptableY.get(randomIndex);
        }
        return coordinates;
    }

    /**
     * Method receives the results of latest turn as parameters and updates the
     * class variables accordingly.
     *
     * @param hit True if the last coordinates returned contained an enemy ship
     * @param ship Contains the enemy ship that was sunk, otherwise null
     * @param x Contains the x coordinate that was last played
     * @param y Contains the y coordinate that was last played
     *
     */
    @Override
    public void feedback(Boolean hit, Ship ship, int x, int y) {
        if (hit == true) {
            super.getEnemyMap()[x][y] = 1;
            destroyedX.add(x);
            destroyedY.add(y);
            shipWasHit = true;
        } else {
            super.getEnemyMap()[x][y] = -1;
        }
        if (ship != null) {
            negateSurroundingAreaAfterSinking(ship);
            shipWasHit = false;
        }
    }

    private void acceptableCoordinates() {
        if (!shipWasHit) {
            acceptAllFreeCoordinates();
        } else {
            Collections.sort(destroyedX);
            Collections.sort(destroyedY);
            acceptOnlySurroundings(destroyedX.size());
        }
    }

    private void acceptOnlySurroundings(int size) {
        int firstX = destroyedX.get(0);
        int lastX = destroyedX.get(destroyedX.size() - 1);
        int firstY = destroyedY.get(0);
        int lastY = destroyedY.get(destroyedY.size() - 1);
        if (size == 1 || isHorizontal()) {
            addAcceptable(firstX - 1, firstY);
            addAcceptable(lastX + 1, firstY);
        } else if (size == 1 || !isHorizontal()) {
            addAcceptable(firstX, firstY - 1);
            addAcceptable(firstX, lastY + 1);
        }
    }

    private Boolean isHorizontal() {
        if (destroyedY.size() > 1) {
            int y1 = destroyedY.get(0);
            int y2 = destroyedY.get(1);
            if (y1 == y2) {
                return true;
            }
        }
        return false;
    }

    private void addAcceptable(int x, int y) {
        if (withinBoundaries(x, y)) {
            acceptableX.add(x);
            acceptableY.add(y);
        }
    }

    private void acceptAllFreeCoordinates() {
        for (int y = 0; y < super.getEnemyMap().length; y++) {
            for (int x = 0; x < super.getEnemyMap().length; x++) {
                if (super.getEnemyMap()[x][y] == 0) {
                    acceptableX.add(x);
                    acceptableY.add(y);
                }
            }
        }
    }

    private Boolean withinBoundaries(int x, int y) {
        return y >= 0
                && x >= 0
                && y < super.getEnemyMap().length
                && x < super.getEnemyMap().length;
    }
}