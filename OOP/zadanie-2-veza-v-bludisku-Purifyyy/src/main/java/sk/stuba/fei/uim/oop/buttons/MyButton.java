package sk.stuba.fei.uim.oop.buttons;

import sk.stuba.fei.uim.oop.Player;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class MyButton extends JButton implements ActionListener {
    protected final Player player;

    MyButton(String label, Player p) {
        this.setText(label);
        this.setFocusable(false);
        addActionListener(this);
        player = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
