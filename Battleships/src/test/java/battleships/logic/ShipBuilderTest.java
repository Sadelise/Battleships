package battleships.logic;

import battleships.ai.Ai;
import battleships.domain.Player;
import battleships.domain.Ship;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShipBuilderTest {

    private final ShipBuilder sb;
    private Player ai;

    public ShipBuilderTest() {
        ai = new Ai();
        sb = new ShipBuilder();
    }

    @Test
    public void BuildsASmallShip() {
        Ship ship = sb.buildShip(0, 1, 0, 1);
        assertEquals(1, ship.getSize());
    }

    @Test
    public void BuildsABigShip() {
        Ship ship = sb.buildShip(0, 1, 0, 8);
        assertEquals(8, ship.getSize());
    }

    @Test
    public void BuildsManyShipsForAi() {
        int ships = ai.getShips().size();
        sb.buildAllShips(ai, 3, 4);
        assertEquals(ships + 3, ai.getShips().size());
    }

    @Test
    public void OrientationIsCorrect() {
        Ship ship = sb.buildShip(0, 1, 0, 2);
        Ship ship2 = sb.buildShip(0, 1, 1, 2);
        assertEquals(ship.getYcoordinates()[0], ship.getYcoordinates()[1]);
        assertEquals(ship2.getXcoordinates()[0], ship2.getXcoordinates()[1]);

    }

    @Test
    public void createsTheRightShips() {
        ai = new Ai();
        sb.buildAllShips(ai, 6, 5);
        ArrayList<Ship> ships = ai.getShips();
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(5, 1);
        expected.put(4, 1);
        expected.put(3, 2);
        expected.put(2, 1);
        expected.put(1, 1);
        HashMap<Integer, Integer> found = new HashMap<>();
        found.put(5, 0);
        found.put(4, 0);
        found.put(3, 0);
        found.put(2, 0);
        found.put(1, 0);
        for (Ship ship : ships) {
            found.put(ship.getSize(), found.get(ship.getSize()) + 1);
        }
        assertEquals(expected, found);
    }

    @Test
    public void createsShipToRightDirection() {
        Ship ship = sb.buildShip(5, 0, 0, 2);
        int[] x = ship.getXcoordinates();
        int[] y = ship.getYcoordinates();
        assertTrue(x[0] == 5
                && x[1] == 6
                && y[0] == 0
                && y[1] == 0
        );
        Ship ship1 = sb.buildShip(5, 5, 1, 2);
        int[] x1 = ship1.getXcoordinates();
        int[] y1 = ship1.getYcoordinates();
        assertTrue(x1[0] == 5
                && x1[1] == 5
                && y1[0] == 5
                && y1[1] == 6
        );
    }
}
