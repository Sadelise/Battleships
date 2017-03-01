package battleships.ui;

import battleships.logic.Battleships;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class PlaceShipsGUI extends GUI {

    private JFrame frame;
    private final Battleships game;
    private final ControlGUI main;
    private final Map<String, JButton> buttons;
    private JButton[][] buttonMap;
    private int mode;

    public PlaceShipsGUI(ControlGUI main) {
        this.main = main;
        this.game = main.getGame();
        this.buttons = new HashMap<>();
    }

    @Override
    public JPanel getPanel() {
        JPanel panel = new JPanel(new MigLayout(
                "fill",
                "50[]20[]100",
                ""));
        panel.setBackground(Color.white);
        JLabel playerLabel = new JLabel("Player 1", SwingConstants.CENTER);
        JLabel instruction = new JLabel("", SwingConstants.CENTER);
        instruction.setText("<html><body><center><b>To place your ships:</b><br>"
                + "- click the ship you want to place<br>"
                + "- click the spot where you want your ship to start from.<br> "
                + "- change the direction your boat is facing by pressing the toggle button.</center></html></body>");
        instruction.setFont(new Font("Serif", Font.PLAIN, 12));
        JLabel error = new JLabel(" ", SwingConstants.CENTER);
        JButton start = new JButton("Start");
        buttons.put("start", start);
        panel.add(instruction, "skip, wrap, center");
        panel.add(playerLabel, "skip, wrap, center");
        panel.add(shipsPanel());
        panel.add(coordinates(), "h 350!, w 350!, center, wrap");
        panel.add(error, "height 200, width 500, center, skip, wrap");
        panel.add(start, "skip, center, wrap");

        addActionListener(start, error, playerLabel);
        return panel;
    }

    public JPanel shipsPanel() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBackground(Color.white);
        JButton boat5 = new JButton("*****");
        JButton boat4 = new JButton("****");
        JButton boat3a = new JButton("***");
        JButton boat3b = new JButton("***");
        JButton boat2 = new JButton("**");
        JButton boat1 = new JButton("*");
        JButton toggleDirection = new JButton("horizontal");
        toggleDirection.setMinimumSize(new Dimension(120, 25));
        JButton randomize = new JButton("randomize");
        JButton reset = new JButton("reset");
        buttons.put("randomize", randomize);
        buttons.put("reset", reset);
        buttons.put("boat5", boat5);
        buttons.put("boat4", boat4);
        buttons.put("boat3a", boat3a);
        buttons.put("boat3b", boat3b);
        buttons.put("boat2", boat2);
        buttons.put("boat1", boat1);
        buttons.put("toggleDirection", toggleDirection);
        panel.add(boat5, "wrap, center");
        panel.add(boat4, "wrap, center");
        panel.add(boat3a, "wrap, center");
        panel.add(boat3b, "wrap, center");
        panel.add(boat2, "wrap, center");
        panel.add(boat1, "wrap, center");
        panel.add(toggleDirection, "wrap, center");
        panel.add(randomize, "wrap, center");
        panel.add(reset, "wrap, center");
        return panel;
    }

    public JPanel coordinates() {
        JPanel panel = new JPanel(new MigLayout(
                "",
                "[]0[]",
                "[]0[]"));
        panel.setBackground(Color.white);
        buttonMap = new JButton[10][10];

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

    private void addActionListener(JButton start, JLabel error, JLabel playerLabel) {
        PlaceShipsListener listener = new PlaceShipsListener(main, buttons, buttonMap, error, playerLabel);
        start.addActionListener(listener);
        for (JButton[] shipButtons : buttonMap) {
            for (JButton button : shipButtons) {
                button.addActionListener(listener);
            }
        }
        for (JButton button : buttons.values()) {
            button.addActionListener(listener);
        }
    }
}
