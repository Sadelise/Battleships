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
    private final List<Integer> destroyedX;
    private final List<Integer> destroyedY;
    private List<Integer> acceptableX;
    private List<Integer> acceptableY;
    private final Random guesser;

    /**
     * Method constructs an implementation of the class and initialises
     * variables.
     *
     */
    public Ai() {
        guesser = new Random();
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
        findAcceptableCoordinates();
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
            getEnemyMap()[x][y] = 1;
            destroyedX.add(x);
            destroyedY.add(y);
            shipWasHit = true;
        } else {
            getEnemyMap()[x][y] = -1;
        }
        if (ship != null) {
            negateSurroundingAreaAfterSinking(ship);
            shipWasHit = false;
            destroyedX.clear();
            destroyedY.clear();
        }
    }

    private void findAcceptableCoordinates() {
        if (!shipWasHit) {
            acceptAllFreeCoordinates();
        } else {
            Collections.sort(destroyedX);
            Collections.sort(destroyedY);
            acceptOnlySurroundings();
        }
    }

    private void acceptOnlySurroundings() {
        int firstX = destroyedX.get(0);
        int lastX = destroyedX.get(destroyedX.size() - 1);
        int firstY = destroyedY.get(0);
        int lastY = destroyedY.get(destroyedY.size() - 1);
        if (destroyedX.size() == 1 || isHorizontal()) {
            addAcceptable(firstX - 1, firstY);
            addAcceptable(lastX + 1, firstY);
        }
        if (destroyedX.size() == 1 || !isHorizontal()) {
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
        if (withinBoundaries(x, y) && getEnemyMap()[x][y] == 0) {
            acceptableX.add(x);
            acceptableY.add(y);
        }
    }

    private void acceptAllFreeCoordinates() {
        for (int y = 0; y < getEnemyMap().length; y++) {
            for (int x = 0; x < getEnemyMap().length; x++) {
                if (getEnemyMap()[x][y] == 0) {
                    acceptableX.add(x);
                    acceptableY.add(y);
                }
            }
        }
    }
}
