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

    private MainGUI main;
    private Battleships game;
    private JButton[][] player1ButtonMap;
    private JButton[][] player2ButtonMap;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel winner;
    private JButton newGame;
    private JButton quit;
    private int mode;

    GamePlayListener(MainGUI main, Battleships game, JButton[][] player1ButtonMap, JButton[][] player2ButtonMap, JLabel player1Label, JLabel player2Label, JLabel winner, JButton newGame, JButton quit, int mode) {
        this.main = main;
        this.game = game;
        this.newGame = newGame;
        this.player1Label = player1Label;
        this.player2Label = player2Label;
        this.player1ButtonMap = player1ButtonMap;
        this.player2ButtonMap = player2ButtonMap;
        this.quit = quit;
        this.winner = winner;
        this.mode = mode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (game.getPlayer1() == game.getPlayerInTurn() && game.getPlayerInTurn() instanceof Person) {
            checkPlayerBoard(game.getPlayer1(), player1ButtonMap, e);
        } else if (game.getPlayer2() == game.getPlayerInTurn() && game.getPlayerInTurn() instanceof Person) {
            checkPlayerBoard(game.getPlayer2(), player2ButtonMap, e);
        }
        if (e.getSource() == newGame) {
            main.switchTo("start");
        }
        if (e.getSource() == quit) {
            main.exit();
        }
    }

    private void checkPlayerBoard(Player player, JButton[][] buttonMap, ActionEvent e) {
        for (int y = 0; y < buttonMap.length; y++) {
            for (int x = 0; x < buttonMap.length; x++) {
                if (e.getSource() == buttonMap[x][y]) {
                    Ship ship = game.play(x, y);
                    changeBoard(player.getEnemyMap(), buttonMap);
                    if (ship == null) {
                        upDateLabels();
                        if (mode == 1) {
                            playAi();
                        }
                    } else if (ship.didItSink()) {
                        if (game.didPlayerWin(player)) {
                            endGame(player);
                        }
                    }
                }
            }
        }
    }

    private void playAi() {
        while (true) {
            Ship ship = game.play(0, 0);
            changeBoard(game.getPlayer2().getEnemyMap(), player2ButtonMap);
            if (ship == null) {
                break;
            } else if (ship.didItSink()) {
                if (game.didPlayerWin(game.getPlayer2())) {
                    endGame(game.getPlayer2());
                }
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

    private void endGame(Player player) {
        if (player == game.getPlayer1()) {
            winner.setText("Player 1 won!");
        } else {
            winner.setText("Player 2 won!");
        }
        for (int i = 0; i < player1ButtonMap.length; i++) {
            for (int j = 0; j < player1ButtonMap.length; j++) {
                player1ButtonMap[j][i].setEnabled(false);
                player2ButtonMap[j][i].setEnabled(false);
            }
        }
        newGame.setVisible(true);
        quit.setVisible(true);
    }
}
