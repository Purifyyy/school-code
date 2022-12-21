package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager extends JPanel implements MouseMotionListener, MouseListener {
    private static final int SIZE_OF_TILE = 20;
    private final Player player;
    private Tile lastHighlighted;
    private boolean isPlayerSelected;

    MouseManager(Player player) {
        this.player = player;
        lastHighlighted = player.getMaze().getTile(1,1);
        isPlayerSelected = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(!isPlayerSelected) {
            Tile t = player.getMaze().getTile(player.getX(),player.getY());
            if((t.getX()==x/ SIZE_OF_TILE) && (t.getY()==y/ SIZE_OF_TILE)) {
                isPlayerSelected = true;
            }
        }
        else {
            Tile t = player.getMaze().getTile(x/ SIZE_OF_TILE,y/ SIZE_OF_TILE);
            if(player.getMaze().getTile(player.getX(),player.getY()).getNeighbours().contains(t)){
                player.moveOver(t);
                isPlayerSelected = false;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(isPlayerSelected) {
            int x = e.getX();
            int y = e.getY();
            Tile t = player.getMaze().getTile(player.getX(), player.getY());
            if (player.getMaze().getTile(x / SIZE_OF_TILE, y / SIZE_OF_TILE).getNeighbours().contains(t)) {
                Tile q = player.getMaze().getTile(x / SIZE_OF_TILE, y / SIZE_OF_TILE);
                if (q != lastHighlighted) {
                    lastHighlighted.setMouseOver(false);
                    q.setMouseOver(true);
                    lastHighlighted = q;
                }
            }
            else {
                lastHighlighted.setMouseOver(false);
                lastHighlighted = player.getMaze().getTile(x/ SIZE_OF_TILE,y/ SIZE_OF_TILE);
            }
            player.getMaze().repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
