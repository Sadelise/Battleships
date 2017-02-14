package battleships.battleships;

import battleships.logic.Battleships;
import battleships.ui.MainGUI;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        Battleships bs = new Battleships(1, 6);
        MainGUI gui = new MainGUI(bs, 1);
        SwingUtilities.invokeLater(gui);
    }

}
