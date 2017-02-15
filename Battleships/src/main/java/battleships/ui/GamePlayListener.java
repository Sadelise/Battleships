package battleships.ui;

import battleships.ai.Ai;
import battleships.logic.Battleships;
import battleships.logic.Person;
import battleships.logic.Player;
import battleships.logic.Ship;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

class GamePlayListener implements ActionListener {

    private final ControlGUI main;
    private final Battleships game;
    private final JButton[][] player1ButtonMap;
    private final JButton[][] player2ButtonMap;
    private final JLabel player1Label;
    private final JLabel player2Label;
    private final JLabel winner;
    private final JButton newGame;
    private final JButton quit;
    private final int mode;

    GamePlayListener(ControlGUI main, JButton[][] player1ButtonMap, JButton[][] player2ButtonMap, JLabel player1Label, JLabel player2Label, JLabel winner, JButton newGame, JButton quit) {
        this.main = main;
        this.game = main.getGame();
        this.newGame = newGame;
        this.player1Label = player1Label;
        this.player2Label = player2Label;
        this.player1ButtonMap = player1ButtonMap;
        this.player2ButtonMap = player2ButtonMap;
        this.quit = quit;
        this.winner = winner;
        this.mode = main.getGame().getMode();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (game.getPlayer1() == game.getPlayerInTurn() && game.getPlayerInTurn() instanceof Person) {
            checkPlayerBoard(game.getPlayer1(), player1ButtonMap, e);
        } else if (game.getPlayer2() == game.getPlayerInTurn() && game.getPlayerInTurn() instanceof Person) {
            checkPlayerBoard(game.getPlayer2(), player2ButtonMap, e);
        }
        if (e.getSource() == newGame) {
            main.switchTo(main.getMenu());
        }
        if (e.getSource() == quit) {
            main.exit();
        }
    }

    private void checkPlayerBoard(Player player, JButton[][] buttonMap, ActionEvent e) {
        for (int y = 0; y < buttonMap.length; y++) {
            for (int x = 0; x < buttonMap.length; x++) {
                if (e.getSource() == buttonMap[x][y]) {
                    playMove(x, y, player, buttonMap);
                }
            }
        }
    }

    private void playMove(int x, int y, Player player, JButton[][] buttonMap) {
        Ship ship = game.play(x, y);
        changeBoard(player.getEnemyMap(), buttonMap);
        if (ship == null) {
            upDateLabels();
            if (mode == 1) {
                playAi();
            }
        } else if (ship.didItSink() && game.didPlayerWin(player)) {
            endGame(player);
        }
    }

    private void playAi() {
        while (true) {
            Ship ship = game.play(0, 0);
            changeBoard(game.getPlayer2().getEnemyMap(), player2ButtonMap);
            if (ship == null) {
                break;
            } else if (ship.didItSink() && game.didPlayerWin(game.getPlayer2())) {
                endGame(game.getPlayer2());
            }
        }
        upDateLabels();
    }

    private void changeBoard(int[][] enemyMap, JButton[][] buttonMap) {
        int[][] map = enemyMap;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (map[x][y] == 0) {
                    buttonMap[x][y].setEnabled(true);
                }
                if (map[x][y] == 1) {
                    buttonMap[x][y].setBackground(Color.black);
                    buttonMap[x][y].setEnabled(false);
                }
                if (map[x][y] == -1) {
                    buttonMap[x][y].setEnabled(false);
                }
            }
        }
    }

    private void upDateLabels() {
        if (game.getPlayer1() == game.getPlayerInTurn()) {
            player1Label.setText("Player 1's turn");
            player2Label.setText("Player 2");
            player1Label.setForeground(Color.red);
            player2Label.setForeground(Color.black);
        }
        if (game.getPlayer2() == game.getPlayerInTurn()) {
            player1Label.setText("Player 1");
            player2Label.setText("Player 2's turn");
            player2Label.setForeground(Color.red);
            player1Label.setForeground(Color.black);
        }
    }

    private void endGame(Player won) {
        if (won == game.getPlayer1()) {
            winner.setText("Player 1 won!");
            showLocations(won, game.getPlayer2(), player2ButtonMap);
        } else {
            winner.setText("Player 2 won!");
            showLocations(won, game.getPlayer1(), player1ButtonMap);
        }
        newGame.setVisible(true);
        quit.setVisible(true);
    }

    private void showLocations(Player won, Player lost, JButton[][] buttonMap) {
        for (int i = 0; i < buttonMap.length; i++) {
            for (int j = 0; j < buttonMap.length; j++) {
                if (won.getLocations()[j][i] != null
                        && lost.getEnemyMap()[j][i] == 0) {
                    buttonMap[j][i].setBackground(Color.red);
                }
                player1ButtonMap[j][i].setEnabled(false);
                player2ButtonMap[j][i].setEnabled(false);
            }
        }
    }
}
