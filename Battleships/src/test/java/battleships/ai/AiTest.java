package battleships.ai;

import battleships.logic.Battleships;
import battleships.domain.Ship;
import org.junit.Test;
import static org.junit.Assert.*;

public class AiTest {

    private final Ai ai;

    public AiTest() {
        ai = new Ai();
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
        ai.feedback(true, null, 5, 5);
        int[] c = ai.getShootingCoordinates();
        assertTrue((c[0] == 4 && c[1] == 5)
                || (c[0] == 6 && c[1] == 5)
                || (c[0] == 5 && c[1] == 4)
                || (c[0] == 5 && c[1] == 6)
        );
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
                && (c[0] == 6 && c[1] == 6)
                && (c[0] == 3 && c[1] == 7)
                && (c[0] == 4 && c[1] == 7)
                && (c[0] == 5 && c[1] == 7)
                && (c[0] == 6 && c[1] == 7)
                && (c[0] == 3 && c[1] == 5)
                && (c[0] == 4 && c[1] == 5)
                && (c[0] == 5 && c[1] == 5)
                && (c[0] == 6 && c[1] == 5));
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
    public void doesntShootASpaceThatIsNextToASunkenShip() {
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

    @Test
    public void ifShipWasSunkNoMoreDestroyedShipPartsInMemory() {
        Ai a = new Ai();
        int[] x = {1, 2};
        int[] y = {2, 2};
        a.feedback(true, null, 1, 2);
        a.feedback(true, new Ship(2, x, y), 2, 2);
        int[] c = a.getShootingCoordinates();
        assertFalse((c[0] == 0 && c[1] == 2) && (c[0] == 3 && c[1] == 2));
        int[] x1 = {5, 5};
        int[] y1 = {8, 9};
        a.feedback(true, null, 5, 8);
        a.feedback(true, new Ship(2, x1, y1), 5, 9);
        int[] c1 = a.getShootingCoordinates();
        assertFalse((c1[0] == 5 && c1[1] == 8) && (c1[0] == 5 && c1[1] == 9));
    }

    @Test
    public void horizontalShipGetsShotAtEnds() {
        Ai a = new Ai();
        a.feedback(true, null, 1, 2);
        a.feedback(true, null, 2, 2);
        int[] c = a.getShootingCoordinates();
        assertTrue((c[0] == 0 && c[1] == 2) || (c[0] == 3 && c[1] == 2));
    }

    @Test
    public void verticalShipGetsShotAtEnds() {
        Ai a = new Ai();
        a.feedback(true, null, 2, 1);
        a.feedback(true, null, 2, 2);
        int[] c = a.getShootingCoordinates();
        assertTrue((c[0] == 2 && c[1] == 0) || (c[0] == 2 && c[1] == 3));
    }

    @Test
    public void oneAcceptableVerticalUp() {
        Ai a = new Ai();
        a.feedback(true, null, 2, 0);
        a.feedback(true, null, 2, 1);
        a.feedback(true, null, 2, 2);
        a.feedback(true, null, 2, 3);
        a.feedback(true, null, 2, 4);
        a.feedback(true, null, 2, 5);
        a.feedback(true, null, 2, 6);
        a.feedback(true, null, 2, 7);
        a.feedback(true, null, 2, 8);
        int[] c = a.getShootingCoordinates();
        assertTrue((c[0] == 2 && c[1] == 9));
    }

    @Test
    public void oneAcceptableVerticalDown() {
        Ai a = new Ai();
        a.feedback(true, null, 2, 1);
        a.feedback(true, null, 2, 2);
        a.feedback(true, null, 2, 3);
        a.feedback(true, null, 2, 4);
        a.feedback(true, null, 2, 5);
        a.feedback(true, null, 2, 6);
        a.feedback(true, null, 2, 7);
        a.feedback(true, null, 2, 8);
        a.feedback(true, null, 2, 9);
        int[] c = a.getShootingCoordinates();
        assertTrue((c[0] == 2 && c[1] == 0));
    }

    @Test
    public void oneAcceptableHorizontalLeft() {
        Ai a = new Ai();
        a.feedback(true, null, 1, 2);
        a.feedback(true, null, 2, 2);
        a.feedback(true, null, 3, 2);
        a.feedback(true, null, 4, 2);
        a.feedback(true, null, 5, 2);
        a.feedback(true, null, 6, 2);
        a.feedback(true, null, 7, 2);
        a.feedback(true, null, 8, 2);
        a.feedback(true, null, 9, 2);
        int[] c = a.getShootingCoordinates();
        assertTrue((c[0] == 0 && c[1] == 2));
    }

    @Test
    public void oneAcceptableHorizontalRight() {
        Ai a = new Ai();
        a.feedback(true, null, 0, 2);
        a.feedback(true, null, 1, 2);
        a.feedback(true, null, 2, 2);
        a.feedback(true, null, 3, 2);
        a.feedback(true, null, 4, 2);
        a.feedback(true, null, 5, 2);
        a.feedback(true, null, 6, 2);
        a.feedback(true, null, 7, 2);
        a.feedback(true, null, 8, 2);
        int[] c = a.getShootingCoordinates();
        assertTrue((c[0] == 9 && c[1] == 2));
    }
}
