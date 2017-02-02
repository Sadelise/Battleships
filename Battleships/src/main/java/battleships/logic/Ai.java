package battleships.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ai extends Player {

    private int[][] enemyMap;
    private Boolean shipWasHit;
    private List<Integer> destroyedX;
    private List<Integer> destroyedY;
    private List<Integer> acceptableX;
    private List<Integer> acceptableY;
    private Random guesser;

    public Ai() {
        guesser = new Random();
        ShipBuilder sb = new ShipBuilder(this, guesser);
        sb.buildShips(6, 5);
        enemyMap = new int[10][10];
        shipWasHit = false;
        destroyedX = new ArrayList<>();
        destroyedY = new ArrayList<>();
    }

    public int[] getShootingCoordinates() {
        acceptableX = new ArrayList();
        acceptableY = new ArrayList();
        int[] coordinates = {-1, -1};
        acceptableCoordinates();
        if (acceptableX.size() > 0) {
            int randomIndex = guesser.nextInt(acceptableX.size());
            coordinates[0] = acceptableX.get(randomIndex);
            coordinates[1] = acceptableY.get(randomIndex);
        }
        return coordinates;
    }

    public void feedback(Boolean hit, Boolean sank, int x, int y) {
        if (hit == true) {
            enemyMap[x][y] = 1;
            destroyedX.add(x);
            destroyedY.add(y);
            shipWasHit = true;
        } else {
            enemyMap[x][y] = -1;
        }
        if (sank == true) {
            negateSurroundingAreaAfterSinking();
            shipWasHit = false;
        }
    }

    public int[][] getEnemyMap() {
        return enemyMap;
    }

    private void acceptableCoordinates() {
        if (shipWasHit) {
            for (int i = 0; i < destroyedX.size(); i++) {
                int x = destroyedX.get(i);
                int y = destroyedY.get(i);
                acceptOnlySurroundings(x, y);
            }
        } else {
            acceptAllFreeCoordinates();

        }
    }

    private void acceptOnlySurroundings(int x, int y) {
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if ((i == y || x == j)
                        && i >= 0 && j >= 0 && i < enemyMap.length && j < enemyMap.length
                        && enemyMap[j][i] == 0) {
                    acceptableX.add(j);
                    acceptableY.add(i);
                }
            }
        }
    }

    private void acceptAllFreeCoordinates() {
        for (int y = 0; y < enemyMap.length; y++) {
            for (int x = 0; x < enemyMap.length; x++) {
                if (enemyMap[x][y] == 0) {
                    acceptableX.add(x);
                    acceptableY.add(y);
                }
            }
        }
    }

    private void negateSurroundingAreaAfterSinking() {
        for (int i = 0; i < destroyedX.size(); i++) {
            for (int y = destroyedY.get(i) - 1; y <= destroyedY.get(i) + 1; y++) {
                for (int x = destroyedX.get(i) - 1; x <= destroyedX.get(i) + 1; x++) {
                    if (x >= 0 && y >= 0 && x < enemyMap.length && y < enemyMap.length
                            && enemyMap[x][y] == 0) {
                        enemyMap[x][y] = -1;
                    }
                }
            }
        }
        destroyedX.clear();
        destroyedY.clear();
    }
}
