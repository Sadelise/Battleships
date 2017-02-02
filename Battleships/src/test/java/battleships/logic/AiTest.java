package battleships.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AiTest {

    private Ai ai;

    public AiTest() {
        ai = new Ai();
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
    public void HasShipsInTheBeginning() {
        assertNotEquals(0, ai.getShips().size());
    }

    @Test
    public void GivesRandomValidCoordinatesNoHitsYet() {
        int[] c = ai.getShootingCoordinates();
        assertTrue(c[0] >= 0 && c[0] < ai.getEnemyMap().length);
        assertTrue(c[1] >= 0 && c[1] < ai.getEnemyMap().length);
    }

    @Test
    public void GivesAdjacentCoordinatesAfterAHit() {
        ai.feedback(true, false, 0, 0);
        int[] c = ai.getShootingCoordinates();
        assertTrue((c[0] == 0 && c[1] == 1) || (c[0] == 1 && c[1] == 0));
    }

    @Test
    public void GivesAdjacentCoordinatesAfterTwoHits() {
        ai.feedback(true, false, 5, 6);
        ai.feedback(true, false, 4, 6);
        int[] c = ai.getShootingCoordinates();
        assertTrue((c[0] == 3 && c[1] == 6)
                || (c[0] == 6 && c[1] == 6)
                || (c[0] == 4 && c[1] == 7)
                || (c[0] == 5 && c[1] == 7)
                || (c[0] == 4 && c[1] == 5)
                || (c[0] == 5 && c[1] == 5));
    }

    @Test
    public void givesRandomCoordinatesAfterSinkingOpponent() {
        ai.feedback(true, false, 5, 6);
        ai.feedback(true, true, 4, 6);
        int[] c = ai.getShootingCoordinates();
        assertFalse((c[0] == 3 && c[1] == 6)
                || (c[0] == 6 && c[1] == 6)
                || (c[0] == 4 && c[1] == 7)
                || (c[0] == 5 && c[1] == 7)
                || (c[0] == 4 && c[1] == 5)
                || (c[0] == 5 && c[1] == 5));
    }

    @Test
    public void onlyAttemptsToShootAvailableSpots() {
        for (int i = 0; i < ai.getEnemyMap().length; i++) {
            for (int j = 0; j < ai.getEnemyMap().length; j++) {
                if (i > 1 || j > 1) {
                    ai.feedback(true, true, j, i);
                }
            }
        }
        int[] c = ai.getShootingCoordinates();
        assertTrue(c[0] == 0 && c[1] == 0);
    }

    @Test
    public void doesntShootASpaceThatIsNextToASankShip() {
        for (int i = 0; i < ai.getEnemyMap().length; i++) {
            for (int j = 0; j < ai.getEnemyMap().length; j++) {
                if (i > 0 || j > 0) {
                    ai.feedback(true, true, j, i);
                }
            }
        }
        int[] c = ai.getShootingCoordinates();
        assertTrue(c[0] == -1 && c[1] == -1);
    }

    @Test
    public void negateOnlyChangesImmediateSurroundings() {
        ai.feedback(true, true, 0, 0);
        int[][] c = ai.getEnemyMap();
        assertTrue(c[0][3] == 0);
        assertTrue(c[1][3] == 0);
        assertTrue(c[2][3] == 0);
        assertTrue(c[3][3] == 0);
        assertTrue(c[3][2] == 0);
        assertTrue(c[3][1] == 0);
        assertTrue(c[3][0] == 0);
    }

    @Test
    public void negateChangesSurroundings() {
        ai.feedback(true, true, 7, 4);
        int[][] m = ai.getEnemyMap();
        assertTrue(m[6][3] == -1);
        assertTrue(m[6][4] == -1);
        assertTrue(m[6][5] == -1);
        assertTrue(m[8][3] == -1);
        assertTrue(m[8][4] == -1);
        assertTrue(m[8][5] == -1);
        assertTrue(m[7][5] == -1);
        assertTrue(m[7][3] == -1);
    }
}
