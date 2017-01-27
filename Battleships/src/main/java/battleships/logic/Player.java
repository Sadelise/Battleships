package battleships.logic;

import java.util.*;

public abstract class Player {

    private ArrayList<Ship> ships;
    private Ship[][] locations;

    public Player() {
        ships = new ArrayList<>();
        locations = new Ship[10][10];
    }

    abstract public Ship shoot(int x, int y);

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

    public Boolean addShip(Ship laiva, int[] x, int[] y) {
        if (shipPosition(laiva, x, y)
                && laiva.getSize() == x.length
                && x.length != 0
                && shipPiecesAttached(x, y)) {
            for (int i = 0; i < x.length; i++) {
                locations[x[i]][y[i]] = laiva;
            }
            ships.add(laiva);
            return true;
        } else {
            return false;
        }
    }

    private Boolean shipPosition(Ship laiva, int[] x, int[] y) {
        for (int i = 0; i < x.length; i++) {
            if (!isSingleCoordinateAcceptable(laiva, x[i], y[i])) {
                return false;
            }
        }
        return true;
    }

    private Boolean isSingleCoordinateAcceptable(Ship laiva, int x, int y) {
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (i >= 0 && i < locations.length && j >= 0 && j < locations.length) {
                    if (locations[j][i] != null) {
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
}
