package battleships.logic;

/**
 * Class contains methods tailored for the requirements of a human player.
 */
public class Person extends Player {

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
        } else {
            super.getEnemyMap()[x][y] = -1;
        }
        if (ship != null) {
            super.negateSurroundingAreaAfterSinking(ship);
        }
    }
}
