package battleships.battleships;

import battleships.logic.Battleships;
import battleships.ui.ControlGUI;
import battleships.ui.GUI;
import battleships.ui.SetShipsGUI;
import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        Battleships bs = new Battleships(1, 6);
        ControlGUI gui = new ControlGUI();
        SwingUtilities.invokeLater(gui);

//        gui.setGame(bs);
//        
//        SetShipsGUI ss = new SetShipsGUI(gui);
//        ss.run();
//                SwingUtilities.invokeLater(ss);
    }
    
}
