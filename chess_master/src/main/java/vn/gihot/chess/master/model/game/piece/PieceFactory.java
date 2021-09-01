package vn.gihot.chess.master.model.game.piece;

import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.game.piece.Piece;
import vn.gihot.chess.master.model.game.piece.Type;

public class PieceFactory {
    private static final PieceFactory instance = new PieceFactory();

    private PieceFactory() {
    }

    public static PieceFactory getInstance() {
        return instance;
    }

    public Piece createPiece(String pieceClass, Type royalty, Position pos, Board board) {
        switch (pieceClass) {
            case Piece.KNIGHT:
                return new Knight(pos, royalty, board);
            case Piece.BISHOP:
                return new Bishop(pos, royalty, board);
            case Piece.ROOK:
                return new Rook(pos, royalty, board);
            case Piece.QUEEN:
                return new Queen(pos, royalty, board);
            case Piece.KING:
                return new King(pos, royalty, board);
            case Piece.PAWN:
                return new Pawn(pos, royalty, board);
            default:
                return null;
        }
    }
}
