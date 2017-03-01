package battleships.ui;

import battleships.logic.Battleships;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class MenuGUI extends GUI {

    private JFrame frame;
    private Battleships game;
    private final ControlGUI main;

    public MenuGUI(ControlGUI main) {
        this.main = main;
    }


    @Override
    public JPanel getPanel() {
        JPanel panel = new JPanel(new MigLayout(
                "fill",
                "",
                "100[]20[]0[]200"));
//        container.add(panel);
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
