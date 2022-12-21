package sk.stuba.fei.uim.oop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardManager implements KeyListener {
    private final Player player;

    KeyboardManager(Player p){
        this.player = p;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            player.moveToTheSide(player.getMaze().getTile(player.getX(),player.getY()-1));
        }
        else if(e.getKeyCode() == KeyEvent.VK_A){
            player.moveToTheSide(player.getMaze().getTile(player.getX()-1,player.getY()));
        }
        else if(e.getKeyCode() == KeyEvent.VK_S){
            player.moveToTheSide(player.getMaze().getTile(player.getX(),player.getY()+1));
        }
        else if(e.getKeyCode() == KeyEvent.VK_D){
            player.moveToTheSide(player.getMaze().getTile(player.getX()+1,player.getY()));
        }
    }
}
