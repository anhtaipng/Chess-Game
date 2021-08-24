package vn.gihot.chess.master.model.game.board;

import vn.gihot.chess.master.model.exception.InvalidDestinationException;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x_pos){
        this.x = x_pos;
    }

    public int getY() {
        return y;
    }

    public void setY(int y_pos){
        this.y = y_pos;
    }
}
