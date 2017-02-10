package battleships.battleships;

import battleships.ai.Ai;
import battleships.logic.Battleships;
import battleships.logic.Ship;
import battleships.ui.MainGUI;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        Battleships bs = new Battleships(1, 6);
        MainGUI gui = new MainGUI(bs);
        SwingUtilities.invokeLater(gui);
    }

}
