package battleships.ui;

import battleships.ai.ShipBuilder;
import battleships.logic.Battleships;
import battleships.logic.Ship;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;

public class SetShipsListener implements ActionListener {

    private Battleships game;
    private MainGUI main;
    private Map<String, JButton> buttons;
    private JButton[][] buttonMap;
    private ShipBuilder sb;
    private int size;
    private int direction;
    private JButton pressed;
    private JLabel error;

    public SetShipsListener(MainGUI main, Battleships game, Map<String, JButton> buttons, JButton[][] buttonMap, JLabel error) {
        this.main = main;
        this.game = game;
        this.buttons = buttons;
        this.buttonMap = buttonMap;
        sb = new ShipBuilder();
        direction = 0;
        this.error = error;
        error.setForeground(Color.red);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        shipPlacement(e);

        if (e.getSource() == buttons.get("start")) {
            main.switchTo("play");
        }

        for (int i = 0; i < buttonMap.length; i++) {
            for (int j = 0; j < buttonMap.length; j++) {
                if (e.getSource() == buttonMap[j][i]) {
                    if (pressed != null) {
                        Ship ship = sb.buildShip(j, i, direction, size);
                        if (game.newShip(ship)) {
                            pressed.setEnabled(false);
                            size = 0;
                            pressed = null;
                            for (int k = 0; k < ship.getSize(); k++) {
                                buttonMap[ship.getXcoordinates()[k]][ship.getYcoordinates()[k]].setEnabled(false);
                            }
                            error.setText(" ");
                        } else {
                            error.setText("You may have tried to place your ship too close to another ship "
                                    + "or inside the wall. Please try again.");
                        }
                    } else {
                        error.setText("You must choose the ship you want to place before trying to place it.");
                    }
                }
            }
        }
    }

    private void shipPlacement(ActionEvent e) {
        if (e.getSource() == buttons.get("boat5")) {
            size = 5;
            pressed = buttons.get("boat5");
            buttons.get("boat5").setEnabled(false);
        }
        if (e.getSource() == buttons.get("boat4")) {
            size = 4;
            pressed = buttons.get("boat4");
            buttons.get("boat4").setEnabled(false);
        }
        if (e.getSource() == buttons.get("boat3a")) {
            size = 3;
            pressed = buttons.get("boat3a");
            buttons.get("boat3a").setEnabled(false);
        }
        if (e.getSource() == buttons.get("boat3b")) {
            size = 3;
            pressed = buttons.get("boat3b");
            buttons.get("boat3b").setEnabled(false);
        }
        if (e.getSource() == buttons.get("boat2")) {
            size = 2;
            pressed = buttons.get("boat2");
            buttons.get("boat2").setEnabled(false);
        }
        if (e.getSource() == buttons.get("boat1")) {
            size = 1;
            pressed = buttons.get("boat1");
            buttons.get("boat1").setEnabled(false);
        }
        if (e.getSource() == buttons.get("toggleDirection")) {
            if (direction == 0) {
                direction = 1;
                buttons.get("toggleDirection").setText("vertical");
            } else {
                direction = 0;
                buttons.get("toggleDirection").setText("horizontal");
            }
        }
    }
}
