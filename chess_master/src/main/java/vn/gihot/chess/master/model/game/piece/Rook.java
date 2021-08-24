package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.Position;

public class Rook extends Piece{

    public Rook(Position position, Board board, Type type) {
        super(position, board, type);
    }

    @Override
    public void updateReachableDestinationsFrom(Position pos) {

    }
}
