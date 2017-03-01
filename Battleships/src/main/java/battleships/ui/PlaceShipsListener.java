package battleships.ui;

import battleships.logic.ShipBuilder;
import battleships.logic.Battleships;
import battleships.domain.Player;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PlaceShipsListener implements ActionListener {

    private final ControlGUI main;
    private final Map<String, JButton> buttons;
    private final JButton[][] buttonMap;
    private final ShipBuilder sb;
    private int size;
    private int orientation;
    private JButton pressed;
    private final JLabel error;
    private final int mode;
    private final JLabel playerLabel;
    private final Battleships game;
    private Player placingShips;

    public PlaceShipsListener(ControlGUI main, Map<String, JButton> buttons, JButton[][] buttonMap, JLabel error, JLabel playerLabel) {
        this.main = main;
        this.buttons = buttons;
        this.buttonMap = buttonMap;
        sb = new ShipBuilder();
        orientation = 0;
        this.error = error;
        error.setForeground(Color.red);
        this.mode = main.getGame().getMode();
        this.playerLabel = playerLabel;
        this.game = main.getGame();
        this.placingShips = game.getPlayer1();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        wasShipChosen(e);
        checkGameArea(e);

        if (e.getSource() == buttons.get("start")) {
            if (game.hasPlayerFinishedPlacingShips(placingShips)) {
                if ((game.getMode() == 1 && placingShips == game.getPlayer1())
                        || (game.getMode() == 2 && placingShips == game.getPlayer2())) {
                    GUI play = new GamePlayGUI(main);
                    main.switchTo(play);
                } else if (placingShips == game.getPlayer1()) {
                    resetButtons();
                    placingShips = game.getPlayer2();
                    playerLabel.setText("Player 2");
                }
            } else if ((placingShips == game.getPlayer1()
                    && !game.hasPlayerFinishedPlacingShips(game.getPlayer1()))
                    || (placingShips == game.getPlayer2()
                    && (!game.hasPlayerFinishedPlacingShips(game.getPlayer2()))
                    && !game.getPlayer2().getShips().isEmpty())) {
                error.setText("You must place all the available ships.");
            }
        }

        if (e.getSource()
                == buttons.get("reset")) {
            resetButtons();
            placingShips.resetShips();
        }

        if (e.getSource()
                == buttons.get("randomize")) {
            error.setText(" ");
            resetButtons();
            placingShips.resetShips();
            game.newFleet(placingShips);
            showPlacements(placingShips);
        }
    }

    private void checkGameArea(ActionEvent e) {
        for (int i = 0; i < buttonMap.length; i++) {
            for (int j = 0; j < buttonMap.length; j++) {
                if (e.getSource() == buttonMap[j][i]) {
                    if (pressed != null) {
                        if (main.getGame().newShip(j, i, orientation, size, placingShips)) {
                            pressed.setEnabled(false);
                            size = 0;
                            pressed = null;
                            showPlacements(placingShips);
                            error.setText("");

                        } else {
                            error.setText("<html><center>You may have tried to place your ship too close to another ship <br>"
                                    + "or inside the wall. Please try again.</center></html>");
                        }
                    } else {
                        error.setText("You must choose the ship you want to place before trying to place it.");
                    }
                }
            }
        }
    }

    private void showPlacements(Player player) {
        for (int i = 0; i < player.getLocations().length; i++) {
            for (int j = 0; j < player.getLocations().length; j++) {
                if (player.getLocations()[j][i] != null) {
                    buttonMap[j][i].setEnabled(false);
                    buttonMap[j][i].setBackground(Color.black);
                }
            }
        }
    }

    private void wasShipChosen(ActionEvent e) {
        if (e.getSource() == buttons.get("boat5")) {
            size = 5;
            pressed = buttons.get("boat5");
        }
        if (e.getSource() == buttons.get("boat4")) {
            size = 4;
            pressed = buttons.get("boat4");
        }
        if (e.getSource() == buttons.get("boat3a")) {
            size = 3;
            pressed = buttons.get("boat3a");
        }
        if (e.getSource() == buttons.get("boat3b")) {
            size = 3;
            pressed = buttons.get("boat3b");
        }
        if (e.getSource() == buttons.get("boat2")) {
            size = 2;
            pressed = buttons.get("boat2");
        }
        if (e.getSource() == buttons.get("boat1")) {
            size = 1;
            pressed = buttons.get("boat1");
        }
        if (e.getSource() == buttons.get("toggleDirection")) {
            if (orientation == 0) {
                orientation = 1;
                buttons.get("toggleDirection").setText("vertical");
            } else {
                orientation = 0;
                buttons.get("toggleDirection").setText("horizontal");
            }
        }
    }

    private void resetButtons() {
        for (JButton[] cbuttons : buttonMap) {
            for (JButton b : cbuttons) {
                b.setEnabled(true);
                b.setBackground(new JButton().getBackground());
            }
        }
        for (JButton button : buttons.values()) {
            button.setEnabled(true);
        }
    }
}
