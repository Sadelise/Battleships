package battleships.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class ShipTest {

    private final Ship ship;

    public ShipTest() {
        int[] x = {4};
        int[] y = {3};
        ship = new Ship(5, x, y);
    }

    @Test
    public void uppoaaKunKaikkiAmmuttu() {
        ship.shot();
        ship.shot();
        ship.shot();
        ship.shot();
        ship.shot();
        assertEquals(true, ship.didItSink());
    }

    @Test
    public void eiUppoaJosKaikkiaPalojaEiOleAmmuttu() {
        ship.shot();
        assertEquals(false, ship.didItSink());
        ship.shot();
        assertEquals(false, ship.didItSink());
        ship.shot();
        assertEquals(false, ship.didItSink());
        ship.shot();
        assertEquals(false, ship.didItSink());
    }

    @Test
    public void uppoaakoKunEiAmmuttu() {
        assertEquals(false, ship.didItSink());
    }

    @Test
    public void uppoaakoKunAmmuttuLiikaa() {
        ship.shot();
        ship.shot();
        ship.shot();
        ship.shot();
        ship.shot();
        ship.shot();
        assertEquals(true, ship.didItSink());
    }
}
