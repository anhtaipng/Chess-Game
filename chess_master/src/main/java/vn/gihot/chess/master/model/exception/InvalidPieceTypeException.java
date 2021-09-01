package vn.gihot.chess.master.model.exception;

public class InvalidPieceTypeException extends Exception{
    public InvalidPieceTypeException(String message) {
        super(message);
    }
    public InvalidPieceTypeException(String expectedPiece,String receivedPiece) {
        super("We were expecting "+ expectedPiece +" but found" + receivedPiece);
    }
}
