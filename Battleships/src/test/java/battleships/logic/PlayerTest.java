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
        player = new Person();
        int[] x = {4, 5, 6, 7, 8};
        int[] y = {3, 3, 3, 3, 3};
        ship = new Ship(5, x, y);
        player.addShip(ship, x, y);
        int[] k = {6};
        int[] h = {6};
        player.addShip(new Ship(1, k, h), k, h);
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
        int[] x = {8, 8, 8, 8};
        int[] y = {6, 7, 8, 9};
        assertEquals(true, player.addShip(new Ship(4, x, y), x, y));

    }

    @Test
    public void tyhjanLaivanAsettaminenEiOnnistu() {
        int[] x = {};
        int[] y = {};
        assertEquals(false, player.addShip(new Ship(0, x, y), x, y));
    }

    @Test
    public void tyhjanLaivanAsettaminenEiOnnistuVaikkaLaivanKokoYliNolla() {
        int[] x = {};
        int[] y = {};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));

    }

    @Test
    public void pienemmanKuinHalutunLaivanAsettaminenEiOnnistu() {
        int[] x = {2};
        int[] y = {8};
        assertEquals(false, player.addShip(new Ship(2, x, y), x, y));
    }

    @Test
    public void suuremmanKuinHalutunLaivanAsettaminenEiOnnistu() {
        int[] x = {7, 8, 9};
        int[] y = {9, 9, 9};
        assertEquals(false, player.addShip(new Ship(2, x, y), x, y));

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
        int[] i = {9};
        int[] j = {9};
        assertEquals(true, player.addShip(new Ship(1, i, j), i, j));
        int[] i2 = {0};
        int[] j2 = {0};
        assertEquals(true, player.addShip(new Ship(1, i2, j2), i2, j2));
        int[] i3 = {0};
        int[] j3 = {9};
        assertEquals(true, player.addShip(new Ship(1, i3, j3), i3, j3));
        int[] i4 = {9};
        int[] j4 = {0};
        assertEquals(true, player.addShip(new Ship(1, i4, j4), i4, j4));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniYlos() {
        int[] x = {6};
        int[] y = {7};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniAlas() {
        int[] x = {6};
        int[] y = {5};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniOikealle() {
        int[] x = {7};
        int[] y = {6};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniVasemmalle() {
        int[] x = {5};
        int[] y = {6};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniYlaoikealle() {
        int[] x = {7};
        int[] y = {7};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniYlavasemmalle() {
        int[] x = {5};
        int[] y = {7};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniAlaoikealle() {
        int[] x = {7};
        int[] y = {5};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniAlavasemmalle() {
        int[] x = {5};
        int[] y = {5};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivanAsettaminenYhteenJaljellaOlevaanRuutuun() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!(i == 6 && j == 2) && i % 2 == 0 && j % 2 == 0) {
                    int y[] = {i};
                    int x[] = {j};
                    player.addShip(new Ship(1, x, y), x, y);
                }
            }
        }
        int y[] = {6};
        int x[] = {2};
        assertEquals(true, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivanAsettaminenTayteenKenttaanEiOnnistu() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int y[] = {i};
                int x[] = {j};
                player.addShip(new Ship(1, x, y), x, y);
            }
        }
        int y[] = {6};
        int x[] = {2};
        assertEquals(false, player.addShip(new Ship(1, x, y), x, y));
    }

    @Test
    public void laivaaEiSaaToistenLaivojenKeskelle() {
        int[] x = {6};
        int[] y = {8};
        player.addShip(new Ship(1, x, y), x, y);
        int[] i = {8};
        int[] j = {6};
        player.addShip(new Ship(1, i, j), i, j);
        int[] k = {8};
        int[] h = {8};
        player.addShip(new Ship(1, k, h), k, h);
        int[] n = {7};
        int[] m = {7};;
        assertEquals(false, player.addShip(new Ship(1, n, m), n, m));
    }
}
