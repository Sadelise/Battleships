package battleships.ui;

import battleships.logic.Battleships;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class GamePlayGUI extends GUI {

    private final ControlGUI main;
    private final JButton[][] player1ButtonMap;
    private final JButton[][] player2ButtonMap;

    public GamePlayGUI(ControlGUI main) {
        this.main = main;
        Battleships game = main.getGame();
        this.player1ButtonMap = new JButton[game.getPlayer1().getLocations().length][game.getPlayer1().getLocations().length];
        this.player2ButtonMap = new JButton[game.getPlayer2().getLocations().length][game.getPlayer2().getLocations().length];
    }

    @Override
    public JPanel getPanel() {
        JPanel panel = new JPanel(new MigLayout(
                "fill",
                "[]20[]",
                "[]20[]"));
        panel.setBackground(Color.white);
        JLabel player1 = new JLabel("Player 1's turn");
        player1.setForeground(Color.red);
        JLabel player2 = new JLabel("Player 2");
        JLabel winner = new JLabel(" ");
        JPanel player1Board = board(player1ButtonMap);
        JPanel player2Board = board(player2ButtonMap);
        JButton newGame = new JButton("New Game");
        JButton quit = new JButton("Quit");
        newGame.setVisible(false);
        quit.setVisible(false);

        panel.add(player1, "center");
        panel.add(player2, "center, wrap");
        panel.add(player1Board, "h 350!, w 350!, center");
        panel.add(player2Board, "h 350!, w 350!, center, wrap");
        panel.add(winner, "span, center, wrap");
        panel.add(newGame, "center");
        panel.add(quit, "center, wrap");

        GamePlayListener listener = new GamePlayListener(main, player1ButtonMap, player2ButtonMap, player1, player2, winner, newGame, quit);
        addActionListener(listener, player1ButtonMap);
        addActionListener(listener, player2ButtonMap);
        newGame.addActionListener(listener);
        quit.addActionListener(listener);

        return panel;
    }

    public JPanel board(JButton[][] buttonMap) {
        JPanel panel = new JPanel(new MigLayout(
                "",
                "[]0[]",
                "[]0[]"));
        panel.setBackground(Color.white);

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                JButton button = new JButton();
                button.setSize(new Dimension(100, 100));
                buttonMap[x][y] = button;
                if (x == 9) {
                    panel.add(button, "height 100, width 25, wrap");
                } else {
                    panel.add(button, "height 100, width 25");
                }
            }
        }

        return panel;
    }

    private void addActionListener(GamePlayListener listener, JButton[][] buttonMap) {
        for (JButton[] buttons : buttonMap) {
            for (JButton button : buttons) {
                button.addActionListener(listener);
            }
        }
    }
}
