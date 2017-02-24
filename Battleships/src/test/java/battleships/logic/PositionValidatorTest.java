package battleships.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PositionValidatorTest {

    private ShipValidator pv;

    public PositionValidatorTest() {
        pv = new ShipValidator();
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
        assertTrue(pv.withinBoundaries(0, 0, 1));
        assertTrue(pv.withinBoundaries(0, 0, 10));
        assertTrue(pv.withinBoundaries(0, 9, 10));
        assertTrue(pv.withinBoundaries(9, 0, 10));
        assertTrue(pv.withinBoundaries(9, 9, 10));
        assertFalse(pv.withinBoundaries(-1, 0, 10));
        assertFalse(pv.withinBoundaries(0, -1, 10));
        assertFalse(pv.withinBoundaries(10, 0, 10));
        assertFalse(pv.withinBoundaries(0, 10, 10));
    }
}
