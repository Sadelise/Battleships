package battleships.logic;

import battleships.domain.Ship;

/**
 * Class contains methods for ensuring that player's ships are correctly shaped
 * and laid out according to rules.
 */
public class ShipValidator {

    /**
     * Method makes sure ship's coordinates are correctly placed.
     *
     * @param x x coordinate to be checked
     * @param y y coordinate to be checked
     * @param locations the locations of player's existing ships
     * @return True if coordinates are placed according to restrictions
     * otherwise false.
     */
    public Boolean validateShip(int[] x, int[] y, Ship[][] locations) {
        if (!shipPiecesAttached(x, y)) {
            return false;
        }
        for (int i = 0; i < x.length; i++) {
            if (!isSingleCoordinateAcceptable(x[i], y[i], locations)) {
                return false;
            }
        }
        return true;
    }

    private Boolean isSingleCoordinateAcceptable(int x, int y, Ship[][] locations) {
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (withinBoundaries(j, i, locations.length)) {
                    if (locations[j][i] != null || !(withinBoundaries(x, y, locations.length))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Boolean shipPiecesAttached(int[] x, int[] y) {
        if (x.length > 1) {
            int changes[], stays[], staysAt;
            if (y[0] == y[1]) {
                staysAt = y[0];
                stays = y;
                changes = x;
            } else {
                staysAt = x[0];
                stays = x;
                changes = y;
            }
            for (int i = 1; i < x.length; i++) {
                if ((changes[i - 1] + 1 != changes[i] || stays[i] != staysAt)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method makes sure given coordinates are within the dimensions of the game
     * area.
     *
     * @param x x coordinate to be checked
     * @param y y coordinate to be checked
     * @param length the length of the game area
     * @return True if coordinates within limits, otherwise false
     */
    public Boolean withinBoundaries(int x, int y, int length) {
        return y >= 0
                && x >= 0
                && y < length
                && x < length;
    }
}
