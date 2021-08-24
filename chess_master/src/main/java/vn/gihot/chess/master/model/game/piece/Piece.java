package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.Position;

import java.util.HashSet;

public abstract class Piece{
    private Type type;
    private Board board;
    private Position position;
    public boolean isCaptured;
    public HashSet<Position> reachableDestinations;


    public Piece(Position position, Board board, Type type) {
        this.position = position;
        isCaptured = false;
        this.board = board;
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract void updateReachableDestinationsFrom(Position pos);
}
