package battleships.logic;

import battleships.domain.Ship;
import battleships.domain.Player;
import battleships.domain.Person;
import battleships.ai.Ai;

/**
 * Class contains the main functionality of the Battleships game.
 */
public class Battleships {

    private final Player player1;
    private Player player2;
    private Player inTurn;
    private Player opponent;
    private final int fleetSize;
    private final int mode;
    private final ShipBuilder sb;

    /**
     * Method constructs an implementation of the class and initialises
     * variables according to parameters.
     *
     * @param mode Number that decides if the game initialises as human vs ai or
     * human vs human,
     * @param fleetSize The amount of ships that each player can have in their
     * fleet.
     */
    public Battleships(int mode, int fleetSize) {
        this.sb = new ShipBuilder();
        this.mode = mode;
        this.fleetSize = fleetSize;
        if (mode == 1) {
            player2 = new Ai();
            sb.buildAllShips(player2, fleetSize, 5);
        }
        if (mode == 2) {
            player2 = new Person();
        }
        player1 = new Person();
        inTurn = player1;
        opponent = player2;
    }

    /**
     * Method returns player1.
     *
     * @return player1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Method returns player2.
     *
     * @return player2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Method returns player whose turn it is.
     *
     * @return Player
     */
    public Player getPlayerInTurn() {
        return inTurn;
    }

    /**
     * Method returns the gamemode, 1 if human vs ai, 2 if human vs human.
     *
     * @return Player
     */
    public int getMode() {
        return this.mode;
    }

    /**
     * Method checks if the player given as parameter has won.
     *
     * @param player The player whose situation is requested
     *
     * @return True if the opponent lost, otherwise false
     */
    public Boolean didPlayerWin(Player player) {
        if (player == player1) {
            return player2.didPlayerLose();
        } else {
            return player1.didPlayerLose();
        }
    }

    /**
     * Method calls the shoot() method of the opponent to shoot at the
     * opponent's fleet. If the player in turn is AI the method will replace the
     * parameter values with coordinates returned by the AI class.
     *
     * @param x The x coordinate of the square the player wants to shoot
     * @param y The y coordinate of the square the player wants to shoot
     *
     * @return The Ship that was hit or null if no Ship was hit
     */
    public Ship play(int x, int y) {
        if (inTurn instanceof Ai) {
            Ai ai = (Ai) inTurn;
            int[] c = ai.getShootingCoordinates();
            x = c[0];
            y = c[1];
            if (x == -1 || y == -1) {
                return null;
            }
        }
        Ship ship = opponent.shoot(x, y);
        giveFeedBack(ship, x, y);
        return ship;
    }

    private void giveFeedBack(Ship ship, int x, int y) {
        if (ship == null) {
            inTurn.feedback(false, null, x, y);
            changeTurn();
        } else if (!ship.didItSink()) {
            inTurn.feedback(true, null, x, y);
        } else if (ship.didItSink()) {
            inTurn.feedback(true, ship, x, y);
        }
    }

    private void changeTurn() {
        Player temp = inTurn;
        inTurn = opponent;
        opponent = temp;
    }

    /**
     * Method creates a new Ship to player1 until player1's fleet is full, then
     * continues to deliver new Ships to player2 until their fleet is full.
     * Method will not add new ships to an AI player.
     *
     * @param x the x coordinate indicating the starting point of the ship
     * @param y the y coordinate indicating the starting point of the ship
     * @param orientation The orientation of the Ship, 0 horizontal, 1 vertical
     * @param size the size of the ship
     * @param player the player that will receive the ship
     *
     * @return True if the Ship was successfully added to the fleet of a player,
     * otherwise false
     */
    public Boolean newShip(int x, int y, int orientation, int size, Player player) {
        Ship ship = sb.buildShip(x, y, orientation, size);
        if (!(player instanceof Ai) && player.getShips().size() < fleetSize) {
            if (player.addShip(ship)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method creates a full fleet of ships to player sent as a parameter.
     *
     * @param player the player that will receive the fleet
     */
    public void newFleet(Player player) {
        sb.buildAllShips(player, fleetSize, 5);
    }

    /**
     * Method determines if player sent as parameter has a full fleet of ships.
     *
     * @param player The player in process of setting their ships
     *
     * @return True if the player has a full fleet of ships, otherwise false
     */
    public Boolean hasPlayerFinishedPlacingShips(Player player) {
        return player.getShips().size() >= fleetSize;
    }

}
