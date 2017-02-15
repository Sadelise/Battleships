package battleships.ui;

import battleships.logic.Battleships;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.MenuListener;
import net.miginfocom.swing.MigLayout;

public class MenuGUI extends GUI implements Runnable {

    private JFrame frame;
    private Battleships game;
    private ControlGUI main;

    public MenuGUI(ControlGUI main) {
        this.main = main;
    }

    @Override
    public void run() {
        frame = new JFrame("Battleships");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        start(frame.getContentPane());
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel start(Container container) {
        JPanel panel = new JPanel(new MigLayout(
                "fill",
                "",
                "100[]20[]0[]200"));
        container.add(panel);
        panel.setBackground(Color.white);
        JLabel welcome = new JLabel("Welcome to Battleships!");
        JButton singleGame = new JButton("Single player game");
        JButton doubleGame = new JButton("2 player game");

        panel.add(welcome, "wrap, center");
        panel.add(singleGame, "wrap, center");
        panel.add(doubleGame, "wrap, center");

        MainMenuListener listener = new MainMenuListener(main, singleGame, doubleGame);
        singleGame.addActionListener(listener);
        doubleGame.addActionListener(listener);
        return panel;
    }
}
