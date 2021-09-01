package vn.gihot.chess.master.model.game.pattern;

import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.move.MoveInfo;

public interface ChessObserver {
    public void update(Board board, MoveInfo moveInfo, boolean isRedoMove);
}
