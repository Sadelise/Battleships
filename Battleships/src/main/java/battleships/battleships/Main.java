package battleships.battleships;

import battleships.logic.Battleships;
import battleships.ui.ControlGUI;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        Battleships bs = new Battleships(1, 6);
        ControlGUI gui = new ControlGUI();
        SwingUtilities.invokeLater(gui);
    }
}
