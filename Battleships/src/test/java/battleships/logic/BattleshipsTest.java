package battleships.logic;

import battleships.ai.Ai;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BattleshipsTest {

    private Battleships bs;

    public BattleshipsTest() {
        bs = new Battleships(1, 6);
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void playReturnsTheShipThatWasHit() {
        int x = bs.getPlayer2().getShips().get(0).getXcoordinates()[0];
        int y = bs.getPlayer2().getShips().get(0).getYcoordinates()[0];
        Ship ship = bs.getPlayer2().getShips().get(0);
        assertEquals(ship, bs.play(x, y));
    }

    @Test
    public void player1moveRecordedToTheirEnemyMap() {
        bs.play(0, 0);
        assertNotEquals(0, bs.getPlayer1().getEnemyMap()[0][0]);
    }

    @Test
    public void aiGetsTurnAfterPlayer1DoesntHit() {
        int[][] emptyMapBeforeAnyTurns = new int[10][10];
        for (int k = 0; k < 10; k++) {
            for (int l = 0; l < 10; l++) {
                emptyMapBeforeAnyTurns[l][k] = 0;
            }
        }
        assertArrayEquals(emptyMapBeforeAnyTurns, bs.getPlayer2().getEnemyMap());

        int player1ShootingResult;
        for (int i = 0; i < 10; i++) {
            bs.play(0, i);
            player1ShootingResult = bs.getPlayer1().getEnemyMap()[0][i];
            if (player1ShootingResult == -1) {
                break;
            }
        }
        bs.play(0, 0);
        int[][] mapAfterPlayer2Turn = bs.getPlayer2().getEnemyMap();

        Boolean shotRecorded = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mapAfterPlayer2Turn[j][i] == -1 || mapAfterPlayer2Turn[j][i] == 1) {
                    shotRecorded = true;
                }
            }
        }
        assertTrue(shotRecorded);
    }

    @Test
    public void player1GetsAnotherTurnAfterHitting() {
        int x = bs.getPlayer2().getShips().get(0).getXcoordinates()[0];
        int y = bs.getPlayer2().getShips().get(0).getYcoordinates()[0];
        bs.play(x, y);
        assertNotEquals(0, bs.getPlayer1().getEnemyMap()[x][y]);
        int j = bs.getPlayer2().getShips().get(1).getXcoordinates()[0];
        int i = bs.getPlayer2().getShips().get(1).getYcoordinates()[0];
        bs.play(j, i);
        assertNotEquals(0, bs.getPlayer1().getEnemyMap()[j][i]);

        int[][] player2NoShotsFired = new int[10][10];
        for (int k = 0; k < 10; k++) {
            for (int l = 0; l < 10; l++) {
                player2NoShotsFired[l][k] = 0;
            }
        }
        assertArrayEquals(player2NoShotsFired, bs.getPlayer2().getEnemyMap());
    }

    @Test
    public void didPlayer1Win() {
        Battleships game = new Battleships(1, 1);
        int[] x = game.getPlayer2().getShips().get(0).getXcoordinates();
        int[] y = game.getPlayer2().getShips().get(0).getYcoordinates();
        for (int i = 0; i < x.length; i++) {
            game.play(x[i], y[i]);
        }
        assertTrue(game.didPlayerWin(game.getPlayer1()));
    }

    @Test
    public void didPlayer2Win() {
        int x[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int y[] = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        Ship player1Ship = new Ship(10, x, y);
        bs.newShip(player1Ship);
        for (int i = 0; i < 201; i++) {
            Ship ship = bs.play(0, 0);
            if (player1Ship.didItSink()) {
                break;
            }
        }
        assertTrue(bs.didPlayerWin(bs.getPlayer2()));
    }

    @Test
    public void noOneWinsBeforeTakingAnyTurns() {
        Battleships game = new Battleships(1, 1);
        int x[] = {0, 1, 2, 3, 4};
        int y[] = {5, 5, 5, 5, 5};
        game.newShip(new Ship(5, x, y));
        assertFalse(game.didPlayerWin(game.getPlayer1()));
        assertFalse(game.didPlayerWin(game.getPlayer2()));
    }
}
