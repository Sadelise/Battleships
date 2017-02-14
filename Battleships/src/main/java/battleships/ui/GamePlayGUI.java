package battleships.ui;

import battleships.logic.Battleships;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;

public class GamePlayGUI implements Runnable {

    private JFrame frame;
    private Battleships game;
    private MainGUI main;
    private JButton[][] player1ButtonMap;
    private JButton[][] player2ButtonMap;
    private int mode;

    public GamePlayGUI(MainGUI main, Battleships game, int mode) {
        this.main = main;
        this.game = game;
        this.player1ButtonMap = new JButton[game.getPlayer1().getLocations().length][game.getPlayer1().getLocations().length];
        this.player2ButtonMap = new JButton[game.getPlayer2().getLocations().length][game.getPlayer2().getLocations().length];
        this.mode = mode;
    }

    @Override
    public void run() {
        frame = new JFrame("Battleships");
        frame.setPreferredSize(new Dimension(1000, 500));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        start(frame.getContentPane());
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel start(Container container) {
        JPanel panel = new JPanel(new MigLayout(
                "",
                "[]20[]",
                "[]20[]"));
        container.add(panel);
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
        panel.add(player1Board);
        panel.add(player2Board, "wrap");
        panel.add(winner, "span, center, wrap");
        panel.add(newGame, "center");
        panel.add(quit, "center, wrap");

        GamePlayListener listener = new GamePlayListener(main, game, player1ButtonMap, player2ButtonMap, player1, player2, winner, newGame, quit, mode);
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
