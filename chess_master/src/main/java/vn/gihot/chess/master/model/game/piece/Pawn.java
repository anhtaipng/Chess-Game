package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.Position;

public class Pawn extends Piece {
    public Pawn(Position position, Board board, Type type) {
        super(position, board, type);
    }

    // Implement possible moves for normal cases as well as for en-passant
    @Override
    public void updateReachableDestinationsFrom(Position pos) {

    }
}
