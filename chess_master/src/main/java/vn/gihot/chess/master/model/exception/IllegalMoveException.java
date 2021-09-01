package vn.gihot.chess.master.model.exception;

import vn.gihot.chess.master.model.game.piece.Piece;

public class IllegalMoveException extends Exception {
    public IllegalMoveException(String mess){
        super(mess);
    }
    // Use this if king is left checked after move
    public IllegalMoveException(String mess, Piece p) {
        super( mess + " attacked by: " + p.toString());
    }
}
