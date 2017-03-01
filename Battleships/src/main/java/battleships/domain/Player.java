package battleships.domain;

import battleships.logic.ShipValidator;
import java.util.*;

/**
 * Class contains methods for managing the players fleet and status.
 */
public abstract class Player {

    private final ArrayList<Ship> ships;
    private final Ship[][] locations;
    private final int[][] enemyMap;
    private final ShipValidator sv;

    /**
     * Initialises arrays containing ship location information and feedback from
     * shooting. Also initialises an empty list that will contain the players
     * ships and an instance of ShipValidator class.
     */
    public Player() {
        ships = new ArrayList<>();
        locations = new Ship[10][10];
        enemyMap = new int[10][10];
        sv = new ShipValidator();
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
    public abstract void feedback(Boolean hit, Ship ship, int x, int y);

    /**
     * Method compares the coordinates received as variables to the location map
     * of player's fleet. If the locations contains a Ship the method will call
     * the shoot method of that particular Ship.
     *
     * @see battleships.domain.Ship#shot()
     * @param x Contains the x coordinate the opponent guessed
     * @param y Contains the y coordinate the opponent guessed
     *
     * @return Ship Object that was hit or null
     */
    public Ship shoot(int x, int y) {
        if (sv.withinBoundaries(x, y, locations.length) && locations[x][y] != null) {
            locations[x][y].shot();
            return locations[x][y];
        }
        return null;
    }

    /**
     * Method returns a list containing the player fleet of Ships.
     *
     * @return List of Ship Objects
     */
    public ArrayList<Ship> getShips() {
        return ships;
    }

    /**
     * Method returns the array containing the locations of the players fleet.
     *
     * @return Array of Ships showing the Ship locations
     */
    public Ship[][] getLocations() {
        return locations;
    }

    /**
     * Method returns the array containing all the locations the player has
     * guessed.
     *
     * @return Array of integers, marking the guessed locations
     */
    public int[][] getEnemyMap() {
        return enemyMap;
    }

    /**
     * Method returns the positionValidator.
     *
     * @return positionValidator
     */
    public ShipValidator getShipValidator() {
        return sv;
    }

    /**
     * Method iterates through players Ships and checks if they have sunk.
     *
     * @return True if all the Ships have sunk, otherwise false
     */
    public Boolean didPlayerLose() {
        if (!ships.isEmpty()) {
            for (Ship laiva : ships) {
                if (!laiva.didItSink()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method adds the parameter Ship to the Player's list of Ships. Also adds
     * the Ship's location information to the Player's map after making sure the
     * Ship is valid.
     *
     * @param ship The ship intended to be added to the players fleet
     *
     * @return True if the ship was added, otherwise false
     */
    public Boolean addShip(Ship ship) {
        int[] x = ship.getXcoordinates();
        int[] y = ship.getYcoordinates();
        if (sv.validateShip(x, y, locations) && ship.getSize() == x.length
                && x.length != 0) {
            for (int i = 0; i < x.length; i++) {
                locations[x[i]][y[i]] = ship;
            }
            ships.add(ship);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method removes all ships the player currently has.
     *
     */
    public void resetShips() {
        ships.clear();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations.length; j++) {
                locations[j][i] = null;
            }
        }
    }

    /**
     * Method automatically changes the values of squares on enemy map adjacent
     * to a sunken ship to -1.
     *
     * @param ship The sunken Ship
     */
    public void negateSurroundingAreaAfterSinking(Ship ship) {
        for (int i = 0; i < ship.getXcoordinates().length; i++) {
            for (int y = ship.getYcoordinates()[i] - 1; y <= ship.getYcoordinates()[i] + 1; y++) {
                for (int x = ship.getXcoordinates()[i] - 1; x <= ship.getXcoordinates()[i] + 1; x++) {
                    if (x >= 0 && y >= 0 && x < enemyMap.length && y < enemyMap.length
                            && enemyMap[x][y] == 0) {
                        enemyMap[x][y] = -1;
                    }
                }
            }
        }
    }
}
