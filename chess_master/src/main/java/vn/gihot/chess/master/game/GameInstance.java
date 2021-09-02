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
    + The game controller: This is the Game Instance Class itself
    + The game model
    Waiting for Duy Thanh code database specification to signal engame and store history + Analysis
 */

public class GameInstance {
    ChessGameModel model;
    ChessGameView view;
    @Autowired
    SocketSender socketSender;
    public GameInstance(Room room, Player player1, Player player2){
        // Initializing the view
        view = new ChessGameView(room.getId(),player1.getPlayerID(),player2.getPlayerID());
        // Initializing the model
        model = new ChessGameModel(player1, player2, room);
    }

    public void processMove(String playerID, MoveInfo move) throws InvalidUserException {
        // Check user meta data.
        if (!playerID.equals(model.getPlayer1().getPlayerID()) || !playerID.equals(model.getPlayer2().getPlayerID())) {
            throw new InvalidUserException("Malicious user tried to interfere with room:" + model.getRoom().getId());
        }
        try {
            model.checkCurrentPlayerTurn(playerID);
            model.checkMoveValidity(move);
        } catch (Exception e) {
            // Should actually terminate the game.
            e.printStackTrace();
            view.signalErrorGame();
        }
        // If everything seems okay
        model.executeMove(move);
        view.sendMove(move);
        if (model.getEndGameResult() != GameEndType.NOT_END_YET) {
            view.notifyGameEnd(model.getEndGameResult());
        }
    }
}
