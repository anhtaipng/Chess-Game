package vn.gihot.chess.master.game;

import org.springframework.beans.factory.annotation.Autowired;
import vn.gihot.chess.master.controller.SocketSender;
import vn.gihot.chess.master.game.core.ChessGameModel;
import vn.gihot.chess.master.game.core.ChessGameView;
import vn.gihot.chess.master.model.exception.InvalidUserException;
import vn.gihot.chess.master.model.game.board.GameEndType;
import vn.gihot.chess.master.model.move.MoveInfo;
import vn.gihot.chess.master.model.player.Player;
import vn.gihot.chess.master.model.room.Room;

/*
    Contain meta-data about 1 specific game:
    + The game view
    + The game controller: This is the GameBoard Instance Class itself
    + The game model
    Waiting for Duy Thanh code database specification to signal engame and store history + Analysis
 */

public class GameInstance {
    ChessGameModel model;
    ChessGameView view;
    public GameInstance(Room room, Player player1, Player player2){
        // Initializing the view
        view = new ChessGameView(room.getId(),player1.getPlayerID(),player2.getPlayerID());
        // Initializing the model
        model = new ChessGameModel(player1, player2, room);
    }

    public ChessGameModel getModel() {
        return model;
    }

    public ChessGameView getView() {
        return view;
    }

    public void processMove(String playerID, MoveInfo move) throws InvalidUserException {
        // Check user meta data.
        if (!playerID.equals(model.getPlayer1().getPlayerID()) && !playerID.equals(model.getPlayer2().getPlayerID())) {
            System.out.println("PROCESSING MOVE:" + playerID + " with Player1 ID:" + model.getPlayer1().getPlayerID() + " And player 2 ID:" +model.getPlayer2().getPlayerID());
            throw new InvalidUserException("Malicious user tried to interfere with room:" + model.getRoom().getId());
        }
        try {
            // Check for some game metaData Error
            model.checkCurrentPlayerTurn(playerID);
            model.checkMoveValidity(move);
        } catch (Exception e) {
            // Should actually terminate the game.
            e.printStackTrace();
            view.signalErrorGame(e.getMessage());
        }
        // If all meta data seems okay. Try execute the move (May still throw logic error)
        try{
            model.executeMove(move);
            view.sendMove(playerID,move);
        }
        catch(Exception e){
            e.printStackTrace();
            view.notifyMoveError(playerID, e.getMessage());
        }
        if (model.getEndGameResult() != GameEndType.NOT_END_YET) {
            view.notifyGameEnd(model.getEndGameResult());
        }
    }
}
