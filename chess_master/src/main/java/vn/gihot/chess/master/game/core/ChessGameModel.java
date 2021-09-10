package vn.gihot.chess.master.game.core;

import vn.gihot.chess.master.model.exception.*;
import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.BoardFactory;
import vn.gihot.chess.master.model.game.board.GameEndType;
import vn.gihot.chess.master.model.game.piece.Piece;
import vn.gihot.chess.master.model.move.MoveInfo;
import vn.gihot.chess.master.model.player.Player;
import vn.gihot.chess.master.model.room.Room;

// Store the game board
public class ChessGameModel {
    Player player1;
    Player player2;
    Room room;
    Board board;
    // currentPlayerTurn = 0 if (player 1 - WHITE) else 1
    int currentPlayerTurn;
    public ChessGameModel(Player player1, Player player2, Room room) {
        this.player1 = player1;
        this.player2 = player2;
        this.room = room;
        this.board = BoardFactory.getInstance().createBoard(room.getGameMode());
        currentPlayerTurn = 0;
    }

    private void updatePlayerTurn(){
        currentPlayerTurn = (currentPlayerTurn + 1) % 2;
    }
    public void checkCurrentPlayerTurn(String playerID) throws InvalidUserException {
        if(playerID.equals(player1.getPlayerID()) && currentPlayerTurn!=0)
            throw new InvalidUserException("Invalid Turn for the player to mvoe:" + playerID);
        else if(playerID.equals(player2.getPlayerID()) && currentPlayerTurn!=1)
            throw new InvalidUserException("Invalid Turn for the player to mvoe:" + playerID);
    }
    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2(){
        return player2;
    }
    public Room getRoom() {
        return room;
    }
    public void checkMoveValidity(MoveInfo moveInfo) throws InvalidMoveException {
        if(moveInfo.getEnpassant() && moveInfo.getPromoting() && moveInfo.getCastling()){
            throw new InvalidMoveException("Cannot Enpass && Promote && Castle in the same turn");
        } else if (moveInfo.getCastling() && !moveInfo.get_piece_moved().equals(Piece.KING)) {
            throw new InvalidMoveException("Castling must be init by a King");
        } else if (moveInfo.getPromoting() || moveInfo.getEnpassant()) {
            if (!moveInfo.get_piece_moved().equals(Piece.PAWN)) {
                throw new InvalidMoveException("Special pawn move must be carried out by a pawn");
            }
        }
    }

    public void executeMove(MoveInfo moveInfo) throws IllegalMoveException, UnSyncedMoveTurnException, InvalidPositionFormatException {
        board.processMove(moveInfo);
        updatePlayerTurn();
    }
    public GameEndType getEndGameResult(){
        return board.getGameEndType();
    }
}
