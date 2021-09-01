package vn.gihot.chess.master.model.exception;

public class InvalidMoveException extends Exception{
    public InvalidMoveException(String mess){
        super(mess);
    }
}
