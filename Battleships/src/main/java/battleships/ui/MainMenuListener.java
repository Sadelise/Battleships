package battleships.ui;

import battleships.logic.Battleships;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class MainMenuListener implements ActionListener {

    private final JButton singleGame;
    private final JButton doubleGame;
    private final ControlGUI main;

    public MainMenuListener(ControlGUI main, JButton singleGame, JButton doubleGame) {
        this.main = main;
        this.doubleGame = doubleGame;
        this.singleGame = singleGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == singleGame) {
            int mode = 1;
            setupGame(mode);
        }
        if (e.getSource() == doubleGame) {
            int mode = 2;
            setupGame(mode);
        }
    }

    private void setupGame(int mode) {
        Battleships game = new Battleships(mode, 6);
        main.setGame(game);
        main.switchTo(new PlaceShipsGUI(main));
    }

}
