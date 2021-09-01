package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.game.pattern.ChessObserver;

import java.util.HashSet;
// QUESTION: Should each piece be checked if they are delivering check ? Currently NO
public abstract class Piece implements ChessObserver {
    public static final String KING = "K";
    public static final String QUEEN = "Q";
    public static final String KNIGHT = "N";
    public static final String ROOK = "R";
    public static final String PAWN = "P";
    public static final String BISHOP = "B";

    Board board;
    protected Type type;
    protected Position position;
    public int capturedOnMove;
    public HashSet<Position> reachableDestinations;


    public Piece(Position position, Type type, Board board) {
        this.position = position;
        capturedOnMove = -1;
        this.type = type;
        this.board = board;
    }

    public Position getPosition() {
        return position;
    }

    public Type getType() {
        return type;
    }

    public abstract String getSymbol();

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract void updateReachableDestinationsFrom(Position pos);

    public abstract boolean canReachTo(Position pos);

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", position=" + position +
                '}';
    }
}
