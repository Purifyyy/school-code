package sk.stuba.fei.uim.oop;

import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private boolean isRoad;
    private boolean isWinningTile;
    private boolean isPlayerStandingOn;
    private boolean isMouseOver;
    private final ArrayList<Tile> neighbours;

    public Tile(int x, int y, boolean isRoad, boolean isWinningTile, boolean isPlayerStandingOn, boolean isMouseOver) {
        this.x = x;
        this.y = y;
        this.isRoad = isRoad;
        this.isWinningTile = isWinningTile;
        this.isPlayerStandingOn = isPlayerStandingOn;
        this.isMouseOver = isMouseOver;
        this.neighbours = new ArrayList<>();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isRoad() {
        return isRoad;
    }

    public void setRoad(boolean road) {
        this.isRoad = road;
    }

    public boolean isWinningTile() {
        return isWinningTile;
    }

    public void setWinningTile(boolean winningTile) {
        isWinningTile = winningTile;
    }

    public boolean isPlayerStandingOn() {
        return isPlayerStandingOn;
    }

    public void setPlayerStandingOn(boolean playerStandingOn) {
        isPlayerStandingOn = playerStandingOn;
    }

    public ArrayList<Tile> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Tile t) {
        neighbours.add(t);
    }

    public void resetNeighbours() {
        neighbours.clear();
    }

    public boolean isMouseOver() { return isMouseOver; }

    public void setMouseOver(boolean mouseOver) { isMouseOver = mouseOver; }
}
