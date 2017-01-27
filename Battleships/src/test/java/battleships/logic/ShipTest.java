package battleships.logic;

import org.junit.Test;
import static org.junit.Assert.*;

public class ShipTest {

    private Ship ship;

    public ShipTest() {
        ship = new Ship(5);
    }

    @Test
    public void uppoaaKunKaikkiAmmuttu() {
        ship.shoot();
        ship.shoot();
        ship.shoot();
        ship.shoot();
        ship.shoot();
        assertEquals(true, ship.didItSink());
    }

    @Test
    public void eiUppoaJosKaikkiaPalojaEiOleAmmuttu() {
        ship.shoot();
        assertEquals(false, ship.didItSink());
        ship.shoot();
        assertEquals(false, ship.didItSink());
        ship.shoot();
        assertEquals(false, ship.didItSink());
        ship.shoot();
        assertEquals(false, ship.didItSink());
    }

    @Test
    public void uppoaakoKunEiAmmuttu() {
        assertEquals(false, ship.didItSink());
    }

    @Test
    public void uppoaakoKunAmmuttuLiikaa() {
        ship.shoot();
        ship.shoot();
        ship.shoot();
        ship.shoot();
        ship.shoot();
        ship.shoot();
        assertEquals(true, ship.didItSink());
    }
}
