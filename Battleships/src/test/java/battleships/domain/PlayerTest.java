package battleships.domain;

import battleships.domain.Ship;
import battleships.domain.Player;
import battleships.domain.Person;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {
    
    private Player player;
    private Ship ship;
    
    public PlayerTest() {
        player = new Person();
        int[] x = {4, 5, 6, 7, 8};
        int[] y = {3, 3, 3, 3, 3};
        ship = new Ship(5, x, y);
        player.addShip(ship);
        int[] k = {6};
        int[] h = {6};
        player.addShip(new Ship(1, k, h));
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
    public void addingNewShipWorks() {
        int[] x = {8, 8, 8, 8};
        int[] y = {6, 7, 8, 9};
        assertEquals(true, player.addShip(new Ship(4, x, y)));
        
    }
    
    @Test
    public void cannotPlaceEmptyShip() {
        int[] x = {};
        int[] y = {};
        assertEquals(false, player.addShip(new Ship(0, x, y)));
    }
    
    @Test
    public void cannotPlaceEmptyShipEvenIfSizeNot0() {
        int[] x = {};
        int[] y = {};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
        
    }
    
    @Test
    public void cannotPlaceShipWhenMissingPieces() {
        int[] x = {2};
        int[] y = {8};
        assertEquals(false, player.addShip(new Ship(2, x, y)));
    }
    
    @Test
    public void cannotPlaceShipWithExtraPieces() {
        int[] x = {7, 8, 9};
        int[] y = {9, 9, 9};
        assertEquals(false, player.addShip(new Ship(2, x, y)));
        
    }
    
    @Test
    public void notHittingReallyDoesntHit() {
        assertEquals(null, player.shoot(4, 2));
        assertEquals(null, player.shoot(3, 3));
    }
    
    @Test
    public void shootingHitsWhenSupposedTo() {
        assertEquals(ship, player.shoot(4, 3));
    }
    
    @Test
    public void shipCanSink() {
        player.shoot(4, 3);
        player.shoot(5, 3);
        player.shoot(6, 3);
        player.shoot(7, 3);
        player.shoot(8, 3);
        assertEquals(true, ship.didItSink());
    }
    
    @Test
    public void playerLosesAfterAllShipsSunk() {
        player.shoot(4, 3);
        player.shoot(5, 3);
        player.shoot(6, 3);
        player.shoot(7, 3);
        player.shoot(8, 3);
        assertEquals(false, player.didPlayerLose());
        player.shoot(6, 6);
        assertEquals(true, player.didPlayerLose());
    }
    
    @Test
    public void canAddNewShipInCorner() {
        int[] i = {9};
        int[] j = {9};
        assertEquals(true, player.addShip(new Ship(1, i, j)));
        int[] i2 = {0};
        int[] j2 = {0};
        assertEquals(true, player.addShip(new Ship(1, i2, j2)));
        int[] i3 = {0};
        int[] j3 = {9};
        assertEquals(true, player.addShip(new Ship(1, i3, j3)));
        int[] i4 = {9};
        int[] j4 = {0};
        assertEquals(true, player.addShip(new Ship(1, i4, j4)));
    }
    
    @Test
    public void cannotPlaceShipRigthAboveAnother() {
        int[] x = {6};
        int[] y = {7};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void cannotPlaceShipRightBelowAnother() {
        int[] x = {6};
        int[] y = {5};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void cannotPlaceShipRightToTheRigthOfAnother() {
        int[] x = {7};
        int[] y = {6};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void cannotPlaceShipRightToTheLeftOfAnother() {
        int[] x = {5};
        int[] y = {6};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void cannotPlaceShipTopRightFromAnother() {
        int[] x = {7};
        int[] y = {7};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void cannotPlaceShipTopLeftFromAnother() {
        int[] x = {5};
        int[] y = {7};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void cannotPlaceShipBottomRightFromAnother() {
        int[] x = {7};
        int[] y = {5};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void cannotPlaceShipBottomLeftFromAnother() {
        int[] x = {5};
        int[] y = {5};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void placingShipIntoLastAvailableSquare() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!(i == 6 && j == 2) && i % 2 == 0 && j % 2 == 0) {
                    int y[] = {i};
                    int x[] = {j};
                    player.addShip(new Ship(1, x, y));
                }
            }
        }
        int y[] = {6};
        int x[] = {2};
        assertEquals(true, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void cannotPlaceShipIfArrayFull() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int y[] = {i};
                int x[] = {j};
                player.addShip(new Ship(1, x, y));
            }
        }
        int y[] = {6};
        int x[] = {2};
        assertEquals(false, player.addShip(new Ship(1, x, y)));
    }
    
    @Test
    public void cannotPlaceShipInTheMiddleOfOthers() {
        int[] x = {6};
        int[] y = {8};
        player.addShip(new Ship(1, x, y));
        int[] i = {8};
        int[] j = {6};
        player.addShip(new Ship(1, i, j));
        int[] k = {8};
        int[] h = {8};
        player.addShip(new Ship(1, k, h));
        int[] n = {7};
        int[] m = {7};
        assertEquals(false, player.addShip(new Ship(1, n, m)));
    }
    
    @Test
    public void enemyMapsEmptyInTheBeginning() {
        int[][] map = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[j][i] = 0;
            }
        }
        assertArrayEquals(map, player.getEnemyMap());
    }
    
    @Test
    public void theEnemyMapIsUpdatedCorrectly() {
        int[] x = {0};
        int[] y = {0};
        player.feedback(true, new Ship(1, x, y), 0, 0);
        assertEquals(1, player.getEnemyMap()[0][0]);
        assertEquals(-1, player.getEnemyMap()[0][1]);
        assertEquals(-1, player.getEnemyMap()[1][1]);
        assertEquals(-1, player.getEnemyMap()[1][0]);
        player.feedback(true, null, 2, 2);
        assertEquals(1, player.getEnemyMap()[2][2]);
        player.feedback(false, null, 3, 3);
        assertEquals(-1, player.getEnemyMap()[3][3]);
        
    }
    
    @Test
    public void locationsIsUpdatedCorrectly() {
        int[] x = {1, 2};
        int[] y = {9, 9};
        player.addShip(new Ship(2, x, y));
        Ship[][] ships = player.getLocations();
        assertTrue(ships[1][9] != null && ships[2][9] != null
                && ships[0][9] == null
                && ships[3][9] == null
                && ships[1][8] == null
                && ships[2][8] == null
                && ships[0][8] == null
                && ships[3][8] == null
        );
    }
    
    @Test
    public void resetShipsWorks() {
        player.resetShips();
        Ship[][] empty = new Ship[player.getLocations().length][player.getLocations().length];
        assertArrayEquals(empty, player.getLocations());
        assertTrue(player.getShips().isEmpty());
    }
}
