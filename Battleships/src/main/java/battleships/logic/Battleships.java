package battleships.logic;

import battleships.ai.Ai;

public class Battleships {

    private Player inTurn;
    private Player opponent;

    public Battleships(int mode) {
        if (mode == 1) {
            inTurn = new Person();
            opponent = new Ai();
        }
    }

    public Boolean didCurrentPlayerWin() {
        return opponent.didPlayerLose();
    }

    public Ship play(int x, int y) {
        Ship ship = inTurn.shoot(x, y);
        if (ship == null) {
            changeTurn();
        }
        return ship;
    }

    private void changeTurn() {
        Player temp = inTurn;
        inTurn = opponent;
        opponent = temp;
        if (inTurn instanceof Ai) {
            aiPlay();
        }
    }

    private void aiPlay() {
        Ai ai = (Ai) inTurn;
        int x = ai.getShootingCoordinates()[0];
        int y = ai.getShootingCoordinates()[1];
        Ship ship = inTurn.shoot(x, y);
        if (ship == null) {
            ai.feedback(false, false, x, y);
            changeTurn();
        } else {
            ai.feedback(true, ship.didItSink(), x, y);
            aiPlay();
        }
    }

    public int[][] getAiShots() {
        if (opponent instanceof Ai) {
            Ai ai = (Ai) opponent;
            return ai.getEnemyMap();
        } else {
            return null;
        }
    }
}
