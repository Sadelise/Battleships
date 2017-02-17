package battleships.ai;

import battleships.logic.Battleships;
import battleships.logic.Ship;
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
    public void HasNoShipsInTheBeginning() {
        assertEquals(0, ai.getShips().size());
    }

    @Test
    public void AiHasShipsWhenGameConstructed() {
        Battleships bs = new Battleships(1, 6);
        assertNotEquals(0, bs.getPlayer2().getShips().size());
    }

    @Test
    public void GivesRandomValidCoordinatesNoHitsYet() {
        int[] c = ai.getShootingCoordinates();
        assertTrue(c[0] >= 0 && c[0] < ai.getEnemyMap().length);
        assertTrue(c[1] >= 0 && c[1] < ai.getEnemyMap().length);
    }

    @Test
    public void GivesAdjacentCoordinatesAfterAHit() {
        ai.feedback(true, null, 0, 0);
        int[] c = ai.getShootingCoordinates();
        assertTrue((c[0] == 0 && c[1] == 1) || (c[0] == 1 && c[1] == 0));
    }

    @Test
    public void GivesEndCoordinatesAfterTwoHits() {
        ai.feedback(true, null, 5, 6);
        ai.feedback(true, null, 4, 6);
        int[] c = ai.getShootingCoordinates();
        assertTrue((c[0] == 3 && c[1] == 6)
                || (c[0] == 6 && c[1] == 6));
    }

    @Test
    public void neverShootsCoordinatesAdjacentToSunkenShip() {
        ai.feedback(true, null, 5, 6);
        int[] x = {4};
        int[] y = {6};
        ai.feedback(true, new Ship(1, x, y), 4, 6);
        int[] c = ai.getShootingCoordinates();
        assertFalse((c[0] == 3 && c[1] == 6)
                || (c[0] == 6 && c[1] == 6)
                || (c[0] == 3 && c[1] == 7)
                || (c[0] == 4 && c[1] == 7)
                || (c[0] == 5 && c[1] == 7)
                || (c[0] == 6 && c[1] == 7)
                || (c[0] == 3 && c[1] == 5)
                || (c[0] == 4 && c[1] == 5)
                || (c[0] == 5 && c[1] == 5)
                || (c[0] == 6 && c[1] == 5));
    }

    @Test
    public void onlyAttemptsToShootAvailableSpots() {
        for (int i = 0; i < ai.getEnemyMap().length; i++) {
            for (int j = 0; j < ai.getEnemyMap().length; j++) {
                if (i > 1 || j > 1) {
                    int[] x = {j};
                    int[] y = {i};
                    ai.feedback(true, new Ship(1, x, y), j, i);
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
                    int[] x = {j};
                    int[] y = {i};
                    ai.feedback(true, new Ship(1, x, y), j, i);
                }
            }
        }
        int[] c = ai.getShootingCoordinates();
        assertTrue(c[0] == -1 && c[1] == -1);
    }

    @Test
    public void negateOnlyChangesImmediateSurroundings() {
        int[] x = {0};
        int[] y = {0};
        Ship ship = new Ship(1, x, y);
        ai.feedback(true, ship, 7, 4);
        ai.feedback(true, ship, 0, 0);
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
        int[] x = {7};
        int[] y = {4};
        Ship ship = new Ship(1, x, y);
        ai.feedback(true, ship, 7, 4);
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

    @Test
    public void feedBackChangesMap() {
        assertEquals(0, ai.getEnemyMap()[9][9]);
        ai.feedback(true, null, 9, 9);
        assertEquals(1, ai.getEnemyMap()[9][9]);
        ai.feedback(false, null, 0, 5);
        assertEquals(-1, ai.getEnemyMap()[0][5]);
        int[] x = {0};
        int[] y = {0};
        ai.feedback(true, new Ship(1, x, y), 0, 0);
        assertEquals(1, ai.getEnemyMap()[0][0]);
        assertEquals(-1, ai.getEnemyMap()[0][1]);
        assertEquals(-1, ai.getEnemyMap()[1][0]);
        assertEquals(-1, ai.getEnemyMap()[1][1]);
    }
}
