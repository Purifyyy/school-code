package sk.stuba.fei.uim.oop.buttons;

import sk.stuba.fei.uim.oop.Player;
import java.awt.event.ActionEvent;

public class ResetButton extends MyButton{

    public ResetButton(String label, Player p) {
        super(label,p);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.getMaze().resetMaze(player);
    }
}
