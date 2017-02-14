package battleships.ui;

import battleships.logic.Battleships;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainGUI implements Runnable {

    private JFrame frame;
    private Battleships game;
    private SetShipsGUI start;
    private GamePlayGUI play;
    private int mode;

    public MainGUI(Battleships game, int mode) {
        this.game = game;
        this.mode = mode;

    }

    @Override
    public void run() {
        frame = new JFrame("Battleships");
        frame.setPreferredSize(new Dimension(200, 200));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        start = new SetShipsGUI(this, game);
        play = new GamePlayGUI(this, game, mode);
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
        if (windowName.equals("play")) {
            play.run();
            frame = play.getFrame();
            frame.pack();
            frame.setVisible(true);
        }
    }

    public void exit() {
        System.exit(0);
    }
}
