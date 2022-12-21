package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player extends JPanel implements ActionListener {
    private int x;
    private int y;
    private final Maze maze;

    public Player(Maze maze) {
        this.x = 1;
        this.y = 1;
        this.maze = maze;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public int getX() { return this.x; }

    @Override
    public int getY() { return this.y; }

    public Maze getMaze() {
        return maze;
    }

    public void playerReset(){
        this.x = 1;
        this.y = 1;
    }

    public void moveToTheSide(Tile currTile){
        if(currTile.isRoad()){
            maze.getTile(this.x,this.y).setPlayerStandingOn(false);
            maze.getTile(this.x,this.y).setMouseOver(false);
            if(currTile.isWinningTile()){
                maze.winGame(this);
            }
            else {
                currTile.setPlayerStandingOn(true);
                this.x = currTile.getX();
                this.y = currTile.getY();
            }
            maze.repaint();
        }
    }

    public void moveOver(Tile curr) {
        maze.getTile(this.x,this.y).setPlayerStandingOn(false);
        if(curr.isWinningTile()){
            maze.winGame(this);
        }
        else {
            curr.setPlayerStandingOn(true);
            this.x = curr.getX();
            this.y = curr.getY();
        }
        maze.repaint();
    }
}
