package vn.gihot.chess.master.model.exception;

public class UnSyncedMoveTurnException extends Exception{
    public UnSyncedMoveTurnException(int expectedStep, int foundStep){
        super("Expecting Step: " + expectedStep + " but found step " + foundStep);
    }
}
