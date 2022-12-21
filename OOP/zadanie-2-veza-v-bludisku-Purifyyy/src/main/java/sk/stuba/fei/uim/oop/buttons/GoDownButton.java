package sk.stuba.fei.uim.oop.buttons;

import sk.stuba.fei.uim.oop.Player;
import java.awt.event.ActionEvent;

public class GoDownButton extends MyButton{

    public GoDownButton(String label, Player p) {
        super(label,p);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.moveToTheSide(player.getMaze().getTile(player.getX(),player.getY()+1));
    }
}
