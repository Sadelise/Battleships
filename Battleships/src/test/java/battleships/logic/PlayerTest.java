package battleships.logic;

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
        ship = new Ship(5);
        player = new Person();
        int[] x = {4, 5, 6, 7, 8};
        int[] y = {3, 3, 3, 3, 3};
        player.addShip(ship, x, y);
        Ship paatti = new Ship(1);
        int[] k = {6};
        int[] h = {6};
        player.addShip(paatti, k, h);
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
    public void uudenLaivanAsettaminenOnnistuu() {
        Ship botski = new Ship(4);
        int[] x = {8, 8, 8, 8};
        int[] y = {6, 7, 8, 9};
        assertEquals(true, player.addShip(botski, x, y));

    }

    @Test
    public void tyhjanLaivanAsettaminenEiOnnistu() {
        Ship botski = new Ship(0);
        int[] x = {};
        int[] y = {};
        assertEquals(false, player.addShip(botski, x, y));
    }

    @Test
    public void tyhjanLaivanAsettaminenEiOnnistuVaikkaLaivanKokoYliNolla() {
        Ship botski = new Ship(1);
        int[] x = {};
        int[] y = {};
        assertEquals(false, player.addShip(botski, x, y));

    }

    @Test
    public void pienemmanKuinHalutunLaivanAsettaminenEiOnnistu() {
        Ship botski = new Ship(2);
        int[] x = {2};
        int[] y = {8};
        assertEquals(false, player.addShip(botski, x, y));
    }

    @Test
    public void suuremmanKuinHalutunLaivanAsettaminenEiOnnistu() {
        Ship botski = new Ship(2);
        int[] x = {7, 8, 9};
        int[] y = {9, 9, 9};
        assertEquals(false, player.addShip(botski, x, y));

    }

    @Test
    public void ohiAmpuminenEiOsu() {
        assertEquals(null, player.shoot(4, 2));
        assertEquals(null, player.shoot(3, 3));
    }

    @Test
    public void ampuminenOsuu() {
        assertEquals(ship, player.shoot(4, 3));
    }

    @Test
    public void laivaUppoaa() {
        player.shoot(4, 3);
        player.shoot(5, 3);
        player.shoot(6, 3);
        player.shoot(7, 3);
        player.shoot(8, 3);
        assertEquals(true, ship.didItSink());
    }

    @Test
    public void pelaajaHaviaaVastaKunViimeinenLaivaUppoaa() {
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
    public void uudenLaivanAsettaminenOnnistuuMyosKulmiin() {
        Ship botski = new Ship(1);
        int[] i = {9};
        int[] j = {9};
        assertEquals(true, player.addShip(botski, i, j));
        int[] i2 = {0};
        int[] j2 = {0};
        assertEquals(true, player.addShip(botski, i2, j2));
        int[] i3 = {0};
        int[] j3 = {9};
        assertEquals(true, player.addShip(botski, i3, j3));
        int[] i4 = {9};
        int[] j4 = {0};
        assertEquals(true, player.addShip(botski, i4, j4));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniYlos() {
        Ship botski = new Ship(1);
        int[] x = {6};
        int[] y = {7};
        assertEquals(false, player.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniAlas() {
        Ship botski = new Ship(1);
        int[] x = {6};
        int[] y = {5};
        assertEquals(false, player.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniOikealle() {
        Ship botski = new Ship(1);
        int[] x = {7};
        int[] y = {6};
        assertEquals(false, player.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniVasemmalle() {
        Ship botski = new Ship(1);
        int[] x = {5};
        int[] y = {6};
        assertEquals(false, player.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniYlaoikealle() {
        Ship botski = new Ship(1);
        int[] x = {7};
        int[] y = {7};
        assertEquals(false, player.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniYlavasemmalle() {
        Ship botski = new Ship(1);
        int[] x = {5};
        int[] y = {7};
        assertEquals(false, player.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniAlaoikealle() {
        Ship botski = new Ship(1);
        int[] x = {7};
        int[] y = {5};
        assertEquals(false, player.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniAlavasemmalle() {
        Ship botski = new Ship(1);
        int[] x = {5};
        int[] y = {5};
        assertEquals(false, player.addShip(botski, x, y));
    }

}
