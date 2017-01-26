package battleships.logic;

public class Henkilo extends Pelaaja {

    @Override
    public Laiva shoot(int x, int y) {
        if (super.getSijainnit()[x][y] != null) {
            super.getSijainnit()[x][y].shoot();
            return super.getSijainnit()[x][y];
        }
        return null;
    }
}
