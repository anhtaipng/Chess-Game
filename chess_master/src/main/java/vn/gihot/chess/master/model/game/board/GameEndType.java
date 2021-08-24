package vn.gihot.chess.master.model.game.board;

public enum GameEndType {
    NOT_END_YET,
    STALE_MATE_ENDED,
    THREE_FOLD_REPETITION_ENDED,
    BLACK_CHECK_MATE_ENDED,
    WHITE_CHECK_MATE_ENDED
}
