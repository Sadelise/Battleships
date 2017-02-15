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

    private final ControlGUI main;
    private final Map<String, JButton> buttons;
    private final JButton[][] buttonMap;
    private final ShipBuilder sb;
    private int size;
    private int direction;
    private JButton pressed;
    private final JLabel error;
    private final int mode;
    private JLabel playerLabel;
    private Battleships game;

    public SetShipsListener(ControlGUI main, Map<String, JButton> buttons, JButton[][] buttonMap, JLabel error, JLabel playerLabel) {
        this.main = main;
        this.buttons = buttons;
        this.buttonMap = buttonMap;
        sb = new ShipBuilder();
        direction = 0;
        this.error = error;
        error.setForeground(Color.red);
        this.mode = main.getGame().getMode();
        this.playerLabel = playerLabel;
        this.game = main.getGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chosenShip(e);

        if (e.getSource() == buttons.get("start")) {
            if (game.getMode() == 1 || game.hasPlayerFinishedSettingShips(game.getPlayer2())) {
                main.switchTo(new GamePlayGUI(main));
            } else if (game.hasPlayerFinishedSettingShips(game.getPlayer1()) && (!playerLabel.getText().equals("Player 2"))) {
                resetButtons();
                playerLabel.setText("Player 2");
            } else {
                error.setText("You must set all the available ships.");
            }
        }

        for (int i = 0; i < buttonMap.length; i++) {
            for (int j = 0; j < buttonMap.length; j++) {
                if (e.getSource() == buttonMap[j][i]) {
                    if (pressed != null) {
                        Ship ship = sb.buildShip(j, i, direction, size);
                        if (main.getGame().newShip(ship)) {
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

    private void chosenShip(ActionEvent e) {
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

    private void resetButtons() {
        for (JButton[] cbuttons : buttonMap) {
            for (JButton b : cbuttons) {
                b.setEnabled(true);
            }
        }
        for (JButton button : buttons.values()) {
            button.setEnabled(true);
        }
    }
}
