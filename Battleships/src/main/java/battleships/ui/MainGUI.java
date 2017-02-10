package battleships.ui;

import battleships.logic.Battleships;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainGUI implements Runnable {

    private JFrame frame;
    private Battleships game;
    private SetShipsGUI start;

    public MainGUI(Battleships game) {
        this.game = game;
    }

    @Override
    public void run() {
        frame = new JFrame("Battleships");
        frame.setPreferredSize(new Dimension(200, 200));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        start = new SetShipsGUI(this, game);
        switchTo("start");
    }

    public JFrame getFrame() {
        return frame;
    }

    public void switchTo(String windowName) {
        if (windowName.equals("start")) {
            start.run();
            frame = start.getFrame();
            frame.pack();
            frame.setVisible(true);
        }

    }
}
