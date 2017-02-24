package battleships.ui;

import battleships.logic.Battleships;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ControlGUI implements Runnable {

    private JFrame frame;
    private final GUI menu;
    private GUI inUse;
    private Battleships game;

    public ControlGUI() {
        menu = new MenuGUI(this);
    }

    @Override
    public void run() {
        frame = new JFrame("Battleships");
        frame.setPreferredSize(new Dimension(800, 550));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        switchTo(menu);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void switchTo(GUI opening) {
        opening.run();
        inUse = opening;
        frame.getContentPane().removeAll();
        opening.start(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    public void exit() {
        System.exit(0);
    }

    public GUI getMenu() {
        return this.menu;
    }

    public void setGame(Battleships game) {
        this.game = game;
    }

    public Battleships getGame() {
        if (game != null) {
            return this.game;
        }
        return null;
    }
}
