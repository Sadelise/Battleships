package battleships.logic;

import battleships.ai.Ai;

/**
 * Class contains the main functionality of the Battleships game.
 */
public class Battleships {

    private Player player1;
    private Player player2;
    private Player inTurn;
    private Player opponent;
    private int fleetSize;

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
        this.fleetSize = fleetSize;
        if (mode == 1) {
            player1 = new Person();
            player2 = new Ai(fleetSize);
            inTurn = player1;
            opponent = player2;
        }
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
        Ship ship;
        if (inTurn instanceof Ai) {
            Ai ai = (Ai) inTurn;
            x = ai.getShootingCoordinates()[0];
            y = ai.getShootingCoordinates()[1];
        }
        ship = opponent.shoot(x, y);
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
     * Method delivers a new Ship to player1 until player1's fleet is full, then
     * continues to deliver new Ships to player2 until their fleet is full.
     * Method will not add new ships to an AI player.
     *
     * @param ship The ship in process of being delivered
     *
     * @return True if the Ship was successfully added to the fleet of a player,
     * otherwise false
     */
    public Boolean newShip(Ship ship) {
        Player player = inTurn;
        if (inTurn.getShips().size() >= fleetSize) {
            player = opponent;
        }
        if (!(player instanceof Ai)) {
            if (player.addShip(ship)) {
                return true;
            }
        }
        return false;
    }
}
