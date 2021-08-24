package vn.gihot.chess.master.model.exception;
// This exception is thrown when a player or malicious user try to move to invalid destination
public class InvalidDestinationException extends Exception {
    public InvalidDestinationException(String message) {
        super(message);
    }
}
