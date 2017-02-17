
package battleships.ui;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public abstract class GUI implements Runnable {

    private JFrame frame;

    @Override
    public void run() {
        frame = new JFrame("Battleships");
        frame.setPreferredSize(new Dimension(1000, 500));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        start(frame.getContentPane());
    }

    public abstract JPanel start(Container container);

    public JFrame getFrame() {
        return frame;
    }
}
