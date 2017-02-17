package battleships.logic;

import java.util.*;

/**
 * Class contains methods for managing the players fleet and status.
 */
public abstract class Player {

    private final ArrayList<Ship> ships;
    private final Ship[][] locations;
    private final int[][] enemyMap;

    /**
     * Method constructs an implementation of the class and initialises
     * variables.
     */
    public Player() {
        ships = new ArrayList<>();
        locations = new Ship[10][10];
        enemyMap = new int[10][10];
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
     * @see battleships.logic.Ship#shot()
     *
     * @param x Contains the x coordinate the opponent guessed
     * @param y Contains the y coordinate the opponent guessed
     *
     * @return Ship Object that was hit or null
     */
    public Ship shoot(int x, int y) {
        if (withinBoundaries(x, y) && locations[x][y] != null) {
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
     * Method adds the parameter Ship to the list of Ships and adds its location
     * information to the map after making sure the Ship is valid.
     *
     * @param ship The ship intended to be added to the players fleet
     *
     * @return True if the ship was added, otherwise false
     */
    public Boolean addShip(Ship ship) {
        int[] x = ship.getXcoordinates();
        int[] y = ship.getYcoordinates();
        if (validateShipPosition(ship, x, y) && ship.getSize() == x.length
                && x.length != 0 && shipPiecesAttached(x, y)) {
            for (int i = 0; i < x.length; i++) {
                locations[x[i]][y[i]] = ship;
            }
            ships.add(ship);
            return true;
        } else {
            return false;
        }
    }

    private Boolean validateShipPosition(Ship ship, int[] x, int[] y) {
        for (int i = 0; i < x.length; i++) {
            if (!isSingleCoordinateAcceptable(x[i], y[i])) {
                return false;
            }
        }
        return true;
    }

    private Boolean isSingleCoordinateAcceptable(int x, int y) {
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (i >= 0 && i < locations.length && j >= 0 && j < locations.length) {
                    if (locations[j][i] != null || !(withinBoundaries(x, y))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Boolean shipPiecesAttached(int[] x, int[] y) {
        if (x.length > 1) {
            int changes[];
            int stays[];
            int staysAt;
            if (y[0] == y[1]) {
                staysAt = y[0];
                stays = y;
                changes = x;
            } else {
                staysAt = x[0];
                stays = x;
                changes = y;
            }
            int prev = changes[0];
            for (int i = 1; i < x.length; i++) {
                if ((prev + 1 != changes[i] || stays[i] != staysAt)) {
                    return false;
                }
                prev = changes[i];
            }
        }
        return true;
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

    /**
     * Method makes sure coordinates correlate with the dimensions of the
     * enemyMap array.
     *
     * @param x x coordinate to be checked
     * @param y y coordinate to be checked
     * @return True if coordinates correlate with the dimensions, otherwise
     * false
     */
    public Boolean withinBoundaries(int x, int y) {
        return y >= 0 && x >= 0 && y < enemyMap.length && x < enemyMap.length;
    }
}
