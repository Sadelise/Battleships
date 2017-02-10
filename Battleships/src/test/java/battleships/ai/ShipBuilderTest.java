package battleships.ai;

import battleships.logic.Player;
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
        int ships = ai.getShips().size();
        sb.buildAllShips(ai, 1, 1);
        assertEquals(ships + 1, ai.getShips().size());
    }

    @Test
    public void BuildsABigShip() {
        int ships = ai.getShips().size();
        sb.buildAllShips(ai, 1, 5);
        assertEquals(ships + 1, ai.getShips().size());
    }

    @Test
    public void BuildsManyShips() {
        int ships = ai.getShips().size();
        sb.buildAllShips(ai, 6, 5);
        assertEquals(ships + 6, ai.getShips().size());
    }
}
