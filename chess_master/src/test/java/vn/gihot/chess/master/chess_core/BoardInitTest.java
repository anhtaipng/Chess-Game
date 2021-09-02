package vn.gihot.chess.master.chess_core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import vn.gihot.chess.master.model.exception.InvalidPositionFormatException;
import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.BoardHelper;
import vn.gihot.chess.master.model.game.board.ClassicBoard;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.game.piece.*;
import vn.gihot.chess.master.model.move.MoveInfo;

import static org.junit.jupiter.api.Assertions.*;

public class BoardInitTest {
    ClassicBoard board = new ClassicBoard();

    @BeforeEach
    void setUp() {
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
    @DisplayName("Check for connection: Horizontal line ")
    void testConnection() {
        board.clearBoard();
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(4, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(5, 5));
        assertFalse(board.connectedByHorizontalLine(BoardHelper.getPosFromCoord(4, 6),
                BoardHelper.getPosFromCoord(5, 5)));
    }

    @Test
    @DisplayName("Check for connection: Horizontal line 2 ")
    void testConnection00() {
        board.clearBoard();
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(4, 6));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(5, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 6));
        assertTrue(board.connectedByHorizontalLine(BoardHelper.getPosFromCoord(4, 6),
                BoardHelper.getPosFromCoord(2, 6)));
    }

    @Test
    @DisplayName("Check for connection: Horizontal line 2 ")
    void testConnection01() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(7, 1));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(5, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 1));
        assertTrue(board.connectedByHorizontalLine(BoardHelper.getPosFromCoord(7, 1),
                BoardHelper.getPosFromCoord(3, 1)));
    }

    @Test
    @DisplayName("Check for connection: Vertical line ")
    void testConnection1() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(7, 1));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(5, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 1));
        assertFalse(board.connectedByVerticalLine(BoardHelper.getPosFromCoord(7, 1),
                BoardHelper.getPosFromCoord(3, 1)));
    }

    @Test
    @DisplayName("Check for connection: Vertical line 2")
    void testConnectoin2() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(7, 1));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(5, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(7, 3));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(7, 6));
        assertFalse(board.connectedByVerticalLine(BoardHelper.getPosFromCoord(7, 1),
                BoardHelper.getPosFromCoord(7, 6)));
    }

    @Test
    @DisplayName("Check for connection: Vertical line 2")
    void testConnectoin12() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(7, 1));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(5, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(7, 6));
        assertTrue(board.connectedByVerticalLine(BoardHelper.getPosFromCoord(7, 1),
                BoardHelper.getPosFromCoord(7, 6)));
    }

    @Test
    @DisplayName("Check for connection: Vertical line 2")
    void testConnectoin11() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        assertTrue(board.connectedByVerticalLine(BoardHelper.getPosFromCoord(2, 6),
                BoardHelper.getPosFromCoord(2, 1)));
    }

    @Test
    @DisplayName("Check for connection: Vertical line 3")
    void testConnectoin20() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        assertTrue(board.connectedByVerticalLine(BoardHelper.getPosFromCoord(2, 6),
                BoardHelper.getPosFromCoord(2, 1)));
    }

    @Test
    @DisplayName("Check for connection: Fist Diagonal 1")
    void testConnectoin21() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(8, 1));
        assertTrue(board.connectedByFirstDiagonal(BoardHelper.getPosFromCoord(8, 1),
                BoardHelper.getPosFromCoord(1, 8)));
    }

    @Test
    @DisplayName("Check for connection: Fist Diagonal 2")
    void testConnectoin22() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(2, 5));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(6, 1));
        assertTrue(board.connectedByFirstDiagonal(BoardHelper.getPosFromCoord(2, 5),
                BoardHelper.getPosFromCoord(6, 1)));
    }

    @Test
    @DisplayName("Check for connection: Fist Diagonal 3")
    void testConnectoin23() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        assertFalse(board.connectedByFirstDiagonal(BoardHelper.getPosFromCoord(2, 6),
                BoardHelper.getPosFromCoord(2, 1)));
    }

    @Test
    @DisplayName("Check for connection: Fist Diagonal 4")
    void testConnectoin24() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        assertFalse(board.connectedByFirstDiagonal(BoardHelper.getPosFromCoord(8, 8),
                BoardHelper.getPosFromCoord(1, 8)));
    }

    @Test
    @DisplayName("Check for connection: Fist Diagonal 5")
    void testConnectoin25() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 3));
        assertFalse(board.connectedByFirstDiagonal(BoardHelper.getPosFromCoord(2, 2),
                BoardHelper.getPosFromCoord(3, 3)));
    }

    @Test
    @DisplayName("Check for connection: Second Diagonal 1")
    void testConnectoin331() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 3));
        assertTrue(board.connectedBySecondDiagonal(BoardHelper.getPosFromCoord(2, 2),
                BoardHelper.getPosFromCoord(3, 3)));
    }

    @Test
    @DisplayName("Check for connection: Second Diagonal 1")
    void testConnectoin33() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 5));
        assertFalse(board.connectedBySecondDiagonal(BoardHelper.getPosFromCoord(2, 6),
                BoardHelper.getPosFromCoord(4, 4)));
    }

    @Test
    @DisplayName("Check for connection: Second Diagonal 2")
    void testConnectoin30x() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(1, 2));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(4, 4));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 5));
        assertFalse(board.connectedBySecondDiagonal(BoardHelper.getPosFromCoord(2, 6),
                BoardHelper.getPosFromCoord(4, 4)));
    }

    @Test
    @DisplayName("Check for connection: Second Diagonal 3")
    void testConnectoin301() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(8, 2));
        assertFalse(board.connectedBySecondDiagonal(BoardHelper.getPosFromCoord(2, 8),
                BoardHelper.getPosFromCoord(8, 2)));
    }

    @Test
    @DisplayName("Check for connection: Second Diagonal 1")
    void testConnectoin31() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(8, 8));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(3, 4));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(4, 5));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 3));
        assertFalse(board.connectedBySecondDiagonal(BoardHelper.getPosFromCoord(4, 5),
                BoardHelper.getPosFromCoord(1, 8)));
    }

    @Test
    @DisplayName("Check for connection: Second Diagonal 1")
    void testConnectoin32() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(3, 4));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 3));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(5, 2));
        assertFalse(board.connectedBySecondDiagonal(BoardHelper.getPosFromCoord(5, 2),
                BoardHelper.getPosFromCoord(3, 4)));
    }

    @Test
    @DisplayName("Check for connection: Knight Move")
    void testConnectoin40() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(3, 4));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 3));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(5, 2));
        assertTrue(board.connectedByOneKnightMove(BoardHelper.getPosFromCoord(2, 6),
                BoardHelper.getPosFromCoord(3, 4)));
    }

    @Test
    @DisplayName("Check for connection: Knight Move")
    void testConnectoin401() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(3, 4));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 3));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(5, 2));
        assertFalse(board.connectedByOneKnightMove(BoardHelper.getPosFromCoord(2, 6),
                BoardHelper.getPosFromCoord(3, 5)));
    }

    @Test
    @DisplayName("Check for connection: Knight Move")
    void testConnectoin40x() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(5, 4));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(6, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 3));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(5, 1));
        assertTrue(board.connectedByOneKnightMove(BoardHelper.getPosFromCoord(6, 2),
                BoardHelper.getPosFromCoord(5, 4)));
    }

    @Test
    @DisplayName("Check for connection: Knight Move")
    void testConnectoin41() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(3, 4));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 5));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(3, 3));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(5, 2));
        assertTrue(board.connectedByOneKnightMove(BoardHelper.getPosFromCoord(2, 5),
                BoardHelper.getPosFromCoord(3, 3)));
    }

    @Test
    @DisplayName("Check for connection: Knight Move")
    void testConnectoin42() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(3, 4));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 2));
        board.addPiece(Piece.KNIGHT, Type.BLACK, BoardHelper.getPosFromCoord(5, 5));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(5, 2));
        assertTrue(board.connectedByOneKnightMove(BoardHelper.getPosFromCoord(5, 5),
                BoardHelper.getPosFromCoord(3, 4)));
    }

    @Test
    @DisplayName("Check for connection: Knight Move")
    void testConnectoin43() {
        board.clearBoard();
        board.addPiece(Piece.QUEEN, Type.WHITE, BoardHelper.getPosFromCoord(3, 4));
        board.addPiece(Piece.ROOK, Type.WHITE, BoardHelper.getPosFromCoord(2, 6));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(1, 8));
        board.addPiece(Piece.KNIGHT, Type.BLACK, BoardHelper.getPosFromCoord(1, 1));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(2, 3));
        board.addPiece(Piece.PAWN, Type.BLACK, BoardHelper.getPosFromCoord(5, 2));
        assertTrue(board.connectedByOneKnightMove(BoardHelper.getPosFromCoord(1, 1),
                BoardHelper.getPosFromCoord(2, 3)));
    }

    // Pawn testing
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

    @Test
    @DisplayName("Test pawn move ment")
    void testPawnMoveMent0() {
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

    @Test
    @DisplayName("Test pawn move ment")
    void testPawnMoveMent01() {
        MoveInfo move0 = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "B2",
                "B4", "false", "false", "null", "false");
        board.processMove(move0);
        MoveInfo move1 = new MoveInfo("2", MoveInfo.BLACK_PLAYER, Piece.PAWN, "B7",
                "B5", "false", "false", "null", "false");
        board.processMove(move1);
        try {
            Position expectedPawnPosAfterMove = BoardHelper.getPositionFromCharNum("B5");
            Piece p = board.getPieceAtPos(expectedPawnPosAfterMove);
            assertEquals(expectedPawnPosAfterMove, p.getPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test pawn capture left")
    void testPawnMoveMent001() {
        MoveInfo move = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E2",
                "E4", "false", "false", "null", "false");
        board.processMove(move);
        MoveInfo move0 = new MoveInfo("2", MoveInfo.BLACK_PLAYER, Piece.PAWN, "D7",
                "D5", "false", "false", "null", "false");
        board.processMove(move0);
        MoveInfo move1 = new MoveInfo("3", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E4",
                "D5", "false", "false", "null", "false");
        board.processMove(move1);
        try {
            Position expectedPawnPosAfterMove = BoardHelper.getPositionFromCharNum("B4");
            Position capturedSpot = BoardHelper.getPositionFromCharNum("D5");
            Piece piece = board.getPieceAtPos(capturedSpot);
            assertTrue(piece instanceof Pawn && piece.getType() == Type.WHITE &&
                    (board.getAlivePieceListFromType(Type.BLACK).size() == 15));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test pawn capture right")
    void testPawnMoveMent002() {
        MoveInfo move = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E2",
                "E4", "false", "false", "null", "false");
        board.processMove(move);
        MoveInfo move0 = new MoveInfo("2", MoveInfo.BLACK_PLAYER, Piece.PAWN, "F7",
                "F5", "false", "false", "null", "false");
        board.processMove(move0);
        MoveInfo move1 = new MoveInfo("3", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E4",
                "F5", "false", "false", "null", "false");
        board.processMove(move1);
        try {
            Position capturedSpot = BoardHelper.getPositionFromCharNum("F5");
            Piece piece = board.getPieceAtPos(capturedSpot);
            assertTrue(piece instanceof Pawn && piece.getType() == Type.WHITE &&
                    (board.getAlivePieceListFromType(Type.BLACK).size() == 15) &&
                    (board.getAlivePieceListFromType(Type.WHITE).size() == 16 &&
                            ((Pawn) piece).canEnPassant()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test pawn enpassant condition")
    void testPawnMoveMent000() {
        MoveInfo move = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E2",
                "E4", "false", "false", "null", "false");
        board.processMove(move);
        MoveInfo move0 = new MoveInfo("2", MoveInfo.BLACK_PLAYER, Piece.PAWN, "F7",
                "F5", "false", "false", "null", "false");
        board.processMove(move0);
        MoveInfo move1 = new MoveInfo("3", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E4",
                "F5", "false", "false", "null", "false");
        board.processMove(move1);
        MoveInfo move2 = new MoveInfo("4", MoveInfo.BLACK_PLAYER, Piece.PAWN, "A7",
                "A5", "false", "false", "null", "false");
        board.processMove(move2);
        try {
            Pawn piece = (Pawn) board.getPieceAtPos(BoardHelper.getPositionFromCharNum("A5"));
            assertTrue(piece.canBeEnpassanted());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test pawn enpassant condition")
    void testPawnMoveMent00a() {
        MoveInfo move = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E2",
                "E4", "false", "false", "null", "false");
        board.processMove(move);
        MoveInfo move0 = new MoveInfo("2", MoveInfo.BLACK_PLAYER, Piece.PAWN, "F7",
                "F5", "false", "false", "null", "false");
        board.processMove(move0);
        MoveInfo move1 = new MoveInfo("3", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E4",
                "F5", "false", "false", "null", "false");
        board.processMove(move1);
        MoveInfo move2 = new MoveInfo("4", MoveInfo.BLACK_PLAYER, Piece.PAWN, "A7",
                "A5", "false", "false", "null", "false");
        board.processMove(move2);
        MoveInfo move3 = new MoveInfo("5", MoveInfo.WHITE_PLAYER, Piece.PAWN, "G2",
                "G4", "false", "false", "null", "false");
        board.processMove(move3);
        try {
            Pawn piece = (Pawn) board.getPieceAtPos(BoardHelper.getPositionFromCharNum("A5"));
            assertFalse(piece.canBeEnpassanted());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test pawn enpassant to left")
    void testPawnMoveMent00b() {
        MoveInfo move = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E2",
                "E4", "false", "false", "null", "false");
        board.processMove(move);
        MoveInfo move0 = new MoveInfo("2", MoveInfo.BLACK_PLAYER, Piece.PAWN, "F7",
                "F5", "false", "false", "null", "false");
        board.processMove(move0);
        MoveInfo move1 = new MoveInfo("3", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E4",
                "E5", "false", "false", "null", "false");
        board.processMove(move1);
        MoveInfo move2 = new MoveInfo("4", MoveInfo.BLACK_PLAYER, Piece.PAWN, "D7",
                "D5", "false", "false", "null", "false");
        board.processMove(move2);
        MoveInfo move3 = new MoveInfo("5", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E5",
                "D6", "false", "false", "null", "true");
        board.processMove(move3);
        try {
            Pawn piece = (Pawn) board.getPieceAtPos(BoardHelper.getPositionFromCharNum("D6"));
            assertFalse(piece.canBeEnpassanted() || piece.canEnPassant());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("Test pawn enpassant to right")
    void testPawnMoveMent00c() {
        MoveInfo move = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E2",
                "E4", "false", "false", "null", "false");
        board.processMove(move);
        MoveInfo move0 = new MoveInfo("2", MoveInfo.BLACK_PLAYER, Piece.PAWN, "D7",
                "D5", "false", "false", "null", "false");
        board.processMove(move0);
        MoveInfo move1 = new MoveInfo("3", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E4",
                "E5", "false", "false", "null", "false");
        board.processMove(move1);
        MoveInfo move2 = new MoveInfo("4", MoveInfo.BLACK_PLAYER, Piece.PAWN, "F7",
                "F5", "false", "false", "null", "false");
        board.processMove(move2);
        MoveInfo move3 = new MoveInfo("5", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E5",
                "F6", "false", "false", "null", "true");
        board.processMove(move3);
        try {
            Pawn piece = (Pawn) board.getPieceAtPos(BoardHelper.getPositionFromCharNum("F6"));
            assertTrue(piece.getType() == Type.WHITE &&
                    board.getAlivePieceListFromType(Type.BLACK).size() == 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test pawn capture and promotion")
    void testPawnMoveMent00ac() {
        Piece capturedRook = board.getPieceAtPos(BoardHelper.getPosFromCoord(8,8));
        MoveInfo move = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E2",
                "E4", "false", "false", "null", "false");
        board.processMove(move);
        MoveInfo move0 = new MoveInfo("2", MoveInfo.BLACK_PLAYER, Piece.PAWN, "D7",
                "D5", "false", "false", "null", "false");
        board.processMove(move0);
        MoveInfo move1 = new MoveInfo("3", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E4",
                "E5", "false", "false", "null", "false");
        board.processMove(move1);
        Pawn pawn = null;
        try {
            pawn = (Pawn) board.getPieceAtPos(BoardHelper.getPositionFromCharNum("E5"));
        } catch (InvalidPositionFormatException e) {
            e.printStackTrace();
        }
        assert pawn != null;
        System.out.println("Testing Pawn: " + pawn);
        MoveInfo move2 = new MoveInfo("4", MoveInfo.BLACK_PLAYER, Piece.PAWN, "F7",
                "F5", "false", "false", "null", "false");
        board.processMove(move2);
        System.out.println("Testing Pawn: " + pawn);
        MoveInfo move3 = new MoveInfo("5", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E5",
                "F6", "false", "false", "null", "true");
        board.processMove(move3);
        System.out.println("Testing Pawn: " + pawn);
        MoveInfo move4 = new MoveInfo("6", MoveInfo.BLACK_PLAYER, Piece.PAWN, "A7",
                "A5", "false", "false", "null", "false");
        board.processMove(move4);
        System.out.println("Testing Pawn: " + pawn);
        MoveInfo move5 = new MoveInfo("7", MoveInfo.WHITE_PLAYER, Piece.PAWN, "F6",
                "G7", "false", "false", "null", "false");
        board.processMove(move5);
        MoveInfo move6 = new MoveInfo("8", MoveInfo.BLACK_PLAYER, Piece.PAWN, "H7",
                "H5", "false", "false", "null", "false");
        board.processMove(move6);
        MoveInfo move7 = new MoveInfo("9", MoveInfo.WHITE_PLAYER, Piece.PAWN, "G7",
                "H8", "false", "true", Piece.QUEEN, "false");
        board.processMove(move7);
        try {
            Piece piece = board.getPieceAtPos(BoardHelper.getPositionFromCharNum("H8"));
            assertTrue(piece.getType() == Type.WHITE && piece instanceof Queen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
