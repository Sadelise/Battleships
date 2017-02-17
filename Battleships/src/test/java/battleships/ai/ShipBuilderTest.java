package battleships.ai;

import battleships.logic.Player;
import battleships.logic.Ship;
import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShipBuilderTest {

    private ShipBuilder sb;
    private Player ai;

    public ShipBuilderTest() {
        ai = new Ai();
        sb = new ShipBuilder();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
}
