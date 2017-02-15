package battleships.ai;

import battleships.logic.Player;
import battleships.logic.Ship;
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
        ai = new Ai(6);
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
}
