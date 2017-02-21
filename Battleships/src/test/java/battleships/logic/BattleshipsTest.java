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
        int x[] = {0, 1, 2};
        int y[] = {5, 5, 5};
        bs.newShip(new Ship(2, x, y));
        bs.getPlayer1().shoot(0, 5);
        bs.getPlayer2().feedback(true, null, 0, 5);
        bs.getPlayer1().shoot(1, 5);
        bs.getPlayer2().feedback(true, null, 1, 5);
        bs.play(0, 0);
        bs.play(0, 0);
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

    @Test
    public void modeIsCorrect() {
        Battleships aiGame = new Battleships(1, 6);
        Battleships twoPlayer = new Battleships(2, 6);
        assertEquals(1, aiGame.getMode());
        assertEquals(2, twoPlayer.getMode());
        assertTrue(aiGame.getPlayer1() instanceof Person);
        assertTrue(aiGame.getPlayer2() instanceof Ai);
        assertTrue(twoPlayer.getPlayer1() instanceof Person);
        assertTrue(twoPlayer.getPlayer2() instanceof Person);
    }

    @Test
    public void newShipWorks() {
        Battleships game = new Battleships(1, 1);
        int x[] = {0, 1, 2, 3, 4};
        int y[] = {5, 5, 5, 5, 5};
        assertTrue(game.newShip(new Ship(5, x, y)));
        assertFalse(game.newShip(new Ship(5, x, y)));
    }

    @Test
    public void hasPlayerFinishedSettingShipsWorks() {
        Battleships game = new Battleships(1, 1);
        int x[] = {0, 1, 2, 3, 4};
        int y[] = {5, 5, 5, 5, 5};
        int q[] = {0, 1, 2, 3};
        int w[] = {3, 3, 3, 3};
        int e[] = {0, 1, 2};
        int r[] = {1, 1, 1};
        int t[] = {0, 1, 2};
        int u[] = {7, 7, 7};
        int i[] = {0, 1};
        int o[] = {9, 9};
        int a[] = {9};
        int s[] = {0};
        game.newShip(new Ship(5, x, y));
        game.newShip(new Ship(4, q, w));
        game.newShip(new Ship(3, e, r));
        game.newShip(new Ship(3, t, u));
        game.newShip(new Ship(2, i, o));
        game.newShip(new Ship(1, a, s));
        assertTrue(game.hasPlayerFinishedSettingShips(game.getPlayer1()));
    }
}
