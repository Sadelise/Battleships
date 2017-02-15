package battleships.ui;

import battleships.logic.Battleships;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class SetShipsGUI extends GUI implements Runnable {

    private JFrame frame;
    private Battleships game;
    private ControlGUI main;
    private Map<String, JButton> buttons;
    private JButton[][] buttonMap;
    private int mode;

    public SetShipsGUI(ControlGUI main) {
        this.main = main;
        this.game = main.getGame();
        this.buttons = new HashMap<>();
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
        JLabel instruction = new JLabel();
        instruction.setText("<html><body><center><b>Welcome to Battleships!</b><br> To set your ships:<br>"
                + "- click the ship you want to place<br>"
                + "- click the spot where you want your ship to start from.<br> "
                + "- change the direction your boat is facing<br>by pressing the toggle button.</center></html></body>");
        instruction.setFont(new Font("Serif", Font.PLAIN, 12));
        JLabel error = new JLabel(" ");

        JButton start = new JButton("Start");
        buttons.put("start", start);

        panel.add(instruction, "skip, wrap, center");
        panel.add(ships(), "height 600");
        panel.add(coordinates(), "wrap, height 600");
        panel.add(error, "wrap, span");
        panel.add(start, "skip, center");

        addActionListener(start, error);
        return panel;
    }

    public JPanel ships() {
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

    private void addActionListener(JButton start, JLabel error) {
        SetShipsListener listener = new SetShipsListener(main, buttons, buttonMap, error);
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
