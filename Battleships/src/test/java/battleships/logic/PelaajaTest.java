package battleships.logic;

import battleships.logic.Pelaaja;
import battleships.logic.Laiva;
import battleships.logic.Henkilo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PelaajaTest {

    private Pelaaja pelaaja;
    private Laiva laiva;

    public PelaajaTest() {
        laiva = new Laiva(5);
        pelaaja = new Henkilo();
        int[] x = {4, 5, 6, 7, 8};
        int[] y = {3, 3, 3, 3, 3};
        pelaaja.addShip(laiva, x, y);
        Laiva paatti = new Laiva(1);
        int[] k = {6};
        int[] h = {6};
        pelaaja.addShip(paatti, k, h);
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
        Laiva botski = new Laiva(4);
        int[] x = {8, 8, 8, 8};
        int[] y = {6, 7, 8, 9};
        assertEquals(true, pelaaja.addShip(botski, x, y));

    }

    @Test
    public void tyhjanLaivanAsettaminenEiOnnistu() {
        Laiva botski = new Laiva(0);
        int[] x = {};
        int[] y = {};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

    @Test
    public void tyhjanLaivanAsettaminenEiOnnistuVaikkaLaivanKokoYliNolla() {
        Laiva botski = new Laiva(1);
        int[] x = {};
        int[] y = {};
        assertEquals(false, pelaaja.addShip(botski, x, y));

    }

    @Test
    public void pienemmanKuinHalutunLaivanAsettaminenEiOnnistu() {
        Laiva botski = new Laiva(2);
        int[] x = {2};
        int[] y = {8};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

    @Test
    public void suuremmanKuinHalutunLaivanAsettaminenEiOnnistu() {
        Laiva botski = new Laiva(2);
        int[] x = {7, 8, 9};
        int[] y = {9, 9, 9};
        assertEquals(false, pelaaja.addShip(botski, x, y));

    }

    @Test
    public void ohiAmpuminenEiOsu() {
        assertEquals(null, pelaaja.shoot(4, 2));
        assertEquals(null, pelaaja.shoot(3, 3));
    }

    @Test
    public void ampuminenOsuu() {
        assertEquals(laiva, pelaaja.shoot(4, 3));
    }

    @Test
    public void laivaUppoaa() {
        pelaaja.shoot(4, 3);
        pelaaja.shoot(5, 3);
        pelaaja.shoot(6, 3);
        pelaaja.shoot(7, 3);
        pelaaja.shoot(8, 3);
        assertEquals(true, laiva.didItSink());
    }

    @Test
    public void pelaajaHaviaaVastaKunViimeinenLaivaUppoaa() {
        pelaaja.shoot(4, 3);
        pelaaja.shoot(5, 3);
        pelaaja.shoot(6, 3);
        pelaaja.shoot(7, 3);
        pelaaja.shoot(8, 3);
        assertEquals(false, pelaaja.didPlayerLose());
        pelaaja.shoot(6, 6);
        assertEquals(true, pelaaja.didPlayerLose());
    }

    @Test
    public void uudenLaivanAsettaminenOnnistuuMyosKulmiin() {
        Laiva botski = new Laiva(1);
        int[] i = {9};
        int[] j = {9};
        assertEquals(true, pelaaja.addShip(botski, i, j));
        int[] i2 = {0};
        int[] j2 = {0};
        assertEquals(true, pelaaja.addShip(botski, i2, j2));
        int[] i3 = {0};
        int[] j3 = {9};
        assertEquals(true, pelaaja.addShip(botski, i3, j3));
        int[] i4 = {9};
        int[] j4 = {0};
        assertEquals(true, pelaaja.addShip(botski, i4, j4));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniYlos() {
        Laiva botski = new Laiva(1);
        int[] x = {6};
        int[] y = {7};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniAlas() {
        Laiva botski = new Laiva(1);
        int[] x = {6};
        int[] y = {5};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniOikealle() {
        Laiva botski = new Laiva(1);
        int[] x = {7};
        int[] y = {6};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniVasemmalle() {
        Laiva botski = new Laiva(1);
        int[] x = {5};
        int[] y = {6};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniYlaoikealle() {
        Laiva botski = new Laiva(1);
        int[] x = {7};
        int[] y = {7};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniYlavasemmalle() {
        Laiva botski = new Laiva(1);
        int[] x = {5};
        int[] y = {7};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniAlaoikealle() {
        Laiva botski = new Laiva(1);
        int[] x = {7};
        int[] y = {5};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

    @Test
    public void laivaaEiSaaToiseenKiinniAlavasemmalle() {
        Laiva botski = new Laiva(1);
        int[] x = {5};
        int[] y = {5};
        assertEquals(false, pelaaja.addShip(botski, x, y));
    }

}
