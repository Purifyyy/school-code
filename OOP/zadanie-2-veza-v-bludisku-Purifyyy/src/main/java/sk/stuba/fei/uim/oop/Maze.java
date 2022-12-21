package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Maze extends JPanel {
    private final Tile[][] maze;
    private final int dimension;
    private final Random rand;
    private final LabelCounter counter;

    Maze(int d, LabelCounter l){
        maze = new Tile[d][d];
        dimension = d;
        rand = new Random();
        counter = l;
        createMaze();
    }

    public Tile getTile(int x, int y) {
        return maze[x][y];
    }

    public void createMaze(){
        fillMaze();
        maze[1][1].setRoad(true);
        createPaths(maze[1][1]);
        addPlayer();
        addFinish();
        makeNeighboursInMaze();
    }

    public void makeNeighboursInMaze() {
        for(int i = 1;i<dimension-1;i++){
            for (int j = 1;j<dimension-1;j++){
                if(maze[i][j].isRoad()){
                    makeNeighboursForTile(maze[i][j]);
                }
            }
        }
    }

    public void makeNeighboursForTile(Tile curr) {
        for(int i = curr.getX()-1;i>0;i--){
            if(maze[i][curr.getY()].isRoad()){
                curr.addNeighbour(maze[i][curr.getY()]);
            }
            else {
                break;
            }
        }
        for(int i = curr.getY()-1;i>0;i--){
            if(maze[curr.getX()][i].isRoad()){
                curr.addNeighbour(maze[curr.getX()][i]);
            }
            else {
                break;
            }
        }
        for(int i = curr.getX()+1;i<dimension-1;i++){
            if(maze[i][curr.getY()].isRoad()){
                curr.addNeighbour(maze[i][curr.getY()]);
            }
            else {
                break;
            }
        }
        for(int i = curr.getY()+1;i<dimension-1;i++){
            if(maze[curr.getX()][i].isRoad()){
                curr.addNeighbour(maze[curr.getX()][i]);
            }
            else {
                break;
            }
        }
    }

    public void fillMaze(){
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                maze[i][j] = new Tile(i,j,false, false, false, false);
                maze[i][j].resetNeighbours();
            }
        }
    }

    public void createPaths(Tile curr){
        String[] directions = generateDirections();
        for (String direction : directions) {
            switch (direction) {
                case "left":
                    if (curr.getX() - 2 <= 0) continue;
                    if (!getTile(curr.getX()-2,curr.getY()).isRoad()) {
                        getTile(curr.getX()-1,curr.getY()).setRoad(true);
                        getTile(curr.getX()-2,curr.getY()).setRoad(true);
                        createPaths(getTile(curr.getX()-2, curr.getY()));
                    }
                    break;
                case "up":
                    if (curr.getY() - 2 <= 0) continue;
                    if (!getTile(curr.getX(),curr.getY()-2).isRoad()) {
                        getTile(curr.getX(),curr.getY()-1).setRoad(true);
                        getTile(curr.getX(),curr.getY()-2).setRoad(true);
                        createPaths(getTile(curr.getX(), curr.getY()-2));
                    }
                    break;
                case "right":
                    if (curr.getX() + 2 >= dimension - 1) continue;
                    if (!getTile(curr.getX()+2,curr.getY()).isRoad()) {
                        getTile(curr.getX()+1,curr.getY()).setRoad(true);
                        getTile(curr.getX()+2,curr.getY()).setRoad(true);
                        createPaths(getTile(curr.getX()+2, curr.getY()));
                    }
                    break;
                case "down":
                    if (curr.getY() + 2 >= dimension - 1) continue;
                    if (!getTile(curr.getX(),curr.getY()+2).isRoad()) {
                        getTile(curr.getX(),curr.getY()+1).setRoad(true);
                        getTile(curr.getX(),curr.getY()+2).setRoad(true);
                        createPaths(getTile(curr.getX(), curr.getY()+2));
                    }
                    break;
            }
        }
    }

    public String[] generateDirections() {
        ArrayList<String> dirs = new ArrayList<>() {{
            add("down");
            add("up");
            add("left");
            add("right");
        }};
        Collections.shuffle(dirs);
        return dirs.toArray(new String[4]);
    }

    public void resetMaze(Player p){
        p.playerReset();
        this.createMaze();
        counter.resetCounter();
        counter.updateCounter();
        repaint();
    }

    public void addPlayer(){
        maze[1][1].setPlayerStandingOn(true);
    }

    public void addFinish() {
        maze[dimension-2][dimension-2].setWinningTile(true);
    }

    public void winGame(Player p) {
        p.playerReset();
        this.createMaze();
        counter.incrementCounter();
        counter.updateCounter();
    }

    public void paint(Graphics g) {
        super.paint(g);
        int scale = 620/dimension;
        for(int i = 0;i<dimension;i++){
            for(int j = 0;j<dimension;j++){
                if(maze[i][j].isWinningTile()){
                    g.setColor(Color.GREEN);
                    g.fillRect(i*scale,j*scale,scale,scale);
                    if(maze[i][j].isMouseOver()){ g.setColor(Color.pink);
                        g.fillOval(i*scale,j*scale,scale,scale); }
                }
                else if(maze[i][j].isPlayerStandingOn()){
                    g.setColor(Color.red);
                    g.fillOval(i*scale,j*scale,scale,scale);
                }
                else if(maze[i][j].isMouseOver()){
                    g.setColor(Color.pink);
                    g.fillOval(i*scale,j*scale,scale,scale);
                }
                else if(!maze[i][j].isRoad()){
                    g.setColor(Color.black);
                    g.fillRect(i*scale,j*scale,scale,scale);
                }
                else if(maze[i][j].isRoad()){
                    g.setColor(Color.white);
                    g.fillRect(i*scale,j*scale,scale,scale);
                }
            }
        }
    }
}


