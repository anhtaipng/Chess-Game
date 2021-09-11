package vn.gihot.chess.master.chess_core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vn.gihot.chess.master.model.exception.InvalidPositionFormatException;
import vn.gihot.chess.master.model.game.board.BoardHelper;
import vn.gihot.chess.master.model.game.board.ClassicBoard;
import vn.gihot.chess.master.model.game.board.GameEndType;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.game.piece.Piece;
import vn.gihot.chess.master.model.game.piece.Type;
import vn.gihot.chess.master.model.move.MoveInfo;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckEndGameTest {
    ClassicBoard board = new ClassicBoard();

    @AfterEach
    void printBoard() {
        board.printBoard(true);
    }


    @Test
    @DisplayName("Test End Game 1")
    void test1(){
        board.clearBoard();
        Position kingPos1 = BoardHelper.getPosFromCoord(2, 3);
        board.addPiece(Piece.KING, Type.WHITE, kingPos1);

        Position kingPos2 = BoardHelper.getPosFromCoord(1, 1);
        board.addPiece(Piece.KING, Type.BLACK, kingPos2);

        Position queenPos1 = BoardHelper.getPosFromCoord(3, 2);
        board.addPiece(Piece.QUEEN, Type.WHITE, queenPos1);

        MoveInfo move1 = new MoveInfo("2", MoveInfo.WHITE_PLAYER, Piece.QUEEN, "C2",
                "B2", "false", "false", "null", "false");
        board.processMove(move1);
        try {
            assertTrue(board.getGameEndType() == GameEndType.BLACK_CHECK_MATE_ENDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test End Game 2")
    void test2() {

        board.clearBoard();
        Position kingPos1 = BoardHelper.getPosFromCoord(5, 5);
        board.addPiece(Piece.KING, Type.WHITE, kingPos1);

        Position kingPos2 = BoardHelper.getPosFromCoord(2, 7);
        board.addPiece(Piece.KING, Type.BLACK, kingPos2);

        Position rookPos1 = BoardHelper.getPosFromCoord(5, 4);
        board.addPiece(Piece.ROOK, Type.WHITE, rookPos1);

        Position rookPos2 = BoardHelper.getPosFromCoord(5, 1);
        board.addPiece(Piece.ROOK, Type.BLACK, rookPos2);

        Position pawnPos1 = BoardHelper.getPosFromCoord(5, 7);
        board.addPiece(Piece.PAWN,Type.WHITE, pawnPos1);

        MoveInfo move1 = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.PAWN, "E7",
                "E8", "false", "true", Piece.QUEEN, "false");
        board.processMove(move1);

        // Don't know why IllegalMoveException in move 1
        try {
            assertTrue(board.getGameEndType() == GameEndType.NOT_END_YET);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test End Game 3")
    void test3() {

        board.clearBoard();
        Position kingPos1 = BoardHelper.getPosFromCoord(5, 8);
        board.addPiece(Piece.KING, Type.WHITE, kingPos1);

        Position kingPos2 = BoardHelper.getPosFromCoord(3, 7);
        board.addPiece(Piece.KING, Type.BLACK, kingPos2);

        Position rookPos1 = BoardHelper.getPosFromCoord(4, 2);
        board.addPiece(Piece.ROOK, Type.WHITE, rookPos1);

        Position rookPos2 = BoardHelper.getPosFromCoord(6, 1);
        board.addPiece(Piece.ROOK, Type.BLACK, rookPos2);

        Position pawnPos1 = BoardHelper.getPosFromCoord(5, 7);
        board.addPiece(Piece.PAWN,Type.WHITE, pawnPos1);

        MoveInfo move1 = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.ROOK, "D2",
                "C2", "false", "false", "null", "false");
        board.processMove(move1);

        // Don't know why IllegalMoveException move1
        try {
            assertTrue(board.getGameEndType() == GameEndType.NOT_END_YET);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test End Game 4")
    void test4() throws InvalidPositionFormatException {

        board.clearBoard();
        Position kingPos1 = BoardHelper.getPosFromCoord(4, 1);
        board.addPiece(Piece.KING, Type.WHITE, kingPos1);

        Position kingPos2 = BoardHelper.getPosFromCoord(4, 5);
        board.addPiece(Piece.KING, Type.BLACK, kingPos2);

        Position queenPos1 = BoardHelper.getPosFromCoord(8, 3);
        board.addPiece(Piece.QUEEN, Type.WHITE, queenPos1);

//        Piece piece = board.getPieceAtPos(BoardHelper.getPositionFromCharNum("E1"));
//        if(piece instanceof King){
//            System.out.println(piece);
//        }
        MoveInfo move1 = new MoveInfo("1", MoveInfo.WHITE_PLAYER, Piece.QUEEN, "H3",
                "E3", "false", "false", "null", "false");
        board.processMove(move1);

        MoveInfo move2 = new MoveInfo("2", MoveInfo.BLACK_PLAYER, Piece.KING, "D5",
                "D6", "false", "false", "null", "false");
        board.processMove(move2);
        //Don't know why IllegalMove in move2

        MoveInfo move3 = new MoveInfo("3", MoveInfo.WHITE_PLAYER, Piece.QUEEN, "E3",
                "E4", "false", "false", "null", "false");
        board.processMove(move3);

        MoveInfo move4 = new MoveInfo("4", MoveInfo.BLACK_PLAYER, Piece.KING, "D6",
                "C5", "false", "false", "null", "false");
        board.processMove(move4);

        MoveInfo move5 = new MoveInfo("5", MoveInfo.WHITE_PLAYER, Piece.QUEEN, "E4",
                "D3", "false", "false", "null", "false");
        board.processMove(move5);

        MoveInfo move6 = new MoveInfo("6", MoveInfo.BLACK_PLAYER, Piece.KING, "C5",
                "C6", "false", "false", "null", "false");
        board.processMove(move6);

        //the BlackKing still in position C5 in move6

        MoveInfo move7 = new MoveInfo("7", MoveInfo.WHITE_PLAYER, Piece.QUEEN, "D3",
                "D4", "false", "false", "null", "false");
        board.processMove(move7);

        MoveInfo move8 = new MoveInfo("8", MoveInfo.BLACK_PLAYER, Piece.KING, "C6",
                "B5", "false", "false", "null", "false");
        board.processMove(move8);

        //the Black King still in position C6 in move8

        MoveInfo move9 = new MoveInfo("9", MoveInfo.WHITE_PLAYER, Piece.QUEEN, "D4",
                "C3", "false", "false", "null", "false");
        board.processMove(move9);

        MoveInfo move10 = new MoveInfo("10", MoveInfo.BLACK_PLAYER, Piece.KING, "B5",
                "B6", "false", "false", "null", "false");
        board.processMove(move10);

        //the Black King still in position B5 in move10

        MoveInfo move11 = new MoveInfo("11", MoveInfo.WHITE_PLAYER, Piece.QUEEN, "C3",
                "C4", "false", "false", "null", "false");
        board.processMove(move11);

        MoveInfo move12 = new MoveInfo("12", MoveInfo.BLACK_PLAYER, Piece.KING, "B6",
                "A7", "false", "false", "null", "false");
        board.processMove(move12);
        //the Black King still in position B6 in move12

        MoveInfo move13 = new MoveInfo("13", MoveInfo.WHITE_PLAYER, Piece.QUEEN, "C4",
                "B5", "false", "false", "null", "false");
        board.processMove(move13);

        MoveInfo move14 = new MoveInfo("14", MoveInfo.BLACK_PLAYER, Piece.KING, "A7",
                "A8", "false", "false", "null", "false");
        board.processMove(move14);
        //the Black King still in position A7 in move14 and the King in B5 was replaced by White Queen

        MoveInfo move15 = new MoveInfo("15", MoveInfo.WHITE_PLAYER, Piece.QUEEN, "B5",
                "B6", "false", "false", "null", "false");
        board.processMove(move15);


        try {
            assertTrue(board.getGameEndType() == GameEndType.NOT_END_YET);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
