package battleships.logic;

public class Henkilo extends Pelaaja {

    @Override
    public Laiva shoot(int x, int y) {
        if (super.getLocations()[x][y] != null) {
            super.getLocations()[x][y].shoot();
            return super.getLocations()[x][y];
        }
        return null;
    }
}
