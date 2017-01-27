package battleships.logic;

public class Person extends Player {

    @Override
    public Ship shoot(int x, int y) {
        if (super.getLocations()[x][y] != null) {
            super.getLocations()[x][y].shoot();
            return super.getLocations()[x][y];
        }
        return null;
    }
}
