package battleships.logic;

import java.util.*;

public abstract class Player {

    private ArrayList<Ship> ships;
    private Ship[][] locations;

    public Player() {
        ships = new ArrayList<>();
        locations = new Ship[10][10];
    }

    public Ship shoot(int x, int y) {
        if (locations[x][y] != null) {
            locations[x][y].shoot();
            return locations[x][y];
        }
        return null;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public Ship[][] getLocations() {
        return locations;
    }

    public Boolean didPlayerLose() {
        for (Ship laiva : ships) {
            if (!laiva.didItSink()) {
                return false;
            }
        }
        return true;
    }

    public Boolean addShip(Ship ship, int[] x, int[] y) {
        if (validateShipPosition(ship, x, y)
                && ship.getSize() == x.length
                && x.length != 0
                && shipPiecesAttached(x, y)) {
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
                    if (locations[j][i] != null || outOfBounds(x, y)) {
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
            int staysAt = 0;
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

    private boolean outOfBounds(int x, int y) {
        if (!(x >= 0 && x < locations.length && y >= 0 && y < locations.length)) {
            return true;
        }
        return false;
    }
}
