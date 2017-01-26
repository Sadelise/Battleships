
package battleships.logic;

import battleships.logic.Laiva;
import org.junit.Test;
import static org.junit.Assert.*;

public class LaivaTest {

    private Laiva laiva;

    public LaivaTest() {
        laiva = new Laiva(5);
    }

    @Test
    public void uppoaaKunKaikkiAmmuttu() {
        laiva.shoot();
        laiva.shoot();
        laiva.shoot();
        laiva.shoot();
        laiva.shoot();
        assertEquals(true, laiva.didItSink());
    }

    @Test
    public void eiUppoaJosKaikkiaPalojaEiOleAmmuttu() {
        laiva.shoot();
        assertEquals(false, laiva.didItSink());
        laiva.shoot();
        assertEquals(false, laiva.didItSink());
        laiva.shoot();
        assertEquals(false, laiva.didItSink());
        laiva.shoot();
        assertEquals(false, laiva.didItSink());
    }

    @Test
    public void uppoaakoKunEiAmmuttu() {
        assertEquals(false, laiva.didItSink());
    }

    @Test
    public void uppoaakoKunAmmuttuLiikaa() {
        laiva.shoot();
        laiva.shoot();
        laiva.shoot();
        laiva.shoot();
        laiva.shoot();
        laiva.shoot();
        assertEquals(true, laiva.didItSink());
    }
}
