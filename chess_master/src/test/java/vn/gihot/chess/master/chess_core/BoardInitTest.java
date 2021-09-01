package vn.gihot.chess.master.chess_core;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.BoardHelper;
import vn.gihot.chess.master.model.game.board.ClassicBoard;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.game.piece.Pawn;
import vn.gihot.chess.master.model.game.piece.Piece;
import vn.gihot.chess.master.model.move.MoveInfo;

public class BoardInitTest {
    ClassicBoard board = new ClassicBoard();

    @BeforeEach
    void setUp() {
        board.initBoard();

    }
    @AfterEach
    void printBoard() {
        board.printBoard(true);
    }

    @Test
    @DisplayName("Kings are properly placed")
    void testWhiteKingPosition() {
        assertTrue(board.getWhiteKing().getPosition().equals(new Position(5, 1))
                && board.getBlackKing().getPosition().equals(new Position(5, 8))
        );
    }

    @Test
    @DisplayName("Test pawn move ment")
    void testPawnMoveMent() {
        MoveInfo move = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "B2",
                "B4", "false", "false", "null", "false");
        board.processMove(move);
        try {
            Position expectedPawnPosAfterMove = BoardHelper.getPositionFromCharNum("B4");
            assertTrue(board.getPieceAtPos(expectedPawnPosAfterMove) instanceof Pawn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
