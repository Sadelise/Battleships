package battleships.logic;

import battleships.domain.Person;
import battleships.domain.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShipValidatorTest {

    private ShipValidator sv;

    public ShipValidatorTest() {
        sv = new ShipValidator();
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
    public void withinBoundariesWorks() {
        assertTrue(sv.withinBoundaries(0, 0, 1));
        assertTrue(sv.withinBoundaries(0, 0, 10));
        assertTrue(sv.withinBoundaries(0, 9, 10));
        assertTrue(sv.withinBoundaries(9, 0, 10));
        assertTrue(sv.withinBoundaries(9, 9, 10));
        assertFalse(sv.withinBoundaries(-1, 0, 10));
        assertFalse(sv.withinBoundaries(0, -1, 10));
        assertFalse(sv.withinBoundaries(10, 0, 10));
        assertFalse(sv.withinBoundaries(0, 10, 10));
    }

    @Test
    public void shipPiecesAttachedWorks() {
        int[] x = {0, 3};
        int[] y = {2, 2};
        Player p = new Person();
        assertFalse(sv.validateShip(x, y, p.getLocations()));
        int[] x1 = {0, 1};
        int[] y1 = {2, 2};
        assertTrue(sv.validateShip(x1, y1, p.getLocations()));
    }
}
