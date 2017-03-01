package battleships.logic;

import battleships.domain.Ship;
import battleships.domain.Person;
import battleships.ai.Ai;
import org.junit.Test;
import static org.junit.Assert.*;

public class BattleshipsTest {

    private final Battleships bs;

    public BattleshipsTest() {
        bs = new Battleships(1, 6);
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
        assertEquals(bs.getPlayer1(), bs.getPlayerInTurn());
        int player1ShootingResult;
        for (int i = 0; i < 10; i++) {
            bs.play(0, i);
            player1ShootingResult = bs.getPlayer1().getEnemyMap()[0][i];
            if (player1ShootingResult == -1) {
                break;
            }
        }
        assertEquals(bs.getPlayer2(), bs.getPlayerInTurn());
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
        Battleships game = new Battleships(1, 1);
        game.getPlayer2().getShips().clear();
        Ship[][] ships = game.getPlayer2().getLocations();
        for (int i = 0; i < ships.length; i++) {
            for (int j = 0; j < ships.length; j++) {
                ships[j][i] = null;
            }
        }
        game.newShip(0, 0, 0, 1, game.getPlayer2());
        game.newShip(0, 0, 0, 1, game.getPlayer1());
        game.play(5, 5);
        game.play(0, 0);
        assertTrue(bs.didPlayerWin(bs.getPlayer2()));
    }

    @Test
    public void noOneWinsBeforeTakingAnyTurns() {
        Battleships game = new Battleships(1, 1);
        game.newShip(0, 5, 0, 5, game.getPlayer1());
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
        assertTrue(game.newShip(0, 5, 0, 5, game.getPlayer1()));
        assertFalse(game.newShip(0, 6, 0, 5, game.getPlayer1()));
        assertFalse(game.newShip(0, 4, 0, 5, game.getPlayer1()));
        assertFalse(game.newShip(5, 5, 0, 5, game.getPlayer1()));
    }

    @Test
    public void hasPlayerFinishedSettingShipsWorks() {
        Battleships game = new Battleships(1, 6);
        assertFalse(game.hasPlayerFinishedPlacingShips(game.getPlayer1()));
        game.newShip(0, 5, 0, 5, game.getPlayer1());
        assertFalse(game.hasPlayerFinishedPlacingShips(game.getPlayer1()));
        game.newShip(0, 3, 0, 4, game.getPlayer1());
        game.newShip(0, 1, 0, 3, game.getPlayer1());
        game.newShip(0, 7, 0, 3, game.getPlayer1());
        game.newShip(0, 9, 0, 2, game.getPlayer1());
        assertFalse(game.hasPlayerFinishedPlacingShips(game.getPlayer1()));
        game.newShip(9, 0, 0, 1, game.getPlayer1());
        assertTrue(game.hasPlayerFinishedPlacingShips(game.getPlayer1()));
    }

    @Test
    public void playReturnsNullWhenAiCantShootAnything() {
        int[][] map = bs.getPlayer2().getEnemyMap();
        Ship[][] ships = bs.getPlayer2().getLocations();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[j][i] = 1;
                ships[j][i] = null;
            }
        }
        bs.play(0, 0);
        Ship ship = bs.play(0, 0);
        assertEquals(ship, null);

    }

    @Test
    public void newShipFleetsizeCheckerWorks() {
        Battleships game = new Battleships(1, 2);
        assertTrue(game.newShip(1, 2, 0, 1, game.getPlayer1()));
        assertTrue(game.newShip(3, 2, 0, 1, game.getPlayer1()));
        assertFalse(game.newShip(5, 2, 0, 1, game.getPlayer1()));
    }

    @Test
    public void newFleetWorks() {
        Battleships game = new Battleships(1, 2);
        game.newFleet(game.getPlayer1());
        assertEquals(2, game.getPlayer1().getShips().size());
    }

    @Test
    public void playerGetsCorrectFeedBackAfterSinkingAShip() {
        int[] x = bs.getPlayer2().getShips().get(1).getXcoordinates();
        int[] y = bs.getPlayer2().getShips().get(1).getYcoordinates();
        for (int i = 0; i < x.length; i++) {
            bs.play(x[i], y[i]);
            int[][] map = bs.getPlayer1().getEnemyMap();
            assertTrue(map[x[i]][y[i]] == 1);
        }
    }
}
